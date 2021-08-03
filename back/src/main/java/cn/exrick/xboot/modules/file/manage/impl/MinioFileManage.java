package cn.exrick.xboot.modules.file.manage.impl;

import cn.exrick.xboot.common.constant.SettingConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.modules.base.entity.Setting;
import cn.exrick.xboot.modules.base.service.SettingService;
import cn.exrick.xboot.modules.base.vo.OssSetting;
import cn.exrick.xboot.modules.file.manage.FileManage;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import io.minio.*;
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
public class MinioFileManage implements FileManage {

    @Autowired
    private SettingService settingService;

    @Override
    public OssSetting getOssSetting() {

        Setting setting = settingService.get(SettingConstant.MINIO_OSS);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new XbootException("您还未配置MinIO存储");
        }
        return new Gson().fromJson(setting.getValue(), OssSetting.class);
    }

    /**
     * 如果存储桶不存在 创建存储通
     * @param os
     * @param minioClient
     * @throws Exception
     */
    public void checkBucket(OssSetting os, MinioClient minioClient) throws Exception {

        // 如果存储桶不存在 创建存储通
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(os.getBucket()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(os.getBucket()).build());
            // 设置隐私权限 公开读
            StringBuilder builder = new StringBuilder();
            builder.append("{\n");
            builder.append("    \"Statement\": [\n");
            builder.append("        {\n");
            builder.append("            \"Action\": [\n");
            builder.append("                \"s3:GetBucketLocation\",\n");
            builder.append("                \"s3:ListBucket\"\n");
            builder.append("            ],\n");
            builder.append("            \"Effect\": \"Allow\",\n");
            builder.append("            \"Principal\": \"*\",\n");
            builder.append("            \"Resource\": \"arn:aws:s3:::" + os.getBucket() + "\"\n");
            builder.append("        },\n");
            builder.append("        {\n");
            builder.append("            \"Action\": \"s3:GetObject\",\n");
            builder.append("            \"Effect\": \"Allow\",\n");
            builder.append("            \"Principal\": \"*\",\n");
            builder.append("            \"Resource\": \"arn:aws:s3:::" + os.getBucket() + "/*\"\n");
            builder.append("        }\n");
            builder.append("    ],\n");
            builder.append("    \"Version\": \"2012-10-17\"\n");
            builder.append("}\n");
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(os.getBucket()).config(builder.toString()).build());
        }
    }

    @Override
    public String inputStreamUpload(InputStream inputStream, String key, MultipartFile file) {

        OssSetting os = getOssSetting();
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(os.getHttp() + os.getEndpoint())
                    .credentials(os.getAccessKey(), os.getSecretKey())
                    .build();
            checkBucket(os, minioClient);
            minioClient.putObject(PutObjectArgs.builder().bucket(os.getBucket()).object(key)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            throw new XbootException("上传出错，请检查MinIO配置");
        }
        return os.getHttp() + os.getEndpoint() + "/" + os.getBucket() + "/" + key;
    }

    @Override
    public String renameFile(String fromKey, String toKey) {

        OssSetting os = getOssSetting();
        copyFile(fromKey, toKey);
        deleteFile(fromKey);
        return os.getHttp() + os.getEndpoint() + "/" + os.getBucket() + "/" + toKey;
    }

    @Override
    public String copyFile(String fromKey, String toKey) {

        OssSetting os = getOssSetting();
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(os.getHttp() + os.getEndpoint())
                    .credentials(os.getAccessKey(), os.getSecretKey())
                    .build();
            checkBucket(os, minioClient);
            minioClient.copyObject(CopyObjectArgs.builder().bucket(os.getBucket()).object(toKey)
                    .source(CopySource.builder().bucket(os.getBucket()).object(fromKey).build()).build());
        } catch (Exception e) {
            throw new XbootException("拷贝文件出错，请检查MinIO配置");
        }
        return os.getHttp() + os.getEndpoint() + "/" + os.getBucket() + "/" + toKey;
    }

    @Override
    public void deleteFile(String key) {

        OssSetting os = getOssSetting();
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(os.getHttp() + os.getEndpoint())
                    .credentials(os.getAccessKey(), os.getSecretKey())
                    .build();
            checkBucket(os, minioClient);
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(os.getBucket()).object(key).build());
        } catch (Exception e) {
            throw new XbootException("删除文件出错，请检查MinIO配置");
        }
    }
}
