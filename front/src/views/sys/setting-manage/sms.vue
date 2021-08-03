<style lang="less">
</style>
<template>
  <div style="display: flex; position: relative">
    <Form
      ref="smsForm"
      :model="sms"
      :label-width="110"
      label-position="left"
      :rules="smsValidate"
    >
      <FormItem label="服务提供商" prop="serviceName">
        <Select
          v-model="sms.serviceName"
          @on-change="changeSms"
          placeholder="请选择"
          style="width: 230px"
        >
          <Option value="ALI_SMS">阿里云</Option>
          <Option value="TENCENT_SMS">腾讯云</Option>
        </Select>
      </FormItem>
      <FormItem label="accessKey" prop="accessKey">
        <Input
          type="text"
          v-model="sms.accessKey"
          placeholder="请输入accessKey/secretId"
          :disabled="changeLoading"
          style="width: 380px"
        />
      </FormItem>
      <FormItem label="secretKey" prop="secretKey">
        <Tooltip placement="right" content="无法编辑时请先点击查看图标">
          <Input
            class="input-see"
            type="text"
            v-model="sms.secretKey"
            :readonly="!changedSmsSK"
            @on-click="seeSecret(sms.serviceName)"
            :disabled="changeLoading"
            icon="ios-eye"
            placeholder="请输入secretKey"
            style="width: 380px"
          />
        </Tooltip>
      </FormItem>
      <FormItem
        v-if="sms.serviceName == 'TENCENT_SMS'"
        label="应用ID"
        prop="appId"
      >
        <Input
          type="text"
          v-model="sms.appId"
          :disabled="changeLoading"
          placeholder="请输入应用ID（短信控制台-应用管理）"
          style="width: 380px"
        />
      </FormItem>
      <FormItem label="短信签名" prop="signName">
        <Input
          type="text"
          v-model="sms.signName"
          :disabled="changeLoading"
          placeholder="请输入短信签名"
          style="width: 380px"
        />
      </FormItem>
      <FormItem label="使用场景" prop="type">
        <Select
          v-model="sms.type"
          @on-change="changeSmsType"
          :disabled="changeLoading"
          placeholder="请选择"
          style="width: 380px"
          transfer
        >
          <Option value="0">通用验证码</Option>
          <Option value="1">注册</Option>
          <Option value="2">登录</Option>
          <Option value="3">修改绑定手机号</Option>
          <Option value="4">修改密码</Option>
          <Option value="5">重置密码</Option>
          <Option value="6">工作流消息</Option>
        </Select>
      </FormItem>
      <FormItem label="模版CODE/ID" prop="templateCode">
        <Input
          type="text"
          v-model="sms.templateCode"
          :disabled="changeLoading"
          placeholder="请输入场景对应短信模版CODE/ID，如SMS_123456789"
          style="width: 380px"
        />
      </FormItem>
      <FormItem>
        <Button
          type="primary"
          style="width: 100px"
          :disabled="changeLoading"
          :loading="saveLoading"
          @click="saveEditSms"
          >保存更改</Button
        >
      </FormItem>
    </Form>
    <Spin fix v-if="loading"></Spin>
  </div>
</template>

<script>
import {
  checkSmsSet,
  seeSecretSet,
  getSmsSet,
  editSmsSet,
  getSmsTemplateCode,
} from "@/api/index";
export default {
  name: "sms",
  data() {
    return {
      loading: false, // 表单加载状态
      changeLoading: false,
      saveLoading: false,
      sms: {
        serviceName: "ALI_SMS",
        accessKey: "",
        secretKey: "",
        appId: "",
        signName: "",
        type: "",
        templateCode: "",
      },
      changedSmsSK: false, // 是否修改短信的secrectKey
      smsValidate: {
        // 表单验证规则
        serviceName: [{ required: true, message: "请选择", trigger: "change" }],
        accessKey: [{ required: true, message: "不能为空", trigger: "change" }],
        secretKey: [{ required: true, message: "不能为空", trigger: "change" }],
        appId: [{ required: true, message: "不能为空", trigger: "change" }],
        signName: [{ required: true, message: "不能为空", trigger: "change" }],
        type: [{ required: true, message: "不能为空", trigger: "change" }],
        templateCode: [
          { required: true, message: "不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    init() {
      this.initSmsSet();
    },
    initSmsSet() {
      this.loading = true;
      checkSmsSet().then((res) => {
        if (res.success && res.result) {
          let sms = res.result;
          getSmsSet(sms).then((res) => {
            this.loading = false;
            if (res.result) {
              // 转换null为""
              for (let attr in res.result) {
                if (res.result[attr] == null) {
                  res.result[attr] = "";
                }
              }
              this.sms = res.result;
              this.sms.type = "0";
              this.changeSmsType(this.sms.type);
            } else {
              this.changedSmsSK = true;
            }
          });
        } else {
          this.loading = false;
          this.changedSmsSK = true;
        }
      });
    },
    changeSms(v) {
      this.changeLoading = true;
      getSmsSet(v).then((res) => {
        this.changeLoading = false;
        if (res.result) {
          // 转换null为""
          for (let attr in res.result) {
            if (res.result[attr] == null) {
              res.result[attr] = "";
            }
          }
          this.sms = res.result;
          this.changedSmsSK = false;
          this.sms.type = "0";
          this.changeSmsType(this.sms.type);
        } else {
          this.sms = { serviceName: v };
          this.sms.type = "0";
          this.changedSmsSK = true;
        }
      });
    },
    seeSecret(name) {
      if (!name) {
        return;
      }
      seeSecretSet(name).then((res) => {
        if (res.success) {
          this.sms.secretKey = res.result;
          this.changedSmsSK = true;
        }
      });
    },
    changeSmsType(v) {
      getSmsTemplateCode(this.sms.serviceName, v).then((res) => {
        if (res.success) {
          this.sms.templateCode = res.result;
        }
      });
    },
    saveEditSms() {
      this.$refs.smsForm.validate((valid) => {
        if (valid) {
          this.saveLoading = true;
          this.sms.changed = this.changedSmsSK;
          editSmsSet(this.sms).then((res) => {
            this.saveLoading = false;
            if (res.success) {
              this.$Message.success("保存成功");
            }
          });
        }
      });
    },
  },
  mounted() {
    this.init();
  },
};
</script>