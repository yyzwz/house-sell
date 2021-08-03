<style lang="less">
@import "@/styles/table-common.less";
</style>
<template>
  <div>
    <Drawer
      title="消息发送详情"
      v-model="visible"
      width="800"
      draggable
      class="search"
    >
      <Row class="operation">
        <Button v-hasRole="'ROLE_ADMIN'" @click="delAll" icon="md-trash"
          >批量删除</Button
        >
        <Button @click="getDataList" icon="md-refresh">刷新</Button>
        <Input
          v-model="searchForm.userId"
          suffix="ios-search"
          @on-change="getDataList"
          placeholder="输入发送用户ID搜索"
          clearable
          style="width: 250px"
        />
      </Row>
      <Alert show-icon>
        已选择
        <span class="select-count">{{ selectList.length }}</span> 项
        <a class="select-clear" @click="clearSelectAll">清空</a>
      </Alert>
      <Table
        :loading="loading"
        border
        :columns="columns"
        :data="data"
        ref="table"
        sortable="custom"
        @on-sort-change="changeSort"
        @on-selection-change="changeSelect"
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
          transfer
        ></Page>
      </Row>
    </Drawer>
  </div>
</template>

<script>
import { getMessageSendData, deleteMessageSend } from "@/api/index";
export default {
  name: "detail",
  props: {
    value: {
      type: Boolean,
      default: false,
    },
    transferData: {
      type: Object,
    },
  },
  data() {
    return {
      visible: this.value,
      loading: true, // 表单加载状态
      selectList: [], // 多选数据
      searchForm: {
        pageNumber: 1, // 当前页数
        pageSize: 10, // 页面大小
        sort: "createTime",
        order: "desc",
        userId: "",
        messageId: "",
        status: "",
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
          title: "发送用户",
          key: "username",
          sortable: true,
          align: "center",
          minWidth: 150,
          render: (h, params) => {
            return h(
              "Tooltip",
              {
                props: {
                  placement: "top",
                  content: params.row.username,
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
          },
        },
        {
          title: "状态",
          key: "status",
          align: "center",
          sortable: true,
          width: 110,
          render: (h, params) => {
            let color = "",
              re = "";
            if (params.row.status == 0) {
              color = "default";
              re = "未读";
            } else if (params.row.status == 1) {
              color = "green";
              re = "已读";
            } else if (params.row.status == 2) {
              color = "orange";
              re = "回收站";
            }
            return h("div", [
              h(
                "Tag",
                {
                  props: {
                    color: color,
                  },
                },
                re
              ),
            ]);
          },
          filters: [
            {
              label: "未读",
              value: 0,
            },
            {
              label: "已读",
              value: 1,
            },
            {
              label: "回收站",
              value: 2,
            },
          ],
          filterMultiple: false,
          filterRemote: (e) => {
            let v = "";
            if (e.length > 0) {
              v = e[0];
            }
            this.searchForm.status = v;
            this.searchForm.pageNumber = 1;
            this.getDataList();
          },
        },
        {
          title: "消息参数",
          key: "params",
          tooltip: true,
          width: 200,
          render: (h, params) => {
            let text = "无";
            if (params.row.params) {
              text = params.row.params;
            }
            return h("span", text);
          },
        },
        {
          title: "创建时间",
          key: "createTime",
          sortable: true,
          sortType: "desc",
          width: 170,
        },
        {
          title: "更新时间",
          key: "updateTime",
          sortable: true,
          width: 170,
        },
        {
          title: "操作",
          key: "action",
          align: "center",
          fixed: "right",
          width: 80,
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
                "删除"
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
    init() {},
    changePage(v) {
      this.searchForm.pageNumber = v;
      this.getDataList();
      this.clearSelectAll();
    },
    changePageSize(v) {
      this.searchForm.pageSize = v;
      this.getDataList();
    },
    changeSort(e) {
      this.searchForm.sort = e.key;
      this.searchForm.order = e.order;
      if (e.order == "normal") {
        this.searchForm.order = "";
      }
    },
    getDataList() {
      this.loading = true;
      getMessageSendData(this.searchForm).then((res) => {
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
    remove(v) {
      this.$Modal.confirm({
        title: "确认删除",
        // 记得确认修改此处
        content: "您确认要删除该条数据?",
        loading: true,
        onOk: () => {
          // 删除
          deleteMessageSend({ ids: v.id }).then((res) => {
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
    clearSelectAll() {
      this.$refs.table.selectAll(false);
    },
    changeSelect(e) {
      this.selectList = e;
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
          deleteMessageSend({ ids: ids }).then((res) => {
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
    setCurrentValue(value) {
      if (value === this.visible) {
        return;
      }
      this.searchForm.messageId = this.transferData.id;
      this.getDataList();
      this.visible = value;
    },
  },
  watch: {
    value(val) {
      this.setCurrentValue(val);
    },
    visible(value) {
      this.$emit("input", value);
    },
  },
  mounted() {},
};
</script>