package cn.exrick.xboot.modules.bm.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
@Table(name = "t_village_style")
@TableName("t_village_style")
@ApiModel(value = "一村一品")
public class VillageStyle extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "备注")
    private String remark;

}