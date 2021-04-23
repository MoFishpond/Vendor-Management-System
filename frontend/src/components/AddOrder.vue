<template>
  <el-dialog
    title="添加订单"
    :visible.sync="dialogVisible"
    width="720px"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :show-close="false"
  >
    <el-form ref="form" :model="orderForm" label-width="100px" :rules="rules">
        <el-form-item label="顾客ID" prop="customerId">
          <el-input v-model="orderForm.customerId" placeholder="请输入顾客ID"></el-input>
        </el-form-item>
        <el-form-item label="国家或地区" prop="area">
          <el-cascader
            expand-trigger="hover"
            :filterable="true"
            v-model="orderForm.area"
            :options="areaOptions"
            placeholder="请选择订单所属的国家或地区"
          >
          </el-cascader>
        </el-form-item>
        <div class="sub-title">订单详情</div>
        <div style="display:flex" v-for="(order, index) in orderForm.detail" :key="index">
            <el-form-item label="商品ID" prop="productId">
              <el-input v-model="order.productId" placeholder="请输入商品ID" style="width:160px"></el-input>
            </el-form-item>
            <el-form-item label="商品数量" prop="amount">
              <el-input v-model.number="order.amount" placeholder="请输入商品数量" style="width:100px"></el-input>
            </el-form-item>
            <el-button class="btn" @click="deleteItem(index)">
                <i class="el-icon-delete"></i>
            </el-button>
            <el-button class="btn" @click="addItem">
                <i class="el-icon-plus"></i>
            </el-button>
        </div>
        <el-form-item label="订单总额" prop="money">
          <el-input v-model.number="orderForm.money" placeholder="请输入库存数量"></el-input>
        </el-form-item>
       </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleAdd">确 定</el-button>
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
        value: 1,
        label: '亚洲',
        children: [
          { value: 11, label: '中国' },
          { value: 12, label: '韩国' },
          { value: 13, label: '日本' },
        ],
      }, {
        value: 2,
        label: '欧洲',
        children: [
          { value: 21, label: '英国' },
          { value: 22, label: '法国' },
          { value: 23, label: '德国' },
        ],
      }, {
        value: 3,
        label: '北美',
        children: [
          { value: 31, label: '美国' },
          { value: 32, label: '加拿大' },
        ],
      }],
      orderForm: {
        customerId: '',
        area: '',
        detail: [{
          productId: '',
          amount: '',
        }],
        money: '',
      },
      rules: {
        customerId: [
          { required: true, message: '顾客ID不能为空', trigger: 'blur' },
        ],
        money: [
          { required: true, message: '订单总额不能为空', trigger: 'change' },
          { type: 'number', message: '订单总额必须为数字', trigger: 'change' },
        ],
        area: [
          { required: true, message: '订单所属的国家或地区不能为空', trigger: 'blur' },
        ],
      },
    };
  },
  props: {
    dialogVisible: {
      type: Boolean,
    },
  },
  methods: {
    handleClose() {
      this.$emit('closeDialog');
      this.orderForm = {
        customerId: '',
        area: '',
        detail: [{
          productId: '',
          amount: '',
        }],
        money: '',
      };
    },
    handleAdd() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          const params = {
            shopId: 1,
            ...this.orderForm,
          };
          this.$emit('add', params);
          this.$emit('closeDialog');
          this.orderForm = {
            customerId: '',
            area: '',
            detail: [{
              productId: '',
              amount: '',
            }],
            money: '',
          };
        }
      });
    },
    addItem() {
      this.orderForm.detail.push({
        productId: '',
        amount: '',
      });
    },
    deleteItem(index) {
      if (this.orderForm.detail.length > 1 && index > -1) {
        this.orderForm.detail.splice(index, 1);
      }
    },
  },
  created() {
    this.areaOptions = this.countries.map((item) => ({
      value: item.short,
      label: item.name,
    }));
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
.btn {
    width: 40px;
    height: 40px;
    padding: 0;
}
.sub-title {
  margin-left: 40px;
  margin-bottom: 20px;
}
</style>
