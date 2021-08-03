<template>
  <div>
    <!-- Drawer抽屉式选择流程 -->
    <Drawer
      title="选择流程"
      closable
      v-model="processModalVisible"
      width="820"
      draggable
    >
      <div class="apply-operation">
        <div>
          <Form
            ref="searchProcessForm"
            :model="searchProcessForm"
            inline
            :label-width="70"
          >
            <FormItem label="流程名称" prop="name">
              <Input
                type="text"
                v-model="searchProcessForm.name"
                clearable
                placeholder="请输入流程名"
                style="width: 140px"
              />
            </FormItem>
            <FormItem label="所属分类" prop="category">
              <Cascader
                v-model="selectCat"
                :data="category"
                :load-data="loadData"
                @on-change="handleChangeCat"
                change-on-select
                filterable
                clearable
                placeholder="请选择分类"
                style="width: 140px"
              ></Cascader>
            </FormItem>
            <FormItem style="margin-left: -70px" class="br">
              <Button @click="searchProcess" type="primary" icon="ios-search"
                >搜索</Button
              >
              <Button @click="handleResetProcess">重置</Button>
              <i-switch
                size="large"
                v-model="searchProcessForm.showLatest"
                @on-change="searchProcess"
                style="margin: 0 5px"
              >
                <span slot="open">最新</span>
                <span slot="close">全部</span>
              </i-switch>
            </FormItem>
          </Form>
        </div>
        <div>
          <RadioGroup v-model="showType" type="button">
            <Radio title="缩略图" label="thumb">
              <Icon type="ios-apps"></Icon>
            </Radio>
            <Radio title="列表" label="list">
              <Icon type="md-list"></Icon>
            </Radio>
          </RadioGroup>
        </div>
      </div>
      <div
        class="process-wrapper"
        v-if="showType == 'thumb'"
        :style="{ maxHeight: maxHeight + 'px' }"
      >
        <Card v-for="(item, i) in processData" :key="i" class="process-card">
          <div class="content" @click="showApply(item)">
            <div class="other">
              <div class="name">{{ i + 1 }}. {{ item.name }}</div>
              <div class="key">{{ item.description }}</div>
              <div class="info">
                版本：v.{{ item.version }} 所属分类：{{ item.categoryTitle }}
              </div>
            </div>
          </div>
        </Card>
        <Spin fix v-if="processLoading" />
      </div>
      <Table
        :height="maxHeight"
        :loading="processLoading"
        border
        :columns="processColumns"
        :data="processData"
        ref="processTable"
        v-if="showType == 'list'"
      ></Table>
      <Row class="process-choose-drawer-footer">
        <Page
          :current="searchProcessForm.pageNumber"
          :page-size="searchProcessForm.pageSize"
          @on-change="changeProcessPage"
          @on-page-size-change="changeProcessPageSize"
          :page-size-opts="[10, 20, 50]"
          size="small"
          show-elevator
          show-sizer
        ></Page>
      </Row>
    </Drawer>

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
import {
  getProcessDataList,
  getFirstNode,
  startBusiness,
  initActCategory,
  loadActCategory,
} from "@/api/activiti";
import { searchUserByName } from "@/api/index";
import dict from "@/views/my-components/xboot/dict";
export default {
  name: "processChoose",
  components: {
    dict,
  },
  props: {
    value: {
      type: Boolean,
      default: false,
    },
    actBusinessId: String,
  },
  data() {
    return {
      processModalVisible: this.value,
      maxHeight: 510,
      userLoading: false,
      error: "",
      showAssign: false,
      isGateway: false,
      isCustom: false,
      submitLoading: false,
      processLoading: true, // 表单加载状态
      applyModalVisible: false,
      selectCat: [],
      category: [],
      processData: [],
      showType: "thumb",
      searchProcessForm: {
        showLatest: true,
        filter: true,
        name: "",
        status: "1", // 激活状态
        pageNumber: 1, // 当前页数
        pageSize: 10, // 页面大小
        sort: "createTime", // 默认排序字段
        order: "desc", // 默认排序方式
      },
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
      processColumns: [
        {
          type: "index",
          width: 60,
          align: "center",
        },
        {
          title: "名称",
          key: "name",
          width: 150,
          sortable: true,
        },
        {
          title: "备注描述",
          key: "description",
          width: 150,
          sortable: true,
        },
        {
          title: "所属分类",
          key: "categoryTitle",
          width: 150,
          sortable: true,
        },
        {
          title: "版本",
          key: "version",
          align: "center",
          sortable: true,
          render: (h, params) => {
            let re = "";
            if (params.row.version) {
              re = "v." + params.row.version;
            }
            return h("div", re);
          },
        },
        {
          title: "操作",
          key: "action",
          width: 135,
          align: "center",
          fixed: "right",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "info",
                    size: "small",
                  },
                  on: {
                    click: () => {
                      this.showApply(params.row);
                    },
                  },
                },
                "选择该流程"
              ),
            ]);
          },
        },
      ],
      userList: [],
    };
  },
  methods: {
    init() {
      this.getProcessList();
      this.initCategoryData();
    },
    getProcessList() {
      this.processLoading = true;
      getProcessDataList(this.searchProcessForm).then((res) => {
        this.processLoading = false;
        if (res.success) {
          this.processData = res.result.content;
        }
      });
    },
    changeProcessPage(v) {
      this.searchProcessForm.pageNumber = v;
      this.getProcessList();
    },
    changeProcessPageSize(v) {
      this.searchProcessForm.pageSize = v;
      this.getProcessList();
    },
    searchProcess() {
      this.searchProcessForm.pageNumber = 1;
      this.getProcessList();
    },
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
    initCategoryData() {
      initActCategory().then((res) => {
        if (res.success) {
          res.result.forEach(function (e) {
            if (e.isParent) {
              e.value = e.id;
              e.label = e.title;
              e.loading = false;
              e.children = [];
            } else {
              e.value = e.id;
              e.label = e.title;
            }
            if (e.status == -1) {
              e.label = "[已禁用] " + e.label;
              e.disabled = true;
            }
          });
          this.category = res.result;
        }
      });
    },
    loadData(item, callback) {
      item.loading = true;
      loadActCategory(item.value).then((res) => {
        item.loading = false;
        if (res.success) {
          res.result.forEach(function (e) {
            if (e.isParent) {
              e.value = e.id;
              e.label = e.title;
              e.loading = false;
              e.children = [];
            } else {
              e.value = e.id;
              e.label = e.title;
            }
            if (e.status == -1) {
              e.label = "[已禁用] " + e.label;
              e.disabled = true;
            }
          });
          item.children = res.result;
          callback();
        }
      });
    },
    handleChangeCat(value, selectedData) {
      let categoryId = "";
      // 获取最后一个值
      if (value && value.length > 0) {
        categoryId = value[value.length - 1];
      }
      this.searchProcessForm.categoryId = categoryId;
    },
    handleResetProcess() {
      this.$refs.searchProcessForm.resetFields();
      this.searchProcessForm.categoryId = "";
      this.selectCat = [];
      // 重新加载数据
      this.getProcessList();
    },
    showApply(v) {
      if (!v.routeName) {
        this.$Message.warning(
          "该流程信息未完善，请于流程管理中编辑流程完善信息"
        );
        return;
      }
      this.form.procDefId = v.id;
      this.form.title = v.name;
      // 加载审批人
      this.userLoading = true;
      getFirstNode(v.id).then((res) => {
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
          this.form.id = this.actBusinessId;
          if (!this.form.id) {
            this.$Message.error("请传入业务表ActBusiness的ID");
            return;
          }
          this.submitLoading = true;
          startBusiness(this.form).then((res) => {
            this.submitLoading = false;
            if (res.success) {
              this.$Message.success("操作成功");
              this.applyModalVisible = false;
              this.processModalVisible = false;
              this.$emit("on-submit", true);
              // 重置
              this.form.id = "";
            }
          });
        }
      });
    },
    setCurrentValue(value) {
      if (value === this.processModalVisible) {
        return;
      }
      this.processModalVisible = value;
    },
  },
  watch: {
    value(val) {
      this.setCurrentValue(val);
    },
    processModalVisible(value) {
      this.$emit("input", value);
    },
  },
  mounted() {
    // 计算高度
    this.maxHeight = Number(document.documentElement.clientHeight - 180);
    this.init();
  },
};
</script>

<style lang="less">
.apply-operation {
  display: flex;
  justify-content: space-between;
  width: 100%;
}
.process-wrapper {
  display: flex;
  flex-wrap: wrap;
  position: relative;
  overflow: auto;
}

.process-card {
  margin: 10px 25px 10px 0;
  width: 355px;
  :hover {
    .content .other .name {
      color: #1890ff;
      transition: color 0.3s;
    }
  }
  cursor: pointer;
  .ivu-card-body {
    padding: 0;
  }
  .content {
    display: flex;
    flex-direction: column;
    .other {
      padding: 16px;
      height: 130px;
      .name {
        font-size: 16px;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        color: rgba(0, 0, 0, 0.85);
        font-weight: 500;
        margin-bottom: 4px;
      }
      .key {
        overflow: hidden;
        text-overflow: ellipsis;
        height: 45px;
        word-break: break-all;
        color: rgba(0, 0, 0, 0.45);
      }
      .info {
        font-size: 12px;
        color: rgba(0, 0, 0, 0.45);
        overflow: hidden;
        text-overflow: ellipsis;
        height: 36px;
        word-break: break-all;
      }
    }
  }
}

.process-choose-drawer-footer {
  z-index: 10;
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  background: #fff;
}
</style>

