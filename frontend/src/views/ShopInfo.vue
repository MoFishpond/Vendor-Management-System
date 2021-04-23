<template>
  <div class="shop-info-container">
    <div class="info-left">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="grid-content">
            <div>营业额</div>
            <div class="grid-content-num">{{money}}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content">
            <div>商品总销量</div>
            <div class="grid-content-num">{{amount}}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content">
            <div>客户数量</div>
            <div class="grid-content-num">{{customerNum}}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content">
            <div>销售国家和地区</div>
            <div class="grid-content-num">{{areaNum}}</div>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <div class="grid-chart">
            <chart></chart>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <div class="grid-chart">
            <world-map></world-map>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="info-right">
      <el-row :gutter="20">
        <el-col :span="24">
          <div class="grid-order">
            <waiting-order></waiting-order>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import Chart from '../components/Chart.vue';
import WaitingOrder from '../components/WaitingOrder.vue';
import WorldMap from '../components/WorldMap.vue';
import { getShopInfo } from '../service/index.js';

export default {
  components: { WorldMap, Chart, WaitingOrder },
  data() {
    return {
      amount: 0,
      areaNum: 0,
      customerNum: 0,
      money: 0,
    };
  },
  mounted() {
    this.getShopInfo();
  },
  methods: {
    async getShopInfo() {
      const params = {
        shopId: 1,
      };
      const res = await this.$http.get('/api/rest/i/vms/shop/getShopInfo', { params });
      console.log(res.body);
      if (res && res.body) {
        const data = res.body;
        this.amount = data.amount;
        this.areaNum = data.areaNum;
        this.customerNum = data.customerNum;
        this.money = data.money;
      }
    },
  },
};
</script>

<style lang="less">
.shop-info-container {
  display: flex;
  width: 100%;
}
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  background-color: #ffffff;
  height: 80px;
  padding: 20px;
  font-weight: 600;
  font-size: 16px;
  &-num {
    font-size: 24px;
    padding-top: 20px;
  }
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.grid-chart {
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.8);
  height: 372px;
}
.grid-order {
  border-radius: 4px;
  background-color: #ffffff;
  width: 480px;
  height: 900px;
}
.info-left {
  width: calc(100% - 400px);
}
.info-right {
  width: 500px;
  padding-left: 20px;
}
</style>
