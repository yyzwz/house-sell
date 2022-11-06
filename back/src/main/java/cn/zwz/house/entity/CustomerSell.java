package cn.zwz.house.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @author 郑为中
 * Q：916077357
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_customer_sell")
@TableName("a_customer_sell")
@ApiModel(value = "房屋求购单")
public class CustomerSell extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "卖家ID")
    private String sellerId;

    @ApiModelProperty(value = "房屋ID")
    private String houseId;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "户型")
    private String houseType;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "面积")
    private BigDecimal houseSize;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "价格")
    private BigDecimal houseMoney;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "电话")
    private String mobile;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "联系人")
    private String houseOwner;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "地址")
    private String address;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "买家ID")
    private String userId;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "买家姓名")
    private String userName;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "买家电话")
    private String userMobile;

    @ApiModelProperty(value = "买家出价")
    private BigDecimal userMoney;

    @ApiModelProperty(value = "买家留言")
    private String userRemark;

    @ApiModelProperty(value = "订单状态",notes = "0 卖家未审核 | 1 卖家同意 | 2 卖家拒绝")
    private int status;
}