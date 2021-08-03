package cn.exrick.xboot.common.sms;

import cn.exrick.xboot.modules.base.vo.SmsSetting;

/**
 * @author Exrick
 */
public interface Sms {

    /**
     * 获取配置
     * @return
     */
    SmsSetting getSmsSetting();

    /**
     * 发送短信
     * @param mobile 手机号 多个,逗号分隔 若为11位国内手机号无需加国家区号86
     *               国际号码需加上区号 [国家或地区码][手机号] 如8109012345678、86为日本、09012345678为手机号
     * @param params 参数 JSON格式，如{"code": "1234"}
     *               若启用腾讯短信会自动按顺序转换为逗号分隔的数组值如[1234]
     * @param templateCode 短信模板code/id
     */
    void sendSms(String mobile, String params, String templateCode);
}
