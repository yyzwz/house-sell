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
@Table(name = "a_house_sell_order")
@TableName("a_house_sell_order")
@ApiModel(value = "房屋销售交易单")
public class HouseSellOrder extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "卖家ID")
    private String sellerId;

    @ApiModelProperty(value = "房屋ID")
    private String houseId;

    @ApiModelProperty(value = "户型")
    private String houseType;

    @ApiModelProperty(value = "面积")
    private BigDecimal houseSize;

    @ApiModelProperty(value = "价格")
    private BigDecimal houseMoney;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "联系人")
    private String houseOwner;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "买家ID")
    private String userId;

    @ApiModelProperty(value = "买家姓名")
    private String userName;

    @ApiModelProperty(value = "买家电话")
    private String userMobile;

    @ApiModelProperty(value = "买家出价")
    private BigDecimal userMoney;

    @ApiModelProperty(value = "买家留言")
    private String userRemark;

    @ApiModelProperty(value = "支付状态",notes = "0 未支付 | 1 已支付")
    private int status;
}