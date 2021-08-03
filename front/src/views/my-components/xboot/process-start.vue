<template>
  <div>
    <Modal
      title="提交申请"
      v-model="applyModalVisible"
      :mask-closable="false"
      :width="500"
    >
      <Form ref="form" :model="form" :label-width="85" :rules="formValidate">
        <FormItem label="标题" prop="title">
          <Input v-model="form.title" placeholder="请输入标题" clearable />
        </FormItem>
        <FormItem
          label="选择审批人"
          prop="assignees"
          v-show="showAssign"
          :error="error"
        >
          <Select
            v-model="form.assignees"
            placeholder="请选择或输入搜索"
            filterable
            clearable
            multiple
            :loading="userLoading"
          >
            <Option
              v-for="(item, i) in assigneeList"
              :key="i"
              :value="item.id"
              :label="item.nickname"
            >
              <span style="margin-right: 10px">{{ item.nickname }}</span>
              <span style="color: #ccc">{{ item.username }}</span>
            </Option>
          </Select>
        </FormItem>
        <FormItem
          label="自定义搜索选择审批人"
          prop="assignees"
          v-show="isCustom"
          :error="error"
        >
          <Select
            v-model="form.assignees"
            placeholder="请输入用户名搜索选择用户"
            filterable
            remote
            multiple
            :remote-method="searchUser"
            :loading="userLoading"
          >
            <Option
              v-for="(item, i) in userList"
              :value="item.id"
              :key="i"
              :label="item.nickname"
            >
              <span style="margin-right: 10px">{{ item.nickname }}</span>
              <span style="color: #ccc">{{ item.username }}</span>
            </Option>
          </Select>
        </FormItem>
        <FormItem label="下一审批人" v-show="isGateway">
          <span
            >分支网关处暂不支持自定义选择下一审批人，将发送给下一节点所有人</span
          >
        </FormItem>
        <FormItem label="优先级" prop="priority">
          <dict dict="priority" v-model="form.priority" />
        </FormItem>
        <FormItem label="消息通知">
          <Checkbox v-model="form.sendMessage">站内消息通知</Checkbox>
          <Checkbox v-model="form.sendSms">短信通知</Checkbox>
          <Checkbox v-model="form.sendEmail">邮件通知</Checkbox>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" @click="applyModalVisible = false">取消</Button>
        <Button type="primary" :loading="submitLoading" @click="handelSubmit"
          >提交</Button
        >
      </div>
    </Modal>
  </div>
</template>

<script>
import { getProcessByKey, getFirstNode, startBusiness } from "@/api/activiti";
import { searchUserByName } from "@/api/index";
import dict from "@/views/my-components/xboot/dict";
export default {
  name: "processStart",
  components: {
    dict,
  },
  props: {
    value: {
      type: Boolean,
      default: false,
    },
    processKey: String,
    actBusinessId: String,
  },
  data() {
    return {
      userLoading: false,
      showAssign: false,
      isGateway: false,
      isCustom: false,
      error: "",
      submitLoading: false,
      applyModalVisible: this.value,
      form: {
        title: "",
        procDefId: "",
        assignees: [],
        priority: "0",
        sendMessage: true,
        sendSms: true,
        sendEmail: true,
      },
      formValidate: {
        // 表单验证规则
        title: [{ required: true, message: "不能为空", trigger: "change" }],
        priority: [{ required: true, message: "不能为空", trigger: "change" }],
      },
      assigneeList: [],
      routeName: "",
      userList: [],
    };
  },
  methods: {
    init() {},
    searchUser(v) {
      if (!v) {
        return;
      }
      this.userLoading = true;
      searchUserByName(v).then((res) => {
        this.userLoading = false;
        if (res.success) {
          this.userList = res.result;
        }
      });
    },
    showApply() {
      // 加载审批人
      this.userLoading = true;
      getFirstNode(this.form.procDefId).then((res) => {
        this.userLoading = false;
        if (res.success) {
          this.error = "";
          if (res.result.type == 3 || res.result.type == 4) {
            this.isGateway = true;
            this.form.firstGateway = true;
            this.showAssign = false;
            this.isCustom = false;
            return;
          }
          if (res.result.type == 5) {
            this.isCustom = true;
            this.isGateway = false;
            this.form.firstGateway = false;
            this.showAssign = false;
            return;
          }
          if (res.result.type == 1) {
            this.showAssign = true;
            this.isGateway = false;
            this.form.firstGateway = false;
            this.isCustom = false;
            if (res.result.users && res.result.users.length > 0) {
              this.assigneeList = res.result.users;
              // 默认勾选
              let ids = [];
              res.result.users.forEach((e) => {
                ids.push(e.id);
              });
              this.form.assignees = ids;
              this.showAssign = true;
            } else {
              this.form.assignees = [];
              this.showAssign = true;
              this.error = '请进入"流程管理"为审批节点分配候选审批人员';
            }
          }
        }
      });
      this.applyModalVisible = true;
    },
    handelSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (
            (this.showAssign || this.isCustom) &&
            this.form.assignees.length < 1
          ) {
            this.error = "请至少选择一个审批人";
            return;
          } else {
            this.error = "";
          }
          this.submitLoading = true;
          startBusiness(this.form).then((res) => {
            this.submitLoading = false;
            if (res.success) {
              this.$Message.success("操作成功");
              this.applyModalVisible = false;
              this.$emit("on-submit", true);
              // 重置
              this.routeName = "";
            }
          });
        }
      });
    },
    show() {
      if (!this.processKey) {
        this.$Message.error("请先传入要申请的流程key");
        this.$emit("input", false);
        return;
      }
      if (!this.actBusinessId) {
        this.$Message.error("请传入业务表ActBusiness的ID");
        this.$emit("input", false);
        return;
      }
      this.form.id = this.actBusinessId;
      this.$emit("on-loading", true);
      getProcessByKey(this.processKey).then((res) => {
        this.$emit("on-loaded", false);
        if (res.success) {
          if (!res.result) {
            this.$Message.error("未找到标识为 " + this.processKey + " 的流程");
            this.$emit("input", false);
            return;
          }
          if (!res.result.routeName) {
            this.$Message.warning(
              "该流程信息未完善，请于流程管理中编辑流程完善信息"
            );
            this.$emit("input", false);
            return;
          }
          this.routeName = res.result.routeName;
          this.form.procDefId = res.result.id;
          this.form.title = res.result.name;
          this.showApply();
        }
      });
    },
    close() {
      this.applyModalVisible = false;
    },
  },
  watch: {
    value(val) {
      if (val == true) {
        this.show();
      }
    },
    applyModalVisible(value) {
      this.$emit("input", value);
    },
  },
  mounted() {
    this.init();
  },
};
</script>

<style lang="less">
</style>

