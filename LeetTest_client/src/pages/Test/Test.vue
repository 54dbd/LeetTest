<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="20" :offset="2">
        <el-breadcrumb separator-class="el-icon-arrow-right" class="bread">
          <el-breadcrumb-item to="/home">首页</el-breadcrumb-item>
          <el-breadcrumb-item to="/testList">题目列表</el-breadcrumb-item>
          <el-breadcrumb-item>{{test.sname}}</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-col class="all" >
      <!--这里是测试测试详细页面传入的参数 -->
      <test-introduce v-if="page=='checkQuestion'" :testIntroduce="{
        question: test.question,
        answer: test.answer,
        difficulty: test.difficulty,
        qchoicea: test.qchoicea,
        qchoiceb: test.qchoiceb,
        qchoicec: test.qchoicec,
        qchoiced: test.qchoiced,
        num:test.num,
        correctnum:test.correctnum,
        points:test.points,
        analysis:test.analysis,
        sname: test.sname,
        accuracy:test.accuracy,
        qtype:test.qtype,
        atype:test.atype,
        tid:this.$route.query.tid
     }"></test-introduce>

      <div v-if="page=='comment'">
        <div id="editor">
          <el-row :gutter="20" class="head">
            <el-col :xs="2" :sm="2" :md="2" :lg="2" :xl="2">
              <div class="grid-content bg-purple"></div>
            </el-col>
            <el-col :xs="2" :sm="2" :md="2" :lg="2" :xl="2">
              <h1 style="font-size: 20px;">评论标题</h1>
            </el-col>
            <el-col :xs="10" :sm="14" :md="14" :lg="14" :xl="14">
              <el-input v-model="title" placeholder="请输入内容"></el-input>
            </el-col>
            <el-col :xs="5" :sm="5" :md="5" :lg="5" :xl="5">
              <el-button type="success" @click="saveComment">发布评论</el-button>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="20" >
              <mavon-editor
                  v-model="mdText"
                  class="me-editor"
                  ref="md"
                  @imgAdd="$imgAdd"
              ></mavon-editor>
            </el-col>
          </el-row>
        </div>

      </div>


      <div v-if="page=='checkComment'" class="commentList" >
        <h1>做题心得</h1>
        <ul style="margin-top: 7px;">
          <h2 v-if="testCommentList.length==0" style="color: grey; margin-left: 50%" >暂无评论！</h2>
          <li style=" margin-bottom: 10px;" v-for="item in testCommentList" :key="item.commentid" class="text-view">
            <el-card >
              <!--<router-link :to="{path: '/test/comment',query: {commentid: item.commentid}}" style="color: #5cb87a; text-decoration: none; font-weight: bold;">{{item.title}}</router-link>-->
              <el-card style="font-size: 20px;font-weight: bold">{{item.title}}</el-card>
              <mavon-editor
                  class="me-editor"
                  :value="item.commenttext"
                  :subfield="false"
                  :defaultOpen="'preview'"
                  :toolbarsFlag="false"
                  :editable="false"
                  :scrollStyle="true"
                  :ishljs="true"
              ></mavon-editor>
            </el-card>
          </li>

        </ul>
      </div >
      <test-history v-if="page=='checkHistory'">
        <h1>错题记录</h1>
        <ul style="margin-top: 7px;">
          <h2 v-if="problemList.length==0" style="color: grey; margin-left: 40%" >暂无错题！</h2>
          <li style=" margin-bottom: 10px;" v-for="item in problemList" :key="item.id" class="text-view">
            <div v-if="item.iscorrect==true">
              <el-card style="width: 80%;font-size: 20px;font-weight: bold;background-color: #e5f3ed !important;border: #e1f3d8 1px solid">
                <h1 v-if="item.score!=null"><i class="el-icon-success" ></i>{{"得分为："+item.score}}</h1>
                <h2 style="white-space:normal;">{{item.wronganswer}}</h2>
                <h3>{{item.createdate}}</h3>
              </el-card>
            </div>
            <div v-else>
              <el-card style="width: 80%;font-size: 20px;font-weight: bold;background-color: #fef0f0 !important;border: #fde2e2 1px solid">
                <h1 v-if="item.score!=null"><i class="el-icon-warning" ></i>{{"得分为："+item.score}}</h1>
                <h2 style="white-space:normal;">{{item.wronganswer}}</h2>
                <h3>{{item.createdate}}</h3>
              </el-card>
            </div>

          </li>

        </ul>
      </test-history>
      <el-card class="right_fbox">
        <el-col class="right_button_list">
            <el-button type="primary" icon="el-icon-edit" class="right_button" @click="comment" >发表心得</el-button>
            <el-button type="primary" icon="el-icon-chat-line-round"class="right_button" @click="checkComment">查看评论</el-button>
            <el-button type="primary" icon="el-icon-notebook-1" class="right_button" @click="checkHistory">错题记录</el-button>
            <el-button type="primary" icon="el-icon-document" class="right_button" @click="checkQuestion">查看题目</el-button>
        </el-col>

      </el-card>
    </el-col>
  </div>
</template>

<script>
import TestIntroduce from '@/components/TestIntroduce/TestIntroduce'

import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import {getToken} from "@/utils/token";
import {reqSaveHistory} from "@/api";
export default {
  name: "TestDetail",
  components: {
    TestIntroduce,
    mavonEditor,


  },
  data() {
    return {
      userid:0,
      test: {},
      testCommentList: [],
      problemList:[],
      page:'checkQuestion',
      mdText: '',
      title: '',
      dialogVisible: false,
      tagIdList: [],
      schoolId: 0,
      form: {},
      schoolList: [],
      state: '',
      timeout: null,
      tagNameList: [],
      newTag: '',
      tid:this.$route.query.tid,
      pageParam: {
        current: 1,
        size: 5
      },
      pages: 0,
      // 总条数
      total: 0,
      keyWord: '',
      loading: true,
      contentList:[]

    }
  },
  methods: {
    async getTestDetail(tid) {
      const result = await this.$API.reqGetTestDetailByTid(tid)
      if (result.data.code === 200) {
        this.test = result.data.data
      } else {
        this.$message.warning('系统异常~ ' + result.data.msg)
      }
    },



    flush:function (){
      this.$router.go(0);
    },
    async comment(){
      if (!getToken()) {
        this.$message.warning('当前尚未登录，请先登录')
        await this.$router.push('/login')
        return
      }
      this.page='comment'

      //this.flush();

    },
    async checkComment (){
      this.testCommentList = await this.getTestCommentByQuestionid(this.test.sname)
      for (var i=0;i<this.testCommentList.length;i++){
        this.contentList.push(this.testCommentList[i].commenttext)
      }
      console.log(this.testCommentList)
      console.log(this.test.sname)
      this.page='checkComment'
    },
    async getTestCommentByQuestionid(questionid) {
      const result = await this.$API.reqGetTestCommentByQuestionid(questionid)
      console.log("questionid="+questionid+" get result "+result.data.questionid);
      if (result.data.code === 200) {
        console.log("获取评论成功！")
        return result.data.data
      } else {
        console.log("获取评论失败！")
        return []
      }

    },
    async getProblemCollectionBySname(){
      this.userid = this.$store.state.user.userInfo.userId;
      console.log("获取错题中"+this.test.sname+this.userid)
      const result = await this.$API.reqGetProblemCollectionBySname(this.test.sname,this.userid)
      console.log("Sname="+this.test.sname+" get result "+result.data.sname);
      if (result.data.code === 200) {
        console.log("获取错题成功！")
        return result.data.data
      } else {
        console.log("获取错题失败！")
        return []
      }
    },

    async checkHistory (){
      this.problemList = await this.getProblemCollectionBySname();
      this.page='checkHistory'
      //this.flush();

    },
    checkQuestion:function (){
      this.page='checkQuestion'
      this.flush();
    },
    /**
     *
     * 发布评论方法
     */
    // 上传图片
    async $imgAdd(pos, $file){
      // 第一步.将图片上传到服务器.
      var formdata = new FormData()
      formdata.append('image', $file)
      const result = await this.$API.reqUploadImage(formdata)
      if (result.data.code === 200) {
        const url = result.data.data
        this.$refs.md.$img2Url(pos, url);
      } else {
        this.$message.error('上传失败~')
      }
    },
    filterTagName(query, item) {
      return item.tagName.indexOf(query) > -1
    },
    async saveComment() {
      if (!this.title) {
        this.$message.warning('请输入标题...')
        return
      }else {
        console.log(this.title)
      }
      if (!this.mdText) {
        this.$message.warning('请输入文章内容...')
        return
      }
      this.tagIdList[0]=this.test.sname
      console.log("before request"+this.title)
      const result = await this.$API.reqSaveComment({
        title: this.title,
        commenttext: this.mdText,
        userid: this.$store.state.user.userInfo.userId,
        questionid: this.test.sname
      })
      console.log("after request")
      if (result.data.code === 200) {
        this.$notify.success('发布成功~')
        await this.$router.push({
          path: '/test',
          query: {
            id: result.data.data.data.commentid
          }
        })
      } else {
        this.$message.error('系统异常~ ' + result.data.data.msg)
      }
    },
    async getTagNameList() {
      const result = await this.$API.reqGetTagNameList()
      if (result.data.code === 200) {
        this.tagNameList = result.data.data
      } else {
        this.$message.error('系统异常~ ' + result.data.msg)
      }
    },

    async loadAll() {
      const result = await this.$API.reqGetSchoolNameList()
      if (result.data.code === 200) {
        this.schoolList = result.data.data.map((school) => {
          return {
            value: school.sname,
            name: school.sid,
          };
        });
      } else {
        this.$message.error('系统异常~ ' + result.data.msg)
      }
    },
    querySearchAsync(queryString, cb) {
      let schoolList = this.schoolList;
      let results = queryString ? schoolList.filter(this.createStateFilter(queryString)) : schoolList;

      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        cb(results);
      }, 500 * Math.random());
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    handleSelect(item) {
      this.schoolId = item.name
    },
    // 添加新的标签
    async addNewTag() {
      const result = await this.$API.reqAddNewTag({tagName: this.newTag})
      if (result.data.code === 200) {
        this.$message.success(result.data.msg)
        this.newTag = ''
        await this.getTagNameList()
      } else {
        this.$message.error(result.data.msg)
      }
    }
  },
  async mounted() {
    this.getTestDetail(this.$route.query.tid);

  },

}
</script>

<style scoped>
.all {
  width: 90%;
  margin: 20px 200px;
}

.right_fbox  {
  width:250px;
  height:360px;
  position: fixed;
  float: right;
  left:57%;
  top:20%;
  z-index: 999;
  margin-left:530px;

}
.right_button_list{
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.right_button{
  margin: 20px 0 !important;
}

#editor {
  height: 100px;
  margin: 20px 30px;
}

.me-editor {
  white-space:normal;
  display: flex;
  z-index: 6 !important;
  margin: 20px 0;
  flex-wrap: wrap;
}

.commentList{
  width: 80% ;
}
</style>
