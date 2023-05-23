<script setup>
import { reqGetRecruitInfo } from "@/api";
</script>

<template>
  <div class="container mt-sm-5">
    <div
      class="page-header py-6 py-md-5 my-sm-3 mb-3 border-radius-xl"
      :style="{
        backgroundImage:
          'url(https://images.unsplash.com/photo-1505664194779-8beaceb93744?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3870&q=80)',
      }"
      loading="lazy"
    >
      <span class="mask bg-gradient-dark"></span>
      <div class="container">
        <div class="row">
          <div class="col-lg-6 ms-lg-5">
            <h1 class="text-white">招考信息</h1>
            <el-row style="height: auto" :gutter="20">
              <el-col class="elCol1" :span="14" :push="2">
                <div>
                  <ul>
                    <li
                      class="liLink"
                      v-for="item in recruitInfoVoList"
                      :key="item.articleId"
                    >
                      <router-link
                        :to="{
                          path: '/article',
                          query: {
                            id: item.articleId,
                          },
                        }"
                        style="text-decoration: none; color: #049cdb"
                        >{{ item.title }}</router-link
                      >
                    </li>
                  </ul>
                  <el-pagination
                    style="text-align: center"
                    :page-size="size"
                    small
                    layout="prev, pager, next"
                    @current-change="currentChange"
                    :total="total"
                  >
                  </el-pagination>
                </div>
              </el-col>
              <el-col class="elCol2" :span="5" :push="2">
                <div>
                  <el-descriptions
                    direction="vertical"
                    :column="1"
                    size="medium"
                    :colon="false"
                  >
                    <el-descriptions-item>
                      <a
                        style="color: #4cb9fc"
                        :href="
                          recruitment.indexOf('URL: ') === -1
                            ? recruitment
                            : recruitment.slice(4)
                        "
                        target="_blank"
                      >
                        招生简章
                      </a>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <router-link
                        style="color: #4cb9fc"
                        :to="{
                          path: '/scoreLine',
                          query: {
                            sid: sid,
                          },
                        }"
                      >
                        分数线
                      </router-link>
                    </el-descriptions-item>
                  </el-descriptions>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
    </div>
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
          size: 10,
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
