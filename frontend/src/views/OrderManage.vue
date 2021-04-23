<template>
  <div class="container">
     <div class="page-header">订单管理</div>
     <div>
      <el-form ref="form" :model="filterForm" label-width="80px" class="filter-form" :inline="true">
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="filterForm.date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="顾客ID">
          <el-input v-model="filterForm.customerId" class="filter-form-input"></el-input>
        </el-form-item>
        <el-form-item label="商品ID">
          <el-input v-model="filterForm.productIdList" class="filter-form-input"></el-input>
        </el-form-item>
        <el-form-item label="订单金额">
          <el-input v-model="filterForm.money" class="filter-form-input"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">订单查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" @click="handleAddOrder">添加新的订单</el-button>
        </el-form-item>
       </el-form>
     </div>
     <el-table
      :data="orderTableData"
      class="order-table"
    >
      <el-table-column
        prop="orderId"
        label="订单ID"
        width="180">
      </el-table-column>
      <el-table-column
        prop="time"
        label="订单创建时间">
      </el-table-column>
      <el-table-column
        prop="customerId"
        label="顾客ID"
        width="180">
      </el-table-column>
      <el-table-column
        prop="area"
        label="国家或地区">
      </el-table-column>
      <el-table-column
        prop="product"
        label="商品详情">
        <template slot-scope="scope">
          <div v-for="(item, index) in scope.row.product" :key="index">
            {{item.name}} x {{item.amount}}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="money"
        label="订单总额">
      </el-table-column>
    </el-table>
    <add-order
      :dialogVisible="addDialogVisible"
      @add="addOrder($event)"
      @closeDialog="addDialogVisible = false;"
    >
    </add-order>
  </div>
</template>

<script>
import AddOrder from '../components/AddOrder.vue';

export default {
  components: { AddOrder },
  data() {
    return {
      orderTableData: [{
        orderId: '2021041000100001',
        time: '2021-04-22 12:36:22',
        customerId: '90029',
        area: 'US',
        product: [{ name: 'PP牌抱枕', amount: 1 }, { name: 'PP神香水', amount: 2 }],
        money: 200,
      }],
      filterForm: {
        date: [],
        customerId: '',
        productIdList: '',
        money: '',
      },
      addDialogVisible: false,
    };
  },
  methods: {
    async onSubmit() {
      const params = {
        shopId: 1,
        customerId: this.filterForm.customerId || '',
        productId: this.filterForm.productId || '',
        from: this.filterForm.date.length > 1 ? this.filterForm.date[0].getTime() : '',
        to: this.filterForm.date.length > 1 ? this.filterForm.date[1].getTime() : '',
        money: this.filterForm.money,
      };
      console.log(params);
      const res = await this.$http.get('/api/rest/i/vms/order/get', { params });
      this.orderTableData = res.body.data;
    },
    handleAddOrder() {
      this.addDialogVisible = true;
    },
    async getAllOrder() {
      const params = {
        shopId: 1,
      };
      const res = await this.$http.get('/api/rest/i/vms/order/getAll', { params });
      console.log(res.body.data);
      this.orderTableData = res.body.data;
    },
    addOrder(params) {
      console.log(params);
      const d = new Date();
      this.orderTableData.push({
        orderId: '2021041000100002',
        time: '2021-04-25 19:32:49',
        customerId: params.customerId,
        area: params.area[0],
        product: [{ name: 'PP牌抱枕', amount: 1 }, { name: 'PP神香水', amount: 1 }],
        money: 100,
      });
      // this.$http.post("/api/rest/i/vms/order/add", params).then(res => {
      //  if (res && res.body) {
      //    this.orderTableData = res.body.data;
      //  }
      // });
    },
  },
  mounted() {
    this.getAllOrder();
  },
};
</script>

<style lang="less">

.filter-form {
  &-input {
    width: 120px;
    height: 20px;
  }
}

.order-table {
  width: 90%;
}

</style>
