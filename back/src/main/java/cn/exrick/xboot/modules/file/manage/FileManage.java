package cn.exrick.xboot.modules.file.manage;

import cn.exrick.xboot.modules.base.vo.OssSetting;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author Exrick
 */
public interface FileManage {

    /**
     * 获取配置
     * @return
     */
    OssSetting getOssSetting();

    /**
     * 文件流上传
     * @param inputStream
     * @param key
     * @param file
     * @return
     */
    String inputStreamUpload(InputStream inputStream, String key, MultipartFile file);

    /**
     * 重命名文件
     * @param fromKey
     * @param toKey
     * @return
     */
    String renameFile(String fromKey, String toKey);

    /**
     * 拷贝文件
     * @param fromKey
     * @param toKey
     * @return
     */
    String copyFile(String fromKey, String toKey);

    /**
     * 删除文件
     * @param key
     */
    void deleteFile(String key);
}
