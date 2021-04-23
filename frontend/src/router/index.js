import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: {
      name: 'shopInfo',
    },
    meta: {
      pageChName: '店铺信息',
    },
  },
  {
    path: '/info',
    name: 'shopInfo',
    component: () => import('../views/ShopInfo.vue'),
    meta: {
      pageChName: '店铺信息',
    },
  },
  {
    path: '/product',
    name: 'productManage',
    component: () => import('../views/ProductManage.vue'),
    meta: {
      pageChName: '商品管理',
    },
  },
  {
    path: '/order',
    name: 'orderManage',
    component: () => import('../views/OrderManage'),
    meta: {
      pageChName: '订单管理',
    },
  },
  {
    path: '/area',
    name: 'areaManage',
    component: () => import('../views/AreaManage'),
    meta: {
      pageChName: '销售地区管理',
    },
  },
];

const router = new VueRouter({
  routes,
});

export default router;
