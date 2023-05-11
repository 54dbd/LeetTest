<script setup>
// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
//example components

import DefaultFooter from "../../examples/footers/FooterDefault.vue";

// image
import image from "@/assets/img/city-profile.jpg";
import MaterialButton from "@/components/MaterialButton.vue";
import MarkDown from "@/components/MarkDown/MarkDown.vue";
</script>
<template>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header min-height-200"
      :style="{ backgroundImage: `url(${image})` }"
      loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-8"></span>
    </div>
  </Header>
  <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6 mb-4">
    <div style="position: relative; left: 5%">
      <el-row style="position: relative; left: 2%">
        <el-col :span="20">
          <el-row>
            <el-col :span="23">
              <h1>{{ test.sname }}</h1>
              <h3 v-if="test.qtype !== 0">{{ test.qtype }}</h3>
            </el-col>
            <el-col :span="1">
              <div class="dropdown" style="margin-top: 10px">
                <MaterialButton
                  variant="gradient"
                  color="success"
                  class="dropdown-toggle"
                  :class="{ show: showDropdown }"
                  @focusout="showDropdown = false"
                  id="dropdownMenuButton"
                  data-bs-toggle="dropdown"
                  :area-expanded="showDropdown"
                  @click="showDropdown = !showDropdown"
                >
                  更多功能
                </MaterialButton>
                <ul
                  class="dropdown-menu px-2 py-3"
                  :class="{ show: showDropdown }"
                  aria-labelledby="dropdownMenuButton"
                >
                  <li @click="comment">
                    <a
                      class="dropdown-item border-radius-md"
                      href="javascript:;"
                      >发表心得</a
                    >
                  </li>
                  <li @click="checkComment">
                    <a
                      class="dropdown-item border-radius-md"
                      href="javascript:;"
                      >查看心得</a
                    >
                  </li>
                  <li @click="checkHistory">
                    <a
                      class="dropdown-item border-radius-md"
                      href="javascript:;"
                      >错题记录</a
                    >
                  </li>
                </ul>
              </div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <div>
        <!--这里是测试测试详细页面传入的参数 -->
        <test-introduce
          v-if="page === 'checkQuestion'"
          :testIntroduce="{
            question: test.question,
            answer: test.answer,
            difficulty: test.difficulty,
            qchoicea: test.qchoicea,
            qchoiceb: test.qchoiceb,
            qchoicec: test.qchoicec,
            qchoiced: test.qchoiced,
            num: test.num,
            correctnum: test.correctnum,
            points: test.points,
            analysis: test.analysis,
            sname: test.sname,
            accuracy: test.accuracy,
            qtype: test.qtype,
            atype: test.atype,
            tid: this.$route.query.tid,
          }"
          @flush="flush"
        ></test-introduce>
      </div>
    </div>
  </div>
  <DefaultFooter />
  <el-dialog width="80%" v-model="commentDialogVisible">
    <div>
      <div
        class="card d-flex blur justify-content-center shadow-lg my-sm-0 my-sm-6 mt-8 mb-5"
      >
        <div
          class="card-header p-0 position-relative mt-n4 mx-3 z-index-2 bg-transparent"
        >
          <div class="bg-gradient-success shadow-success border-radius-lg p-3">
            <h3 class="text-white text-success mb-0">发表心得</h3>
          </div>
          <div class="grid-content bg-purple"></div>
          <h1 style="font-size: 20px">评论标题</h1>
          <el-input v-model="title" placeholder="请输入内容"></el-input>
          <el-button type="success" @click="saveComment">发布评论 </el-button>
          <mavon-editor
            v-model="mdText"
            class="me-editor"
            ref="md"
            @imgAdd="$imgAdd"
          ></mavon-editor>
        </div>
      </div>
    </div>
  </el-dialog>
  <el-dialog
    width="80%"
    v-model="checkCommentDialogVisible"
    class="commentList"
  >
    <div>
      <p v-if="testCommentList.length === 0">暂无评论！</p>
      <div
        style="margin-bottom: 10px"
        v-for="item in testCommentList"
        :key="item.commentid"
      >
        <div
          class="card d-flex blur justify-content-center shadow-lg my-sm-0 my-sm-6 mt-8 mb-5"
        >
          <div
            class="card-header p-0 position-relative mt-n4 mx-3 z-index-2 bg-transparent"
          >
            <div
              class="bg-gradient-success shadow-success border-radius-lg p-3"
            >
              <h3 class="text-white text-success mb-0">{{ item.title }}</h3>
            </div>
            <MarkDown :text="item.commenttext" class="content" />
          </div>
        </div>
      </div>
    </div>
  </el-dialog>

  <el-dialog width="80%" v-model="checkHistoryDialogVisible">
    <div
      class="card d-flex blur justify-content-center shadow-lg my-sm-0 my-sm-6 mt-8 mb-5"
    >
      <div
        class="card-header p-0 position-relative mt-n4 mx-3 z-index-2 bg-transparent"
      >
        <div class="bg-gradient-success shadow-success border-radius-lg p-3">
          <h3 class="text-white text-success mb-0">错题记录</h3>
        </div>
        <p v-if="problemList.length === 0">暂无错题！</p>
        <div v-for="item in problemList" :key="item.id" class="text-view">
          <div v-if="item.iscorrect === true">
            <el-card
              style="
                width: 80%;
                font-size: 20px;
                font-weight: bold;
                background-color: #e5f3ed !important;
                border: #e1f3d8 1px solid;
              "
            >
              <h1 v-if="item.score != null">
                <i class="el-icon-success"></i>{{ "得分为：" + item.score }}
              </h1>
              <h2 style="white-space: normal">{{ item.wronganswer }}</h2>
              <h3>{{ item.createdate }}</h3>
            </el-card>
          </div>
          <div v-else>
            <el-card
              style="
                width: 80%;
                font-size: 20px;
                font-weight: bold;
                background-color: #fef0f0 !important;
                border: #fde2e2 1px solid;
              "
            >
              <h1 v-if="item.score != null">
                <i class="el-icon-warning"></i>{{ "得分为：" + item.score }}
              </h1>
              <h2 style="white-space: normal">{{ item.wronganswer }}</h2>
              <h3>{{ item.createdate }}</h3>
            </el-card>
          </div>
        </div>
      </div>
    </div>
  </el-dialog>
</template>
<script>
import TestIntroduce from "@/views/Test/TestIntroduce.vue";

import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import { getToken } from "@/utils/token";
import * as api from "@/api";

export default {
  name: "TestDetail",
  components: {
    TestIntroduce,
    mavonEditor,
  },
  data() {
    return {
      userid: 0,
      test: {},
      testCommentList: [],
      problemList: [],
      page: "checkQuestion",
      mdText: "",
      title: "",
      dialogVisible: false,
      tagIdList: [],
      schoolId: 0,
      form: {},
      schoolList: [],
      state: "",
      timeout: null,
      tagNameList: [],
      newTag: "",
      tid: this.$route.query.tid,
      pageParam: {
        current: 1,
        size: 5,
      },
      pages: 0,
      // 总条数
      total: 0,
      keyWord: "",
      loading: true,
      contentList: [],
      // 更多功能
      commentDialogVisible: false,
      checkCommentDialogVisible: false,
      checkHistoryDialogVisible: false,
    };
  },
  methods: {
    async getTestDetail(tid) {
      const result = await api.reqGetTestDetailByTid(tid);

      if (result.data.code === 200) {
        this.test = result.data.data;
      } else {
        this.$message.warning("系统异常~ " + result.data.msg);
      }
    },

    flush: async function (num) {
      if (num >= 1 && num <= 302) await this.getTestDetail(num);
    },
    async comment() {
      if (!getToken()) {
        this.$message.warning("当前尚未登录，请先登录");
        await this.$router.push("/login");
        return;
      }
      this.commentDialogVisible = true;
      //this.flush();
    },
    async checkComment() {
      this.testCommentList = await this.getTestCommentByQuestionid(
        this.test.sname
      );
      for (let i = 0; i < this.testCommentList.length; i++) {
        this.contentList.push(this.testCommentList[i].commenttext);
      }
      console.log(this.testCommentList);
      console.log(this.test.sname);
      this.checkCommentDialogVisible = true;
    },
    async getTestCommentByQuestionid(questionid) {
      const result = await api.reqGetTestCommentByQuestionid(questionid);
      console.log(
        "questionid=" + questionid + " get result " + result.data.questionid
      );
      if (result.data.code === 200) {
        console.log("获取评论成功！");
        return result.data.data;
      } else {
        console.log("获取评论失败！");
        return [];
      }
    },
    async getProblemCollectionBySname() {
      this.userid = this.$store.state.user.userInfo.userId;
      console.log("获取错题中" + this.test.sname + this.userid);
      const result = await api.reqGetProblemCollectionBySname(
        this.test.sname,
        this.userid
      );
      console.log(
        "Sname=" + this.test.sname + " get result " + result.data.sname
      );
      if (result.data.code === 200) {
        console.log("获取错题成功！");
        return result.data.data;
      } else {
        console.log("获取错题失败！");
        return [];
      }
    },

    async checkHistory() {
      this.problemList = await this.getProblemCollectionBySname();
      this.checkHistoryDialogVisible = true;
      //this.flush();
    },
    /**
     *
     * 发布评论方法
     */
    // 上传图片
    async $imgAdd(pos, $file) {
      // 第一步.将图片上传到服务器.
      var formdata = new FormData();
      formdata.append("image", $file);
      const result = await api.reqUploadImage(formdata);
      if (result.data.code === 200) {
        const url = result.data.data;
        this.$refs.md.$img2Url(pos, url);
      } else {
        this.$message.error("上传失败~");
      }
    },
    filterTagName(query, item) {
      return item.tagName.indexOf(query) > -1;
    },
    async saveComment() {
      if (!this.title) {
        this.$message.warning("请输入标题...");
        return;
      } else {
        console.log(this.title);
      }
      if (!this.mdText) {
        this.$message.warning("请输入文章内容...");
        return;
      }
      this.tagIdList[0] = this.test.sname;
      console.log("before request" + this.title);
      const result = await api.reqSaveComment({
        title: this.title,
        commenttext: this.mdText,
        userid: this.$store.state.user.userInfo.userId,
        questionid: this.test.sname,
      });
      console.log("after request");
      if (result.data.code === 200) {
        this.$notify.success("发布成功~");
        await this.$router.push({
          path: "/test",
          query: {
            id: result.data.data.data.commentid,
          },
        });
      } else {
        this.$message.error("系统异常~ " + result.data.data.msg);
      }
    },
    async getTagNameList() {
      const result = await api.reqGetTagNameList();
      if (result.data.code === 200) {
        this.tagNameList = result.data.data;
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
      }
    },

    async loadAll() {
      const result = await api.reqGetSchoolNameList();
      if (result.data.code === 200) {
        this.schoolList = result.data.data.map((school) => {
          return {
            value: school.sname,
            name: school.sid,
          };
        });
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
      }
    },
    querySearchAsync(queryString, cb) {
      let schoolList = this.schoolList;
      let results = queryString
        ? schoolList.filter(this.createStateFilter(queryString))
        : schoolList;

      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        cb(results);
      }, 500 * Math.random());
    },
    createStateFilter(queryString) {
      return (state) => {
        return (
          state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelect(item) {
      this.schoolId = item.name;
    },
    // 添加新的标签
    async addNewTag() {
      const result = await api.reqAddNewTag({ tagName: this.newTag });
      if (result.data.code === 200) {
        this.$message.success(result.data.msg);
        this.newTag = "";
        await this.getTagNameList();
      } else {
        this.$message.error(result.data.msg);
      }
    },
  },
  async mounted() {
    await this.getTestDetail(this.$route.query.tid);
  },
  async activated() {
    await this.getTestDetail(this.$route.query.tid);
  },
};
</script>

<style scoped>
.all {
  width: 90%;
  margin: 20px 200px;
}

.right_fbox {
  width: 250px;
  height: 360px;
  position: fixed;
  float: right;
  left: 57%;
  top: 20%;
  z-index: 999;
  margin-left: 530px;
}

.right_button_list {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.right_button {
  margin: 20px 0 !important;
}

#editor {
  height: 100px;
  margin: 20px 30px;
}

.me-editor {
  white-space: normal;
  display: flex;
  z-index: 6 !important;
  margin: 20px 0;
  flex-wrap: wrap;
}

.commentList {
  width: 80%;
}
.el-dialog-div {
  max-height: 60vh;
  overflow: auto;
}
</style>
<style>
.el-dialog__header {
  background: rgba(0, 0, 0, 0);

  padding: 0;
}
.el-dialog {
  background-color: rgba(0, 0, 0, 0);
}
.el-dialog__body {
  padding: 0;
  background: rgba(0, 0, 0, 0);
}
</style>
