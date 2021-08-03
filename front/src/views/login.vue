<template>
  <div class="login">
    <Row
     
      justify="center"
      align="middle"
      @keydown.enter.native="submitLogin"
      style="height:100%"
    >
    <!-- 头部 -->
      <div class="loginUp">
        <div class="loginLeft">
          <img src="../assets/login/logo.png" alt="" srcset="">
          <span class="line"></span>
          <span class="title">数字乡村管理系统</span>
        </div>
      </div>
      <div class="loginMiddle">
        <div class="login-background">
          <div class="loginBg"></div>
           <div class="loginRight">
              <Row v-if="!socialLogining" class="loginRow">
                <Tabs v-model="tabName" class="loginTab">
                  <TabPane :label="$t('usernameLogin')" name="username">
                    <Form
                      ref="usernameLoginForm"
                      :model="form"
                      :rules="rules"
                      class="form"
                      v-if="tabName=='username'"
                    >
                      <FormItem prop="username" class="loginInput">
                        <Row>  
                            <Input 
                            v-model="form.username"
                            size="large"                     
                            clearable
                            placeholder="账号/邮箱/手机号"
                            autocomplete="off">
                                <Icon class="iconfont icon-yonghu" slot="prefix" style="line-height:50px" />
                            </Input>                   
                            
                        </Row>
                        
                      </FormItem>
                      <FormItem prop="password">
                      
                        <Input style="height:50px;line-height:50px"
                           type="password"
                           v-model="form.password"
                           size="large"                     
                           placeholder="请输入密码"
                           password
                           autocomplete="off">
                                <Icon class="iconfont icon-mima1" slot="prefix" style="line-height:50px" />
                        </Input>   
                      </FormItem>
                      <FormItem prop="imgCode">
                        <Row
                          type="flex"
                          justify="space-between"
                          style="align-items: center;overflow: hidden;"
                        >
                          <Input
                            v-model="form.imgCode"
                            size="large"
                            clearable
                            placeholder="请输入图片验证码"
                            :maxlength="10"
                            class="input-verify"
                          />
                          <div class="code-image" style="position:relative;font-size:12px;">
                            <Spin v-if="loadingCaptcha" fix></Spin>
                            <img
                              :src="captchaImg"
                              @click="getCaptchaImg"
                              alt="加载验证码失败"
                              style="width:110px;cursor:pointer;display:block"
                            />
                          </div>
                        </Row>
                      </FormItem>
                    </Form>
                  </TabPane>
                  <TabPane :label="$t('mobileLogin')" name="mobile">
                    <Form
                      ref="mobileLoginForm"
                      :model="form"
                      :rules="rules"
                      class="form"
                      v-if="tabName=='mobile'"
                    >
                      <FormItem prop="mobile" class="loginInput">
                         <Input 
                            v-model="form.mobile"
                            size="large"                     
                            clearable
                            placeholder="请输入手机号"
                            autocomplete="off">
                                <Icon class="iconfont icon-shouji" slot="prefix" style="line-height:50px" />
                          </Input>   
                     
                      </FormItem>
                      <FormItem prop="code" :error="errorCode">
                        <Row type="flex" justify="space-between">
                           <Input 
                            v-model="form.code"
                            size="large"                     
                            clearable
                            placeholder="请输入短信验证码"
                            class="input-verify"
                            :maxlength="6"
                            >
                                <Icon class="iconfont icon-youjian" slot="prefix" style="line-height:50px" />
                          </Input>
                         
                          <CountDownButton
                            ref="countDown"
                            @on-click="sendSmsCode"
                            :autoCountDown="false"
                            size="large"
                            :loading="sending"
                            :text="getSms"
                          />
                        </Row>
                      </FormItem>
                    </Form>
                  </TabPane>
                </Tabs>

                <Row type="flex" justify="space-between" align="middle">
                  <Checkbox v-model="saveLogin" size="large">{{ $t('autoLogin') }}</Checkbox>
                  <Dropdown trigger="click" @on-click="handleDropDown">
                    <a class="forget-pass">{{ $t('forgetPass') }}</a>
                    <DropdownMenu slot="list">
                      <DropdownItem name="test">体验测试账号</DropdownItem>
                      <DropdownItem name="resetByMobile">使用手机号重置密码(付费)</DropdownItem>
                      <DropdownItem name="resetByEmail">使用邮箱重置密码(付费)</DropdownItem>
                    </DropdownMenu>
                  </Dropdown>
                </Row>
                <Row>
                  <Button
                    class="login-btn"
                    type="primary"
                    size="large"
                    :loading="loading"
                    @click="submitLogin"
                    long
                  >
                    <span v-if="!loading" style="letter-spacing:20px; font-weight:bold">{{ $t('login') }}</span>
                    <span v-else>{{ $t('logining') }}</span>
                  </Button>
                </Row>
                
              </Row>
              <p class="loginBottom">
                  ART YOUR DREAM      
              </p>
              <div v-if="socialLogining">
                <RectLoading />
              </div>
            </div>
        </div>
      </div>
      <div class="loginDown">
        <p style="margin-top:10px">帮助 | 隐私 | 条款</p>
        <p>
          <span>Copyright © 2020 - 至今 XXX 版权所有</span>
          <span style="display:inline-block; width:4px;height:6px"></span>
          <a target="_blank" href="https://beian.miit.gov.cn" style="color:#848585">ICP备案 浙ICP备XXXXXXXX号</a>
          <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=XXXXXXXXXXXXXX号">
            <img src="../assets/login/gonganlogo.png" style="margin-left:6px"/>
            <p style="display:inline-block;color:#848585">浙公网安备 XXXXXXXXXXXXXX号</p>
          </a>
        </p>
      </div>
    </Row>
  </div>
</template>

<script>
import {
  login,
  userInfo,
  githubLogin,
  qqLogin,
  weiboLogin,
  wechatLogin,
  getJWT,
  sendLoginSms,
  smsLogin,
  initCaptcha,
  drawCodeImage,
  getOtherSet,
  getNotice
} from "@/api/index";
import { validateMobile } from "@/libs/validate";
import Cookies from "js-cookie";
import Header from "@/views/main-components/header";
import Footer from "@/views/main-components/footer";
import LangSwitch from "@/views/main-components/lang-switch";
import RectLoading from "@/views/my-components/xboot/rect-loading";
import CountDownButton from "@/views/my-components/xboot/count-down-button";
import util from "@/libs/util.js";
export default {
  components: {
    CountDownButton,
    RectLoading,
    LangSwitch,
    Header,
    Footer
  },
  data() {
    return {
      captchaId: "",
      captchaImg: "",
      loadingCaptcha: true,
      socialLogining: true,
      error: false,
      tabName: "username",
      saveLogin: true,
      getSms: "获取验证码",
      loading: false,
      sending: false,
      errorCode: "",
      form: {
        username: "admin",
        password: "123456",
        mobile: "",
        code: ""
      },
      rules: {
        username: [
          {
            required: true,
            message: "账号不能为空",
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            message: "密码不能为空",
            trigger: "blur"
          }
        ],
        imgCode: [
          {
            required: true,
            message: "验证码不能为空",
            trigger: "blur"
          }
        ],
        mobile: [
          {
            required: true,
            message: "手机号不能为空",
            trigger: "blur"
          },
          {
            validator: validateMobile,
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    getCaptchaImg() {
      this.loadingCaptcha = true;
      initCaptcha().then(res => {
        this.loadingCaptcha = false;
        if (res.success) {
          this.captchaId = res.result;
          this.captchaImg = drawCodeImage + this.captchaId;
        }
      });
    },
    sendSmsCode() {
      this.$refs.mobileLoginForm.validate(valid => {
        if (valid) {
          this.sending = true;
          this.getSms = "发送中";
          sendLoginSms(this.form.mobile).then(res => {
            this.getSms = "获取验证码";
            this.sending = false;
            if (res.success) {
              this.$Message.success("发送短信验证码成功");
              // 开始倒计时
              this.$refs.countDown.startCountDown();
            }
          });
        }
      });
    },
    afterLogin(res) {
      let accessToken = res.result;
      this.setStore("accessToken", accessToken);
      getOtherSet().then((res) => {
        if (res.result) {
          let domain = res.result.ssoDomain;
          Cookies.set("accessToken", accessToken, {
            domain: domain,
            expires: 7,
          });
        }
      });
      // 获取用户信息
      userInfo().then((res) => {
        if (res.success) {
          // 避免超过大小限制
          delete res.result.permissions;
          let roles = [];
          res.result.roles.forEach((e) => {
            roles.push(e.name);
          });
          delete res.result.roles;
          this.setStore("roles", roles);
          this.setStore("saveLogin", this.saveLogin);
          if (this.saveLogin) {
            // 保存7天
            Cookies.set("userInfo", JSON.stringify(res.result), {
              expires: 7,
            });
          } else {
            Cookies.set("userInfo", JSON.stringify(res.result));
          }
          this.setStore("userInfo", res.result);
          this.$store.commit("setUserInfo", res.result);
          // 加载菜单
          util.initRouter(this);
          // window.location.reload();
          this.$router.push({
            name: "home_index",
          });
        } else {
          this.loading = false;
        }
      });
    },
    submitLogin() {
      if (this.tabName == "username") {
        this.$refs.usernameLoginForm.validate(valid => {
          if (valid) {
            this.loading = true;
            login({
              username: this.form.username,
              password: this.form.password,
              code: this.form.imgCode,
              captchaId: this.captchaId,
              saveLogin: this.saveLogin
            }).then(res => {
              if (res.success) {
                this.afterLogin(res);
              } else {
                this.loading = false;
                this.getCaptchaImg();
              }
            });
          }
        });
      } else if (this.tabName == "mobile") {
        this.$refs.mobileLoginForm.validate(valid => {
          if (valid) {
            if (this.form.code == "") {
              this.errorCode = "验证码不能为空";
              return;
            } else {
              this.errorCode = "";
            }
            this.loading = true;
            this.form.saveLogin = this.saveLogin;
            smsLogin(this.form).then(res => {
              if (res.success) {
                this.afterLogin(res);
              } else {
                this.loading = false;
              }
            });
          }
        });
      }
    },
    toGithubLogin() {
      this.socialLogining = true;
      githubLogin().then(res => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.socialLogining = false;
        }
      });
    },
    toQQLogin() {
      this.socialLogining = true;
      qqLogin().then(res => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.socialLogining = false;
        }
      });
    },
    toWeiboLogin() {
      this.socialLogining = true;
      weiboLogin().then(res => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.socialLogining = false;
        }
      });
    },
    toWeixinLogin() {
      this.socialLogining = true;
      wechatLogin().then(res => {
        if (res.success) {
          window.location.href = res.result;
        } else {
          this.socialLogining = false;
        }
      });
    },
    relatedLogin() {
      let q = this.$route.query;
      let error = q.error;
      if (error !== "" && error !== undefined) {
        this.$Message.error(error);
      }
      let related = q.related;
      let JWTKey = q.JWTKey;
      if (related && related == "1" && JWTKey) {
        getJWT({ JWTKey: JWTKey }).then(res => {
          if (res.success) {
            this.socialLogining = true;
            let accessToken = res.result;
            this.setStore("accessToken", accessToken);
            getOtherSet().then(res => {
              if (res.result) {
                let domain = res.result.ssoDomain;
                Cookies.set("accessToken", accessToken, {
                  domain: domain,
                  expires: 7
                });
              }
            });
            // 获取用户信息
            userInfo().then(res => {
              if (res.success) {
                // 避免超过大小限制
                delete res.result.permissions;
                let roles = [];
                res.result.roles.forEach(e => {
                  roles.push(e.name);
                });
                this.setStore("roles", roles);
                // 保存7天
                Cookies.set("userInfo", JSON.stringify(res.result), {
                  expires: 7
                });
                this.setStore("userInfo", res.result);
                this.$store.commit("setAvatarPath", res.result.avatar);
                // 加载菜单
                util.initRouter(this);
                this.$router.push({
                  name: "home_index"
                });
              } else {
                this.socialLogining = false;
                this.$Message.error("获取登录用户信息失败");
              }
            });
          } else {
            this.socialLogining = false;
            this.$Message.error("使用第三方账号登录失败");
          }
        });
      } else {
        this.socialLogining = false;
      }
    },
    handleDropDown(v) {
      if (v == "test") {
        this.test();
      } else if (v == "resetByMobile") {
        this.$router.push({
          name: "reset"
        });
      } else if (v == "resetByEmail") {
        this.$router.push({
          name: "reset",
          query: {
            type: "1"
          }
        });
      }
    },
    // showNotice() {
    //   getNotice().then(res => {
    //     if (res.success) {
    //       if (!res.result) {
    //         return;
    //       }
    //       let data = res.result;
    //       if (
    //         data.open &&
    //         (data.title || data.content) &&
    //         data.position == "LOGIN"
    //       ) {
    //         this.$Notice.info({
    //           title: data.title,
    //           desc: data.content,
    //           duration: data.duration
    //         });
    //       }
    //     }
    //   });
    // },
    test() {
      this.$Notice.info({
        title: "测试体验账号",
        desc: "账号：test或test2<br>密码：123456"
      });
    }
  },
  mounted() {
    // this.showNotice();
    this.relatedLogin();
    this.getCaptchaImg();
  }
};
</script>

<style lang="less">
@import "./login.less";
</style>
