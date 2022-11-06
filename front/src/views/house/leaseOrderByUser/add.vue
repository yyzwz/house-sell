<template>
  <div>
    <Card>
      <div slot="title">
        <div class="edit-head">
          <a @click="close" class="back-title">
            <Icon type="ios-arrow-back" />返回
          </a>
          <div class="head-name">添加</div>
          <span></span>
          <a @click="close" class="window-close">
            <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
          </a>
        </div>
      </div>
      <Form ref="form" :model="form" :label-width="100" :rules="formValidate" label-position="left" >          <FormItem label="户型" prop="houseType"  >
            <Input v-model="form.houseType" clearable style="width:570px"/>
          </FormItem>
          <FormItem label="面积" prop="houseSize"  >
            <InputNumber v-model="form.houseSize" min="0" max="5000000" style="width:570px"></InputNumber>
          </FormItem>
          <FormItem label="每月租金" prop="houseMoney"  >
            <InputNumber v-model="form.houseMoney" min="0" max="5000000" style="width:570px"></InputNumber>
          </FormItem>
          <FormItem label="押金" prop="initMoney"  >
            <InputNumber v-model="form.initMoney" min="0" max="5000000" style="width:570px"></InputNumber>
          </FormItem>
          <FormItem label="电话" prop="mobile"  >
            <Input v-model="form.mobile" clearable style="width:570px"/>
          </FormItem>
          <FormItem label="联系人" prop="houseOwner"  >
            <Input v-model="form.houseOwner" clearable style="width:570px"/>
          </FormItem>
          <FormItem label="地址" prop="address"  >
            <Input v-model="form.address" clearable style="width:570px"/>
          </FormItem>
          <FormItem label="备注" prop="remark"  >
            <Input v-model="form.remark" clearable style="width:570px"/>
          </FormItem>
          <FormItem label="买家姓名" prop="userName"  >
            <Input v-model="form.userName" clearable style="width:570px"/>
          </FormItem>
          <FormItem label="买家电话" prop="userMobile"  >
            <Input v-model="form.userMobile" clearable style="width:570px"/>
          </FormItem>
          <FormItem label="买家出价" prop="userMoney"  >
            <InputNumber v-model="form.userMoney" min="0" max="5000000" style="width:570px"></InputNumber>
          </FormItem>
          <FormItem label="买家留言" prop="userRemark"  >
            <Input v-model="form.userRemark" clearable style="width:570px"/>
          </FormItem>
          <FormItem label="支付状态" prop="status"  >
            <Input v-model="form.status" clearable style="width:570px"/>
          </FormItem>
          <Form-item class="br">
            <Button
              @click="handleSubmit"
              :loading="submitLoading"
              type="primary"
            >提交并保存</Button>
            <Button @click="handleReset">重置</Button>
            <Button type="dashed" @click="close">关闭</Button>
          </Form-item>
        </Form>
    </Card>
  </div>
</template>

<script>
import { addHouseLeaseOrder } from "./api.js";
export default {
  name: "add",
  components: {
    },
  data() {
    return {
      submitLoading: false, // 表单提交状态
      form: { // 添加或编辑表单对象初始化数据
        houseType: "",
        houseSize: 0,
        houseMoney: 0,
        initMoney: 0,
        mobile: "",
        houseOwner: "",
        address: "",
        remark: "",
        userName: "",
        userMobile: "",
        userMoney: 0,
        userRemark: "",
        status: "",
      },
      // 表单验证规则
      formValidate: {
      }
    };
  },
  methods: {
    init() {},
    handleReset() {
      this.$refs.form.resetFields();
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          addHouseLeaseOrder(this.form).then(res => {
            this.submitLoading = false;
            if (res.success) {
              this.$Message.success("操作成功");
              this.submited();
            }
          });
        }
      });
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