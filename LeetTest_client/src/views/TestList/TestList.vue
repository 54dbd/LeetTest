<script setup>
import { onMounted, onUnmounted } from "vue";

//example components
import DefaultNavbar from "../../examples/navbars/NavbarDefault.vue";
import DefaultFooter from "../../examples/footers/FooterDefault.vue";
import Pagination from "../../components/MaterialPagination.vue"
//image
import bg0 from "@/assets/img/bg9.jpg";

//dep
import Typed from "typed.js";

// //sections
// import Information from "./Sections/AboutInformation.vue";
// import AboutTeam from "./Sections/AboutTeam.vue";
// import Featuring from "./Sections/AboutFeaturing.vue";
// import Newsletter from "./Sections/AboutNewsletter.vue";

const body = document.getElementsByTagName("body")[0];
//hooks
onMounted(() => {
  body.classList.add("about-us");
  body.classList.add("bg-gray-200");

  if (document.getElementById("typed")) {
    // eslint-disable-next-line no-unused-vars
    var typed = new Typed("#typed", {
      stringsElement: "#typed-strings",
      typeSpeed: 90,
      backSpeed: 90,
      backDelay: 200,
      startDelay: 500,
      loop: true,
    });
  }
});

onUnmounted(() => {
  body.classList.remove("about-us");
  body.classList.remove("bg-gray-200");
});
</script>
<template>
  <DefaultNavbar
    :action="{
      route: 'javascript:;',
      label: 'Buy Now',
      color: 'btn-white',
    }"
    transparent
  />
  <header class="bg-gradient-dark">
    <div
      class="page-header min-vh-75"
      :style="{ backgroundImage: `url(${bg0})` }"
    >
      <span class="mask bg-gradient-dark opacity-6"></span>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-8 text-center mx-auto my-auto">
            <h1 class="text-white">
              LeetTest<span class="text-white" id="typed"></span>题库
            </h1>
            <div id="typed-strings">
              <h1>考研政治</h1>
              <h1>考研数学</h1>
              <h1>计算机408</h1>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>

  <div class="card card-body blur shadow-xl mx-3 mx-md-7 mt-n3">
    <!--    TabBar -->
    <div class="row">
      <div class="col-lg-4 mx-auto">
        <div class="nav-wrapper position-relative end-0">
          <ul class="nav nav-pills nav-fill p-1" role="tablist">
            <li class="nav-item">
              <a
                class="nav-link mb-0 px-0 py-1 active"
                data-bs-toggle="tab"
                role="tab"
                aria-controls="profile"
                aria-selected="true"
              >
                政治
              </a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link mb-0 px-0 py-1"
                data-bs-toggle="tab"
                role="tab"
                aria-controls="profile"
                aria-selected="true"
              >
                英语
              </a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link mb-0 px-0 py-1"
                data-bs-toggle="tab"
                role="tab"
                aria-controls="dashboard"
                aria-selected="false"
              >
                计算机408
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <ul v-for="test in testList" :key="test">
      <li>
        {{ test }}
      </li>
    </ul>
<!--    ElementUI solution-->
    <el-col :xs="16" :sm="18" :md="19" :lg="18" :xl="20" class="elCol">
      <el-table
          v-loading="loading"
          :data="testList"
          border
          stripe
          lazy
          style="margin-top: 10px;margin-left: 20%">
        <el-table-column
            fixed
            label="题目"
            width="140">
          <template slot-scope="scope">
            <svg t="1646188413276" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2311" width="18" height="18"><path d="M945.4 383.3l-399-204.6c-23.7-12.1-51.9-12.1-75.5 0.2L77.2 383.4c-9.8 5.1-15.8 15.1-15.8 26.1 0 11 6.1 21 15.8 26.1l37.5 19.5v193.5c-14.8 8.3-25 24-25 42.2 0 26.8 21.7 48.5 48.5 48.5s48.5-21.7 48.5-48.5c0-18.2-10.1-33.9-25-42.2v-169l104.5 54.3V746c0 25.7 16.5 48.2 41 56L432 841.7c25.6 8.1 52 12.2 78.3 12.2 25.3 0 50.6-3.7 75.2-11.2l134.1-40.8c24.8-7.6 41.6-30.1 41.7-56l0.8-216.2 183.3-94c9.9-5.1 16-15.1 16-26.1-0.1-11.2-6.2-21.2-16-26.3zM714.2 745.7c0 5.2-3.4 9.7-8.3 11.2l-134.1 40.8c-41.2 12.5-84.6 12.2-125.7-0.8l-124.7-39.6c-4.9-1.6-8.2-6.1-8.2-11.2v-188L470.9 640c11.9 6.2 24.9 9.3 37.9 9.3 12.9 0 25.8-3 37.6-9.1l168.5-86.4-0.7 191.9zM525 598.4c-10.1 5.2-22.2 5.2-32.3-0.1L129 409.4l363.6-188.8c10.1-5.3 22.2-5.3 32.3-0.1l368.4 188.9-368.3 189z" p-id="2312" fill="#1296db"></path></svg>
            <el-link type="success">
              <router-link :to="{path: '/test',query: {tid: scope.row.tid}}" style="color: #5cb87a; text-decoration: none; font-weight: bold;">{{scope.row.sname}}</router-link>
            </el-link>
          </template>
        </el-table-column>

        <el-table-column
            sortable
            prop="num"
            label="做题人数"
            width="110">
          <template slot-scope="scope">
            <el-tag type="success">{{scope.row.num}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            sortable
            prop="accuracy"
            label="准确率"
            width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.accuracy">{{(scope.row.accuracy*100).toFixed(2)+'%'}}</el-tag>
            <el-tag v-if="!scope.row.accuracy">暂无数据</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            label="难度"
            width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.difficulty" type="warning">{{scope.row.difficulty}}</el-tag>
            <el-tag v-if="!scope.row.difficulty" type="warning">暂无数据</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="computerSubject"
            label="题目类型"
            width="120">
          <template slot-scope="scope">
            <el-card v-if="scope.row.atype==0" shadow="hover">
              简答题
            </el-card>
            <el-card v-if="scope.row.atype==1" shadow="hover">
              选择题
            </el-card>
          </template>
        </el-table-column>
        <el-table-column
            prop="points"
            label="知识点"
            width="400">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.points" type="danger">{{scope.row.points}}</el-tag>
            <el-tag v-if="!scope.row.points" type="danger">暂无数据</el-tag>
          </template>
        </el-table-column>

      </el-table>
    </el-col>
<!--  Pagination-->
    <div class="row mx-auto">
      <Pagination>
      </Pagination>
    </div>
  </div>
  <DefaultFooter />
</template>
<script>
import { getToken } from "@/utils/token";

export default {
  name: "testList",
  components: {},
  data() {
    return {
      testList: [],
      pageParam: {
        current: 1,
        size: 20,
      },
      // 总页数
      pages: 0,
      // 总条数
      total: 0,
      keyWord: "",
      loading: true,
      subject: "政治",
    };
  },
  methods: {
    async getTestList(pageParam) {
      //const result = await this.$API.reqGetSchoolList(pageParam)
      // if (!getToken()) {
      //   this.$message.warning("未登录，请先登录~");
      //   await this.$router.push("/login");
      //   return;
      // }
      const result = await this.$API.reqGetTestList(pageParam);
      if (result.data.code === 200) {
        this.testList = result.data.data.testVoList;
        this.pages = result.data.data.pages;
        this.total = result.data.data.total;
        this.loading = false;
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
      }
    },
    currentChange(val) {
      this.pageParam.current = val;
      if (this.keyWord) {
        this.searchTest();
      } else {
        this.getTestList(this.pageParam);
      }
    },
    async searchTest(current) {
      if (current) {
        this.pageParam.current = current;
      }
      const result = await this.$API.reqSearchTest({
        text: this.keyWord,
        current: this.pageParam.current,
        size: this.pageParam.size,
        uid: this.$store.state.user.userInfo.userId,
      });
      if (result.data.code === 200) {
        this.testList = result.data.data.testVoList;
        this.pages = result.data.data.pages;
        this.total = result.data.data.total;
        this.loading = false;
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
      }
    },
    zz: function () {
      this.subject = "政治";
      this.keyWord = this.subject;
    },
    cs: function () {
      this.subject = "四零八";
      this.keyWord = this.subject;
    },
    math: function () {
      this.subject = "数学";
      this.keyWord = this.subject;
    },
  },
  mounted() {
    this.getTestList(this.pageParam);
  },
  watch: {
    keyWord() {
      this.searchTest(1);
    },
  },
};
</script>
