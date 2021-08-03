package cn.exrick.xboot.modules.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class VaptchaSetting implements Serializable {

    @ApiModelProperty(value = "vid")
    private String vid;

    @ApiModelProperty(value = "secretKey")
    private String secretKey;

    @ApiModelProperty(value = "场景")
    private String scene;

    @ApiModelProperty(value = "是否改变secretkey")
    private Boolean changed;
}
