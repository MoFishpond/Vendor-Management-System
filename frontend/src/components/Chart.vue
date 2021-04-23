<template>
  <div id="chart"></div>
</template>

<script>
import * as echarts from 'echarts';
import _rawData from '../assets/life.json';

export default {
  data() {
    return {
      option: {},
      countries: ['Finland', 'France', 'Germany', 'Iceland', 'Norway', 'Poland', 'Russia', 'United Kingdom'],
      datasetWithFilters: [],
      seriesList: [],
    };
  },
  methods: {
    insertData() {
      echarts.util.each(this.countries, (country) => {
        const datasetId = `dataset_${country}`;
        this.datasetWithFilters.push({
          id: datasetId,
          fromDatasetId: 'dataset_raw',
          transform: {
            type: 'filter',
            config: {
              and: [
                { dimension: 'Year', gte: 1 },
                { dimension: 'Country', '=': country },
              ],
            },
          },
        });
        this.seriesList.push({
          type: 'line',
          datasetId,
          showSymbol: false,
          name: country,
          endLabel: {
            show: true,
            formatter(params) {
              return `${params.value[3]}: ${params.value[0]}`;
            },
          },
          labelLayout: {
            moveOverlap: 'shiftY',
          },
          emphasis: {
            focus: 'series',
          },
          encode: {
            x: 'Year',
            y: 'Income',
            label: ['Country', 'Income'],
            itemName: 'Year',
            tooltip: ['Income'],
          },
        });
      });
    },
    updateOption() {
      this.option = {
        animationDuration: 3000,
        dataset: [{
          id: 'dataset_raw',
          source: _rawData,
        }].concat(this.datasetWithFilters),
        title: {
          text: '各销售国家和地区营业额趋势图',
        },
        tooltip: {
          order: 'valueDesc',
          trigger: 'axis',
        },
        xAxis: {
          type: 'category',
          nameLocation: 'middle',
        },
        yAxis: {
          name: '营业额',
        },
        grid: {
          right: 140,
          left: 60,
          top: 80,
        },
        series: this.seriesList,
      };
    },
  },
  created() {
    this.insertData();
    this.updateOption();
  },
  mounted() {
    this.$nextTick(() => {
      const el = document.getElementById('chart');
      this.chart = echarts.init(el);
		  this.chart.setOption(this.option);
    });
  },
};

// var countries = ['Australia', 'Canada', 'China', 'Cuba', 'Finland', 'France', 'Germany', 'Iceland', 'India', 'Japan', 'North Korea', 'South Korea', 'New Zealand', 'Norway', 'Poland', 'Russia', 'Turkey', 'United Kingdom', 'United States'];
// var countries = ['Finland', 'France', 'Germany', 'Iceland', 'Norway', 'Poland', 'Russia', 'United Kingdom'];
// var datasetWithFilters = [];
// var seriesList = []

</script>

<style lang="less">
#chart	{
	width: 100%;
	height: 100%;
	padding: 20px;
}
</style>
