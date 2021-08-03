<style lang="less">
@import "@/styles/single-common.less";
</style>
<template>
  <div>
    <Card>
      <div slot="title">
        <div class="edit-head">
          <a @click="close" class="back-title">
            <Icon type="ios-arrow-back" />返回
          </a>
          <div class="head-name">
            {{ title }}
          </div>
          <span></span>
          <a @click="close" class="window-close">
            <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
          </a>
        </div>
      </div>
      <Form
        ref="form"
        :model="form"
        :label-width="90"
        :rules="formValidate"
        style="position: relative"
      >
        <FormItem label="消息ID：" prop="id" v-show="type == 1">
          {{ form.id }}
        </FormItem>
        <FormItem label="消息类型" prop="type">
          <dict dict="message_type" v-model="form.type" style="width: 300px" />
        </FormItem>
        <FormItem label="标题" prop="title">
          <Input v-model="form.title" style="width: 600px" />
        </FormItem>
        <FormItem style="margin-bottom: 5px" v-show="form.isTemplate">
          <Alert show-icon
            >消息模版变量格式：${变量名}【标题及内容都支持】
            示例：您的验证码为：${code}，该验证码5分钟内有效，请勿泄漏于他人
            <a
              href="https://www.kancloud.cn/zwz/xboot/1021404"
              target="_blank"
              >详见开发文档</a
            ></Alert
          >
        </FormItem>
        <FormItem label="内容" prop="content">
          <editor v-model="form.content" height="250"></editor>
        </FormItem>
        <FormItem label="新创建的账号也推送" prop="createSend">
          <i-switch size="large" v-model="form.createSend">
            <span slot="open">开启</span>
            <span slot="close">关闭</span>
          </i-switch>
        </FormItem>
        <div v-if="type == 0">
          <FormItem label="发送范围">
            <RadioGroup v-model="form.range">
              <Radio :label="0" border>全体用户</Radio>
              <Radio :label="1" border>指定用户成员</Radio>
            </RadioGroup>
          </FormItem>
          <div>
            <FormItem label="选择用户" v-if="form.range == 1">
              <user-choose
                text="选择发送用户"
                v-model="selectUsers"
              ></user-choose>
            </FormItem>
          </div>
        </div>
        <FormItem class="br">
          <Button
            type="primary"
            :loading="submitLoading"
            @click="handelSubmit"
            style="width: 100px"
            >提交</Button
          >
          <Button @click="close">取消</Button>
        </FormItem>
        <Spin size="large" fix v-if="loading"></Spin>
      </Form>
    </Card>
  </div>
</template>

<script>
import { getMessageDataById, addMessage, editMessage } from "@/api/index";
import editor from "@/views/my-components/xboot/editor";
import userChoose from "@/views/my-components/xboot/user-choose";
import dict from "@/views/my-components/xboot/dict";
export default {
  name: "add_edit_message",
  components: {
    userChoose,
    editor,
    dict,
  },
  props: {
    transferData: Object,
  },
  data() {
    return {
      type: 0,
      title: "",
      loading: false, // 表单加载状态
      selectUsers: [],
      userModalVisible: false,
      modalTitle: "", // 添加或编辑标题
      form: {
        // 添加或编辑表单对象初始化数据
        title: "",
        content: "",
        type: "",
        range: 0,
        createSend: false,
        isTemplate: false,
      },
      formValidate: {
        // 表单验证规则
        type: [
          { required: true, message: "消息类型不能为空", trigger: "change" },
        ],
        title: [{ required: true, message: "标题不能为空", trigger: "change" }],
        content: [
          { required: true, message: "内容不能为空", trigger: "change" },
        ],
      },
      submitLoading: false, // 添加或编辑提交状态
      backRoute: "",
    };
  },
  methods: {
    init() {
      this.type = this.transferData.type;
      if (this.type == 1) {
        this.title = "编辑消息";
        this.form.id = this.transferData.id;
        this.getData();
      } else if (this.type == 0) {
        this.title = "发送新消息";
        this.selectUsers = [];
      } else if (this.type == -1) {
        this.title = "添加系统模版消息";
        this.selectUsers = [];
        this.form.isTemplate = true;
        this.form.range = -1;
      }
    },
    getData() {
      this.loading = true;
      getMessageDataById(this.form.id).then((res) => {
        this.loading = false;
        if (res.success) {
          // 转换null为""
          let v = res.result;
          for (let attr in v) {
            if (v[attr] == null) {
              v[attr] = "";
            }
          }
          let str = JSON.stringify(v);
          let data = JSON.parse(str);
          this.form = data;
        }
      });
    },
    handelSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.submitLoading = true;
          if (this.type == 0) {
            // 添加 避免编辑后传入id等数据 记得删除
            delete this.form.id;
            // 用户id数据
            let ids = [];
            this.selectUsers.forEach((e) => {
              ids += e.id + ",";
            });
            if (ids.length > 0) {
              ids = ids.substring(0, ids.length - 1);
            }
            this.form.userIds = ids;
            addMessage(this.form).then((res) => {
              this.submitLoading = false;
              if (res.success) {
                this.$Message.success("操作成功");
                this.submited();
              }
            });
          } else if (this.type == 1) {
            // 编辑
            editMessage(this.form).then((res) => {
              this.submitLoading = false;
              if (res.success) {
                this.$Message.success("操作成功");
                this.submited();
              }
            });
          } else {
            addMessage(this.form).then((res) => {
              this.submitLoading = false;
              if (res.success) {
                this.$Message.success("操作成功");
                this.submited();
              }
            });
          }
        }
      });
    },
    close() {
      this.$emit("close", true);
    },
    submited() {
      this.$emit("submited", true);
    },
  },
  mounted() {
    this.init();
  },
};
</script>