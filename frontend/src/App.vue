<template>
  <div id="app">
    <el-container>
      <el-header>
          <my-header></my-header>
        </el-header>
      <el-container>
        <el-aside width="200px">
          <side-bar></side-bar>
        </el-aside>

        <el-main>
          <router-view/>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<style lang="less">
.el-header {
  padding: 0;
  z-index: 1;
}
.el-main {
  overflow: hidden;
}
</style>

<script>
import SideBar from './components/SideBar.vue';
import MyHeader from './components/MyHeader.vue';
import './style/index.css';

export default {
  components: {
    SideBar,
    MyHeader,
  },
};
</script>
