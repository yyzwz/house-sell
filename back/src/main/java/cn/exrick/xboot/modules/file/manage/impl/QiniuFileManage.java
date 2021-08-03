package cn.exrick.xboot.modules.file.manage.impl;

import cn.exrick.xboot.common.constant.SettingConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.modules.base.entity.Setting;
import cn.exrick.xboot.modules.base.service.SettingService;
import cn.exrick.xboot.modules.base.vo.OssSetting;
import cn.exrick.xboot.modules.file.manage.FileManage;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author Exrick
 */
@Slf4j
@Component
public class QiniuFileManage implements FileManage {

    @Autowired
    private SettingService settingService;

    @Override
    public OssSetting getOssSetting() {

        Setting setting = settingService.get(SettingConstant.QINIU_OSS);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new XbootException("您还未配置七牛云对象存储");
        }
        return new Gson().fromJson(setting.getValue(), OssSetting.class);
    }

    public Configuration getConfiguration(Integer zone) {

        Configuration cfg = null;
        if (zone.equals(SettingConstant.ZONE_ZERO)) {
            cfg = new Configuration(Region.region0());
        } else if (zone.equals(SettingConstant.ZONE_ONE)) {
            cfg = new Configuration(Region.region1());
        } else if (zone.equals(SettingConstant.ZONE_TWO)) {
            cfg = new Configuration(Region.region2());
        } else if (zone.equals(SettingConstant.ZONE_THREE)) {
            cfg = new Configuration(Region.regionNa0());
        } else if (zone.equals(SettingConstant.ZONE_FOUR)) {
            cfg = new Configuration(Region.regionAs0());
        } else {
            cfg = new Configuration(Region.autoRegion());
        }
        return cfg;
    }

    public UploadManager getUploadManager(Configuration cfg) {

        UploadManager uploadManager = new UploadManager(cfg);
        return uploadManager;
    }

    @Override
    public String inputStreamUpload(InputStream inputStream, String key, MultipartFile file) {

        OssSetting os = getOssSetting();
        Auth auth = Auth.create(os.getAccessKey(), os.getSecretKey());
        String upToken = auth.uploadToken(os.getBucket());
        try {
            Response response = getUploadManager(getConfiguration(os.getZone())).put(inputStream, key, upToken, null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return os.getHttp() + os.getEndpoint() + "/" + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            throw new XbootException("上传文件出错，请检查七牛云配置，" + r.toString());
        }
    }

    @Override
    public String renameFile(String fromKey, String toKey) {

        OssSetting os = getOssSetting();
        Auth auth = Auth.create(os.getAccessKey(), os.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, getConfiguration(os.getZone()));
        try {
            bucketManager.move(os.getBucket(), fromKey, os.getBucket(), toKey);
            return os.getHttp() + os.getEndpoint() + "/" + toKey;
        } catch (QiniuException ex) {
            throw new XbootException("重命名文件失败，" + ex.response.error);
        }
    }

    @Override
    public String copyFile(String fromKey, String toKey) {

        OssSetting os = getOssSetting();
        Auth auth = Auth.create(os.getAccessKey(), os.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, getConfiguration(os.getZone()));
        try {
            bucketManager.copy(os.getBucket(), fromKey, os.getBucket(), toKey);
            return os.getHttp() + os.getEndpoint() + "/" + toKey;
        } catch (QiniuException ex) {
            throw new XbootException("复制文件失败，" + ex.response.error);
        }
    }

    @Override
    public void deleteFile(String key) {

        OssSetting os = getOssSetting();
        Auth auth = Auth.create(os.getAccessKey(), os.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, getConfiguration(os.getZone()));
        FileInfo fileInfo = null;
        try {
            fileInfo = bucketManager.stat(os.getBucket(), key);
        } catch (QiniuException ex) {
            log.error("获取文件失败，" + ex.response.error);
        }
        try {
            if (fileInfo != null) {
                bucketManager.delete(os.getBucket(), key);
            }
        } catch (QiniuException ex) {
            throw new XbootException("删除文件失败，" + ex.response.error);
        }
    }
}
