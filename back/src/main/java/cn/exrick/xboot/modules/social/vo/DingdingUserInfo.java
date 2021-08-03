package cn.exrick.xboot.modules.social.vo;

import lombok.Data;

/**
 * https://ding-doc.dingtalk.com/doc#/serverapi2/ege851/AaRQe
 * @author Exrickx
 */
@Data
public class DingdingUserInfo {

    private String errcode;

    private String errmsg;

    private String userid;

    private String unionid;

    private String name;

    private String remark;

    private String isBoss;

    private String hiredDate;

    private String isSenior;

    private String tel;

    private String workPlace;

    private String email;

    private String mobile;

    private String active;

    private String avatar;

    private String isAdmin;

    private String isHide;

    private String jobnumber;

    private String stateCode;

    private String position;
}
