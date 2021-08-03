package cn.exrick.xboot.modules.social.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
@Accessors(chain = true)
public class RelateUserInfo implements Serializable {

    private String githubId;

    private Boolean github = false;

    private String githubUsername;

    private String qqId;

    private Boolean qq = false;

    private String qqUsername;

    private String weiboId;

    private Boolean weibo = false;

    private String weiboUsername;

    private String wechatId;

    private Boolean wechat = false;

    private String wechatUsername;

    private String dingdingId;

    private Boolean dingding = false;

    private String dingdingUsername;

    private String workwechatId;

    private Boolean workwechat = false;

    private String workwechatUsername;
}
