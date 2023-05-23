<script setup>
import { reqGetRecruitInfo } from "@/api";
import BuiltByDevelopers from "@/views/Presentation/Components/BuiltByDevelopers.vue";
</script>

<template>
  <div class="container mt-sm-5">
    <BuiltByDevelopers
      title="招考信息"
      :subtitle="sname"
      :links="recruitInfoVoList"
      :pagesize="false"
      id="list4"
      url="https://raw.githubusercontent.com/creativetimofficial/public-assets/master/soft-ui-design-system/assets/img/desktop.jpg"
    />
  </div>
</template>

<script>
export default {
  name: "index",
  props: ["schoolIntroduce"],
  data() {
    return {
      sid: 0,
      sname: "",
      recruitment: "",
      recruitInfoVoList: [],
      current: 0,
      size: 0,
      total: 0,
    };
  },
  watch: {
    async schoolIntroduce(sc) {
      this.sid = sc.sid;
      this.sname = sc.sname;
      this.recruitment = sc.recruitment;
      await this.getRecruitInfo();
    },
  },
  methods: {
    currentChange(current) {
      this.current = current;
      this.getRecruitInfo();
    },
    async getRecruitInfo() {
      const result = await reqGetRecruitInfo({
        schoolId: this.sid,
        page: {
          current: this.current,
          size: 100,
        },
      });
      if (result.data.code === 200) {
        this.recruitInfoVoList = result.data.data.recruitInfoVoList;
        this.current = result.data.data.current;
        this.size = result.data.data.size;
        this.total = result.data.data.total;
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
      }
    },
  },
};
</script>

<style scoped>
h1 {
  font-size: 22px;
}
.elCol1 {
  margin: 14px 0;
  /*width: 700px;*/
  /*float: left;*/
}
.liLink {
  font-size: 15px;
  margin: 12px auto;
}
.elCol2 {
  width: 348px;
  margin: 14px 6px;
  height: 400px;
}
</style>
