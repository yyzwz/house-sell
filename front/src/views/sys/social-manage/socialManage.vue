<style lang="less">
@import "@/styles/table-common.less";
@import "./socialManage.less";
</style>
<template>
  <div class="search">
    <Card>
      <Tabs v-model="tabName" :animated="false" @on-click="changeTab">
        <TabPane label="Github" name="0"></TabPane>
        <TabPane label="QQ" name="1"></TabPane>
        <TabPane label="微信" name="2"></TabPane>
        <TabPane label="微博" name="3"></TabPane>
        <TabPane label="钉钉" name="4"></TabPane>
        <TabPane label="企业微信" name="5"></TabPane>
      </Tabs>
      <Row v-show="openSearch" @keydown.enter.native="handleSearch">
        <Form ref="searchForm" :model="searchForm" inline :label-width="100">
          <FormItem label="社交账号昵称" prop="username">
            <Input
              type="text"
              v-model="searchForm.username"
              placeholder="请输入第三方社交账号昵称"
              clearable
              style="width: 200px"
            />
          </FormItem>
          <FormItem label="绑定账号" prop="relateUsername">
            <Input
              type="text"
              v-model="searchForm.relateUsername"
              placeholder="请输入系统登录账号"
              clearable
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
          <FormItem style="margin-left: -35px" class="br">
            <Button @click="handleSearch" type="primary" icon="ios-search"
              >搜索</Button
            >
            <Button @click="handleReset">重置</Button>
          </FormItem>
        </Form>
      </Row>
      <Row class="operation">
        <Button @click="delAll" icon="md-trash">批量删除解绑</Button>
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
import { getRelatedListData, unRelate } from "@/api/index";
import { shortcuts } from "@/libs/shortcuts";
export default {
  name: "social-manage",
  data() {
    return {
      openSearch: true,
      openTip: true,
      tabName: "0",
      data: [],
      total: 0,
      selectDate: null,
      options: {
        shortcuts: shortcuts,
      },
      loading: true, // 表单加载状态
      selectList: [], // 多选数据
      searchForm: {
        // 搜索框对应data对象
        platform: "0",
        username: "",
        relateUsername: "",
        pageNumber: 1, // 当前页数
        pageSize: 10, // 页面大小
        sort: "createTime", // 默认排序字段
        order: "desc", // 默认排序方式
        startDate: "", // 起始时间
        endDate: "", // 终止时间
      },
      columns: [
        // 表头
        {
          type: "selection",
          width: 60,
          align: "center",
          fixed: "left",
        },
        {
          type: "index",
          width: 60,
          align: "center",
          fixed: "left",
        },
        {
          title: "社交账号昵称",
          key: "username",
          minWidth: 180,
          sortable: true,
          fixed: "left",
          render: (h, params) => {
            if (
              this.$route.meta.permTypes &&
              this.$route.meta.permTypes.includes("view")
            ) {
              return h("span", params.row.username);
            } else {
              return h("span", "你无权查看该数据");
            }
          },
        },
        {
          title: "头像",
          key: "avatar",
          width: 80,
          align: "center",
          render: (h, params) => {
            if (params.row.avatar) {
              return h("Avatar", {
                props: {
                  src: params.row.avatar,
                },
              });
            } else {
              let name = "",
                color = "";
              // 过滤特殊字符
              let username = params.row.username.replace(
                /[^\u4e00-\u9fa5\w]/g,
                ""
              );
              if (username.length == 1) {
                name = username;
                color = "#3296fa";
              } else if (username.length > 1) {
                var pattern = new RegExp("^[a-zA-Z0-9]+$");
                if (pattern.test(username)) {
                  // 英文取首字母
                  name = username.substring(0, 2);
                } else {
                  name = username.slice(-2);
                }
                color = "#3296fa";
              }
              return h(
                "Avatar",
                {
                  style: {
                    backgroundColor: color,
                  },
                },
                name
              );
            }
          },
        },
        {
          title: "openID",
          key: "openId",
          minWidth: 200,
          sortable: true,
          render: (h, params) => {
            if (
              this.$route.meta.permTypes &&
              this.$route.meta.permTypes.includes("view")
            ) {
              return h("span", params.row.openId);
            } else {
              return h("span", "你无权查看该数据");
            }
          },
        },
        {
          title: "是否绑定用户",
          key: "isRelated",
          width: 150,
          align: "center",
          render: (h, params) => {
            if (params.row.isRelated) {
              return h("div", [
                h(
                  "Tag",
                  {
                    props: {
                      color: "blue",
                    },
                  },
                  "已绑定"
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
                  "未绑定"
                ),
              ]);
            }
          },
        },
        {
          title: "绑定用户",
          key: "relateUsername",
          minWidth: 200,
          sortable: true,
          align: "center",
          render: (h, params) => {
            if (params.row.relateUsername) {
              return h(
                "Tooltip",
                {
                  props: {
                    placement: "top",
                    content: params.row.relateUsername,
                  },
                },
                [
                  h(
                    "Tag",
                    {
                      style: {
                        "margin-right": "8px",
                      },
                      props: {
                        type: "border",
                      },
                    },
                    params.row.nickname
                  ),
                ]
              );
            }
          },
        },
        {
          title: "创建时间",
          key: "createTime",
          minWidth: 180,
          sortable: true,
          sortType: "desc",
        },
        {
          title: "操作",
          key: "action",
          width: 130,
          align: "center",
          fixed: "right",
          render: (h, params) => {
            return h("div", [
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.remove(params.row);
                    },
                  },
                },
                "删除解绑"
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
    changeTab(v) {
      this.searchForm.pageNumber = 1;
      this.searchForm.platform = v;
      this.getDataList();
    },
    changePage(v) {
      this.searchForm.pageNumber = v;
      this.getDataList();
      this.clearSelectAll();
    },
    changePageSize(v) {
      this.tabName.pageSize = v;
      this.getDataList();
    },
    selectDateRange(v) {
      this.searchForm.startDate = v[0];
      this.searchForm.endDate = v[1];
    },
    getDataList() {
      this.loading = true;
      getRelatedListData(this.searchForm).then((res) => {
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
    remove(v) {
      this.$Modal.confirm({
        title: "确认删除解绑",
        // 记得确认修改此处
        content: "您确认要删除解绑该条数据 ?",
        loading: true,
        onOk: () => {
          // 删除
          let params = {
            ids: v.id,
          };
          unRelate(params).then((res) => {
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
        title: "确认删除解绑",
        content:
          "您确认要删除解绑所选的 " + this.selectList.length + " 条数据?",
        loading: true,
        onOk: () => {
          let ids = "";
          this.selectList.forEach(function (e) {
            ids += e.id + ",";
          });
          ids = ids.substring(0, ids.length - 1);
          let params = {
            ids: ids,
          };
          unRelate(params).then((res) => {
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