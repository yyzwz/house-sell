package cn.exrick.xboot.common.sms;

import cn.exrick.xboot.common.constant.SettingConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.modules.base.entity.Setting;
import cn.exrick.xboot.modules.base.service.SettingService;
import cn.exrick.xboot.modules.base.vo.SmsSetting;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Exrickx
 */
@Component
@Slf4j
public class SmsUtil {

    @Autowired
    private SettingService settingService;

    @Autowired
    private SmsFactory smsFactory;

    public String getSmsUsed() {

        Setting setting = settingService.get(SettingConstant.SMS_USED);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new XbootException("您还未配置短信服务");
        }
        String type = setting.getValue();
        return type;
    }

    public SmsSetting getSmsSetting() {

        Setting sms = settingService.get(getSmsUsed());
        return new Gson().fromJson(sms.getValue(), SmsSetting.class);
    }

    /**
     * 获得对应完整短信模版Key
     * @param type
     * @return
     */
    public String getTemplate(Integer type) {

        return getSmsUsed() + "_" + getTemplateSuffix(type);
    }

    /**
     * 获得对应模版Key后缀
     * @param type
     * @return
     */
    public static String getTemplateSuffix(Integer type) {

        switch (type) {
            case 0:
                return SettingConstant.SMS_COMMON;
            case 1:
                return SettingConstant.SMS_REGIST;
            case 2:
                return SettingConstant.SMS_LOGIN;
            case 3:
                return SettingConstant.SMS_CHANGE_MOBILE;
            case 4:
                return SettingConstant.SMS_CHANG_PASS;
            case 5:
                return SettingConstant.SMS_RESET_PASS;
            case 6:
                return SettingConstant.SMS_ACTIVITI;
            default:
                return SettingConstant.SMS_COMMON;
        }
    }

    public String getTemplateCode(Integer type){

        Setting setting = settingService.get(getTemplate(type));
        if (StrUtil.isBlank(setting.getValue())) {
            throw new XbootException("系统还未配置短信服务或相应短信模版");
        }
        return setting.getValue();
    }

    /**
     * 发送验证码 模版变量为 code 无需模版编号
     * @param mobile
     * @param code
     * @param type   0通用模版 1注册 2登录 3修改手机 4修改密码 5重置密码
     * @return
     * @throws ClientException
     */
    public void sendCode(String mobile, String code, Integer type) {

        sendCode(mobile, code, getTemplateCode(type));
    }

    /**
     * 发送验证码 模版变量为单个 code 需传入模版编号
     * @param mobile
     * @param code
     * @param templateCode
     * @return
     * @throws ClientException
     */
    public void sendCode(String mobile, String code, String templateCode) {

        sendSms(mobile, "{code:" + code + "}", templateCode);
    }

    /**
     * 发送工作流消息 模版变量为单个 content
     * @param mobile
     * @param content
     * @return
     * @throws ClientException
     */
    public void sendActMessage(String mobile, String content) {

        // 获取工作流消息模板
        sendSms(mobile, "{content:" + content + "}", getTemplateCode(6));
    }

    /**
     * 发送短信
     * @param mobile       手机号 多个,逗号分隔 若为11位国内手机号无需加国家区号86
     *                     国际号码需加上区号 [国家或地区码][手机号] 如8109012345678、86为日本、09012345678为手机号
     * @param params       参数 JSON格式，如{"code": "1234"}
     *                     若启用腾讯短信会自动按顺序转换为逗号分隔的数组值如[1234]
     * @param templateCode 短信模板code/id
     */
    public void sendSms(String mobile, String params, String templateCode) {

        smsFactory.getSms().sendSms(mobile, params, templateCode);
    }
}
