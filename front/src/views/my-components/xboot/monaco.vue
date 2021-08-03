<template>
  <div style="position: relative">
    <div :id="id" :style="{ overflow: 'hidden', height: height + 'px' }"></div>
    <i-switch
      v-model="themeSwitch"
      @on-change="changeTheme"
      class="monaco-theme"
      v-if="showThemeChange"
    >
      <Icon type="md-moon" slot="open"></Icon>
      <Icon type="md-sunny" slot="close"></Icon>
    </i-switch>
  </div>
</template>
<script>
export default {
  name: "monaco",
  props: {
    id: {
      type: String,
      default: "monaco",
    },
    height: {
      type: [Number, String],
      default: 500,
    },
    value: {
      type: String,
      default: "",
    },
    language: String,
    lineNumbers: {
      type: String,
      default: "on",
    },
    readOnly: {
      type: Boolean,
      default: false,
    },
    cursorStyle: {
      type: String,
      default: "line",
    },
    fontSize: {
      type: Number,
      default: 12,
    },
    showThemeChange: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      data: this.value,
      monacoEditor: null,
      themeSwitch: false,
      theme: "vs",
    };
  },
  methods: {
    init() {
      this.monacoEditor = monaco.editor.create(
        document.getElementById(this.id),
        {
          value: this.value,
          language: this.language,

          theme: this.theme, // 主题'vs' (default), 'vs-dark', 'hc-black'
          lineNumbers: this.lineNumbers, // 显示行号
          readOnly: this.readOnly, // 是否只读
          cursorStyle: this.cursorStyle, // 光标
          fontSize: this.fontSize, //字体大小
        }
      );
      // 格式化代码
      setTimeout(() => {
        if (!this.monacoEditor) {
          return;
        }
        this.monacoEditor.getAction(["editor.action.formatDocument"])._run();
      }, 500);
      // 监听事件
      this.monacoEditor.onDidChangeModelContent((e) => {
        let v = this.monacoEditor.getValue();
        this.data = v;
        this.$emit("input", this.data);
        this.$emit("on-change", this.data);
      });
    },
    changeTheme(v) {
      let theme = "vs";
      if (v) {
        theme = "vs-dark";
      }
      this.monacoEditor.updateOptions({
        theme: theme,
      });
    },
    setData(value) {
      if (!this.monacoEditor) {
        this.init();
      }
      if (value != this.data) {
        this.data = value;
        this.monacoEditor.setValue(this.data);
        // 格式化代码
        this.monacoEditor.getAction(["editor.action.formatDocument"])._run();
        this.$emit("input", this.data);
        this.$emit("on-change", this.data);
      }
    },
    layout(){
      this.monacoEditor.layout();
    }
  },
  beforeDestroy() {
    // 调用销毁 API 对当前编辑器实例进行销毁
    if (this.monacoEditor != null) {
      this.monacoEditor.dispose();
    }
  },
  watch: {
    value(val) {
      this.setData(val);
    },
  },
  mounted() {
    this.init();
  },
};
</script>
<style lang="less" scoped>
.monaco-theme {
  position: absolute;
  right: 25px;
  bottom: 15px;
}
</style>