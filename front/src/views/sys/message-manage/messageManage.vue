<style lang="less">
@import "@/styles/table-common.less";
</style>
<template>
  <div class="search">
    <addEdit
      v-if="currView == 'addEdit'"
      @close="currView = 'index'"
      :transferData="transferData"
      @submited="submited"
    />
    <detail v-model="showDetail" :transferData="transferData" />
    <Card v-show="currView == 'index'">
      <Tabs v-model="tabName" @on-click="changeTab">
        <TabPane label="普通消息" name="normal"></TabPane>
        <TabPane label="系统消息模版" name="system"></TabPane>
      </Tabs>
      <Row v-show="openSearch" @keydown.enter.native="handleSearch">
        <Form ref="searchForm" :model="searchForm" inline :label-width="70">
          <FormItem label="消息标题" prop="title">
            <Input
              type="text"
              v-model="searchForm.title"
              placeholder="请输入消息标题"
              clearable
              style="width: 200px"
            />
          </FormItem>
          <FormItem label="消息内容" prop="content">
            <Input
              type="text"
              v-model="searchForm.content"
              placeholder="请输入消息内容"
              clearable
              style="width: 200px"
            />
          </FormItem>
          <span v-if="drop">
            <FormItem label="消息类型" prop="type">
              <dict
                dict="message_type"
                v-model="searchForm.type"
                style="width: 200px"
              />
            </FormItem>
            <FormItem label="创建时间">
              <DatePicker
                :options="options"
                v-model="selectDate"
                type="daterange"
                format="yyyy-MM-dd"
                clearable
                @on-change="selectDateRange"
                placeholder="选择起始时间"
                style="width: 200px"
              ></DatePicker>
            </FormItem>
          </span>
          <FormItem style="margin-left: -35px" class="br">
            <Button @click="handleSearch" type="primary" icon="ios-search"
              >搜索</Button
            >
            <Button @click="handleReset">重置</Button>
            <a class="drop-down" @click="dropDown">
              {{ dropDownContent }}
              <Icon :type="dropDownIcon"></Icon>
            </a>
          </FormItem>
        </Form>
      </Row>
      <Row class="operation">
        <Button
          @click="add(0)"
          type="primary"
          icon="md-add"
          v-show="tabName == 'normal'"
          >发送新消息</Button
        >
        <Button
          @click="add(-1)"
          type="primary"
          icon="md-add"
          v-show="tabName == 'system'"
          >添加消息模版</Button
        >
        <Button @click="delAll" icon="md-trash">批量删除</Button>
        <Button @click="getDataList" icon="md-refresh">刷新</Button>
        <Button type="dashed" @click="openSearch = !openSearch">{{
          openSearch ? "关闭搜索" : "开启搜索"
        }}</Button>
        <Button type="dashed" @click="openTip = !openTip">{{
          openTip ? "关闭提示" : "开启提示"
        }}</Button>
      </Row>
      <Alert show-icon v-show="openTip">
        已选择
        <span class="select-count">{{ selectList.length }}</span> 项
        <a class="select-clear" @click="clearSelectAll">清空</a>
      </Alert>
      <Table
        :loading="loading"
        border
        :columns="columns"
        :data="data"
        sortable="custom"
        @on-sort-change="changeSort"
        @on-selection-change="showSelect"
        ref="table"
      ></Table>
      <Row type="flex" justify="end" class="page">
        <Page
          :current="searchForm.pageNumber"
          :total="total"
          :page-size="searchForm.pageSize"
          @on-change="changePage"
          @on-page-size-change="changePageSize"
          :page-size-opts="[10, 20, 50]"
          size="small"
          show-total
          show-elevator
          show-sizer
        ></Page>
      </Row>
    </Card>
  </div>
</template>

<script>
import {
  getMessageData,
  addMessage,
  editMessage,
  deleteMessage,
} from "@/api/index";
import { shortcuts } from "@/libs/shortcuts";
import detail from "./detail.vue";
import addEdit from "./addEdit.vue";
import dict from "@/views/my-components/xboot/dict";
export default {
  title: "message-manage",
  components: {
    detail,
    addEdit,
    dict,
  },
  data() {
    return {
      tabName: "normal",
      currView: "index",
      showDetail: false,
      openSearch: true,
      openTip: true,
      loading: true, // 表单加载状态
      userLoading: true,
      selectList: [], // 多选数据
      drop: false,
      dropDownContent: "展开",
      dropDownIcon: "ios-arrow-down",
      searchForm: {
        // 搜索框对应data对象
        title: "",
        content: "",
        pageNumber: 1, // 当前页数
        pageSize: 10, // 页面大小
        sort: "createTime", // 默认排序字段
        order: "desc", // 默认排序方式
        startDate: "", // 起始时间
        endDate: "", // 终止时间
        isTemplate: false,
      },
      transferData: {},
      selectDate: null, // 选择日期绑定modal
      options: {
        shortcuts: shortcuts,
      },
      columns: [
        // 表头
        {
          type: "selection",
          width: 60,
          align: "center",
        },
        {
          type: "index",
          width: 60,
          align: "center",
        },
        {
          title: "消息ID",
          key: "id",
          minWidth: 160,
          sortable: true,
        },
        {
          title: "消息标题",
          key: "title",
          minWidth: 200,
          sortable: true,
          tooltip: true,
        },
        {
          title: "消息内容",
          key: "contentText",
          minWidth: 300,
          tooltip: true,
        },
        {
          title: "类型",
          key: "type",
          width: 120,
          align: "center",
        },
        {
          title: "新创建账号推送",
          key: "createSend",
          align: "center",
          width: 135,
          render: (h, params) => {
            if (params.row.createSend) {
              return h("div", [
                h(
                  "Tag",
                  {
                    props: {
                      color: "blue",
                    },
                  },
                  "开启"
                ),
              ]);
            } else {
              return h("div", [
                h(
                  "Tag",
                  {
                    props: {
                      color: "default",
                    },
                  },
                  "关闭"
                ),
              ]);
            }
          },
        },
        {
          title: "创建时间",
          key: "createTime",
          width: 180,
          sortable: true,
          sortType: "desc",
        },
        {
          title: "操作",
          key: "action",
          width: 220,
          align: "center",
          fixed: "right",
          render: (h, params) => {
            return h("div", [
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.edit(params.row);
                    },
                  },
                },
                "编辑"
              ),
              h("Divider", {
                props: {
                  type: "vertical",
                },
              }),
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.sendDetail(params.row);
                    },
                  },
                },
                "发送详情"
              ),
              h("Divider", {
                props: {
                  type: "vertical",
                },
              }),
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.remove(params.row);
                    },
                  },
                },
                "删除撤回"
              ),
            ]);
          },
        },
      ],
      data: [], // 表单数据
      total: 0, // 表单数据总数
    };
  },
  methods: {
    init() {
      this.getDataList();
    },
    changeTab(name) {
      if (name == "normal") {
        this.searchForm.isTemplate = false;
      } else {
        this.searchForm.isTemplate = true;
      }
      this.getDataList();
    },
    submited() {
      this.currView = "index";
      this.getDataList();
    },
    dropDown() {
      if (this.drop) {
        this.dropDownContent = "展开";
        this.dropDownIcon = "ios-arrow-down";
      } else {
        this.dropDownContent = "收起";
        this.dropDownIcon = "ios-arrow-up";
      }
      this.drop = !this.drop;
    },
    changePage(v) {
      this.searchForm.pageNumber = v;
      this.getDataList();
      this.clearSelectAll();
    },
    changePageSize(v) {
      this.searchForm.pageSize = v;
      this.getDataList();
    },
    selectDateRange(v) {
      if (v) {
        this.searchForm.startDate = v[0];
        this.searchForm.endDate = v[1];
      }
    },
    getDataList() {
      this.loading = true;
      getMessageData(this.searchForm).then((res) => {
        this.loading = false;
        if (res.success) {
          this.data = res.result.content;
          this.total = res.result.totalElements;
          if (this.data.length == 0 && this.searchForm.pageNumber > 1) {
            this.searchForm.pageNumber -= 1;
            this.getDataList();
          }
        }
      });
    },
    handleSearch() {
      this.searchForm.pageNumber = 1;
      this.searchForm.pageSize = 10;
      this.getDataList();
    },
    handleReset() {
      this.$refs.searchForm.resetFields();
      this.searchForm.pageNumber = 1;
      this.searchForm.pageSize = 10;
      this.selectDate = null;
      this.searchForm.startDate = "";
      this.searchForm.endDate = "";
      // 重新加载数据
      this.getDataList();
    },
    changeSort(e) {
      this.searchForm.sort = e.key;
      this.searchForm.order = e.order;
      if (e.order == "normal") {
        this.searchForm.order = "";
      }
      this.getDataList();
    },
    add(v) {
      this.transferData.type = v;
      this.currView = "addEdit";
    },
    edit(v) {
      // 转换null为""
      for (let attr in v) {
        if (v[attr] == null) {
          v[attr] = "";
        }
      }
      let str = JSON.stringify(v);
      let data = JSON.parse(str);
      data.type = 1;
      this.transferData = data;
      this.currView = "addEdit";
    },
    sendDetail(v) {
      // 转换null为""
      for (let attr in v) {
        if (v[attr] == null) {
          v[attr] = "";
        }
      }
      this.transferData = v;
      this.showDetail = true;
    },
    remove(v) {
      this.$Modal.confirm({
        title: "确认删除",
        // 记得确认修改此处
        content: "您确认要删除ID为 " + v.id + " 的数据?",
        loading: true,
        onOk: () => {
          // 删除
          deleteMessage({ ids: v.id }).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.clearSelectAll();
              this.$Message.success("操作成功");
              this.getDataList();
            }
          });
        },
      });
    },
    showSelect(e) {
      this.selectList = e;
    },
    clearSelectAll() {
      this.$refs.table.selectAll(false);
    },
    delAll() {
      if (this.selectList.length <= 0) {
        this.$Message.warning("您还未选择要删除的数据");
        return;
      }
      this.$Modal.confirm({
        title: "确认删除",
        content: "您确认要删除所选的 " + this.selectList.length + " 条数据?",
        loading: true,
        onOk: () => {
          let ids = "";
          this.selectList.forEach(function (e) {
            ids += e.id + ",";
          });
          ids = ids.substring(0, ids.length - 1);
          // 批量删除
          deleteMessage({ ids: ids }).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.clearSelectAll();
              this.getDataList();
            }
          });
        },
      });
    },
  },
  mounted() {
    this.init();
  },
};
</script>