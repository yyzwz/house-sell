<template>
  <div style="display: inline-block" class="message-content">
    <Dropdown trigger="click" placement="bottom-end">
      <div style="display: inline-block">
        <div class="header-right-icon header-action">
          <Badge :count="messageCount">
            <Icon type="md-notifications" :size="20" style="margin-top: 3px" />
          </Badge>
        </div>
      </div>
      <div slot="list" class="message-tip">
        <div class="title-content">
          <div class="title">{{ $t("Notifications") }}</div>
          <a class="mark" v-if="messageList.length > 0" @click="markAll">{{
            $t("markAll")
          }}</a>
          <a class="mark" v-else @click="getData">{{
            $t("refresh")
          }}</a>
        </div>
        <div class="mes-list">
          <div v-if="messageList.length == 0" class="empty-message">
            <Icon type="ios-mail-open-outline" size="70" color="#e4e4e4" />
            <p>{{ $t("noNewMes") }}</p>
          </div>
          <div v-else>
            <div
              v-for="(item, index) in messageList"
              :key="index"
              :class="{
                'mes-item': item.status == 0,
                'mes-item opacity': item.status == 1,
              }"
              @click="messageDetail(item)"
            >
              <div class="icon">
                <Avatar
                  v-if="item.type == '通知'"
                  style="background-color: #ff9900"
                  icon="md-send"
                  class="icon"
                />
                <Avatar
                  v-else-if="item.type == '提醒'"
                  style="background-color: #ed4014"
                  icon="md-alarm"
                  class="icon"
                />
                <Avatar
                  v-else-if="item.type == '私信'"
                  style="background-color: #87d068"
                  icon="md-person"
                  class="icon"
                />
                <Avatar
                  v-else-if="item.type == '工作流'"
                  style="background-color: #2db7f5"
                  icon="md-briefcase"
                  class="icon"
                />
                <Avatar
                  v-else-if="item.type == '系统公告'"
                  style="background-color: #2d8cf0"
                  icon="md-mail"
                  class="icon"
                />
                <Avatar v-else icon="ios-mail" class="icon" />
              </div>
              <div class="detail">
                <div class="mes-title">{{ item.title }}</div>
                <div class="mes-time">
                  <Icon type="md-time" style="margin-right: 3px" /><Time
                    :time="item.createTime"
                  />
                </div>
              </div>
            </div>
          </div>
          <Spin fix v-if="loading" />
        </div>
        <a class="show-more" @click="showMessage">
          <Icon
            type="ios-arrow-dropright-circle"
            size="16"
            style="margin: 0 5px 2px 0"
          />{{ $t("showMore") }}
        </a>
      </div>
    </Dropdown>
  </div>
</template>

<script>
import { ws, getMessageSendData, allMessageSend } from "@/api/index";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import util from "@/libs/util.js";
import Cookies from "js-cookie";
export default {
  name: "messageTip",
  props: {},
  data() {
    return {
      loading: false,
      userId: "",
      messageList: [],
      client: null,
    };
  },
  computed: {
    messageCount() {
      return this.$store.state.app.messageCount;
    },
  },
  stompClient: {
    monitorIntervalTime: 100,
    stompReconnect: true,
    timeout(orgCmd) {},
  },
  methods: {
    init() {
      let userInfo = JSON.parse(Cookies.get("userInfo"));
      this.userId = userInfo.id;
      this.getData();
      // 消息开关 websocket
      let messageOpen = this.getStore("messageOpen");
      if (messageOpen != "0") {
        this.connect();
      }
    },
    getData() {
      // 读取未读消息
      this.loading = true;
      getMessageSendData({
        userId: this.userId,
        status: 0,
        pageSize: 5,
        sort: "createTime",
        order: "desc",
      }).then((res) => {
        if (res.success) {
          this.loading = false;
          this.messageList = res.result.content;
          this.$store.commit("setMessageCount", res.result.totalElements);
        }
      });
    },
    showMessage() {
      util.openNewPage(this, "message_index");
      if (this.$route.name == "message_index") {
        return;
      }
      this.$router.push({
        name: "message_index",
      });
    },
    connect() {
      let headers = {};
      this.client = Stomp.over(new SockJS(ws));
      this.client.connect(headers, this.onConnected, this.onFailed);
    },
    onConnected(frame) {
      console.log("连接ws成功: " + frame);
      let topicSubscription = this.client.subscribe(
        "/topic/subscribe",
        this.responseCallback
      );
      let queueSubscription = this.client.subscribe(
        "/user/" + this.userId + "/queue/subscribe",
        this.responseCallback
      );
    },
    onFailed(frame) {
      console.log("连接ws失败： " + JSON.stringify(frame));
    },
    responseCallback(frame) {
      console.log("收到消息：" + frame.body);
      this.getData();
    },
    messageDetail(v) {
      if (v.status == 1) {
        return;
      }
      util.openNewPage(this, "message_index");
      this.$router.push({
        name: "message_index",
        query: { count: this.messageCount, content: v },
      });
      v.status = 1;
    },
    markAll() {
      allMessageSend(0).then((res) => {
        if (res.success) {
          this.getData();
        }
      });
    },
  },
  mounted() {
    this.init();
  },
};
</script>

<style lang="less">
.message-content .ivu-select-dropdown {
  padding: 0;
}
.message-tip {
  display: flex;
  flex-direction: column;
  width: 320px;
  .title-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px;
    .title {
      color: #515a6e;
      font-size: 16px;
      font-weight: 500;
    }
    .mark {
      font-size: 12px;
    }
  }
  .empty-message {
    height: 220px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #a7a7a7;
    p {
      margin-top: 10px;
    }
  }
  .mes-list {
    position: relative;
    display: flex;
    flex-direction: column;
    max-height: 261px;
    overflow: auto;
    .mes-item {
      display: flex;
      height: 87px;
      padding: 12px 16px 12px 16px;
      cursor: pointer;
      transition: all 0.2s ease-in-out;
      &:hover {
        background-color: #f6f6f6;
      }
      .icon {
        margin: 1px 16px 0 0;
        width: 32px;
      }
      .detail {
        .mes-title {
          color: #515a6e;
          min-height: 42px;
          margin-bottom: 5px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }
        .mes-time {
          font-size: 12px;
          color: #869ac0;
        }
      }
    }
    .opacity {
      opacity: 0.4;
    }
  }
  .mes-list::-webkit-scrollbar {
    width: 6px;
    height: 6px;
  }

  .mes-list::-webkit-scrollbar-thumb {
    border-radius: 4px;
    -webkit-box-shadow: inset 0 0 2px #d1d1d1;
    background: #e4e4e4;
  }
  .show-more {
    border-top: 1px solid #eff2f7;
    padding: 16px;
    display: flex;
    align-content: center;
    justify-content: center;
    align-items: center;
  }
}
</style>