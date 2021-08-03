package cn.exrick.xboot.modules.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
public class PermissionDTO {

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;
}
