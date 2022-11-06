<template>
<div>
    <Card>
        <div slot="title">
            <div class="edit-head">
                <a @click="close" class="back-title">
                    <Icon type="ios-arrow-back" />返回
                </a>
                <div class="head-name">编辑</div>
                <span></span>
                <a @click="close" class="window-close">
                    <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
                </a>
            </div>
        </div>
        <Form ref="form" :model="form" :label-width="100" :rules="formValidate" label-position="left">
            <FormItem label="户型" prop="houseType">
                <Input v-model="data.houseType" readonly style="width:570px" />
            </FormItem>
            <FormItem label="面积" prop="houseSize">
                <InputNumber v-model="data.houseSize" readonly min="0" max="5000000" style="width:570px"></InputNumber>
            </FormItem>
            <FormItem label="价格" prop="houseMoney">
                <InputNumber v-model="data.houseMoney" readonly min="0" max="5000000" style="width:570px"></InputNumber>
            </FormItem>
            <FormItem label="电话" prop="mobile">
                <Input v-model="data.mobile" readonly style="width:570px" />
            </FormItem>
            <FormItem label="联系人" prop="houseOwner">
                <Input v-model="data.houseOwner" readonly style="width:570px" />
            </FormItem>
            <FormItem label="地址" prop="address">
                <Input v-model="data.address" readonly style="width:570px" />
            </FormItem>
            <FormItem label="备注" prop="remark">
                <Input v-model="data.remark" readonly style="width:570px" />
            </FormItem>
            <FormItem label="买家姓名" prop="userName">
                <Input v-model="data.userName" readonly style="width:570px" />
            </FormItem>
            <FormItem label="买家电话" prop="userMobile">
                <Input v-model="data.userMobile" readonly style="width:570px" />
            </FormItem>
            <FormItem label="买家出价" prop="userMoney">
                <InputNumber v-model="data.userMoney" readonly min="0" max="5000000" style="width:570px"></InputNumber>
            </FormItem>
            <FormItem label="买家留言" prop="userRemark">
                <Input v-model="data.userRemark" readonly style="width:570px" />
            </FormItem>
            <FormItem label="支付状态" prop="status">
                <Input v-model="data.status" readonly style="width:570px" />
            </FormItem>
        </Form>
    </Card>
</div>
</template>

<script>
import {
} from "./api.js";
export default {
    name: "edit",
    components: {},
    props: {
        data: Object
    },
    data() {
        return {
            submitLoading: false,
            formValidate: {}
        };
    },
    methods: {
        init() {
            this.handleReset();
        },
        close() {
            this.$emit("close", true);
        },
        submited() {
            this.$emit("submited", true);
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="less">
// 建议引入通用样式 具体路径自行修改 可删除下面样式代码
// @import "../../../styles/single-common.less";
.edit-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;

    .back-title {
        color: #515a6e;
        display: flex;
        align-items: center;
    }

    .head-name {
        display: inline-block;
        height: 20px;
        line-height: 20px;
        font-size: 16px;
        color: #17233d;
        font-weight: 500;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .window-close {
        z-index: 1;
        font-size: 12px;
        position: absolute;
        right: 0px;
        top: -5px;
        overflow: hidden;
        cursor: pointer;

        .ivu-icon-ios-close {
            color: #999;
            transition: color .2s ease;
        }
    }

    .window-close .ivu-icon-ios-close:hover {
        color: #444;
    }
}
</style>
