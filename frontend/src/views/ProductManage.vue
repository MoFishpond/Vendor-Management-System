<template>
   <div class="container">
     <div class="page-header">商品管理</div>
     <div>
       <el-form ref="form" :model="filterForm" label-width="80px" class="filter-form" :inline="true">

        <el-form-item label="商品ID">
          <el-input v-model="filterForm.productId" class="filter-form-input"></el-input>
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input v-model="filterForm.name" class="filter-form-input"></el-input>
        </el-form-item>
        <el-form-item label="关键字">
          <el-input v-model="filterForm.keyword" class="filter-form-input"></el-input>
        </el-form-item>
        <el-form-item label="库存">
          <el-select v-model="filterForm.stock.operator" placeholder="请选择">
            <el-option
              v-for="item in oprOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-input v-model="filterForm.stock.value" class="filter-form-input"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">商品查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" @click="handleAddProduct">添加新的商品</el-button>
        </el-form-item>
       </el-form>
     </div>
     <el-table
      :data="productTableData"
      style="width: 88%"
     >
      <el-table-column
        prop="productId"
        label="商品ID"
        width="80">
      </el-table-column>
      <el-table-column
        prop="name"
        label="商品名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="image"
        label="缩略图">
        <template slot-scope="scope">
          <img :src="scope.row.image" alt="#">
        </template>
      </el-table-column>
      <el-table-column
        prop="price"
        label="价格">
      </el-table-column>
      <el-table-column
        prop="introduction"
        label="商品介绍">
      </el-table-column>
      <el-table-column
        label="可销售国家和地区">
        <template slot-scope="scope">
          <el-tag
            v-for="(area, index) in scope.row.area"
            :key="index">
            {{area}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="stock"
        label="库存数量">
      </el-table-column>
      <el-table-column
        label="销售情况">
        <template>
          <el-button @click="openProductDetail">
            <i class="el-icon-document" ></i>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <edit-product
      :dialogVisible="editDialogVisible"
      :formData="formData"
      :isEdit="isEdit"
      @edit="editProduct($event)"
      @add="addProduct($event)"
      @closeDialog="editDialogVisible = false;"
      @openAreaDialog="packageDialogVisible = true;"
    >
    </edit-product>
    <add-area-package
      :dialogVisible="packageDialogVisible"
      @closeAreaDialog="packageDialogVisible = false;"
    ></add-area-package>
    <board ref="detailBoard"></board>
  </div>
</template>

<script>
import AddAreaPackage from '../components/AddAreaPackage.vue';
import Board from '../components/Board.vue';
import EditProduct from '../components/EditProduct.vue';

export default {
  components: {
    EditProduct,
    AddAreaPackage,
    Board,
  },
  data() {
    return {
      productTableData: [{
        productId: 1,
        name: 'PP牌抱枕',
        image: '#',
        price: 100,
        introduction: 'PP神专用抱枕',
        area: ['CN', 'USA'],
        stock: 100,
      }],
      filterForm: {
        productId: '',
        name: '',
        keyword: '',
        stock: {
          operator: '',
          value: 0,
        },
      },
      editDialogVisible: false,
      packageDialogVisible: false,
      formData: {},
      isEdit: false,
      oprOptions: [{
        value: 'eq',
        label: '=',
      }, {
        value: 'le',
        label: '<',
      }, {
        value: 'ge',
        label: '>',
      }],
    };
  },
  methods: {
    handleEdit(index, row) {
      this.isEdit = true;
      this.editDialogVisible = true;
      this.formData = row;
      console.log(index);
      console.log(row);
    },
    handleDelete(index, row) {
      console.log(index);
      console.log(row);
      this.$confirm('此操作将永久删除该商品记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        const params = {
          productId: row.productId,
          shopId: 1,
        };
        this.deleteProduct(params);
        this.$message({
          type: 'success',
          message: '删除成功!',
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除',
        });
      });
    },
    async onSubmit() {
      const params = {
        shopId: 1,
        ...this.filterForm,
      };
      const res = await this.$http.get('/api/rest/i/vms/product/get', { params });
      console.log(res);
    },
    handleAddProduct() {
      this.formData = {};
      this.isEdit = false;
      this.editDialogVisible = true;
    },
    openProductDetail() {
      this.$refs.detailBoard.show();
    },
    async getAllProduct() {
      const params = {
        shopId: 1,
      };
      const res = await this.$http.get('/api/rest/i/vms/product/getAll', { params });
      console.log(res.body.data);
      this.productTableData = res.body.data;
    },
    editProduct(params) {
      console.log(params);
      this.$http.post('/api/rest/i/vms/product/edit', params).then((res) => {
        if (res && res.body) {
          this.productTableData = res.body.data;
        }
      });
    },
    addProduct(params) {
      params = {
        shopId: 1,
        ...params,
      };
      console.log(params);
      this.$http.post('/api/rest/i/vms/product/add', params).then((res) => {
        if (res && res.body) {
          this.productTableData = res.body.data;
        }
      });
    },
    deleteProduct(params) {
      console.log(params);
      this.$http.post('/api/rest/i/vms/product/delete', params).then((res) => {
        if (res && res.body) {
          this.productTableData = res.body.data;
        }
      });
    },
  },
  mounted() {
    this.getAllProduct();
  },
};
</script>

<style lang="less" scoped>
.filter-form {
  &-input {
    width: 120px;
    height: 20px;
  }
}

.el-tag {
  margin-left: 4px;
}

</style>
