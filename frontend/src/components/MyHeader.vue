<template>
  <div class="my-header">
    <div class="header-left">
      <img :src="icon" alt="" class="icon">
        <div class="name">MyTube</div>
    </div>
    <div class="shop-info">
      <img :src="store" class="avatar">
      <div class="shop-name">
        ChewPP杂货铺
      </div>
    </div>
  </div>
</template>

<script>
import icon from '../assets/dm.png';
import store from '../assets/like.png';

export default {
  data() {
    return {
      icon,
      store,
    };
  },
};
</script>

<style lang="less" scoped>
.my-header {
    width: 100%;
    height: 60px;
    background-color: #ffffff;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-shadow: 0 1px 2px 0 rgba(0,0,0,0.08), 0 1px 4px 0 rgba(0,0,0,0.12);
}
.header-left {
  padding-left: 30px;
  display: flex;
  align-items: center;
  .icon {
    width: 32px;
    height: 32px;
  }
  .name {
    font-size: 20px;
    font-weight: 600;
    padding-left: 6px;
  }
}

.shop-info {
  display: flex;
  align-items: center;
  padding-right: 30px;
  .avatar {
    width: 20px;
    height: 20px;
  }
}

.shop-name {
  padding-left: 10px;
}
</style>
