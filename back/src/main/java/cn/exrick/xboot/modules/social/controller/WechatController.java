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
import cn.exrick.xboot.modules.social.vo.WechatUserInfo;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
 * https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html
 * @author Exrickx
 */
@Slf4j
@Api(tags = "微信登录接口")
@RequestMapping("/xboot/social/wechat")
@Controller
public class WechatController {

    @Value("${xboot.social.wechat.appId}")
    private String appId;

    @Value("${xboot.social.wechat.appSecret}")
    private String appSecret;

    @Value("${xboot.social.wechat.callbackUrl}")
    private String callbackUrl;

    @Value("${xboot.social.callbackFeUrl}")
    private String callbackFeUrl;

    @Value("${xboot.social.callbackFeRelateUrl}")
    private String callbackFeRelateUrl;

    private static final String STATE = SecurityConstant.WECHAT_STATE;

    private static final Integer TYPE = CommonConstant.SOCIAL_TYPE_WECHAT;

    @Autowired
    private SocialService socialService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    /**
     * 微信认证服务器地址
     */
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * 申请令牌地址
     */
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取用户信息地址
     */
    private static final String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "获取wechat认证链接")
    @ResponseBody
    public Result<Object> login() throws UnsupportedEncodingException {

        // 生成并保存state 忽略该参数有可能导致CSRF攻击
        String state = String.valueOf(System.currentTimeMillis());
        redisTemplate.set(STATE + state, "VALID", 3L, TimeUnit.MINUTES);

        // 传递参数response_type、client_id、state、redirect_uri
        String url = AUTHORIZE_URL + "?appid=" + appId + "&redirect_uri=" + URLEncoder.encode(callbackUrl, "utf-8") + "&response_type=code" +
                "&scope=snsapi_login&state=" + state;

        return ResultUtil.data(url);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    @ApiOperation(value = "获取accessToken")
    @SystemLog(description = "微信关联登录", type = LogType.LOGIN)
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

        // 传递参数grant_type、code、redirect_uri、appid、appsecret
        String accessTokenUrl = ACCESS_TOKEN_URL + "?appid=" + appId + "&secret=" + appSecret + "&code=" + code +
                "&grant_type=authorization_code";

        // 申请令牌
        String result = HttpUtil.get(accessTokenUrl);
        if (!result.contains("access_token")) {
            return "redirect:" + callbackFeUrl + "?error=" + URLEncoder.encode("获取access_token失败", "utf-8");
        }

        JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
        String accessToken = jsonObject.get("access_token").getAsString();
        String openId = jsonObject.get("openid").getAsString();
        // 获取用户信息
        String userInfo = HttpUtil.get(GET_USERINFO_URL + "?access_token=" + accessToken + "&openid=" + openId);
        WechatUserInfo wu = new Gson().fromJson(userInfo, WechatUserInfo.class);
        // 存入数据库
        Social wechat = socialService.findByOpenIdAndPlatform(wu.getOpenid(), TYPE);
        if (wechat == null) {
            Social w = new Social().setOpenId(wu.getOpenid()).setUsername(wu.getNickname()).setAvatar(wu.getHeadimgurl()).setPlatform(TYPE);
            wechat = socialService.save(w);
        }

        String url = "";
        // 判断是否绑定账号
        if (StrUtil.isNotBlank(wechat.getRelateUsername())) {
            // 已绑定 直接登录
            String JWT = securityUtil.getToken(wechat.getRelateUsername(), true);
            // 存入redis
            String JWTKey = IdUtil.simpleUUID();
            redisTemplate.set(JWTKey, JWT, 2L, TimeUnit.MINUTES);
            url = callbackFeUrl + "?related=1&JWTKey=" + JWTKey;
            // 记录日志使用
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    new SecurityUserDetails(new User().setUsername(wechat.getRelateUsername())), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 未绑定 Redis存入id
            String idToken = IdUtil.simpleUUID();
            redisTemplate.set(idToken, wechat.getId(), 5L, TimeUnit.MINUTES);
            url = callbackFeRelateUrl + "?socialType=" + TYPE + "&id=" + idToken;
        }
        return "redirect:" + url;
    }
}
