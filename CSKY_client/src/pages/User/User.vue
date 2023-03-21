<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>
    <el-row >
      <el-col :xs="24" :sm="24" :lg="24">
        <div class="chart-wrapper" style="justify-content: center">
          <pie-chart />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from '../../utils/components/PanelGroup'
import LineChart from '../../utils/components/LineChart'
import RaddarChart from '../../utils/components/RaddarChart'
import PieChart from '../../utils/components/PieChart'
const lineChartData = {
  newVisitis: {
    expectedData: [0, 0, 0, 0, 2, 22, 0],
    actualData: [0, 0, 0, 0, 0, 7, 0]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}
export default {
  name: "User",
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,

  },

  data() {

    return {
      lineChartData: lineChartData.newVisitis,
      userid:'',
    }
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },

  },

}

</script>

<style lang="scss" scoped>
.dashboard-editor-container {
 padding: 32px;
 background-color: rgb(240, 242, 245);
 position: relative;



.chart-wrapper {
  background: #fff;
  padding: 16px 16px 0;
  margin-bottom: 32px;
}
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>