<template>
  <el-dialog
    :title="isEdit ? '修改自定义国家包' : '添加自定义国家包'"
    :visible.sync="dialogVisible"
    width="720px"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :show-close="false"
  >
    <el-form :model="areaPackageForm" label-width="100px" :rules="rules">
      <el-form-item label="名称" prop="name">
        <el-input v-model="areaPackageForm.name" placeholder="请输入自定义国家包的名称"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="desc">
        <el-input v-model="areaPackageForm.desc" placeholder="请输入自定义国家包的描述，最长不超过100字符" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="国家和地区" prop="area">
        <el-cascader
          expand-trigger="hover"
          :props="{ multiple: true }"
          :filterable="true"
          v-model="areaPackageForm.area"
          placeholder="请选择包含的国家和地区"
          :options="areaOptions"
        >
        </el-cascader>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleAdd">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import countries from '../assets/countries.json';

export default {
  data() {
    return {
      countries,
      areaOptions: [{
        value: 1,
        label: '亚洲',
        children: [
          { value: 11, label: '中国' },
          { value: 12, label: '韩国' },
          { value: 13, label: '日本' },
        ],
      }, {
        value: 2,
        label: '欧洲',
        children: [
          { value: 21, label: '英国' },
          { value: 22, label: '法国' },
          { value: 23, label: '德国' },
        ],
      }, {
        value: 3,
        label: '北美',
        children: [
          { value: 31, label: '美国' },
          { value: 32, label: '加拿大' },
        ],
      }],
      areaPackageForm: {
        name: '',
        desc: '',
        area: [],
      },
      rules: {
        name: [
          { required: true, message: '商品名称不能为空', trigger: 'blur' },
          {
            min: 1, max: 40, message: '名称最长为40字符', trigger: 'blur',
          },
        ],
        desc: [
          {
            min: 1, max: 200, message: '内容最长为100字符', trigger: 'change',
          },
        ],
        area: [
          {
            type: 'array', required: true, message: '请至少选择一个可销售国家或地区', trigger: 'blur',
          },
        ],
      },
    };
  },
  props: {
    dialogVisible: {
      type: Boolean,
    },
    isEdit: {
      type: Boolean,
    },
  },
  methods: {
    handleClose() {
      this.$emit('closeAreaDialog');
    },
    handleAdd() {
      if (this.isEdit) this.$emit('editArea', this.areaPackageForm);
      else this.$emit('addArea', this.areaPackageForm);
    },
  },
  created() {
    this.areaOptions = this.countries.map((item) => ({
      value: item.short,
      label: item.name,
    }));
  },
};
</script>
