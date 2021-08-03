package cn.exrick.xboot.modules.social.vo;

import lombok.Data;

/**
 * https://work.weixin.qq.com/api/doc/90000/90135/90196
 * @author Exrickx
 */
@Data
public class WokWechatUserInfo {

    private String errcode;

    private String errmsg;

    private String userid;

    private String name;

    private String mobile;

    private String gender;

    private String email;

    private String avatar;

    private String thumb_avatar;

    private String telephone;

    private String alias;

    private String address;

    private String open_userid;

    private String status;

    private String qr_code;

    private String main_department;

    private String external_position;
}
