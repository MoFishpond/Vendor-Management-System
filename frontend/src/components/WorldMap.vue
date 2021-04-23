<template>
  <div id="myMap">
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { LatLong, mapData } from '../assets/const.js';
import '../assets/world.js';

export default {
  data() {
    return {
      option: {
        backgroundColor: '#F9DCDE',
        title: {
          text: '销售情况分布图',
          left: 'center',
          top: 'top',
          textStyle: {
            color: '#fff',
          },
        },
        tooltip: {
          formatter(params) {
            console.log(params);
            return `${params.name} : ${params.data.value[2]}`;
          },
        },
        visualMap: {
          show: false,
          min: 0,
          max: 20000,
          inRange: {
            symbolSize: [6, 60],
          },
        },
        geo: {
          name: 'World Population (2010)',
          type: 'map',
          map: 'world',
          roam: true,
          label: {
            emphasis: {
              show: false,
            },
          },
          itemStyle: {
            normal: {
              areaColor: '#EF8594',
              borderColor: '#fff',
            },
            emphasis: {
              areaColor: '#ED6A7C',
            },
          },
        },
        series: [{
          type: 'scatter',
          coordinateSystem: 'geo',
          data: mapData.map((itemOpt) => ({
            name: itemOpt.name,
            value: [
              LatLong[itemOpt.code].longitude,
              LatLong[itemOpt.code].latitude,
              itemOpt.value,
            ],
            itemStyle: {
              normal: {
                color: itemOpt.color,
              },
            },
          })),
        }],
      },
      chart: null,
    };
  },
  method: {
    updateChart() {
		  this.chart.setOption(this.option);
    },
  },
  mounted() {
    this.$nextTick(() => {
      const el = document.getElementById('myMap');
      this.chart = echarts.init(el);
		  this.chart.setOption(this.option);
      // this.updateChart();
    });
  },
};

</script>

<style lang="less">
a:link, a:visited {
	color: #4682b4;
}

a:hover {
	color: #4169e1;
}

#myMap	{
	width: 100%;
	height: 100%;
}
</style>
