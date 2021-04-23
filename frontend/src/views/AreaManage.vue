<template>
  <div class="container">
    <div class="page-header">自定义国家包</div>
    <el-button type="primary" icon="el-icon-plus" @click="handleAddArea" style="margin-bottom: 20px">
      添加新的自定义国家包
    </el-button>
    <el-table
      :data="areaTableData"
      style="width: 80%"
    >
      <el-table-column
        prop="name"
        label="名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="desc"
        label="描述"
        width="280">
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
        width="180"
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
    <add-area-package
      :dialogVisible="packageDialogVisible"
      :isEdit="isEdit"
      @addArea="addArea($event)"
      @editArea="editArea($event)"
      @closeAreaDialog="packageDialogVisible = false;"
    ></add-area-package>
  </div>
</template>

<script>
import AddAreaPackage from '../components/AddAreaPackage.vue';

export default {
  components: { AddAreaPackage },
  data() {
    return {
      isEdit: false,
      editIndex: null,
      areaTableData: [{
        name: '亚洲国家包',
        desc: '本商店主要销售的亚洲国家',
        area: ['CN', 'JP', 'KR'],
      }, {
        name: '欧洲国家包',
        desc: '本商店主要销售的欧洲国家',
        area: ['UK', 'FR', 'GE'],
      }, {
        name: '北美国家包',
        desc: '本商店主要销售的北美国家',
        area: ['US', 'CA'],
      }, {
        name: '饮料销售国家包',
        desc: '饮料类商品主要销售的国家',
        area: ['CN', 'KR', 'US'],
      }],
      packageDialogVisible: false,
    };
  },
  methods: {
    handleEdit(index, row) {
      this.editIndex = index;
      this.isEdit = true;
      this.packageDialogVisible = true;
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该国家包, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.areaTableData = this.areaTableData.filter((item) => item.name != row.name);
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
    handleAddArea() {
      this.isEdit = false;
      this.packageDialogVisible = true;
    },
    getAllPackage() {

    },
    addArea(params) {
      console.log(params);
      params.area = params.area.map((item) => {
        if (item.length > 1) return item[1];
        return item[0];
      });
      this.areaTableData.push(params);
      this.packageDialogVisible = false;
    },
    editArea(index, params) {
      console.log(params);
      params.area = params.area.map((item) => {
        if (item.length > 1) return item[1];
        return item[0];
      });
      this.areaTableData[this.editIndex] = params;
      this.packageDialogVisible = false;
    },
  },
};
</script>

<style lang="less" scoped>
.el-tag {
  margin-right: 4px;
}
</style>
