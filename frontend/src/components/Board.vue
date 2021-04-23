<template>
    <el-dialog
      title="商品销量"
      :visible.sync="dialogVisible"
      width="720px"
      @open="drawChart"
    >
      <el-form :inline="true">
        <el-form-item label="时间">
          <el-date-picker
            v-model="date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div id="chart" class="chart-wrapper" style="width:600px; height:200px"></div>
    </el-dialog>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
      chartInstance: null,
      radio: '',
      dialogVisible: false,
      date: '',
    };
  },
  methods: {
    drawChart() {
      this.$nextTick(() => {
        const el = document.getElementById('chart');
        this.chartInstance = echarts.init(el);
        this.updateChart();
      });
    },
    updateChart() {
      this.chartInstance.setOption({
        title: {
          text: 'ECharts 入门示例',
        },
        tooltip: {},
        xAxis: {
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'],
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20],
        }],
      });
    },
    show() {
      this.dialogVisible = true;
    },
  },
};
</script>

<style lang="less">

</style>
