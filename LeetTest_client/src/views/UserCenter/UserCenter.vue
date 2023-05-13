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
    <el-col class="py-5 position-relative mt-n8 text-center blur-shadow-avatar">
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
import * as api from "@/api";

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
      dayNumList: [],
      dayCorrectNumList: [],
      dayList: [],
    };
  },
  methods: {
    async getDayNumById() {
      const result = await api.getDayNumById(this.userid);
      if (result.data.code === 200) {
        let temp = result.data.data;
        temp.forEach((item) => {
          const [time, value] = Object.entries(item)[0];
          this.dayList.push(time);
          this.dayNumList.push({
            value: value,
            time: time,
          });
        });
      } else {
        this.$message.warning("获取失败~ " + result.msg);
      }
    },
    async getDayCorrectNumById() {
      const result = await api.getDayCorrectNumById(this.userid);

      if (result.data.code === 200) {
        let temp = result.data.data;
        console.log(temp);
        temp.forEach((item) => {
          const [time, value] = Object.entries(item)[0];
          this.dayCorrectNumList.push({
            value: value,
            time: time,
          });
        });
        console.log(this.dayCorrectNumList);
      } else {
        this.$message.warning("获取失败~ " + result.msg);
      }
    },
    async initEchart() {
      await this.getDayNumById();
      await this.getDayCorrectNumById();
      const chartDom = document.getElementById("lineChart");
      let myChart = echarts.init(chartDom, null, {
        renderer: "canvas",
        useDirtyRect: false,
      });
      let option;

      option = {
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: this.dayList,
        },
        yAxis: {
          type: "value",
        },
        legend: {
          show: true,
          data: ["做题数", "正确数"],
        },
        tooltip: {
          trigger: "axis",
        },
        series: [
          {
            name: "做题数",
            data: this.dayNumList,
            type: "line",
            areaStyle: {},
          },
          {
            name: "正确数",
            data: this.dayCorrectNumList,
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
  },

  mounted() {
    this.userid = this.$store.state.user.userInfo.userId;
    this.initEchart();
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
