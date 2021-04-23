<template>
  <div class="waiting-order">
    <div class="header-container">
      <img :src="icon" alt="" class="icon">
      <div class="header">待处理订单</div>
    </div>
    <div>
      <div v-for="(item, index) in data" :key="index">
        <el-divider></el-divider>
        <span class="item">{{item.date}}</span>
        <span class="item">{{item.id}}</span>
        <el-checkbox @change="handleCheck(item.id)" v-model="item.isHandle">已处理</el-checkbox>
      </div>
    </div>
  </div>
</template>

<script>
import icon from '../assets/save.png';

export default {
  data() {
    return {
      icon,
      data: [{ date: '2020-3-22', id: '1000101010', isHandle: false },
        { date: '2020-3-22', id: '1000101011', isHandle: false },
        { date: '2020-3-23', id: '1000101013', isHandle: false },
        { date: '2020-3-23', id: '1000101019', isHandle: false },
        { date: '2020-3-24', id: '1000101020', isHandle: false },
        { date: '2020-3-24', id: '1000101022', isHandle: false },
        { date: '2020-3-26', id: '1000101029', isHandle: false },
        { date: '2020-3-26', id: '1000101031', isHandle: false },
        { date: '2020-3-28', id: '1000101033', isHandle: false },
        { date: '2020-3-30', id: '1000101050', isHandle: false },
      ],
    };
  },
  methods: {
    handleCheck(id) {
      this.data = this.data.filter((item) => item.id != id);
    },
  },
};
</script>

<style lang="less" scoped>
.waiting-order {
  width: calc(100% - 80px);
  height: 100%;
  padding: 30px;
  .header {
    font-size: 16px;
    font-weight: 600;
  }

  .item {
    padding: 0 40px 20px 20px;
  }
}

.icon {
  width: 22px;
  height: 22px;
  padding-right: 10px;
}

.header-container {
  display: flex;
  align-items: center;
  padding-bottom: 40px;
}
</style>
