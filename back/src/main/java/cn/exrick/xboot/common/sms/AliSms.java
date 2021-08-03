package cn.exrick.xboot.common.sms;

import cn.exrick.xboot.common.constant.SettingConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.modules.base.entity.Setting;
import cn.exrick.xboot.modules.base.service.SettingService;
import cn.exrick.xboot.modules.base.vo.SmsSetting;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author exrick
 */
@Slf4j
@Component
public class AliSms implements Sms {

    @Autowired
    private SettingService settingService;

    @Override
    public SmsSetting getSmsSetting() {

        Setting setting = settingService.get(SettingConstant.ALI_SMS);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new XbootException("您还未配置阿里云短信服务");
        }
        return new Gson().fromJson(setting.getValue(), SmsSetting.class);
    }

    @Override
    public void sendSms(String mobile, String params, String templateCode) {

        SmsSetting s = getSmsSetting();

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", s.getAccessKey(), s.getSecretKey());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔，上限为1000个手机号码
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", s.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", params);

        CommonResponse response;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            log.error(e.getMessage());
            throw new XbootException("请求发送短信验证码失败，" + e.getErrMsg());
        }
        JsonObject result = JsonParser.parseString(response.getData()).getAsJsonObject();
        String code = result.get("Code").getAsString();
        String message = result.get("Message").getAsString();
        if (!"OK".equals(code) && !"OK".equals(message)) {
            throw new XbootException("请求发送验证码失败，" + message);
        }
    }
}
