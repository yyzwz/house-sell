<style lang="less">
@import "../../../styles/tree&table-common.less";
.search {
  .preview {
    font-weight: 600;
    color: #40a9ff;
    cursor: pointer;
  }
}
</style>
<template>
  <div class="search">
    <Card>
      <Row class="operation">
        <span>组件英文名：</span>
        <Tooltip content="生成的请求api文件中将使用该组件名拼接，建议驼峰法命名" placement="right" transfer max-width="250">
          <Input v-model="vueName" clearable style="width:150px;margin-right:10px" />
        </Tooltip>
        <span>表单显示：</span>
        <Select v-model="rowNum" style="width:150px;margin-right:10px">
          <Option value="1">一行一列</Option>
          <Option value="2">一行两列</Option>
          <Option value="3">一行三列</Option>
          <Option value="4">一行四列</Option>
        </Select>
        <Button @click="add" type="primary" icon="md-add">添加字段</Button>
        <Button @click="entityModal=true" type="warning" icon="ios-construct">读取字段</Button>
        <Button @click="generate" :loading="loading" type="success" icon="md-send">生成代码</Button>
        <Button @click="clear" icon="md-trash">清空全部</Button>
      </Row>
      <Row>
        <Alert show-icon>
          将生成的代码复制粘贴至XBoot前端新建的空白组件中，再做少许修改并自行调通接口即可
          <span @click="preview" class="preview">树形结构预览</span>
          【字段 title、parentId、sortOrder、parentTitle 将自动生成，无需填入】
        </Alert>
      </Row>
      <Row>
        <Table border :columns="columns" :data="data" ref="table">
          <template slot-scope="{ row }" slot="field">
            <Input v-model="row.field" @on-blur="changeField(row, $event)" />
          </template>
          <template slot-scope="{ row }" slot="name">
            <Input v-model="row.name" @on-blur="changeName(row, $event)" />
          </template>
          <template slot-scope="{ row }" slot="editable">
            <i-switch v-model="row.editable" @on-change="changeEditable(row, $event)">
              <span slot="open">开</span>
              <span slot="close">关</span>
            </i-switch>
          </template>
          <template slot-scope="{ row }" slot="type">
            <Select
              v-model="row.type"
              :disabled="!row.editable"
              @on-change="changeType(row, $event)"
              transfer
            >
              <Option value="text">文本输入框</Option>
              <Option value="select">选择下拉框</Option>
              <Option value="number">数字输入框</Option>
              <Option value="date">日期选择器</Option>
              <Option value="daterange">日期范围选择器</Option>
              <Option value="upload">图片上传输入组件</Option>
              <Option value="uploadThumb">图片上传缩略图组件</Option>
              <Option value="switch">Switch开关</Option>
              <Option value="radio">Radio单选框</Option>
              <Option value="time">时间选择器</Option>
              <Option value="area">省市县级联</Option>
              <Option value="slider">Slider滑块</Option>
              <Option value="quill">富文本组件Quill</Option>
              <Option value="wangEditor">富文本组件wangEditor</Option>
              <Option value="password">密码强度提示输入组件</Option>
            </Select>
          </template>
          <template slot-scope="{ row }" slot="validate">
            <i-switch
              v-model="row.validate"
              @on-change="changeValidate(row, $event)"
              :disabled="!row.editable"
            >
              <span slot="open">开</span>
              <span slot="close">关</span>
            </i-switch>
          </template>
          <template slot-scope="{ row }" slot="action">
            <Button size="small" style="margin-right: 5px" @click="edit(row)">编辑</Button>
            <Button type="error" size="small" @click="remove(row)">删除</Button>
          </template>
        </Table>
      </Row>
    </Card>

    <Modal :title="modalTitle" v-model="modalVisible" :mask-closable="false" :width="500">
      <Form ref="form" :model="form" :label-width="100" :rules="formValidate">
        <FormItem label="排序值" prop="sortOrder">
          <InputNumber v-model="form.sortOrder"></InputNumber>
        </FormItem>
        <FormItem label="字段英文名" prop="field" class="block-tool">
          <Tooltip placement="right" content="与后端实体字段匹配">
            <Input v-model="form.field" />
          </Tooltip>
        </FormItem>
        <FormItem label="名称" prop="name">
          <Input v-model="form.name" />
        </FormItem>
        <FormItem label="可添加编辑" prop="editable">
          <i-switch v-model="form.editable">
            <span slot="open">开</span>
            <span slot="close">关</span>
          </i-switch>
        </FormItem>
        <FormItem label="字段表单类型" prop="type" v-show="form.editable">
          <Select v-model="form.type">
            <Option value="text">文本输入框</Option>
            <Option value="select">选择下拉框</Option>
            <Option value="number">数字输入框</Option>
            <Option value="date">日期选择器</Option>
            <Option value="daterange">日期范围选择器</Option>
            <Option value="upload">图片上传输入组件</Option>
            <Option value="uploadThumb">图片上传缩略图组件</Option>
            <Option value="switch">Switch开关</Option>
            <Option value="radio">Radio单选框</Option>
            <Option value="time">时间选择器</Option>
            <Option value="area">省市县级联</Option>
            <Option value="slider">Slider滑块</Option>
            <Option value="quill">富文本组件Quill</Option>
            <Option value="wangEditor">富文本组件wangEditor</Option>
            <Option value="password">密码强度提示输入组件</Option>
          </Select>
        </FormItem>
        <FormItem label="显示级别" prop="level" v-show="form.type=='area'">
          <Select v-model="form.level">
            <Option value="0">仅显示省</Option>
            <Option value="1">显示省和县</Option>
            <Option value="2">显示省市县</Option>
          </Select>
        </FormItem>
        <FormItem label="表单非空验证" prop="validate" v-show="form.editable">
          <i-switch v-model="form.validate">
            <span slot="open">开</span>
            <span slot="close">关</span>
          </i-switch>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" @click="modalVisible=false">取消</Button>
        <Button type="primary" @click="handleSubmit">提交</Button>
      </div>
    </Modal>
    <Modal
      title="生成代码"
      v-model="codeModal"
      :width="900"
      :fullscreen="full"
      :styles="full?{}:modalStyle"
    >
      <RadioGroup v-model="resultType" style="margin-bottom:15px" @on-change="changeRadio">
        <Radio label="resultApi" border>树结构(含API)</Radio>
        <Radio label="result" border>树结构(模拟请求)</Radio>
      </RadioGroup>
      <Tabs v-model="tabName" type="card" @on-click="changeCode">
        <TabPane name="index.vue" label="index.vue"></TabPane>
        <TabPane v-if="resultType=='resultApi'" name="api.js" label="api.js"></TabPane>
      </Tabs>
      <Input
        v-if="!full"
        v-model="code"
        type="textarea"
        autosize
        style="max-height:60vh;overflow:auto;"
      />
      <Input v-if="full" v-model="code" :rows="32" type="textarea" />
      <div slot="footer">
        <Button @click="full=!full" icon="md-expand">全屏开/关</Button>
        <Button
          v-clipboard:copy="code"
          v-clipboard:success="onCopy"
          v-clipboard:error="onError"
          type="primary"
          icon="md-copy"
        >复制代码</Button>
      </div>
    </Modal>
    <Modal title="自动读取字段" v-model="entityModal" :width="700" :fullscreen="fullEntity">
      <Alert show-icon>输入实体类的引用路径，将自动读取其字段生成所需JSON配置数据，免去手动配置添加字段</Alert>
      <Form ref="entityForm" :model="entityForm" :label-width="130" :rules="entityFormValidate">
        <FormItem label="实体类引用路径" prop="path">
          <Row type="flex" justify="space-between">
            <Input
              v-model="entityForm.path"
              placeholder="例如：cn.exrick.xboot.modules.base.entity.User"
              clearable
              style="width:410px"
            />
            <Button type="warning" icon="md-play" @click="generateEntityData">读取字段</Button>
          </Row>
        </FormItem>
      </Form>
      <Input
        v-if="!fullEntity"
        v-model="entityData"
        type="textarea"
        autosize
        style="max-height:50vh;overflow:auto;"
        placeholder="生成结果"
      />
      <Input v-if="fullEntity" v-model="entityData" :rows="32" type="textarea" />
      <div slot="footer">
        <Button @click="fullEntity=!fullEntity" icon="md-expand">全屏开/关</Button>
        <Button type="primary" icon="md-checkmark-circle-outline" @click="submitEntityData">确认更新字段值</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import { generateTree, getEntityData } from "@/api/index";
export default {
  name: "tree-generator",
  data() {
    return {
      resultType: "resultApi",
      tabName: "index.vue",
      modalStyle: {
        top: "30px"
      },
      result: {},
      entityModal: false,
      fullEntity: false,
      entityData: "",
      entityForm: {
        path: ""
      },
      loading: false,
      code: "",
      vueName: "",
      rowNum: "1",
      full: false,
      codeModal: false,
      modalVisible: false,
      modalType: 0,
      modalTitle: "",
      form: {
        sortOrder: 0,
        field: "",
        name: "",
        level: "2",
        editable: true,
        type: "text",
        validate: true
      },
      formValidate: {
        field: [{ required: true, message: "不能为空", trigger: "blur" }],
        name: [{ required: true, message: "不能为空", trigger: "blur" }]
      },
      entityFormValidate: {
        path: [{ required: true, message: "不能为空", trigger: "blur" }]
      },
      columns: [
        // 表头
        {
          title: "排序",
          key: "sortOrder",
          sortType: "asc",
          sortable: true,
          align: "center",
          minWidth: 80
        },
        {
          title: "字段英文名",
          slot: "field",
          minWidth: 150
        },
        {
          title: "名称",
          slot: "name",
          minWidth: 150
        },
        {
          title: "可添加编辑",
          slot: "editable",
          minWidth: 100
        },
        {
          title: "表单类型",
          slot: "type",
          minWidth: 200
        },
        {
          title: "表单非空验证",
          slot: "validate",
          minWidth: 110
        },
        {
          title: "操作",
          slot: "action",
          align: "center",
          minWidth: 130
        }
      ],
      data: []
    };
  },
  methods: {
    init() {
      // 取缓存数据
      let data = this.getStore("treeData");
      let vueName = this.getStore("treeVueName");
      let rowNum = this.getStore("treeRowNum");
      if (data && data != "undefined") {
        this.data = JSON.parse(data);
      }
      if (vueName && vueName != "undefined") {
        this.vueName = vueName;
      }
    },
    generateEntityData() {
      this.$refs.entityForm.validate(valid => {
        if (valid) {
          getEntityData(this.entityForm.path).then(res => {
            if (res.success) {
              this.entityData = res.result;
            }
          });
        }
      });
    },
    submitEntityData() {
      if (!this.entityData) {
        this.$Message.warning("请先输入实体类引用路径生成JSON数据");
        return;
      }
      try {
        let data = JSON.parse(this.entityData);
        this.data = data.data;
        this.entityModal = false;
      } catch (e) {
        this.$Message.error("请确保JSON数据格式正确");
      }
    },
    preview() {
      this.$router.push({
        name: "tree"
      });
    },
    changeField(row, v) {
      for (let attr in row) {
        if (row[attr] == null) {
          row[attr] = "";
        }
      }
      let str = JSON.stringify(row);
      let data = JSON.parse(str);
      data.field = v.target.value;
      this.data.splice(row._index, 1, data);
      this.save();
    },
    changeName(row, v) {
      for (let attr in row) {
        if (row[attr] == null) {
          row[attr] = "";
        }
      }
      let str = JSON.stringify(row);
      let data = JSON.parse(str);
      data.name = v.target.value;
      this.data.splice(row._index, 1, data);
      this.save();
    },
    changeType(row, v) {
      for (let attr in row) {
        if (row[attr] == null) {
          row[attr] = "";
        }
      }
      let str = JSON.stringify(row);
      let data = JSON.parse(str);
      data.type = v;
      this.data.splice(row._index, 1, data);
      this.save();
    },
    changeEditable(row, v) {
      for (let attr in row) {
        if (row[attr] == null) {
          row[attr] = "";
        }
      }
      let str = JSON.stringify(row);
      let data = JSON.parse(str);
      data.editable = v;
      this.data.splice(row._index, 1, data);
      this.save();
    },
    changeValidate(row, v) {
      for (let attr in row) {
        if (row[attr] == null) {
          row[attr] = "";
        }
      }
      let str = JSON.stringify(row);
      let data = JSON.parse(str);
      data.validate = v;
      this.data.splice(row._index, 1, data);
      this.save();
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          // 判断富文本
          if (this.form.type == "wangEditor" || this.form.type == "quill") {
            let flag = false;
            this.data.forEach(e => {
              if (e.type == "wangEditor" || e.type == "quill") {
                flag = true;
                return;
              }
            });
            if (flag) {
              this.$Message.warning("已添加了富文本组件，暂仅支持添加1个");
              return;
            }
          }
          // 转换null为""
          let v = this.form;
          for (let attr in v) {
            if (v[attr] == null) {
              v[attr] = "";
            }
          }
          let str = JSON.stringify(v);
          let data = JSON.parse(str);
          if (this.modalType == 0) {
            this.data.push(data);
            this.data = this.data;
          } else {
            this.data.splice(v._index, 1, data);
          }
          this.save();
          this.modalVisible = false;
        }
      });
    },
    add() {
      this.modalType = 0;
      this.modalTitle = "添加字段";
      this.$refs.form.resetFields();
      this.modalVisible = true;
    },
    edit(v) {
      this.modalType = 1;
      this.modalTitle = "编辑字段";
      // 转换null为""
      for (let attr in v) {
        if (v[attr] == null) {
          v[attr] = "";
        }
      }
      let str = JSON.stringify(v);
      let data = JSON.parse(str);
      this.form = data;
      this.modalVisible = true;
    },
    remove(v) {
      this.data.splice(v._index, 1);
      this.save();
      this.$Message.success("操作成功");
    },
    clear() {
      this.$Modal.confirm({
        title: "确认清空",
        content: "您确认要清空所有数据 ?",
        onOk: () => {
          this.data = [];
          this.vueName = "";
          this.rowNum = "1";
          this.save();
          this.$Message.success("操作成功");
        }
      });
    },
    generate() {
      if (!this.vueName) {
        this.$Message.warning("请输入组件名");
        return;
      }
      this.loading = true;
      // 判断富文本
      let count = 0;
      this.data.forEach(e => {
        if (e.type == "wangEditor" || e.type == "quill") {
          count++;
          return;
        }
      });
      if (count > 1) {
        this.$Message.warning("暂仅支持添加1种类别富文本组件，请勿添加多个");
        return;
      }
      // 判断是否有重复字段
      let flag = false;
      this.data.forEach(e => {
        if (
          e.field == "title" ||
          e.field == "parentId" ||
          e.field == "sortOrder" ||
          e.field == "parentTitle"
        ) {
          flag = true;
        }
      });
      if (flag) {
        this.loading = false;
        this.$Modal.confirm({
          title: "无效字段提示",
          content:
            "您添加的字段中包含以下无效的重复字段：title、parentId、sortOrder、parentTitle，确认后将自动过滤生成?",
          onOk: () => {
            this.loading = true;
            this.data = this.data.filter(e => {
              return (
                e.field != "title" &&
                e.field != "parentId" &&
                e.field != "sortOrder" &&
                e.field != "parentTitle"
              );
            });
            generateTree(this.vueName, this.rowNum, this.data).then(res => {
              this.loading = false;
              if (res.success) {
                this.result = res.result;
                this.changeCode();
                this.codeModal = true;
                this.save();
              }
            });
          }
        });
      } else {
        generateTree(this.vueName, this.rowNum, this.data).then(res => {
          this.loading = false;
          if (res.success) {
            this.result = res.result;
            this.changeCode();
            this.codeModal = true;
            this.save();
          }
        });
      }
    },
    changeRadio() {
      this.tabName = "index.vue";
      this.changeCode();
    },
    changeCode() {
      if (this.resultType == "resultApi") {
        if (this.tabName == "index.vue") {
          this.code = this.result.resultApi;
        }
        if (this.tabName == "api.js") {
          this.code = this.result.api;
        }
      }
      if (this.resultType == "result") {
        this.code = this.result.result;
      }
    },
    onCopy() {
      this.$Message.success("复制成功");
    },
    onError() {
      this.$Message.warning("复制失败，请手动复制");
    },
    save() {
      this.setStore("treeData", JSON.stringify(this.data));
      this.setStore("treeVueName", this.vueName);
      this.setStore("treeRowNum", this.rowNum);
    }
  },
  mounted() {
    this.init();
  }
};
</script>