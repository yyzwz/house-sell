package cn.exrick.xboot.modules.zz.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 郑为中
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_village_introduce")
@TableName("t_village_introduce")
@ApiModel(value = "村情介绍")
public class VillageIntroduce extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;

    @Length(max = 16384)
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建日期")
    private String createDate;

    @ApiModelProperty(value = "外链地址")
    private String url;

    @ApiModelProperty(value = "审核状态")
    private int auditStatus;

    @ApiModelProperty(value = "类型") // 0普通文章 1外链文章
    private String type;

    @ApiModelProperty(value = "优先级")
    private int level;

    @ApiModelProperty(value = "备注")
    private String remark;

}