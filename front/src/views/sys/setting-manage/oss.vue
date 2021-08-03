<style lang="less">
</style>
<template>
  <div style="display: flex; position: relative">
    <Form
      ref="ossForm"
      :model="oss"
      :label-width="110"
      label-position="left"
      :rules="ossValidate"
    >
      <FormItem label="服务提供商" prop="serviceName">
        <Select
          v-model="oss.serviceName"
          @on-change="changeOss"
          placeholder="请选择"
          style="width: 230px"
        >
          <Option value="LOCAL_OSS">本地服务器</Option>
          <Option value="MINIO_OSS">MinIO</Option>
          <Option value="QINIU_OSS">七牛云</Option>
          <Option value="ALI_OSS">阿里云</Option>
          <Option value="TENCENT_OSS">腾讯云</Option>
        </Select>
      </FormItem>
      <div v-if="oss.serviceName != 'LOCAL_OSS'">
        <FormItem label="accessKey" prop="accessKey">
          <Input
            type="text"
            v-model="oss.accessKey"
            placeholder="请输入accessKey/secretId"
            style="width: 380px"
            :disabled="changeLoading"
          />
        </FormItem>
        <FormItem label="secretKey" prop="secretKey">
          <Tooltip placement="right" content="无法编辑时请先点击查看图标">
            <Input
              class="input-see"
              type="text"
              v-model="oss.secretKey"
              :disabled="changeLoading"
              :readonly="!changedOssSK"
              @on-click="seeSecret(oss.serviceName)"
              icon="ios-eye"
              placeholder="请输入secretKey"
              style="width: 380px"
            />
          </Tooltip>
        </FormItem>
        <FormItem
          v-if="oss.serviceName == 'QINIU_OSS'"
          label="zone存储区域"
          prop="zone"
        >
          <Select
            v-model="oss.zone"
            placeholder="请选择存储区域"
            style="width: 380px"
            :disabled="changeLoading"
          >
            <Option :value="-1">自动判断</Option>
            <Option :value="0">华东</Option>
            <Option :value="1">华北</Option>
            <Option :value="2">华南</Option>
            <Option :value="3">北美</Option>
            <Option :value="4">东南亚</Option>
          </Select>
        </FormItem>
        <FormItem label="bucket空间" prop="bucket">
          <Input
            type="text"
            v-model="oss.bucket"
            placeholder="请输入bucket空间名"
            style="width: 380px"
            :disabled="changeLoading"
          />
        </FormItem>
        <FormItem
          v-if="oss.serviceName == 'TENCENT_OSS'"
          label="所属地域"
          prop="bucketRegion"
        >
          <dict
            dict="tencent_bucket_region"
            v-model="oss.bucketRegion"
            placeholder="请选择或输入搜索"
            filterable
            :disabled="changeLoading"
            transfer
          />
        </FormItem>
        <FormItem label="endpoint域名" prop="endpoint" :error="errorMsg">
          <Input
            type="text"
            v-model="oss.endpoint"
            :placeholder="endpointPH"
            style="width: 380px"
            :disabled="changeLoading"
          >
            <Select
              v-model="oss.http"
              slot="prepend"
              style="width: 80px"
              prop="http"
            >
              <Option value="http://">http://</Option>
              <Option value="https://">https://</Option>
            </Select>
          </Input>
        </FormItem>
      </div>
      <div v-else>
        <FormItem label="存储磁盘路径" prop="filePath" :error="errorMsg2">
          <Input
            type="text"
            v-model="oss.filePath"
            placeholder="例如D:/upload"
            style="width: 380px"
            :disabled="changeLoading"
          />
        </FormItem>
        <FormItem label="文件预览接口" prop="endpoint" :error="errorMsg">
          <Input
            type="text"
            v-model="oss.endpoint"
            placeholder="请输入文件预览接口"
            style="width: 380px"
          >
            <Select
              v-model="oss.http"
              slot="prepend"
              style="width: 80px"
              prop="http"
              :disabled="changeLoading"
            >
              <Option value="http://">http://</Option>
              <Option value="https://">https://</Option>
            </Select>
          </Input>
        </FormItem>
      </div>
      <FormItem>
        <Button
          type="primary"
          style="width: 100px"
          :disabled="changeLoading"
          :loading="saveLoading"
          @click="saveEditOss"
          >保存并启用</Button
        >
      </FormItem>
    </Form>
    <Spin fix v-if="loading"></Spin>
  </div>
</template>

<script>
import { seeSecretSet, checkOssSet, getOssSet, editOssSet } from "@/api/index";
import dict from "@/views/my-components/xboot/dict";
export default {
  name: "oss",
  components: {
    dict,
  },
  data() {
    return {
      loading: false, // 表单加载状态
      changeLoading: false,
      saveLoading: false,
      errorMsg: "",
      errorMsg2: "",
      oss: {
        serviceName: "",
        accessKey: "",
        secretKey: "",
        endpoint: "",
        bucket: "",
        bucketRegion: "",
        zone: null,
      },
      changedOssSK: false, // 是否修改oss的secrectKey
      ossValidate: {
        // 表单验证规则
        serviceName: [{ required: true, message: "请选择", trigger: "change" }],
        accessKey: [{ required: true, message: "不能为空", trigger: "blur" }],
        secretKey: [{ required: true, message: "不能为空", trigger: "blur" }],
        endpoint: [{ required: true, message: "不能为空", trigger: "blur" }],
        bucket: [{ required: true, message: "不能为空", trigger: "blur" }],
        zone: [
          {
            required: true,
            type: "number",
            message: "不能为空",
            trigger: "change",
          },
        ],
        bucketRegion: [
          { required: true, message: "不能为空", trigger: "change" },
        ],
      },
      endpointPH: "请输入空间访问域名",
    };
  },
  methods: {
    init() {
      this.initOssSet();
    },
    initOssSet() {
      this.loading = true;
      checkOssSet().then((res) => {
        if (res.success && res.result) {
          let oss = res.result;
          getOssSet(oss).then((res) => {
            this.loading = false;
            if (res.result) {
              // 转换null为""
              for (let attr in res.result) {
                if (res.result[attr] == null) {
                  res.result[attr] = "";
                }
              }
              this.oss = res.result;
            } else {
              this.changedOssSK = true;
            }
          });
        } else {
          this.loading = false;
          this.changedOssSK = true;
        }
      });
    },
    changeOss(v) {
      if (v == "ALI_OSS") {
        this.endpointPH = "请输入EndPoint(地域节点) 非Bucket域名";
      } else if (v == "TENCENT_OSS") {
        this.endpointPH = "请输入完整请求域名";
      } else {
        this.endpointPH = "请输入空间访问域名";
      }
      this.changeLoading = true;
      getOssSet(v).then((res) => {
        this.changeLoading = false;
        if (res.result) {
          // 转换null为""
          for (let attr in res.result) {
            if (res.result[attr] == null) {
              res.result[attr] = "";
            }
          }
          this.oss = res.result;
          this.changedOssSK = false;
        } else {
          this.oss = { serviceName: v };
          if (this.oss.serviceName == "LOCAL_OSS") {
            this.oss.http = "http://";
            this.oss.endpoint = "127.0.0.1:9999/xboot/file/view";
          }
          this.changedOssSK = true;
        }
      });
    },
    seeSecret(name) {
      if (!name) {
        return;
      }
      seeSecretSet(name).then((res) => {
        if (res.success) {
          this.oss.secretKey = res.result;
          this.changedOssSK = true;
        }
      });
    },
    saveEditOss() {
      if (this.oss.serviceName == "LOCAL_OSS") {
        if (!this.oss.http) {
          this.errorMsg = "请选择http协议";
          return;
        } else {
          this.errorMsg = "";
        }
        if (!this.oss.endpoint) {
          this.errorMsg = "请输入文件预览接口";
          return;
        } else {
          this.errorMsg = "";
        }
        if (!this.oss.filePath) {
          this.errorMsg2 = "请输入存储磁盘路径";
          return;
        } else {
          this.errorMsg2 = "";
        }
        editOssSet(this.oss).then((res) => {
          this.saveLoading = false;
          if (res.success) {
            this.$Message.success("保存成功");
          }
        });
      } else {
        this.$refs.ossForm.validate((valid) => {
          if (valid) {
            if (!this.oss.http) {
              this.errorMsg = "请选择http协议";
              return;
            } else {
              this.errorMsg = "";
            }
            this.saveLoading = true;
            this.oss.changed = this.changedOssSK;
            editOssSet(this.oss).then((res) => {
              this.saveLoading = false;
              if (res.success) {
                this.$Message.success("保存成功");
              }
            });
          }
        });
      }
    },
  },
  mounted() {
    this.init();
  },
};
</script>