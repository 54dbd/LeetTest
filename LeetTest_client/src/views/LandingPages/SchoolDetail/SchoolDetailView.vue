<script setup>
import {reqGetSchoolDetailBySid} from "@/api";
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import image from "@/assets/img/city-profile.jpg";
import Header from "@/examples/Header.vue";
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
          class="page-header min-height-200"
          :style="{ backgroundImage: `url(${image})` }"
          loading="lazy"
      >
        <span class="mask bg-gradient-dark opacity-8"></span>
      </div>
    </Header>
    <div class="all">
      <school-introduce :schoolIntroduce="{
        introduction: school.introduction,
        address: school.address,
        phone: school.phone,
        officialSite: school.officialSite,
        postgraduateSite: school.postgraduateSite,
        sname: school.sname
     }"></school-introduce>
      <profession-introduce :schoolIntroduce="{
       sid: school.sid,
       sname: school.sname,
       recruitment: school.recruitment
     }"></profession-introduce>
      <teacher-list :schoolIntroduce="{
       sname: school.sname,
     }"></teacher-list>
    </div>
  </div>
</template>

<script>
import ProfessionIntroduce from '@/components/myComponents/ExamInfo.vue';
import SchoolIntroduce from "@/components/myComponents/SchoolIntroduce.vue";
import TeacherList from '@/components/myComponents/TeacherList.vue';
export default {
  name: "SchoolDetail",
  components: {
    SchoolIntroduce,
    ProfessionIntroduce,
    TeacherList
  },
  data() {
    return {
      school: {}
    }
  },
  methods: {
    async getSchoolDetail(sid) {
      const result = await reqGetSchoolDetailBySid(sid);
      if (result.data.code === 200) {
        this.school = result.data.data;
      } else {
        this.$message.warning('系统异常~ ' + result.data.msg);
      }
    }
  },
  mounted() {
    this.getSchoolDetail(this.$route.query.sid);
  }
}
</script>

<style scoped>

.all {
  margin: 20px 30px;
}

</style>