<template>
    <el-menu
      default-active="shopInfo"
      :router="true"
      @select="handleSelect"
    >
      <el-menu-item
        index="info"
        :route="{name: 'shopInfo'}"
      >
        <i class="el-icon-menu"></i>
        <span slot="title">店铺信息</span>
      </el-menu-item>
      <el-menu-item
        index="product"
        :route="{name: 'productManage'}"
      >
        <i class="el-icon-setting"></i>
        <span slot="title">商品管理</span>
      </el-menu-item>
      <el-menu-item
        index="order"
        :route="{name: 'orderManage'}"
      >
        <i class="el-icon-setting"></i>
        <span slot="title">订单管理</span>
      </el-menu-item>
      <el-menu-item
        index="area"
        :route="{name: 'areaManage'}"
      >
        <i class="el-icon-setting"></i>
        <span slot="title">销售区域管理</span>
      </el-menu-item>
    </el-menu>
</template>

<script>
export default {
  methods: {
    handleSelect() {
      console.log('open');
    },
  },
};
</script>

<style lang="less" scoped>
.el-menu {
  background-color: #ffffff;
  height: 100vh;
  border: 0;
  padding-top: 24px;
}

.el-menu-item {
  padding-left: 32px !important;
}

.shop-name {
  font-size: 14px;
  padding: 20px 0;
}
</style>
