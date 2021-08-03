<template>
  <div>
    <div :id="`${id}-toolbar`">
      <button class="ql-bold" title="粗体"></button>
      <button class="ql-italic" title="斜体"></button>
      <button class="ql-underline" title="下划线"></button>
      <button class="ql-strike" title="删除线"></button>
      <select class="ql-size" title="字体大小">
        <option value="small"></option>
        <option selected></option>
        <option value="large"></option>
        <option value="huge"></option>
      </select>
      <select class="ql-header" title="标题大小">
        <option value="1"></option>
        <option value="2"></option>
        <option value="3"></option>
        <option value="4"></option>
        <option value="5"></option>
        <option value="6"></option>
        <option selected></option>
      </select>
      <select class="ql-font" title="字体"></select>
      <select class="ql-align" title="对齐方式"></select>
      <select class="ql-color" title="字体颜色"></select>
      <select class="ql-background" title="背景颜色"></select>
      <button class="ql-blockquote" title="引用"></button>
      <button class="ql-code-block" title="代码块"></button>
      <button class="ql-list" value="ordered" title="数字列表"></button>
      <button class="ql-list" value="bullet" title="点列表"></button>
      <button class="ql-script" value="sub" title="右下标"></button>
      <button class="ql-script" value="super" title="右上标"></button>
      <button class="ql-indent" value="-1" title="向左缩进"></button>
      <button class="ql-indent" value="+1" title="向右缩进"></button>
      <button class="ql-clean" title="清空样式"></button>

      <button class="ql-link" title="链接"></button>
      <button class="ql-image" title="插入图片" v-if="base64"></button>
      <div class="q-menu" title="插入图片" v-if="!base64">
        <Upload
          :action="uploadFileUrl"
          :headers="accessToken"
          :on-success="handleSuccess"
          :on-error="handleError"
          :format="['jpg','jpeg','png','gif','bmp']"
          accept=".jpg, .jpeg, .png, .gif, .bmp"
          :max-size="5120"
          :on-format-error="handleFormatError"
          :on-exceeded-size="handleMaxSize"
          :before-upload="beforeUpload"
          :show-upload-list="false"
          ref="qup"
        >
          <Icon type="md-images" size="20" />
        </Upload>
      </div>
      <button class="ql-video" title="插入视频链接"></button>
      <div class="q-menu" title="编辑HTML代码" @click="editHTML" v-if="expandHtml">
        <Icon type="md-code-working" size="22" />
      </div>
      <div class="q-menu" title="预览" @click="fullscreenModal=true" v-if="expandPreview">
        <Icon type="ios-eye" size="24" />
      </div>
      <div class="q-menu" title="清空" @click="clear" v-if="expandClear">
        <Icon type="md-trash" size="18" style="display: block;" />
      </div>
    </div>

    <div :id="id" :style="{height: height}"></div>

    <Modal
      title="编辑html代码"
      v-model="showHTMLModal"
      :mask-closable="false"
      :width="900"
      :fullscreen="full"
    >
      <Input
        v-if="!full"
        v-model="dataEdit"
        :rows="15"
        type="textarea"
        style="max-height:60vh;overflow:auto;"
      />
      <Input v-if="full" v-model="dataEdit" :rows="32" type="textarea" />
      <div slot="footer">
        <Button @click="full=!full" icon="md-expand">全屏开/关</Button>
        <Button @click="editHTMLOk" type="primary" icon="md-checkmark-circle-outline">确定保存</Button>
      </div>
    </Modal>
    <Modal title="预览" v-model="fullscreenModal" fullscreen>
      <div v-html="data">{{data}}</div>
      <div slot="footer">
        <Button @click="fullscreenModal=false">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import { uploadFile } from "@/api/index";
import Quill from "quill";
import "quill/dist/quill.snow.css";
import xss from "xss";
export default {
  name: "editor",
  props: {
    id: {
      type: String,
      default: "quill"
    },
    value: String,
    base64: {
      type: Boolean,
      default: false
    },
    height: {
      type: String,
      default: "300px"
    },
    expandHtml: {
      type: Boolean,
      default: true
    },
    expandPreview: {
      type: Boolean,
      default: true
    },
    expandClear: {
      type: Boolean,
      default: true
    },
    openXss: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      accessToken: {},
      uploadFileUrl: uploadFile,
      editor: null,
      options: {
        theme: "snow",
        modules: {
          toolbar: `#${this.id}-toolbar`
        },
        placeholder: "在这输入内容 ..."
      },
      data: this.value, // 富文本数据
      dataEdit: "", // 编辑数据
      showHTMLModal: false, // 显示html
      full: false, // html全屏开关
      fullscreenModal: false // 显示全屏预览
    };
  },
  methods: {
    initEditor() {
      this.accessToken = {
        accessToken: this.getStore("accessToken")
      };
      this.editor = new Quill(`#${this.id}`, this.options);
      let that = this;
      if (this.value) {
        this.editor.pasteHTML(this.value);
      }
      this.editor.on("text-change", function(delta, oldDelta, source) {
        let html = that.editor.container.firstChild.innerHTML;
        if (that.openXss) {
          that.data = xss(html);
        } else {
          that.data = html;
        }
        that.$emit("input", that.data);
        that.$emit("on-change", that.data);
      });
    },
    handleFormatError(file) {
      this.$Notice.warning({
        title: "不支持的文件格式",
        desc:
          "所选文件‘ " +
          file.name +
          " ’格式不正确, 请选择 .jpg .jpeg .png .gif .bmp格式文件"
      });
    },
    handleMaxSize(file) {
      this.$Notice.warning({
        title: "文件大小过大",
        desc: "所选文件‘ " + file.name + " ’大小过大, 不得超过 5M."
      });
    },
    beforeUpload() {
      return true;
    },
    handleSuccess(res, file) {
      if (res.success) {
        let url = res.result;
        // 获取光标位置
        let range = this.editor.getSelection(true);
        // 总元素
        let delta = this.editor.getContents().length;
        let index;
        if (range) {
          index = range.index;
        } else {
          index = delta;
        }
        // 插入元素
        this.editor.insertEmbed(index, "image", url);
        this.editor.setSelection(index + 1, 0);
      } else {
        this.$Message.error(res.message);
      }
    },
    handleError(error, file, fileList) {
      this.$Message.error(error.toString());
    },
    editHTML() {
      this.dataEdit = this.data;
      this.showHTMLModal = true;
    },
    editHTMLOk() {
      this.editor.pasteHTML(this.dataEdit);
      this.$emit("input", this.data);
      this.$emit("on-change", this.data);
      this.showHTMLModal = false;
    },
    clear() {
      this.$Modal.confirm({
        title: "确认清空",
        content: "确认要清空编辑器内容？清空后不能撤回",
        onOk: () => {
          this.data = "";
          this.editor.pasteHTML(this.data);
          this.$emit("input", this.data);
          this.$emit("on-change", this.data);
        }
      });
    },
    setData(value) {
      if (!this.editor) {
        this.initEditor();
      }
      if (value != this.data) {
        this.data = value;
        let index = this.editor.selection.savedRange.index;
        this.editor.pasteHTML(this.data);
        this.editor.setSelection(index, 0);
        this.$emit("input", this.data);
        this.$emit("on-change", this.data);
      }
    }
  },
  watch: {
    value(val) {
      this.setData(val);
    }
  },
  mounted() {
    this.initEditor();
  }
};
</script>

<style lang="less">
.q-menu {
  margin: 0 3px;
  display: inline-block;
  cursor: pointer;
  color: #444;
  :hover {
    color: #06c;
  }
}
.ql-tooltip {
  left: 30% !important;
}

// 中文
.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
    border-right: 0px;
    content: '保存';
    padding-right: 0px;
}
.ql-snow .ql-tooltip[data-mode=video]::before {
    content: "请输入视频地址:";
}
.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}

.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
  content: "10px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
  content: "18px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}

.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: '标题';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: '标题1';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: '标题2';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: '标题3';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: '标题4';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: '标题5';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: '标题6';
}

.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: "标准字体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
  content: "衬线字体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
  content: "等宽字体";
}
</style>

