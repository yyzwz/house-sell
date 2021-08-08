<template>
  <div class="search">
    <add v-if="currView=='add'" @close="currView='index'" @submited="submited" />
    <edit v-if="currView=='edit'" @close="currView='index'" @submited="submited" :data="formData" />
    <Card v-show="currView=='index'">
        <Row v-show="openSearch" @keydown.enter.native="handleSearch">
          <Form ref="searchForm" :model="searchForm" inline :label-width="0">
            <Form-item
              ref="searchForm"
              :model="searchForm"
              inline
              :label-width="0"
              style="display:flex;"
            >
            <FormItem label="" prop="title">
              <Input type="text" v-model="searchForm.title" placeholder="请输入标题" @on-blur="getDataList" clearable style="width: 200px"/>
            </FormItem>
            <Form-item style="margin-left:10px;" class="br">
              <Button
                @click="handleSearch"
                type="primary"
                icon="ios-search"
                size="small"
              >搜索</Button>
              <Button
                @click="handleReset"
                type="warning"
                size="small"
                icon="md-refresh"
              >重置</Button>
              <Button
                @click="add"
                type="info"
                size="small"
                icon="md-add"
              >添加</Button>
              <Button
                @click="delAll"
                type="error"
                icon="md-trash"
                size="small"
              >删除</Button>
              <Button
                @click="excelData"
                type="success"
                icon="md-paper-plane"
                size="small"
              >导出</Button>
            </Form-item>
            <Alert
              show-icon
              style="position:relative;top:-33px;left:600px;width:60%"
            >
              已选择
              <span class="select-count">{{ selectCount }}</span> 项
              <a class="select-clear" @click="clearSelectAll">清空</a>
            </Alert>
            <Form-item style="position:fixed;right:50px;top:130px">
              <Button
                type="info"
                @click="showFilterPanelFlag = !showFilterPanelFlag"
                class="showFilterPanelFlag"
                icon="md-settings"
                size="small"
              >
              </Button>
              <Button
                type="warning"
                @click="modal1 = true"
                class="showFilterPanelFlag"
                icon="ios-help-circle-outline"
                size="small"
              >
              </Button>
              <Modal v-model="modal1" title="教程">
                <p>1.XXXXXXXXXXXXXXXXXXXXXXXX</p>
                <p>2.XXXXXXXXXXXXXXXXXXXXXXXX</p>
                <p>3.XXXXXXXXXXXXXXXXXXXXXXXX</p>
              </Modal>
            </Form-item>
          </Form-item>
          </Form>
        </Row>
      <Row class="operation" style="position:relative;">
        <transition>
          <div v-show="showFilterPanelFlag" class="filter-panel">
            <CheckboxGroup v-model="selected">
              <div
                v-for="item in mycolumns"
                :key="item.key"
                v-if="item.title != null"
              >
                <Checkbox
                  :label="item.title"
                  style="margin: 2px 5px"
                ></Checkbox>
              </div>
            </CheckboxGroup>
          </div>
        </transition>
      </Row>
      <Row v-show="openTip"> </Row>
      <Row>
        <Table
          :loading="loading"
          border
          :columns="columns"
          :data="data"
          ref="table"
          sortable="custom"
          @on-sort-change="changeSort"
          @on-selection-change="changeSelect"
          @on-row-click="rowClick"
          :row-class-name="rowClassNmae"
        ></Table>
      </Row>
      <Row type="flex" justify="end" class="page">
        <Page
          :current="searchForm.pageNumber"
          :total="total"
          :page-size="searchForm.pageSize"
          @on-change="changePage"
          @on-page-size-change="changePageSize"
          :page-size-opts="[10,20,50]"
          size="small"
          show-total
          show-elevator
          show-sizer
        ></Page>
      </Row>
    </Card>
    <Modal
        v-model="auditModal"
        title="审核确认"
        @on-ok="auditFx">
        <Row>
          <Select v-model="auditStatus" clearable>
            <Option value="1">同意</Option>
            <Option value="2">退回</Option>
          </Select>
        </Row>
    </Modal>
  </div>
</template>

<script>
import { getVillageIntroduceList, deleteVillageIntroduce,audit } from "./api.js";
import add from "./add.vue";
import edit from "./edit.vue";
import { shortcuts } from "@/libs/shortcuts";
export default {
  name: "villageIntroduce",
  components: {
    add,
    edit,
  },
  data() {
    return {
      auditRow: {},
      auditStatus: "1",
      auditModal: false,
      selected: [
        "选择",
        "序号",
        "标题",
        "类型",
        "是否审核",
        "创建时间",
        "查看",
        "操作",
      ],
      mycolumns: [],
      modal1: false,
      selectCount: 0,
      selectRow: 0,
      showFilterPanelFlag: false,
      openSearch: true, // 显示搜索
      openTip: true, // 显示提示
      formData: {},
      currView: "index",
      loading: true, // 表单加载状态
      searchForm: { // 搜索框初始化对象
        pageNumber: 1, // 当前页数
        pageSize: 10, // 页面大小
        sort: "createTime", // 默认排序字段
        order: "desc", // 默认排序方式
        auditStatus: 0
      },
      selectList: [], // 多选数据
      columns: [
      // 表头
      {
        type: "selection",
        width: 60,
        title: "选择",
        fixed: "left",
        align: "center"
      },
      {
        title: "序号",
        width: 85,
        align: "center",
        fixed: "left",
        sortType: true,
        render: (h, params) => {
          return h(
            "span",
            params.index +
              (this.searchForm.pageNumber - 1) * this.searchForm.pageSize +
              1
          );
        },
      },
      {
        title: "标题",
        key: "title",
        fixed: "left",
        minWidth: 200,
        align: "center",
        tooltip: true,
        sortable: false,
      },
      {
        title: "类型",
        key: "type",
        minWidth: 120,
        sortable: false,
        align: "center",
        tooltip: true,
        render: (h, params) => {
          if (params.row.auditStatus == 1) {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    color: "#333333",
                  },
                },
                "外链文章"
              ),
            ]);
          } else {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    color: "#111111",
                  },
                },
                "普通文章"
              ),
            ]);
          }
        },
      },
      {
        title: "是否审核",
        key: "auditStatus",
        minWidth: 120,
        sortable: false,
        align: "center",
        tooltip: true,
        render: (h, params) => {
          if (params.row.auditStatus == 1) {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    color: "#666666",
                  },
                },
                "审核通过"
              ),
            ]);
          } else if (params.row.auditStatus == 2) {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    color: "#ff0000",
                  },
                },
                "审核退回"
              ),
            ]);
          } else {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    color: "#111111",
                  },
                },
                "未审核"
              ),
            ]);
          }
        },
      },
      {
        title: "优先级",
        key: "level",
        minWidth: 120,
        sortable: false,
        align: "center",
        tooltip: true,
      },
      {
        title: "创建时间",
        key: "createTime",
        sortable: true,
        sortType: "desc",
        minWidth: 150,
        align: "center",
        tooltip: true,
      },
      {
        title: "创建者",
        key: "createBy",
        sortable: true,
        sortType: "desc",
        minWidth: 120,
        align: "center",
        tooltip: true,
      },
      {
        title: "修改时间",
        key: "updateTime",
        sortable: true,
        sortType: "desc",
        minWidth: 150,
        align: "center",
        tooltip: true,
      },
      {
        title: "修改者",
        key: "updateBy",
        sortable: true,
        sortType: "desc",
        minWidth: 120,
        align: "center",
        tooltip: true,
      },
      {
        title: "查看",
        key: "look",
        align: "center",
        width: 75,
        fixed: "right",
        render: (h, params) => {
          return h("div", [
            h(
              "a",
              {
                on: {
                  click: () => {
                    this.lookHtml(params.row);
                  }
                }
              },
              "查看"
            ),
          ]);
        }
      },
      {
        title: "操作",
        key: "action",
        align: "center",
        fixed: "right",
        width: 150,
        render: (h, params) => {
          if(this.permTypes.includes("other")) {
            return h("div", [
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.edit(params.row);
                    }
                  }
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
                      this.remove(params.row);
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      },
      {
        title: "审核",
        key: "audit1",
        align: "center",
        fixed: "right",
        width: 150,
        render: (h, params) => {
          if(this.permTypes.includes("other")) {
            return h("div", [
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.openAuditWindow(params.row);
                    }
                  }
                },
                "审核"
              )
            ]);
          }
        }
      }
    ],
      data: [], // 表单数据
      pageNumber: 1, // 当前页数
      pageSize: 10, // 页面大小
      total: 0, // 表单数据总数
      permTypes: []
    };
  },
  methods: {
    init() {
      if (this.$route.meta.permTypes) {
        this.permTypes = this.$route.meta.permTypes;
      }
      if(this.permTypes.includes("other")) {
        this.getDataList();
      } else {
        this.searchForm.auditStatus = 666;
        this.getDataList();
      }
    },
    rowClick(row, index) {
      this.selectRow = row;
    },
    rowClassNmae(row, index) {
      if (row.id == this.selectRow.id) {
        return "rowClassNmaeColor";
      }
      return "";
    },
    submited() {
      this.currView = "index";
      this.getDataList();
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
    handleSearch() {
      this.searchForm.pageNumber = 1;
      this.searchForm.pageSize = 10;
      this.getDataList();
    },
    handleReset() {
      this.$refs.searchForm.resetFields();
      this.searchForm.pageNumber = 1;
      this.searchForm.pageSize = 10;
      // 重新加载数据
      this.getDataList();
    },
    changeSort(e) {
      this.searchForm.sort = e.key;
      this.searchForm.order = e.order;
      if (e.order === "normal") {
        this.searchForm.order = "";
      }
      this.getDataList();
    },
    clearSelectAll() {
      this.$refs.table.selectAll(false);
    },
    changeSelect(e) {
      this.selectList = e;
    },
    getDataList() {
      this.loading = true;
        getVillageIntroduceList(this.searchForm).then(res => {
          this.loading = false;
          if (res.success) {
            this.data = res.result.records;
            this.total = res.result.total;
          }
        });
    },
    lookHtml(e) {
      if(e.type == "0") {
        window.open("http://127.0.0.1:8080/xboot/villageIntroduce/getHtmlPage?id=" + e.id);
      } else {
        window.open(e.url);
      }
    },
    add() {
      this.currView = "add";
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
      this.formData = data;
      this.currView = "edit";
    },
    openAuditWindow(e) {
      this.auditRow = e;
      this.auditModal = true;
    },
    auditFx() {
      var that = this;
      audit({id: that.auditRow.id,status: that.auditStatus}).then(res => {
        if(res.success) {
          this.$Message.success("操作成功");
          that.getDataList();
        }
      })
    },
    remove(v) {
      this.$Modal.confirm({
        title: "确认删除",
        // 记得确认修改此处
        content: "您确认要删除该条数据?",
        loading: true,
        onOk: () => {
          // 删除
            deleteVillageIntroduce({ids: v.id}).then(res => {
              this.$Modal.remove();
              if (res.success) {
                this.$Message.success("操作成功");
                this.clearSelectAll();
                this.getDataList();
              }
            });
        }
      });
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
          this.selectList.forEach(function(e) {
            ids += e.id + ",";
          });
          ids = ids.substring(0, ids.length - 1);
          // 批量删除
          deleteVillageIntroduce({ids: ids}).then(res => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.clearSelectAll();
              this.getDataList();
            }
          });
        }
      });
    }
  },
  mounted() {
    this.init();
    this.mycolumns = this.columns;
    let showcolumns = [];
    for (var i = 0; i < this.selected.length; i++) {
      var item = this.selected[i];
      for (var j = 0; j < this.columns.length; j++) {
        if (this.columns[j].title == item) {
          showcolumns.push(this.columns[j]);
        }
      }
    }
    this.columns = showcolumns;
  },  
  watch: {
    selected: function(newcolumns) {
      let showcolumns = [];
      for (var i = 0; i < this.mycolumns.length; i++) {
        var item = this.mycolumns[i];
        if (item.title == undefined) showcolumns.push(item);
        else if (newcolumns.includes(item.title)) showcolumns.push(item);
      }
      this.columns = showcolumns;
    },
  },
};
</script>
<style lang="less">
.search {
    .operation {
      margin-bottom: 2vh;
    }
    .select-count {
      font-weight: 600;
      color: #40a9ff;
    }
    .select-clear {
      margin-left: 10px;
    }
    .page {
      margin-top: 2vh;
    }
    .drop-down {
      margin-left: 5px;
    }
  }
  .filter-panel {
    width: 166px;
    min-height: 120px;
    height: 100px;
    position: absolute;
    background-color: white;
    z-index: 9999;
    margin-left: 1px;
    overflow-y: scroll;
    border: 1px solid blue;
    top: 35px;
    right: 10px;
  }
  
  .openSearch {
    position: absolute;
    right: 240px;
  }
  .openTip {
    position: absolute;
    right: 130px;
  }
  .showFilterPanelFlag {
    position: static !important;
    right: 10px;
    margin-right: 10px;
  }
  .ivu-table td {
    height: 38px !important;
  }
  .ivu-table-cell-with-expand {
    height: 38px !important;
    line-height: 38px !important;
  }
  .ivu-table .rowClassNmaeColor td {
    background-color: #2db7f5 !important;
    color: #fff !important;
    font-size: 18px;
  }
</style>