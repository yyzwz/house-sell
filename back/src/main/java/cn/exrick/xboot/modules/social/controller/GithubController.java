package cn.exrick.xboot.modules.social.controller;

import cn.exrick.xboot.common.annotation.SystemLog;
import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.common.constant.SecurityConstant;
import cn.exrick.xboot.common.enums.LogType;
import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.config.security.SecurityUserDetails;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.social.entity.Social;
import cn.exrick.xboot.modules.social.service.SocialService;
import cn.exrick.xboot.modules.social.vo.GithubUserInfo;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
 * @author Exrickx
 */
@Slf4j
@Api(tags = "Github登录接口")
@RequestMapping("/xboot/social/github")
@Controller
public class GithubController {

    @Value("${xboot.social.github.clientId}")
    private String clientId;

    @Value("${xboot.social.github.clientSecret}")
    private String clientSecret;

    @Value("${xboot.social.github.callbackUrl}")
    private String callbackUrl;

    @Value("${xboot.social.callbackFeUrl}")
    private String callbackFeUrl;

    @Value("${xboot.social.callbackFeRelateUrl}")
    private String callbackFeRelateUrl;

    private static final String STATE = SecurityConstant.GITHUB_STATE;

    private static final Integer TYPE = CommonConstant.SOCIAL_TYPE_GITHUB;

    @Autowired
    private SocialService socialService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    /**
     * Github认证服务器地址
     */
    private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize";

    /**
     * 申请令牌地址
     */
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    /**
     * 获取用户信息地址
     */
    private static final String GET_USERINFO_URL = "https://api.github.com/user?access_token=";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "获取github认证链接")
    @ResponseBody
    public Result<Object> login() {

        // 生成并保存state 忽略该参数有可能导致CSRF攻击
        String state = String.valueOf(System.currentTimeMillis());
        redisTemplate.set(STATE + state, "VALID", 3L, TimeUnit.MINUTES);

        // 传递参数response_type、client_id、state、redirect_uri
        String url = AUTHORIZE_URL + "?response_type=code&" + "client_id=" + clientId + "&state=" + state
                + "&redirect_uri=" + callbackUrl;

        return ResultUtil.data(url);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    @ApiOperation(value = "获取accessToken")
    @SystemLog(description = "Github关联登录", type = LogType.LOGIN)
    public String callback(@RequestParam(required = false) String code,
                                 @RequestParam(required = false) String state) throws UnsupportedEncodingException {

        if (StrUtil.isBlank(code)) {
            return "redirect:" + callbackFeUrl + "?error=" + URLEncoder.encode("您未同意授权", "utf-8");
        }
        // 验证state
        String v = redisTemplate.get(STATE + state);
        redisTemplate.delete(STATE + state);
        if (StrUtil.isBlank(v)) {
            return "redirect:" + callbackFeUrl + "?error=" + URLEncoder.encode("授权超时或state不正确", "utf-8");
        }

        // 传递参数grant_type、code、redirect_uri、client_id
        String params = "grant_type=authorization_code&code=" + code + "&redirect_uri=" +
                callbackUrl + "&client_id=" + clientId + "&client_secret=" + clientSecret;

        // 申请令牌 post请求
        String result = HttpUtil.post(ACCESS_TOKEN_URL, params);

        if (!result.contains("access_token=")) {
            return "redirect:" + callbackFeUrl + "?error=" + URLEncoder.encode("获取access_token失败", "utf-8");
        }

        String accessToken = StrUtil.subBetween(result, "access_token=", "&scope");
        // 获取用户信息
        String userInfo = HttpUtil.get(GET_USERINFO_URL + accessToken);
        GithubUserInfo gu = new Gson().fromJson(userInfo, GithubUserInfo.class);
        // 存入数据库
        Social github = socialService.findByOpenIdAndPlatform(gu.getId(), TYPE);
        if (github == null) {
            Social g = new Social().setOpenId(gu.getId()).setUsername(gu.getLogin()).setAvatar(gu.getAvatar_url()).setPlatform(TYPE);
            github = socialService.save(g);
        }

        String url = "";
        // 判断是否绑定账号
        if (StrUtil.isNotBlank(github.getRelateUsername())) {
            // 已绑定 直接登录
            String JWT = securityUtil.getToken(github.getRelateUsername(), true);
            // 存入redis
            String JWTKey = IdUtil.simpleUUID();
            redisTemplate.set(JWTKey, JWT, 2L, TimeUnit.MINUTES);
            url = callbackFeUrl + "?related=1&JWTKey=" + JWTKey;
            // 记录日志使用
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    new SecurityUserDetails(new User().setUsername(github.getRelateUsername())), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 未绑定 Redis存入id
            String idToken = IdUtil.simpleUUID();
            redisTemplate.set(idToken, github.getId(), 5L, TimeUnit.MINUTES);
            url = callbackFeRelateUrl + "?socialType=" + TYPE + "&id=" + idToken;
        }
        return "redirect:" + url;
    }
}
