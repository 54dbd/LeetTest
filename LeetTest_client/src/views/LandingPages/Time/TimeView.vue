<script setup>
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
</script>

<template>
  <div class="container position-sticky z-index-sticky top-0">
    <div class="row">
      <div class="col-12">
        <DefaultNavbar :sticky="true" />
      </div>
    </div>
  </div>
  <Header>
    <div
      class="page-header min-vh-70"
      :style="{
        backgroundImage:
          'url(https://images.unsplash.com/photo-1497294815431-9365093b7331?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80)',
      }"
    >
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-8 text-center mx-auto my-auto">
            <h1 class="text-white">
              考研时间线<span class="text-white"></span>
            </h1>
          </div>
        </div>
      </div>
    </div>
  </Header>

  <div class="card card-body blur shadow-blur mx-3 mx-md-10 mt-n6">
    <div class="row mx-md-6">
      <el-timeline>
        <el-timeline-item size="large" timestamp="1-2月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>在线估分</h4>
            <p>
              <router-link
                :to="{
                  path: '/article',
                  query: {
                    id: 358,
                  },
                }"
                style="color: #42b983"
                >初试后关注的八件事
              </router-link>
            </p>
            <p>
              <router-link
                :to="{
                  path: '/article',
                  query: {
                    id: 359,
                  },
                }"
                style="color: #42b983"
                >如何预判分数及复试准备
              </router-link>
            </p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="2-3月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>成绩查询</h4>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="3月上旬" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>34所复试线</h4>
            <p>
              <router-link
                :to="{
                  path: '/scoreLineList',
                  query: {
                    id: 359,
                  },
                }"
                style="color: #42b983"
                >历年复试分数线汇总
              </router-link>
            </p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="3月中旬" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>考研国家线</h4>
            <p>历年考研国家线</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="3-4月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>考研复试</h4>
            <p>复试信息</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="3月18-4月30" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>考研调剂</h4>
            <p>考研调剂信息</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="3-6月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>择校择业</h4>
            <p>如何择校择业</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="6-7月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>录取通知书</h4>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="7-8月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>考研大纲</h4>
            <p>
              <router-link
                :to="{
                  path: '/examQuestion',
                  query: {
                    id: 359,
                  },
                }"
                style="color: #42b983"
                >专业课复习资料
              </router-link>
            </p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="8-10月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>招生简章</h4>
            <p>简章</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="9-10月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>考研报名</h4>
            <p>考研预报名时间</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item size="large" timestamp="11月" placement="top">
          <el-card shadow="hover" class="elCard">
            <h4>现场确认</h4>
            <p>
              <router-link
                :to="{
                  path: '/article',
                  query: {
                    id: 361,
                  },
                }"
                style="color: #42b983"
                >现场确认所需材料
              </router-link>
            </p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item
          size="large"
          timestamp="11月15日-12月14日"
          placement="top"
        >
          <el-card shadow="hover" class="elCard">
            <h4>冲刺备考</h4>
            <p>冲刺备考攻略</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item
          size="large"
          timestamp="12月14日-12月25日"
          placement="top"
        >
          <el-card shadow="hover" class="elCard">
            <h4>准考证打印</h4>
            <p>打印时间：14日-25日</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item
          size="large"
          timestamp="12月23日-12月24日"
          placement="top"
        >
          <el-card shadow="hover" class="elCard">
            <h4>考研时间</h4>
            <p>
              <router-link
                :to="{
                  path: '/article',
                  query: {
                    id: 360,
                  },
                }"
                style="color: #42b983"
                >初试各科时间安排
              </router-link>
            </p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<style lang="less" scoped>
#home {
  background: url("~@/assets/img/illustrations/illustration-signin.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}

.box {
  width: 1300px;
  height: 800px;
  box-shadow: 0px 0px 7px #cdcdcd;
  position: absolute;
  top: 75%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #e6f0fe;
}

.u-dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  border: 4px solid #65c0ea;
  border-radius: 50%;
  background: #fff;
}
.u-desc {
  font-size: 16px;
  font-weight: 500;
}
.elCard {
}
.elCard img {
  height: 100px;
  width: 96px;
  float: left;
  padding: 5px;
  margin: 0 10px 0 -13px;
}
.elCard a {
  font-weight: bold;
  text-decoration: none;
  margin: -5px 0 5px 0;
  display: block;
}
.elCard div {
  font-size: 16px;
}
</style>
