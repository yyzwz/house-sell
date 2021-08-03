<style lang="less">
@import "./message.less";
</style>

<template>
  <div class="message-main-content">
    <div class="message-mainlist-content">
      <div>
        <Button
          @click="setCurrentMesType('unread')"
          size="large"
          long
          type="text"
        >
          <div class="mes-wrap">
            <transition name="mes-current-type-btn">
              <Icon
                v-show="currentMesType == 'unread'"
                type="md-checkmark"
              ></Icon>
            </transition>
            <span class="mes-type-btn-text">未读消息</span>
            <Badge
              class="message-count-badge-outer"
              class-name="message-count-badge-red"
              :count="unreadCount"
            ></Badge>
          </div>
        </Button>
      </div>
      <div>
        <Button
          @click="setCurrentMesType('hasread')"
          size="large"
          long
          type="text"
        >
          <div class="mes-wrap">
            <transition name="mes-current-type-btn">
              <Icon
                v-show="currentMesType == 'hasread'"
                type="md-checkmark"
              ></Icon>
            </transition>
            <span class="mes-type-btn-text">已读消息</span>
            <Badge
              class="message-count-badge-outer"
              class-name="message-count-badge"
              :count="hasreadCount"
            ></Badge>
          </div>
        </Button>
      </div>
    </div>
    <div class="message-content-content">
      <transition name="view-message">
        <div v-if="showMesList" class="message-title-list-content">
          <Table
            ref="messageList"
            :loading="loading"
            :columns="columns"
            :data="mesList"
          ></Table>
          <div class="message-page">
            <Poptip
              confirm
              transfer
              title="确定标记所有消息为已读？"
              @on-ok="readAll"
              v-if="currentMesType == 'unread'"
            >
              <Button icon="md-notifications-off" :disabled="unreadCount <= 0"
                >全部标记已读</Button
              >
            </Poptip>
            <Poptip
              confirm
              transfer
              title="确定彻底删除所有已读消息？"
              @on-ok="deleteAll"
              v-if="currentMesType == 'hasread'"
            >
              <Button icon="md-trash" :disabled="hasreadCount <= 0"
                >全部彻底删除</Button
              >
            </Poptip>
            <Page
              :current="params.pageNumber"
              :total="total"
              :page-size="params.pageSize"
              @on-change="changePage"
              @on-page-size-change="changePageSize"
              :page-size-opts="[5, 10]"
              size="small"
              show-total
              show-elevator
              show-sizer
              class="page-fix"
            ></Page>
          </div>
        </div>
      </transition>
      <transition name="back-message-list">
        <div v-if="!showMesList" class="message-view-content-content">
          <div class="message-content-top-bar">
            <span class="mes-back-btn-content">
              <Button type="text" @click="backList">
                <Icon type="ios-arrow-back"></Icon>&nbsp;&nbsp;返回
              </Button>
            </span>
            <h3 class="mes-title">{{ mes.title }}</h3>
          </div>
          <p class="mes-time-content">
            <Icon type="android-time"></Icon>
            &nbsp;&nbsp;{{ mes.time }}
          </p>
          <div class="message-content-body">
            <p class="message-content" v-html="mes.content">
              {{ mes.content }}
            </p>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import {
  getMessageSendData,
  editMessageSend,
  deleteMessageSend,
  allMessageSend,
} from "@/api/index";
export default {
  name: "message_index",
  data() {
    const markAsreadBtn = (h, params) => {
      return h(
        "Button",
        {
          props: {
            icon: "md-eye-off",
            size: "small",
          },
          on: {
            click: () => {
              // 标记已读
              let v = params.row;
              // 转换null为""
              for (let attr in v) {
                if (v[attr] == null) {
                  v[attr] = "";
                }
              }
              let str = JSON.stringify(v);
              let data = JSON.parse(str);
              data.status = 1;
              this.loading = true;
              editMessageSend(data).then((res) => {
                this.loading = false;
                if (res.success) {
                  this.$Message.success("操作成功");
                  this.getCount();
                  this.refreshMessage();
                }
              });
            },
          },
        },
        "标为已读"
      );
    };
    const deleteRealBtn = (h, params) => {
      return h(
        "Button",
        {
          props: {
            icon: "md-trash",
            size: "small",
          },
          on: {
            click: () => {
              // 彻底删除
              let v = params.row;
              this.loading = true;
              deleteMessageSend({ ids: v.id }).then((res) => {
                this.loading = false;
                if (res.success) {
                  this.$Message.success("操作成功");
                  this.getCount();
                  this.refreshMessage();
                }
              });
            },
          },
        },
        "删除"
      );
    };
    return {
      loading: true,
      params: {
        userId: JSON.parse(Cookies.get("userInfo")).id,
        status: 0,
        pageNumber: 1, // 当前页数
        pageSize: 10, // 页面大小
        sort: "createTime", // 默认排序字段
        order: "desc", // 默认排序方式
      },
      total: 0,
      mesList: [],
      currentMesType: "unread",
      showMesList: true,
      unreadCount: 0,
      hasreadCount: 0,
      mes: {
        title: "",
        time: "",
        content: "",
      },
      columns: [
        {
          type: "index",
          width: 60,
          align: "center",
        },
        {
          title: " ",
          key: "title",
          align: "left",
          ellipsis: true,
          render: (h, params) => {
            return h("span", [
              h(
                "a",
                {
                  style: {
                    margin: "0 30px 0 0",
                  },
                  on: {
                    click: () => {
                      this.getContent(params.row);
                    },
                  },
                },
                "【" + params.row.type + "】 " + params.row.title
              ),
            ]);
          },
        },
        {
          title: " ",
          key: "time",
          align: "center",
          width: 190,
          render: (h, params) => {
            return h("span", [
              h("Icon", {
                props: {
                  type: "md-time",
                  size: 16,
                },
                style: {
                  margin: "0 5px 3px 0",
                },
              }),
              h("span", params.row.createTime),
            ]);
          },
        },
        {
          title: " ",
          key: "asread",
          align: "center",
          width: 210,
          render: (h, params) => {
            if (this.currentMesType == "unread") {
              return h("div", [markAsreadBtn(h, params)]);
            } else if (this.currentMesType == "hasread") {
              return h("div", [deleteRealBtn(h, params)]);
            }
          },
        },
      ],
    };
  },
  methods: {
    changePage(v) {
      this.params.pageNumber = v;
      this.refreshMessage();
    },
    changePageSize(v) {
      this.params.pageSize = v;
      this.refreshMessage();
    },
    refreshMessage() {
      let status = 0;
      let type = this.currentMesType;
      if (type == "unread") {
        status = 0;
      } else if (type == "hasread") {
        status = 1;
      }
      this.params.status = status;
      this.loading = true;
      getMessageSendData(this.params).then((res) => {
        this.loading = false;
        if (res.success) {
          this.mesList = res.result.content;
          this.total = res.result.totalElements;
          if (type == "unread") {
            this.unreadCount = this.total;
          } else if (type == "hasread") {
            this.hasreadCount = this.total;
          }
        }
      });
    },
    initMessage() {
      // 是否跳转查看消息详情
      let q = this.$route.query;
      if (q.content) {
        this.getContent(q.content);
      }
      this.getCount(true);
    },
    // 获取消息计数
    getCount(init) {
      this.getUnreadCount(init);
      this.getReadCount();
    },
    // 未读消息计数
    getUnreadCount(init) {
      this.params.status = 0;
      getMessageSendData(this.params).then((res) => {
        if (res.success) {
          if (init) {
            this.loading = false;
            this.mesList = res.result.content;
          }
          this.unreadCount = res.result.totalElements;
          this.$store.commit("setMessageCount", this.unreadCount);
        }
      });
    },
    // 已读消息计数
    getReadCount() {
      this.params.status = 1;
      getMessageSendData(this.params).then((res) => {
        if (res.success) {
          this.hasreadCount = res.result.totalElements;
        }
      });
    },
    deleteMessage(id) {
      deleteMessageSend({ ids: id }).then((res) => {
        if (res.success) {
          this.$Message.success("删除成功");
        }
      });
    },
    setCurrentMesType(type) {
      if (this.currentMesType != type) {
        this.showMesList = true;
      }
      this.currentMesType = type;
      this.params.pageNumber = 1;
      this.refreshMessage();
    },
    getContent(v) {
      this.showMesList = false;
      this.mes.title = v.title;
      this.mes.time = v.createTime;
      this.mes.content = v.content;
      if (v.status == 0) {
        // 标记已读 转换null为""
        for (let attr in v) {
          if (v[attr] == null) {
            v[attr] = "";
          }
        }
        let str = JSON.stringify(v);
        let data = JSON.parse(str);
        data.status = 1;
        editMessageSend(data).then((res) => {
          if (res.success) {
            this.getCount();
            this.refreshMessage();
          }
        });
      }
    },
    readAll() {
      allMessageSend(0).then((res) => {
        if (res.success) {
          this.getCount();
          this.refreshMessage();
        }
      });
    },
    deleteAll() {
      allMessageSend(1).then((res) => {
        if (res.success) {
          this.getCount();
          this.refreshMessage();
        }
      });
    },
    backList() {
      this.showMesList = true;
      this.$router.push({
        name: "message_index",
      });
    },
  },
  mounted() {
    this.initMessage();
  },
  watch: {
    // 监听路由变化通过id获取数据
    $route(to, from) {
      if (to.name == "message_index") {
        let q = this.$route.query;
        if (q.content) {
          this.initMessage();
          return;
        }
        this.refreshMessage();
      }
    },
  },
};
</script>

