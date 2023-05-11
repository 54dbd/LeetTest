<script setup>
// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";

// sections
import Footer from "@/examples/footers/FooterDefault.vue";

// image
import image from "@/assets/img/city-profile.jpg";
import MaterialAvatar from "@/components/MaterialAvatar.vue";
import UserPanel from "@/views/UserCenter/UserPanel.vue";
import store from "@/stores"; // 引入store实例
const userName = store.state.user.userInfo.username;
</script>
<template>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header min-height-400"
      :style="{ backgroundImage: `url(${image})` }"
    >
      <span class="mask bg-gradient-dark opacity-8"></span>
    </div>
  </Header>
  <div class="card card-body blur shadow-blur mx-3 mt-n6">
    <el-col
      class="py-5 position-relative mt-n8 text-center blur-shadow-avatar"
    >
      <MaterialAvatar
        size="xxl"
        class="shadow-xl position-relative z-index-2"
        :image="store.state.user.userInfo.userImg"
        alt="Avatar"
      />
      <h2>
        {{ userName }}
      </h2>
    </el-col>
    <UserPanel />
    <div id="lineChart"></div>
  </div>
  <Footer />
</template>

<script>
const lineChartData = {
  newVisitis: {
    expectedData: [0, 0, 0, 0, 2, 22, 0],
    actualData: [0, 0, 0, 0, 0, 7, 0],
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130],
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130],
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130],
  },
};
import * as echarts from "echarts";

export default {
  name: "UserCenter",
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      userid: "",
    };
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type];
    },
  },
  mounted() {
    const chartDom = document.getElementById("lineChart");
    var myChart = echarts.init(chartDom, null, {
      renderer: "canvas",
      useDirtyRect: false,
    });

    var option;

    option = {
      xAxis: {
        type: "category",
        boundaryGap: false,
        data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
      },
      yAxis: {
        type: "value",
      },
      series: [
        {
          data: [820, 932, 901, 934, 1290, 1330, 1320],
          type: "line",
          areaStyle: {},
        },
      ],
    };

    if (option && typeof option === "object") {
      myChart.setOption(option);
    }

    window.addEventListener("resize", myChart.resize);
  },
};
</script>

<style lang="scss" scoped>
#lineChart {
  width: 80%;
  height: 30rem;
}
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

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}

#lineChart {
  width: 100%;
  height: 360px;
}
</style>
