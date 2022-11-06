package cn.zwz.house.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author 郑为中
 * Q：916077357
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_house_lease")
@TableName("a_house_lease")
@ApiModel(value = "房屋出租单")
public class HouseLease extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "卖家ID")
    private String sellerId;

    @ApiModelProperty(value = "户型")
    private String houseType;

    @ApiModelProperty(value = "面积")
    private BigDecimal houseSize;

    @ApiModelProperty(value = "房屋图片")
    private String imageUrl;

    @ApiModelProperty(value = "房屋图片")
    private String imageUrl2;

    @ApiModelProperty(value = "房屋图片")
    private String imageUrl3;

    @ApiModelProperty(value = "每月租金")
    private BigDecimal houseMoney;

    @ApiModelProperty(value = "押金")
    private BigDecimal initMoney;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "联系人")
    private String houseOwner;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "审核状态")
    private int status;
}