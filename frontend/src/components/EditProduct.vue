<template>
  <el-dialog
    :title="isEdit ? '修改商品信息' : '添加商品'"
    :visible.sync="dialogVisible"
    width="720px"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :show-close="false"
  >
    <el-form ref="form" :model="productForm" label-width="140px" :rules="rules">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="productForm.name" placeholder="请输入商品名称"></el-input>
        </el-form-item>
        <el-form-item label="缩略图">
          <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model.number="productForm.price" placeholder="请输入商品价格"></el-input>
        </el-form-item>
        <el-form-item label="商品介绍" prop="introduction">
          <el-input v-model="productForm.introduction" type="textarea" placeholder="介绍一下你的商品吧，不超过1000字符"></el-input>
        </el-form-item>
        <el-form-item label="可销售国家和地区" prop="area">
          <el-cascader
            expand-trigger="hover"
            :props="{ multiple: true }"
            :filterable="true"
            v-model="productForm.area"
            placeholder="请选择商品可销售的国家和地区"
            :options="areaOptions"
          >
          </el-cascader>
        </el-form-item>
        <el-button @click="openAddArea" class="add-btn">新建自定义国家包</el-button>
        <el-form-item label="库存" prop="stock">
          <el-input v-model.number="productForm.stock" placeholder="请输入库存数量"></el-input>
        </el-form-item>
       </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleEdit">确 定</el-button>
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
        label: '亚洲国家包',
        children: [
          { value: 'CN', label: '中国' },
          { value: 'KR', label: '韩国' },
          { value: 'JP', label: '日本' },
        ],
      }, {
        label: '欧洲国家包',
        children: [
          { value: 'UK', label: '英国' },
          { value: 'FR', label: '法国' },
          { value: 'DE', label: '德国' },
        ],
      }, {
        label: '北美国家包',
        children: [
          { value: 'US', label: '美国' },
          { value: 'CA', label: '加拿大' },
        ],
      }],
      productForm: {
        name: '',
        image: '',
        price: '',
        introduction: '',
        area: [],
        stock: '',
      },
      rules: {
        name: [
          { required: true, message: '商品名称不能为空', trigger: 'blur' },
          {
            min: 1, max: 20, message: '名称最长为20字符', trigger: 'blur',
          },
        ],
        price: [
          { required: true, message: '商品价格不能为空', trigger: 'change' },
          { type: 'number', message: '商品价格必须为数字', trigger: 'change' },
        ],
        introduction: [
          {
            min: 1, max: 1000, message: '内容最长为1000字符', trigger: 'blur',
          },
        ],
        area: [
          {
            type: 'array', required: true, message: '请至少选择一个可销售国家或地区', trigger: 'blur',
          },
        ],
        stock: [
          { required: true, message: '库存数量不能为空', trigger: 'change' },
          { type: 'number', message: '库存必须为数字', trigger: 'change' },
        ],
      },
    };
  },
  props: {
    dialogVisible: {
      type: Boolean,
    },
    formData: {
      type: Object,
    },
    isEdit: {
      type: Boolean,
    },
  },
  methods: {
    handleClose() {
      this.productForm = {};
      this.$emit('closeDialog');
    },
    handleEdit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log('edit');
          const params = this.productForm;
          params.area = params.area.map((item) => {
            if (item.length > 1) return item[1];
            return item[0];
          });
          if (this.isEdit) {
            this.$emit('edit', params);
            this.$message({
              message: '编辑成功！',
              type: 'success',
            });
          } else {
            this.$emit('add', params);
            this.$message({
              message: '添加成功！',
              type: 'success',
            });
          }
          this.$emit('closeDialog');
        }
      });
    },
    openAddArea() {
      this.$emit('openAreaDialog');
    },
  },
  mounted() {
  },
  watch: {
    dialogVisible() {
      this.productForm = this.formData;
    },
  },
  created() {
    this.countries.map((item) => {
      this.areaOptions.push({
        value: item.short,
        label: item.name,
      });
    });
  },
};
</script>

<style lang="less" scoped>
.el-input {
  width: 500px;
}
.el-textarea {
  width: 500px;
  height: 80px;
}
.el-cascader {
  width: 500px;
}
.add-btn {
  margin: -10px 0 20px 140px;
  background-color: #F9DCDE;
}
</style>
