<script setup>
import {reqGetScoreLineBySid} from "@/api";
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
import image from "@/assets/img/city-profile.jpg";
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
        class="page-header min-height-200"
        :style="{ backgroundImage: `url(${image})` }"
        loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-8"></span>
    </div>
  </Header>

  <div>
    <el-row :gutter="20">
      <el-col :span="20" :offset="2">
        <el-breadcrumb separator-class="el-icon-arrow-right" class="bread">
          <el-breadcrumb-item :to="{ path: '/scoreline' }">考研分数线汇总</el-breadcrumb-item>
          <el-breadcrumb-item>
            <div v-if="scorelineList[0]">{{scorelineList[0].sname}}</div>
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :xs="4" :sm="3" :md="2" :lg="3" :xl="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
      <el-col :xs="16" :sm="18" :md="19" :lg="18" :xl="20">
        <el-table
            border
            stripe
            v-loading="loading"
            :data="scorelineList"
            lazy
            style="width: 100%; margin-top: 10px;">
          <el-table-column
              label="序号"
              type="index"
              fixed
              width="50">
          </el-table-column>
          <el-table-column
              prop="sname"
              fixed
              label="学校名称"
              width="140">
          </el-table-column>
          <el-table-column
              fixed
              prop="collegeName"
              label="学院名称"
              width="110">
          </el-table-column>
          <el-table-column
              prop="majorCode"
              label="专业编码"
              width="80">
          </el-table-column>
          <el-table-column
              prop="majorName"
              label="专业名称"
              width="100">
          </el-table-column>
          <el-table-column
              label="专业方向"
              width="90">
            <template v-slot:default="scope">
              <div v-if="scope.row.majorDir">{{scope.row.majorDir}}</div>
              <div v-if="!scope.row.majorDir">无</div>
            </template>
          </el-table-column>
          <el-table-column
              sortable
              prop="score21"
              label="2021年分数线"
              width="90">
            <template v-slot:default="scope">
              <el-tag type="warning">{{scope.row.score21}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
              sortable
              prop="score20"
              label="2020年分数线"
              width="90">
            <template v-slot:default="scope">
              <el-tag type="danger">{{scope.row.score20}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
              sortable
              prop="score19"
              label="2019年分数线"
              width="90">
            <template v-slot:default="scope">
              <el-tag type="success">{{scope.row.score19}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
              prop="politicsSubject"
              label="政治科目"
              width="90">
          </el-table-column>
          <el-table-column
              prop="mathSubject"
              label="数学科目"
              width="100">
          </el-table-column>
          <el-table-column
              prop="englishSubject"
              label="英语科目"
              width="100">
          </el-table-column>
          <el-table-column
              prop="computerSubject"
              label="专业课"
              width="180">
            <template v-slot:default="scope">
              <el-card shadow="hover">
                {{scope.row.computerSubject}}
              </el-card>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :xs="4" :sm="3" :md="3" :lg="2" :xl="2">
        <div class="grid-content bg-purple-light"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  name: "ScoreLine",
  data() {
    return {
      scorelineList: [],
      loading: true
    }
  },
  mounted() {
    this.getScoreLineBySid()
  },
  methods: {
    async getScoreLineBySid() {
      const result = await reqGetScoreLineBySid(this.$route.query.sid)
      console.log(result)
      if (result.data.code === 200) {
        this.scorelineList = result.data.data
        this.loading = false
      } else {
        this.$message.error(result.data.msg)
      }
    },
    currentChange(current) {
      this.current = current
      this.getScoreLineList()
    }
  }
}
</script>

<style scoped>
</style>