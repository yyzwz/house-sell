package cn.exrick.xboot.common.constant;

import cn.hutool.core.util.IdUtil;

/**
 * @author Exrickx
 */
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer ";

    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = IdUtil.simpleUUID();

    /**
     * token参数头
     */
    String HEADER = "accessToken";

    /**
     * appToken参数头
     */
    String APP_HEADER = "appToken";

    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    /**
     * 用户选择JWT保存时间参数头
     */
    String SAVE_LOGIN = "saveLogin";

    /**
     * github保存state前缀key
     */
    String GITHUB_STATE = "XBOOT_GITHUB:";

    /**
     * qq保存state前缀key
     */
    String QQ_STATE = "XBOOT_QQ:";

    /**
     * 微信保存state前缀key
     */
    String WECHAT_STATE = "XBOOT_WECHAT:";

    /**
     * 微博保存state前缀key
     */
    String WEIBO_STATE = "XBOOT_WEIBO:";

    /**
     * 企业微信保存state前缀key
     */
    String WORKWECHAT_STATE = "XBOOT_WORKWECHAT:";

    /**
     * 钉钉保存state前缀key
     */
    String DINGDING_STATE = "XBOOT_DINGDING:";

    /**
     * 交互token前缀key
     */
    String TOKEN_PRE = "XBOOT_TOKEN_PRE:";

    /**
     * 用户token前缀key 单点登录使用
     */
    String USER_TOKEN = "XBOOT_USER_TOKEN:";

    /**
     * 会员交互token前缀key
     */
    String TOKEN_MEMBER_PRE = "XBOOT_TOKEN_MEMBER_PRE:";

    /**
     * 会员token前缀key
     */
    String MEMBER_TOKEN = "XBOOT_MEMBER_TOKEN:";
}
