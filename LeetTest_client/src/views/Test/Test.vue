<script setup>
// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
//example components

import DefaultFooter from "../../examples/footers/FooterDefault.vue";

// image
import image from "@/assets/img/city-profile.jpg";
import MarkDown from "@/components/MarkDown/MarkDown.vue";
import GreenHeaderBox from "@/components/myComponents/GreenHeaderBox.vue";
import OtherOption from "@/views/Test/OtherOption.vue";
import MaterialButton from "@/components/MaterialButton.vue";
import MaterialInput from "@/components/MaterialInput.vue";
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
              <OtherOption
                @comment="comment"
                @check-comment="checkComment"
                @check-history="checkHistory"
              ></OtherOption>
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
          :corrected="correctedMode"
          :corrrectedNext="correctedNextId"
          :corrrectedLast="correctedLastId"
          @flush="flush"
        ></test-introduce>
      </div>
    </div>
  </div>
  <DefaultFooter />
  <el-dialog width="80%" v-model="commentDialogVisible">
    <GreenHeaderBox title="发表心得">
      <el-row style="padding: 10px 5px 0 5px">
        <el-col :span="2">
          <h1 style="font-size: 20px; margin-top: 10px">评论标题</h1>
        </el-col>
        <el-col :span="19">
          <MaterialInput
            v-model="title"
            style="margin: 5px"
            class="input-group-static mb-4"
            type="message"
          />
          <!--          <el-input v-model="title" placeholder="请输入内容"></el-input>-->
        </el-col>
        <el-col :span="3">
          <MaterialButton
            variant="gradient"
            color="success"
            size="md"
            style="width: 90%; margin: 10px"
            type="button"
            @click="saveComment"
            >发布评论
          </MaterialButton>
          <!--          <el-button type="success">发布评论 </el-button>-->
        </el-col>
      </el-row>

      <mavon-editor
        v-model="mdText"
        class="me-editor"
        ref="md"
        @imgAdd="$imgAdd"
      ></mavon-editor>
    </GreenHeaderBox>
  </el-dialog>
  <el-dialog
    width="80%"
    v-model="checkCommentDialogVisible"
    class="commentList"
  >
    <div>
      <div v-if="testCommentList.length === 0">
        <GreenHeaderBox title="暂无心得"> &nbsp; </GreenHeaderBox>
      </div>
      <div v-for="item in testCommentList" :key="item.commentid">
        <GreenHeaderBox :title="item.title">
          <MarkDown :text="item.commenttext" class="content" />
        </GreenHeaderBox>
      </div>
    </div>
  </el-dialog>

  <el-dialog width="80%" v-model="checkHistoryDialogVisible">
    <GreenHeaderBox title="答题记录">
      <p v-if="problemList.length === 0">暂无记录</p>
      <el-card
        v-for="item in problemList"
        :key="item.id"
        :class="[item.iscorrect ? rightAnswerCls : wrongAnswerCls]"
      >
        <div
          style="font-size: larger; font-weight: bolder"
          v-if="item.score != null"
        >
          {{ "得分：" + item.score }}
        </div>
        <div style="">{{ item.wronganswer }}</div>
        <div style="color: #7a7a7a; font-size: small">
          {{ item.createdate }}
        </div>
      </el-card>
    </GreenHeaderBox>
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
      userid: this.$store.state.user.userInfo.userId,
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
      // cls
      wrongAnswerCls: "wrong_answer_box",
      rightAnswerCls: "right_answer_box",
      correctedMode: this.$route.query.corrected === "true",
      correctedTestNumber: 0,
      correctedTestList: {},
      correctedNextId: 0,
      correctedLastId: 0,
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
    updateNextAndLastPage() {
      if (this.correctedTestList.length > 0) {
        if (this.correctedTestNumber < this.correctedTestList.length - 1)
          this.correctedNextId =
            this.correctedTestList[this.correctedTestNumber + 1].tid;
        else {
          this.correctedNextId =
            this.correctedTestList[this.correctedTestNumber].tid;
        }
        if (this.correctedTestNumber > 1)
          this.correctedLastId =
            this.correctedTestList[this.correctedTestNumber - 1].tid;
        else
          this.correctedLastId =
            this.correctedTestList[this.correctedTestNumber].tid;
      }
    },
    async getCorrectedTestList() {
      const result = await api.reqGetCorrectedTestById(this.userid);

      if (result.data.code === 200) {
        this.correctedTestList = result.data.data;
      } else {
        this.$message.warning("系统异常~ " + result.data.msg);
      }
      this.updateNextAndLastPage();
    },
    flush: async function (num, forward = true) {
      if (this.correctedMode === true && num !== this.tid) {
        if (forward) this.correctedTestNumber++;
        else this.correctedTestNumber--;
        if (this.correctedTestNumber >= this.correctedTestList.length) {
          this.correctedTestNumber = this.correctedTestList.length - 1;
        } else if (this.correctedTestNumber <= 0) {
          this.correctedTestNumber = 0;
        }
        this.updateNextAndLastPage();
        await this.getTestDetail(
          this.correctedTestList[this.correctedTestNumber].tid
        );
      } else {
        if (num >= 1 && num <= 302) await this.getTestDetail(num);
      }
    },
    async comment() {
      if (!getToken()) {
        this.$message.warning("当前尚未登录，请先登录");
        await this.$router.push("/login");
        return;
      }
      this.commentDialogVisible = true;
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
    await this.getCorrectedTestList();
    await this.getTestDetail(this.$route.query.tid);
  },
  async activated() {
    await this.getCorrectedTestList();
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
  display: flex;
  flex-wrap: wrap;
  margin: 10px auto;
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
.wrong_answer_box {
  background-color: #fef0f0;
  margin: 10px;
}
.right_answer_box {
  background-color: #e5f3ed;
  margin: 10px;
}
</style>
