<script setup>
import { reqGetSchoolDetailBySid } from "@/api";
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import image from "@/assets/img/city-profile.jpg";
import Header from "@/examples/Header.vue";
import GreenHeaderBox from "@/components/myComponents/GreenHeaderBox.vue";
</script>

<template>
  <div>
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
        :style="{ backgroundImage: `url(${image})` }"
      >
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-8 text-center mx-auto my-auto">
              <h1 class="text-white">
                <span class="text-white">学校介绍</span>
              </h1>
            </div>
          </div>
        </div>
      </div>
    </Header>

    <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6">
      <div class="row mx-md-6 mt-6">
        <el-row v-if="school.recruitment">
          <el-col :span="16"></el-col>
          <el-col :span="3">
            <div class="nav-item">
              <a
                class="btn btn-sm mb-0 btn-outline-success"
                :href="
                  school.recruitment.indexOf('URL: ') === -1
                    ? school.recruitment
                    : school.recruitment.slice(4)
                "
                onclick="smoothToPricing('pricing-soft-ui')"
                style="font-size: 15px"
                >招生简章</a
              >
            </div>
          </el-col>
          <el-col :span="3">
            <div class="nav-item">
              <router-link
                style="font-size: 15px"
                class="btn btn-sm mb-0 btn-outline-success"
                :to="{
                  path: '/scorelinedetail',
                  query: {
                    sid: school.sid,
                  },
                }"
              >
                分数线
              </router-link>
            </div>
          </el-col>
        </el-row>
        <school-introduce
          :schoolIntroduce="{
            introduction: school.introduction,
            address: school.address,
            phone: school.phone,
            officialSite: school.officialSite,
            postgraduateSite: school.postgraduateSite,
            sname: school.sname,
          }"
        ></school-introduce>
        <profession-introduce
          :schoolIntroduce="{
            sid: school.sid,
            sname: school.sname,
            recruitment: school.recruitment,
          }"
        ></profession-introduce>
        <teacher-list
          :schoolIntroduce="{
            sname: school.sname,
          }"
        ></teacher-list>
      </div>
    </div>
  </div>
</template>

<script>
import ProfessionIntroduce from "@/components/myComponents/ExamInfo.vue";
import SchoolIntroduce from "@/components/myComponents/SchoolIntroduce.vue";
import TeacherList from "@/components/myComponents/TeacherList.vue";
export default {
  name: "SchoolDetail",
  components: {
    SchoolIntroduce,
    ProfessionIntroduce,
    TeacherList,
  },
  data() {
    return {
      school: {},
    };
  },
  methods: {
    async getSchoolDetail(sid) {
      const result = await reqGetSchoolDetailBySid(sid);
      if (result.data.code === 200) {
        this.school = result.data.data;
      } else {
        this.$message.warning("系统异常~ " + result.data.msg);
      }
    },
  },
  mounted() {
    this.getSchoolDetail(this.$route.query.sid);
  },
  activated() {
    this.getSchoolDetail(this.$route.query.sid);
  },
};
</script>

<style scoped>
.all {
  margin: 20px 30px;
}
</style>
