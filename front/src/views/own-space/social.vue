<template>
  <div>
    <div class="item">
      <div class="social">
        <div class="logo">
          <Icon type="logo-github" size="42" color="#181617" />
        </div>
        <div>
          <div class="title">Github</div>
          <div class="desc">
            <span v-if="relate.github"
              >已绑定Github账号：{{ relate.githubUsername }}</span
            >
            <span v-else>当前未绑定Github账号</span>
          </div>
        </div>
      </div>
      <div>
        <a v-if="!relate.github" @click="toRelateGithub()">立即绑定</a>
        <a v-else @click="unRelateGithub()">解除绑定</a>
      </div>
    </div>
    <div class="item">
      <div class="social">
        <div class="logo">
          <img src="@/assets/icon/qq.png" width="42px" />
        </div>
        <div>
          <div class="title">QQ</div>
          <div class="desc">
            <span v-if="relate.qq">已绑定QQ账号：{{ relate.qqUsername }}</span>
            <span v-else>当前未绑定QQ账号</span>
          </div>
        </div>
      </div>
      <div>
        <a v-if="!relate.qq" @click="toRelateQQ()">立即绑定</a>
        <a v-else @click="unRelateQQ()">解除绑定</a>
      </div>
    </div>
    <div class="item">
      <div class="social">
        <div class="logo">
          <Icon custom="iconfont icon-weixin" size="40" color="#60c126"></Icon>
        </div>
        <div>
          <div class="title">微信</div>
          <div class="desc">
            <span v-if="relate.wechat"
              >已绑定微信账号：{{ relate.wechatUsername }}</span
            >
            <span v-else>当前未绑定微信账号</span>
          </div>
        </div>
      </div>
      <div>
        <a v-if="!relate.wechat" @click="toRelateWechat()">立即绑定</a>
        <a v-else @click="unRelateWechat()">解除绑定</a>
      </div>
    </div>
    <div class="item">
      <div class="social">
        <div class="logo">
          <img src="@/assets/icon/weibo.png" width="41px" />
        </div>
        <div>
          <div class="title">微博</div>
          <div class="desc">
            <span v-if="relate.weibo"
              >已绑定微博账号：{{ relate.weiboUsername }}</span
            >
            <span v-else>当前未绑定微博账号</span>
          </div>
        </div>
      </div>
      <div>
        <a v-if="!relate.weibo" @click="toRelateWeibo()">立即绑定</a>
        <a v-else @click="unRelateWeibo()">解除绑定</a>
      </div>
    </div>
    <div class="item">
      <div class="social">
        <div class="logo">
          <img src="@/assets/icon/dingding.png" width="41px" />
        </div>
        <div>
          <div class="title">钉钉</div>
          <div class="desc">
            <span v-if="relate.dingding"
              >已绑定钉钉账号：{{ relate.dingdingUsername }}</span
            >
            <span v-else>当前未绑定钉钉账号</span>
          </div>
        </div>
      </div>
      <div>
        <a v-if="!relate.dingding" @click="toRelateDingding()">立即绑定</a>
        <a v-else @click="unRelateDingding()">解除绑定</a>
      </div>
    </div>
    <div class="item">
      <div class="social">
        <div class="logo">
          <img src="@/assets/icon/qiyeweixin.png" width="41px" />
        </div>
        <div>
          <div class="title">企业微信</div>
          <div class="desc">
            <span v-if="relate.workwechat"
              >已绑定企业微信账号：{{ relate.workwechatUsername }}</span
            >
            <span v-else>当前未绑定企业微信</span>
          </div>
        </div>
      </div>
      <div>
        <a v-if="!relate.workwechat" @click="toRelateWorkwechat()">立即绑定</a>
        <a v-else @click="unRelateWorkwechat()">解除绑定</a>
      </div>
    </div>
    <Spin fix v-if="jumping">跳转中...</Spin>
  </div>
</template>

<script>
import {
  relatedInfo,
  unRelate,
  githubLogin,
  qqLogin,
  weiboLogin,
  wechatLogin,
  dingdingLogin,
  workwechatLogin,
} from "@/api/index";
import Cookies from "js-cookie";
export default {
  components: {},
  name: "social",
  data() {
    return {
      relate: {},
      jumping: false,
    };
  },
  methods: {
    init() {
      let v = JSON.parse(Cookies.get("userInfo"));
      // 转换null为""
      for (let attr in v) {
        if (v[attr] == null) {
          v[attr] = "";
        }
      }
      let str = JSON.stringify(v);
      let userInfo = JSON.parse(str);

      relatedInfo(userInfo.username).then((res) => {
        if (res.success) {
          this.relate = res.result;
        } else {
          this.$Message.error("加载绑定第三方账号信息失败");
        }
      });
    },
    toRelateGithub() {
      this.jumping = true;
      githubLogin().then((res) => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.jumping = false;
        }
      });
    },
    toRelateQQ() {
      this.jumping = true;
      qqLogin().then((res) => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.jumping = false;
        }
      });
    },
    toRelateWeibo() {
      this.jumping = true;
      weiboLogin().then((res) => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.jumping = false;
        }
      });
    },
    toRelateWechat() {
      this.jumping = true;
      wechatLogin().then((res) => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.jumping = false;
        }
      });
    },
    toRelateDingding() {
      this.jumping = true;
      dingdingLogin().then((res) => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.jumping = false;
        }
      });
    },
    toRelateWorkwechat() {
      this.jumping = true;
      workwechatLogin().then((res) => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.jumping = false;
        }
      });
    },
    unRelateGithub() {
      this.$Modal.confirm({
        title: "确认解绑Github账号",
        content: "您确认要解除绑定 " + this.relate.githubUsername + " ?",
        loading: true,
        onOk: () => {
          let params = {
            ids: [this.relate.githubId],
          };
          unRelate(params).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.relate.github = false;
            }
          });
        },
      });
    },
    unRelateQQ() {
      this.$Modal.confirm({
        title: "确认解绑QQ账号",
        content: "您确认要解除绑定 " + this.relate.qqUsername + " ?",
        loading: true,
        onOk: () => {
          let params = {
            ids: [this.relate.qqId],
          };
          unRelate(params).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.relate.qq = false;
            }
          });
        },
      });
    },
    unRelateWeibo() {
      this.$Modal.confirm({
        title: "确认解绑微博账号",
        content: "您确认要解除绑定 " + this.relate.weiboUsername + " ?",
        loading: true,
        onOk: () => {
          let params = {
            ids: [this.relate.weiboId],
          };
          unRelate(params).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.relate.weibo = false;
            }
          });
        },
      });
    },
    unRelateWechat() {
      this.$Modal.confirm({
        title: "确认解绑微信账号",
        content: "您确认要解除绑定 " + this.relate.wechatUsername + " ?",
        loading: true,
        onOk: () => {
          let params = {
            ids: [this.relate.wechatId],
          };
          unRelate(params).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.relate.wechat = false;
            }
          });
        },
      });
    },
    unRelateDingding() {
      this.$Modal.confirm({
        title: "确认解绑钉钉账号",
        content: "您确认要解除绑定 " + this.relate.dingdingUsername + " ?",
        loading: true,
        onOk: () => {
          let params = {
            ids: [this.relate.dingdingId],
          };
          unRelate(params).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.relate.dingding = false;
            }
          });
        },
      });
    },
    unRelateWorkwechat() {
      this.$Modal.confirm({
        title: "确认解绑企业微信账号",
        content: "您确认要解除绑定 " + this.relate.workwechatUsername + " ?",
        loading: true,
        onOk: () => {
          let params = {
            ids: [this.relate.workwechatId],
          };
          unRelate(params).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.relate.workwechat = false;
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
