package cn.exrick.xboot.modules.social.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_social")
@TableName("t_social")
@ApiModel(value = "文件")
public class Social extends XbootBaseEntity {

    @ApiModelProperty(value = "社交账号唯一id")
    private String openId;

    @ApiModelProperty(value = "社交账号平台 0Github 1QQ 2微信 3微博")
    private Integer platform;

    @ApiModelProperty(value = "社交账号用户名")
    private String username;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "绑定用户账号")
    private String relateUsername;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "是否绑定")
    private Boolean isRelated;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "绑定用户名")
    private String nickname;
}
