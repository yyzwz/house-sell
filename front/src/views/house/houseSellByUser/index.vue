<template>
<div class="search">
    <Card>
        <Row v-show="openSearch" @keydown.enter.native="handleSearch">
            <Form ref="searchForm" :model="searchForm" inline :label-width="0">
                <Form-item ref="searchForm" :model="searchForm" inline :label-width="0" style="display:flex;">
                    <Form-item label="" prop="houseType">
                        <Select v-model="searchForm.houseType" placeholder="请选择户型" clearable style="width:200px">
                            <Option value="单身公寓">单身公寓</Option>
                            <Option value="三室一厅">三室一厅</Option>
                            <Option value="四室一厅">四室一厅</Option>
                            <Option value="五室两厅">五室两厅</Option>
                            <Option value="其他">其他</Option>
                        </Select>
                    </Form-item>
                    <Form-item label="" prop="address">
                        <Input v-model="searchForm.address" clearable placeholder="请输入地址" style="width:200px" />
                    </Form-item>
                    <Form-item label="" prop="mobile">
                        <Input v-model="searchForm.mobile" clearable placeholder="请输入电话" style="width:200px" />
                    </Form-item>
                    <Form-item style="margin-left:10px;" class="br">
                        <Button @click="handleSearch" type="primary" icon="ios-search" size="small" ghost>搜索</Button>
                        <Button @click="handleReset" type="warning" size="small" icon="md-refresh" ghost>重置</Button>
                    </Form-item>
                    <Form-item style="position:fixed;right:50px;top:130px">
                        <Button type="info" @click="showFilterPanelFlag = !showFilterPanelFlag" class="showFilterPanelFlag" icon="md-settings" size="small" ghost>
                            列筛选</Button>
                        <Button type="warning" @click="modal1 = true" class="showFilterPanelFlag" icon="ios-help-circle-outline" size="small" ghost>
                            使用教程</Button>
                        <Modal v-model="modal1" title="使用教程">
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
                        <div v-for="item in mycolumns" :key="item.key">
                            <Checkbox :label="item.title" style="margin: 2px 5px"></Checkbox>
                        </div>
                    </CheckboxGroup>
                </div>
            </transition>
        </Row>
        <Row v-show="openTip"> </Row>
        <Row>
            <Table :loading="loading" :height="tableHeight" border stripe size="small" :columns="columns" :data="data" ref="table" sortable="custom" @on-sort-change="changeSort" @on-selection-change="changeSelect" @on-row-click="rowClick" :row-class-name="rowClassNmae"></Table>
        </Row>
        <Row type="flex" justify="end" class="page">
            <Page :current="searchForm.pageNumber" :total="total" :page-size="searchForm.pageSize" @on-change="changePage" @on-page-size-change="changePageSize" :page-size-opts="[15,20,50]" size="small" show-total show-elevator show-sizer></Page>
        </Row>
    </Card>
    <Modal v-model="frontOrderModal" title="意向预定" @on-ok="frontBuyFx">
        <Row :gutter="16">
            <Form :label-width="100" label-position="left" style="width:90%">
                <FormItem label="价格（万元）" prop="houseMoney">
                    <InputNumber v-model="tempHouseMoney" min="0" max="5000000" style="width:100%"></InputNumber>
                </FormItem>
                <FormItem label="备注" prop="remark">
                    <Input v-model="tempRemark" type="textarea" :rows="4" clearable style="width:100%" />
                </FormItem>
            </Form>
        </Row>
    </Modal>
</div>
</template>

<script>
import {
    getHouseSellList,
    frontBuy
} from "./api.js";
export default {
    name: "single-window",
    components: {},
    data() {
        return {
            tempHouseMoney: "",
            tempRemark: "",
            frontOrderModal: false,
            tableHeight: 0,
            selected: [
                "选择",
                "序号",
                "户型",
                "面积",
                "图片1",
                "图片2",
                "图片3",
                "价格",
                "电话",
                "联系人",
                "地址",
                "备注",
                "操作",
            ],
            modal1: false,
            openSearch: true, // 显示搜索
            openTip: true, // 显示提示
            formData: {},
            loading: true, // 表单加载状态
            searchForm: { // 搜索框初始化对象
                pageNumber: 1, // 当前页数
                pageSize: 15, // 页面大小
                sort: "createTime", // 默认排序字段
                order: "desc", // 默认排序方式
                status: -1
            },
            selectList: [], // 多选数据
            selectCount: 0, // 多选计数
            selectRow: 0,
            columns: [
                // 表头
                {
                    type: "selection",
                    width: 60,
                    title: "选择",
                    align: "center",
                    fixed: "left",
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
                    title: "户型",
                    key: "houseType",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "面积",
                    key: "houseSize",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "图片1",
                    key: "imageUrl",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                    render: (h, params) => {
                        return h('div', [
                            h('img', {
                                attrs: {
                                    src: params.row.imageUrl
                                },
                                style: {
                                    width: 'auto',
                                    height: '50px'
                                }
                            }),
                        ]);
                    }
                },
                {
                    title: "图片2",
                    key: "imageUrl2",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                    render: (h, params) => {
                        return h('div', [
                            h('img', {
                                attrs: {
                                    src: params.row.imageUrl2
                                },
                                style: {
                                    width: 'auto',
                                    height: '50px'
                                }
                            }),
                        ]);
                    }
                },
                {
                    title: "图片3",
                    key: "imageUrl3",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                    render: (h, params) => {
                        return h('div', [
                            h('img', {
                                attrs: {
                                    src: params.row.imageUrl3
                                },
                                style: {
                                    width: 'auto',
                                    height: '50px'
                                }
                            }),
                        ]);
                    }
                },
                {
                    title: "价格",
                    key: "houseMoney",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "电话",
                    key: "mobile",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "联系人",
                    key: "houseOwner",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "地址",
                    key: "address",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "备注",
                    key: "remark",
                    minWidth: 120,
                    tooltip: true,
                    sortable: false,
                },
                {
                    title: "操作",
                    key: "action",
                    align: "center",
                    width: 200,
                    fixed: "right",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button", {
                                    props: {
                                        type: "primary",
                                        size: "small",
                                        icon: "ios-create-outline",
                                        ghost: true
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            this.openFrontBuyFx(params.row);
                                        }
                                    }
                                },
                                "预定意向"
                            ),
                        ]);
                    }
                }
            ],
            data: [], // 表单数据
            pageNumber: 1, // 当前页数
            pageSize: 10, // 页面大小
            total: 0, // 表单数据总数
            showFilterPanelFlag: false,
            mySelectRow: {}
        };
    },
    methods: {
        init() {
            this.getDataList();
        },
        openFrontBuyFx(e) {
            this.mySelectRow = e;
            this.frontOrderModal = true;
        },
        frontBuyFx() {
            var that = this;
            this.$Modal.confirm({
                title: "确认提交预定单",
                content: "您确认提交预定单?",
                loading: true,
                onOk: () => {
                    frontBuy({
                        id: that.mySelectRow.id,
                        money: that.tempHouseMoney,
                        remark: that.tempRemark
                    }).then(res => {
                        this.$Modal.remove();
                        if (res.success) {
                            this.$Message.success("操作成功");
                            that.getDataList();
                        }
                    });
                }
            });
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
        rowClick(row, index) {
            this.selectRow = row;
        },
        rowClassNmae(row, index) {
            if (row.id == this.selectRow.id) {
                return "rowClassNmaeColor";
            }
            return "";
        },
        handleSearch() {
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 15;
            this.getDataList();
        },
        handleReset() {
            this.$refs.searchForm.resetFields();
            this.searchForm.pageNumber = 1;
            this.searchForm.pageSize = 15;
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
            this.selectCount = e.length;
        },
        getDataList() {
            this.loading = true;
            getHouseSellList(this.searchForm).then(res => {
                this.loading = false;
                if (res.success) {
                    this.data = res.result.records;
                    this.total = res.result.total;
                }
            });
        }
    },
    mounted() {
        this.init();
        this.tableHeight = Number(window.innerHeight - 273);
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
        selected: function (newcolumns) {
            let showcolumns = [];
            for (var i = 0; i < this.mycolumns.length; i++) {
                var item = this.mycolumns[i];
                if (item.title == undefined) showcolumns.push(item);
                else if (newcolumns.contains(item.title)) showcolumns.push(item);
            }
            this.columns = showcolumns;
        },
    },
};
</script>

<style lang="less">
// @import "../../../styles/table-common.less";
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
    height: 200px;
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
    background-color: #b0b3b6 !important;
    color: #ffffff !important;
    font-size: 12px;
}
</style>
