<style lang="less">
@import "./settingManage.less";
</style>
<template>
  <div>
    <Card>
      <Layout>
        <Sider
          hide-trigger
          style="background: #fff; max-width: 220px; flex: 0 0 220px"
        >
          <Menu
            :active-name="currName"
            theme="light"
            width="auto"
            @on-select="currName = $event"
          >
            <MenuItem name="oss">文件对象存储配置</MenuItem>
            <MenuItem name="sms">短信配置</MenuItem>
            <MenuItem name="email">邮件配置</MenuItem>
            <MenuItem name="vaptcha">Vaptcha验证码</MenuItem>
            <MenuItem name="stopWord">禁用词管理</MenuItem>
            <MenuItem name="notice">系统公告配置</MenuItem>
            <MenuItem name="other">其他配置</MenuItem>
          </Menu>
        </Sider>
        <Content
          :style="{
            padding: '8px 40px',
            minHeight: '550px',
            background: '#fff',
          }"
        >
          <div v-show="currName == 'oss'">
            <div class="setting-title">文件对象存储配置</div>
            <oss />
          </div>
          <div v-show="currName == 'sms'">
            <div class="setting-title">短信配置</div>
            <sms />
          </div>
          <div v-show="currName == 'email'">
            <div class="setting-title">邮箱配置</div>
            <div style="display: flex; position: relative">
              <Form
                ref="emailForm"
                :model="email"
                :label-width="110"
                label-position="left"
                :rules="emailValidate"
              >
                <FormItem label="邮箱服务器" prop="host">
                  <Input
                    type="text"
                    v-model="email.host"
                    placeholder="请输入邮箱服务器Host，例如QQ邮箱为smtp.qq.com"
                    style="width: 380px"
                  />
                </FormItem>
                <FormItem label="发送邮箱账号" prop="username">
                  <Input
                    type="text"
                    v-model="email.username"
                    placeholder="请输入发送者Email账号"
                    style="width: 380px"
                  />
                </FormItem>
                <FormItem label="邮箱授权码" prop="password">
                  <Tooltip
                    placement="right"
                    content="无法编辑时请先点击查看图标"
                  >
                    <Input
                      class="input-see"
                      type="text"
                      v-model="email.password"
                      :readonly="!changedEmailPass"
                      @on-click="seeSecret('EMAIL_SETTING')"
                      icon="ios-eye"
                      placeholder="请输入邮箱授权码，进入邮箱-设置中可找到"
                      style="width: 380px"
                    />
                  </Tooltip>
                </FormItem>
                <FormItem>
                  <Button
                    type="primary"
                    style="width: 100px"
                    :loading="saveLoading"
                    @click="saveEditEmail"
                    >保存更改</Button
                  >
                </FormItem>
              </Form>
              <Spin fix v-if="loading"></Spin>
            </div>
          </div>
          <div v-show="currName == 'vaptcha'">
            <div class="setting-title">Vaptcha验证码配置</div>
            <div style="display: flex; position: relative">
              <Form
                ref="vaptchaForm"
                :model="vaptcha"
                :label-width="110"
                label-position="left"
                :rules="vaptchaValidate"
              >
                <FormItem label="VID" prop="vid">
                  <Input
                    type="text"
                    v-model="vaptcha.vid"
                    placeholder="请输入验证单元VID"
                    style="width: 380px"
                  />
                </FormItem>
                <FormItem label="secretKey" prop="secretKey">
                  <Tooltip
                    placement="right"
                    content="无法编辑时请先点击查看图标"
                  >
                    <Input
                      class="input-see"
                      type="text"
                      v-model="vaptcha.secretKey"
                      :readonly="!changedVaptchaSK"
                      @on-click="seeSecret('VAPTCHA_SETTING')"
                      icon="ios-eye"
                      placeholder="请输入验证单元Key"
                      style="width: 380px"
                    />
                  </Tooltip>
                </FormItem>
                <FormItem>
                  <Button
                    type="primary"
                    style="width: 100px"
                    :loading="saveLoading"
                    @click="saveEditVaptcha"
                    >保存更改</Button
                  >
                </FormItem>
              </Form>
              <Spin fix v-if="loading"></Spin>
            </div>
          </div>
          <div v-show="currName == 'stopWord'">
            <div class="setting-title">禁用词管理</div>
            <stop-word />
          </div>
          <div v-show="currName == 'notice'">
            <div class="setting-title">
              系统公告配置
              <Tooltip content="访问系统公告，右上角弹出" placement="right">
                <Icon
                  type="md-help-circle"
                  size="20"
                  color="#c5c5c5"
                  style="margin-left: 10px; cursor: pointer"
                />
              </Tooltip>
            </div>
            <div style="display: flex">
              <Form
                ref="noticeForm"
                :model="notice"
                :label-width="110"
                label-position="left"
              >
                <FormItem label="公告开关" prop="open">
                  <i-switch v-model="notice.open">
                    <span slot="open">开</span>
                    <span slot="close">关</span>
                  </i-switch>
                </FormItem>
                <FormItem label="展示时长" prop="duration">
                  <Tooltip
                    trigger="hover"
                    placement="right"
                    content="设为0不自动关闭"
                  >
                    <InputNumber
                      :max="1000"
                      :min="0"
                      v-model="notice.duration"
                    ></InputNumber
                    ><span style="margin-left: 5px">秒</span>
                  </Tooltip>
                </FormItem>
                <FormItem label="展示页面" prop="position">
                  <Select
                    v-model="notice.position"
                    placeholder="请选择"
                    style="width: 380px"
                  >
                    <Option value="LOGIN">登录页（访问系统时）</Option>
                    <Option value="HOME">首页（登录系统后）</Option>
                  </Select>
                </FormItem>
                <FormItem label="公告标题" prop="title">
                  <Input v-model="notice.title" style="width: 380px" />
                </FormItem>
                <FormItem label="公告内容" prop="content">
                  <Input
                    type="textarea"
                    v-model="notice.content"
                    :rows="4"
                    placeholder
                    style="width: 380px"
                  />
                </FormItem>
                <FormItem>
                  <Button
                    type="primary"
                    style="width: 100px"
                    :loading="saveLoading"
                    @click="saveEditNotice"
                    >保存更改</Button
                  >
                </FormItem>
              </Form>
              <Spin fix v-if="loading"></Spin>
            </div>
          </div>
          <div v-show="currName == 'other'">
            <div class="setting-title">其他配置</div>
            <div style="display: flex">
              <Form
                ref="otherForm"
                :model="other"
                :label-width="110"
                label-position="left"
                :rules="otherValidate"
              >
                <FormItem label="应用部署域名" prop="domain">
                  <Tooltip placement="right" content="拼接部分资源访问链接使用">
                    <Input
                      type="text"
                      v-model="other.domain"
                      placeholder="请输入应用部署域名前缀，如http://zwz.cn"
                      style="width: 380px"
                    />
                  </Tooltip>
                </FormItem>
                <FormItem label="单点登录域名" prop="ssoDomain">
                  <Tooltip
                    max-width="250"
                    transfer
                    placement="right"
                    content="该域名及子域名下将可获得单点登录所需数据"
                  >
                    <Input
                      type="text"
                      v-model="other.ssoDomain"
                      placeholder="请输入应用部署一级域名，如zwz.cn"
                      style="width: 380px"
                    />
                  </Tooltip>
                </FormItem>
                <FormItem label="IP黑名单" prop="blacklist">
                  <Input
                    type="textarea"
                    v-model="other.blacklist"
                    :rows="4"
                    placeholder="多个以回车分隔"
                    style="width: 380px"
                  />
                </FormItem>
                <FormItem>
                  <Button
                    type="primary"
                    style="width: 100px"
                    :loading="saveLoading"
                    @click="saveEditOther"
                    >保存更改</Button
                  >
                </FormItem>
              </Form>
              <Spin fix v-if="loading"></Spin>
            </div>
          </div>
        </Content>
      </Layout>
    </Card>
  </div>
</template>

<script>
import {
  seeSecretSet,
  getEmailSet,
  editEmailSet,
  getVaptchaSet,
  editVaptchaSet,
  getOtherSet,
  editOtherSet,
  getNoticeSet,
  editNoticeSet
} from "@/api/index";
import oss from "./oss.vue";
import sms from "./sms.vue";
import stopWord from "./stopWord.vue";
export default {
  name: "setting-manage",
  components: {
    oss,
    sms,
    stopWord,
  },
  data() {
    return {
      currName: "oss",
      loading: false, // 表单加载状态
      saveLoading: false,
      email: {
        host: "",
        username: "",
        password: "",
      },
      changedEmailPass: false, // 是否修改邮件密码
      vaptcha: {
        vID: "",
        secretKey: "",
      },
      changedVaptchaSK: false, // 是否修改Vaptcha的secrectKey
      notice: {
        duration: 10,
      },
      other: {
        domain: "http://127.0.0.1:8888",
        ssoDomain: "",
        blacklist: "",
      },
      emailValidate: {
        // 表单验证规则
        host: [{ required: true, message: "不能为空", trigger: "change" }],
        username: [{ required: true, message: "不能为空", trigger: "change" }],
        password: [{ required: true, message: "不能为空", trigger: "change" }],
      },
      vaptchaValidate: {
        // 表单验证规则
        vid: [{ required: true, message: "不能为空", trigger: "change" }],
        secretKey: [{ required: true, message: "不能为空", trigger: "change" }],
      },
      otherValidate: {
        // 表单验证规则
        domain: [{ required: true, message: "不能为空", trigger: "change" }],
      },
      
    };
  },
  methods: {
    init() {
      this.initEmailSet();
      this.initVaptchaSet();
      this.initNoticeSet();
      this.initOtherSet();
    },
    initEmailSet() {
      this.loading = true;
      getEmailSet().then((res) => {
        this.loading = false;
        if (res.result) {
          // 转换null为""
          for (let attr in res.result) {
            if (res.result[attr] == null) {
              res.result[attr] = "";
            }
          }
          this.email = res.result;
        } else {
          this.changedEmailPass = true;
        }
      });
    },
    initVaptchaSet() {
      this.loading = true;
      getVaptchaSet().then((res) => {
        this.loading = false;
        if (res.result) {
          // 转换null为""
          for (let attr in res.result) {
            if (res.result[attr] == null) {
              res.result[attr] = "";
            }
          }
          this.vaptcha = res.result;
        } else {
          this.changedVaptchaSK = true;
        }
      });
    },
    initOtherSet() {
      this.loading = true;
      getOtherSet().then((res) => {
        this.loading = false;
        if (res.result) {
          // 转换null为""
          for (let attr in res.result) {
            if (res.result[attr] == null) {
              res.result[attr] = "";
            }
          }
          this.other = res.result;
        }
      });
    },
    initNoticeSet() {
      this.loading = true;
      getNoticeSet().then((res) => {
        this.loading = false;
        if (res.result) {
          // 转换null为""
          for (let attr in res.result) {
            if (res.result[attr] == null) {
              res.result[attr] = "";
            }
          }
          this.notice = res.result;
        }
      });
    },
    seeSecret(name) {
      if (!name) {
        return;
      }
      seeSecretSet(name).then((res) => {
        if (res.success) {
          if (this.currName == "sms") {
            this.sms.secretKey = res.result;
            this.changedSmsSK = true;
          } else if (this.currName == "email") {
            this.email.password = res.result;
            this.changedEmailPass = true;
          } else if (this.currName == "vaptcha") {
            this.vaptcha.secretKey = res.result;
            this.changedVaptchaSK = true;
          }
        }
      });
    },
    saveEditEmail() {
      this.$refs.emailForm.validate((valid) => {
        if (valid) {
          this.saveLoading = true;
          this.email.changed = this.changedEmailPass;
          editEmailSet(this.email).then((res) => {
            this.saveLoading = false;
            if (res.success) {
              this.$Message.success("保存成功");
            }
          });
        }
      });
    },
    saveEditVaptcha() {
      this.$refs.vaptchaForm.validate((valid) => {
        if (valid) {
          this.saveLoading = true;
          this.vaptcha.changed = this.changedVaptchaSK;
          editVaptchaSet(this.vaptcha).then((res) => {
            this.saveLoading = false;
            if (res.success) {
              this.$Message.success("保存成功");
            }
          });
        }
      });
    },
    saveEditOther() {
      this.$refs.otherForm.validate((valid) => {
        if (valid) {
          this.saveLoading = true;
          editOtherSet(this.other).then((res) => {
            this.saveLoading = false;
            if (res.success) {
              this.$Message.success("保存成功");
            }
          });
        }
      });
    },
    saveEditNotice() {
      this.saveLoading = true;
      let str = JSON.stringify(this.notice);
      let params = JSON.parse(str);
      if (!this.notice.duration) {
        params.duration = 0;
      }
      editNoticeSet(params).then((res) => {
        this.saveLoading = false;
        if (res.success) {
          this.$Message.success("保存成功");
        }
      });
    },
  },
  mounted() {
    let name = this.$route.query.name;
    if (name) {
      this.currName = name;
    }
    this.init();
  },
};
</script>