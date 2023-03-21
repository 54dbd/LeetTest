<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="24" :sm="24" :lg="8" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('num')">
        <div class="card-panel-icon-wrapper icon-people">
          <i class="el-icon-s-order" style="width: 40px;height: 40px;font-size: 40px" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            做题数
          </div>
          <count-to :start-val="0" :end-val="num" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="8" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('accuracy')">
        <div class="card-panel-icon-wrapper icon-message">
          <i class="el-icon-s-marketing" style="width: 40px;height: 40px;font-size: 40px" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            正确数
          </div>
          <count-to :start-val="0" :end-val="correct" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="8" class="card-panel-col">
      <div class="card-panel" @click="set">
        <div class="card-panel-icon-wrapper icon-message">
          <i class="el-icon-s-data" style="width: 40px;height: 40px;font-size: 40px" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            正确率
          </div>
          <count-to :start-val="0" :end-val="accuracy" :decimals="2" :suffix="suffix" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>

  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
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
  components: {
    CountTo
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      userid:'',
      num:'0',
      correct:'0',
      accuracy:40,
      suffix:'%'
    }
  },
  methods: {
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    },
    set:function (){
      this.accuracy = (this.correct*100/this.num).toFixed(2);
    },
    async getNumById(){
      console.log("userid:"+this.userid)
      const result = await this.$API.reqGetNumById(this.userid);
      this.num = result.data.data.data
      console.log("num:"+this.num)

    },
    async getCorrectById(){
      console.log("userid:"+this.userid)
      const result = await this.$API.reqGetCorrectById(this.userid);
      this.correct = result.data.data.data
      console.log("correct:"+this.correct)

    }
  },
  mounted() {
    this.userid = this.$store.state.user.userInfo.userId;
    this.getNumById();
    this.getCorrectById();
  }
}
</script>

<style lang="scss" scoped>

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
