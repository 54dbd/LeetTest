<script setup>
import { onMounted, onUnmounted } from "vue";

//example components
import DefaultNavbar from "../../examples/navbars/NavbarDefault.vue";
import DefaultFooter from "../../examples/footers/FooterDefault.vue";

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
              {{ title }}
            </h1>
            <p class="lead mb-4 text-white opacity-8 letter-spacing-5">
              <i class="fas fa-comment"></i>
              <span>{{ commentCount }}</span>
              <i class="fas fa-eye"></i>
              <span>{{ commentCount }}</span>
            </p>
            <p class="lead mb-4 text-white opacity-8">
              作者:
              {{ author.username }}<br />
              {{ create_time }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </header>
  <div
    class="card-header p-0 position-relative mt-n8 mx-12 z-index-2 bg-transparent"
  >
    <div class="bg-gradient-success shadow-success border-radius-lg p-3">
      <h3 class="text-white text-success mb-0" style="text-align: center">
        {{ title }}
      </h3>
    </div>
  </div>
  <div class="card card-body blur shadow-xl mx-3 mx-md-7 mt-n3">
    <MarkDown :text="content" class="p-6" />

    <!--    <Information />-->
    <!--    <AboutTeam />-->
    <!--    <Featuring />-->
    <!--    <Newsletter />-->
  </div>
  <div
    class="card-footer p-0 position-relative mt-n4 mx-12 z-index-2 bg-transparent"
  >
    <div class="bg-gradient-success shadow-success border-radius-lg p-3">
      <h3 class="text-white text-success mb-0" style="text-align: center">
        文章结束啦，喜欢的话点个赞吧~
      </h3>
    </div>
  </div>
  <div class="row justify-space-between text-center py-2">
    <div class="col-12 mx-auto">
      <button class="btn bg-gradient-primary btn-icon btn-lg m-3" type="button">
        <div class="d-flex align-items-center">
          <i class="material-icons me-2" aria-hidden="true">favorite</i>
          点赞
        </div>
      </button>
      <button class="btn bg-gradient-warning btn-icon btn-lg m-3" type="button">
        <div class="d-flex align-items-center">
          <i class="material-icons me-2" aria-hidden="true">star</i>
          收藏
        </div>
      </button>
    </div>
  </div>
  <div class="card card-body blur shadow-xl mx-3 mx-md-7 mt-8">
    <comment-text
      ref="my_comment"
      :placeholder="placeholder"
      @submit_click="submit_click"
    />
    <h4>全部评论: {{ commentCount }}</h4>
    <el-dialog title="说点什么呢？" v-model:visible="dialogVisible" width="50%">
      <el-input
        type="textarea"
        :placeholder="'回复' + commentParam.username"
        maxlength="200"
        v-model="replyText"
        show-word-limit
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="giveup_son_click">取 消</el-button>
        <el-button type="primary" @click="submit_son_click">确 定</el-button>
      </span>
    </el-dialog>
  </div>
  <DefaultFooter />
</template>

<script>
import MarkDown from "@/views/Article/Sections/MarkDown/MarkDown.vue";
import CommentText from "@/views/Article/Sections/CommentText/CommentText.vue";
import { getToken } from "@/utils/token";
import { useAppStore } from "@/stores";

import Clipboard from "clipboard";

export default {
  name: "index",
  components: {
    MarkDown,
    CommentText,
  },
  data() {
    return {
      content: "",
      comments: "",
      title: "",
      likesCount: 0,
      isLiked: false,
      repostCount: 0,
      favoriteCount: 0,
      isFavorite: false,
      historyCount: 1,
      commentCount: 0,
      commentList: [],
      tagList: [],
      author: {},
      create_time: "",
      model_time: "",
      placeholder: "想说点什么？评论支持markdown语法。", //默认文字提示。
      // 展示回复框
      dialogVisible: false,
      replyText: "",
      // 回复用的comment
      commentParam: {
        userId: useAppStore().user.userInfo.userId,
        articleId: this.$route.query.id,
        parentId: 0,
        toUserId: 0,
      },
      // 分页信息
      page: {
        current: 1,
        size: 10,
      },
      pages: 0,
      total: 0,
      // 更多
      moreArticle: [],
      tagNameList: [],
      url: "1",
    };
  },
  methods: {
    //点击评论按钮后，触发的事件
    async submit_click() {
      // 先判断是否登录
      if (!getToken()) {
        this.$message.warning("未登录，请先登录~");
        await this.$router.push("/login");
        return;
      }
      const result = await this.$API.reqAddParentComment({
        userId: useAppStore().user.userInfo.userId,
        articleId: this.$route.query.id,
        parentId: 0,
        toUserId: 0,
        content: this.comment_text,
      });
      if (result.data.code === 200) {
        this.$refs.my_comment.success_submit("评论成功", 1500);
        await this.getComment();
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
        this.$refs.my_comment.err_submitFn("评论失败", 1500);
      }
      //你可以在这里验证用户输入的格式。
      //若格式错误可调用此函数：
      //this.$refs.my_comment.err_submitFn("格式错误",1500)

      //你可以在这儿请求AJAX
      //失败回调：
      // this.$refs.my_comment.err_submitFn("评论失败",1500)
      //成功回调
      // this.$refs.my_comment.success_submit("评论成功", 1500)
    },
    async submit_son_click() {
      // 先判断是否登录
      if (!getToken()) {
        this.$message.warning("未登录，请先登录~");
        await this.$router.push("/login");
        return;
      }
      if (this.replyText === "") {
        this.$message.warning("评论区不能为空~");
        return;
      }
      this.commentParam.content = this.replyText;
      const result = await this.$API.reqAddParentComment(this.commentParam);
      if (result.data.code === 200) {
        this.$message.success("评论成功~");
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
      }
      this.replyText = "";
      this.dialogVisible = false;
      await this.getComment();
    },
    giveup_son_click() {
      this.dialogVisible = false;
    },
    reply(comment, index, comment_) {
      this.dialogVisible = true;
      if (index === 0) {
        this.commentParam.parentId = comment.id;
        this.commentParam.toUserId = comment.author.userId;
        this.commentParam.username = comment.author.username;
      } else {
        this.commentParam.parentId = comment_.id;
        this.commentParam.username = comment.author.username;
      }
    },
    async getArticle() {
      const result = await this.$API.reqGetArticleById(this.$route.query.id);
      if (result.data.code === 200) {
        this.title = result.data.data.article.title;
        this.content = result.data.data.article.content;
        this.author = result.data.data.author;
        this.tagList = result.data.data.tagsList;
        await this.getMoreArticle();
        this.create_time = result.data.data.article.createTime;
        this.model_time = result.data.data.article.modifyTime;
        this.likesCount = result.data.data.likesCount;
        this.repostCount = result.data.data.repostCount;
        this.favoriteCount = result.data.data.favoriteCount;
        this.historyCount = result.data.data.historyCount;
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
        await this.$router.push({ path: "/404" });
      }
    },
    async getAUCondition() {
      const result = await this.$API.reqGetAUCondition({
        aid: this.$route.query.id,
        uid: useAppStore().user.userInfo.userId,
      });
      if (result.data.code === 200) {
        this.isLiked = result.data.data.isLiked;
        this.isFavorite = result.data.data.isFavorit;
      } else {
        this.$message.error(result.data.msg);
      }
    },
    async getComment() {
      const result = await this.$API.reqGetCommentById({
        articleId: this.$route.query.id,
        page: this.page,
        userId: useAppStore().user.userInfo.userId,
      });
      if (result.data.code === 200) {
        this.commentCount = result.data.data.commentCount;
        this.commentList = result.data.data.commentVoList;
        this.pages = result.data.data.pages;
        this.total = result.data.data.total;
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
      }
    },
    async addCommentLike(comment) {
      if (!getToken()) {
        await this.$message.warning("当前尚未登录，请先登录...");
        await this.$router.push("/login");
      }
      if (comment.isLiked) {
        const result = await this.$API.reqRevokeCommentLike({
          cid: comment.id,
          uid: useAppStore().user.userInfo.userId,
        });
        if (result.data.code === 200) {
          this.$notify.warning({
            title: "警告",
            message: "取消成功~",
          });
          await this.getComment();
        } else {
          this.$message.error("系统异常~ " + result.data.msg);
        }
        return;
      }
      const result = await this.$API.reqAddCommentLike({
        aid: this.$route.query.id,
        cid: comment.id,
        uid: useAppStore().user.userInfo.userId,
      });
      if (result.data.code === 200) {
        this.$notify.success({
          title: "成功",
          message: "点赞成功~",
        });
        await this.getComment();
      } else {
        this.$message.error("系统异常~ " + result.data.msg);
      }
    },
    handleSizeChange(size) {
      this.page.size = size;
      this.getComment();
    },
    handleCurrentChange(current) {
      this.page.current = current;
      this.getComment();
    },
    async addLikes() {
      if (!getToken()) {
        this.$message.warning("当前尚未登录，请先登录");
        await this.$router.push("/login");
        return;
      }
      if (!this.isLiked) {
        const result = await this.$API.addLikes({
          aid: this.$route.query.id,
          uid: useAppStore().user.userInfo.userId,
        });
        if (result.data.code === 200) {
          this.isLiked = true;
          this.likesCount = result.data.data.likeCount;
          this.$notify.success({
            title: "成功",
            message: "点赞成功！",
          });
        } else {
          this.$notify.error({
            title: "错误",
            message: "点赞失败~ " + result.data.msg,
          });
        }
      } else {
        const result = await this.$API.revokeLikes({
          aid: this.$route.query.id,
          uid: useAppStore().user.userInfo.userId,
        });
        if (result.data.code === 200) {
          this.isLiked = false;
          this.likesCount = result.data.data.likeCount;
          this.$notify.warning({
            title: "警告",
            message: "已取消点赞~",
          });
        } else {
          this.$notify.error({
            title: "错误",
            message: "取消失败~ " + result.data.msg,
          });
        }
      }
    },
    async addFavorite() {
      if (!getToken()) {
        this.$message.warning("当前尚未登录，请先登录");
        await this.$router.push("/login");
        return;
      }
      if (!this.isFavorite) {
        const result = await this.$API.addFavorite({
          aid: this.$route.query.id,
          uid: useAppStore().user.userInfo.userId,
        });
        if (result.data.code === 200) {
          this.isFavorite = true;
          this.favoriteCount = result.data.data.favoriteCount;
          this.$notify.success({ title: "成功", message: "收藏成功！" });
        } else {
          this.$notify.error({
            title: "失败",
            message: "收藏失败~" + result.data.msg,
          });
        }
      } else {
        const result = await this.$API.revokeFavorite({
          aid: this.$route.query.id,
          uid: useAppStore().user.userInfo.userId,
        });
        if (result.data.code === 200) {
          this.isFavorite = false;
          this.favoriteCount = result.data.data.favoriteCount;
          this.$notify.warning({ title: "警告", message: "已取消收藏~" });
        } else {
          this.$notify.error({
            title: "失败",
            message: "取消收藏失败~" + result.data.msg,
          });
        }
      }
    },
    async addRepost() {
      /* const url = this.$refs.url;
       url.select(); // 选择对象
       document.execCommand("Copy"); // 执行浏览器复制命令
       alert("已复制好，可贴粘。");*/
      /*const result = await this.$API.addRepost({
        aid: this.$route.query.id,
        uid: useAppStore().user.userInfo.userId
      })
      if (result.data.code === 200) {
        this.repostCount = result.data.data.repostCount
      }*/
    },
    async getMoreArticle() {
      const result = await this.$API.reqSearchArticle({
        text: this.tagList[0].tagName,
        current: 1,
        size: 5,
      });
      if (result.data.code === 200) {
        this.moreArticle = result.data.data.articleBriefParams;
      } else {
        this.$message.error(result.data.msg);
      }
    },
    async getTagNameList() {
      const result = await this.$API.reqGetTagNameList();
      if (result.data.code === 200) {
        this.tagNameList = result.data.data;
      }
    },
  },
  mounted() {
    this.getArticle();
    this.getAUCondition();
    this.getComment();
    this.$API.reqAddHistory({
      aid: this.$route.query.id,
      uid: useAppStore().user.userInfo.userId,
    });
    this.getTagNameList();
  },
  computed: {
    comment_text() {
      //获取子组件的评论内容。
      return this.$refs.my_comment.insert_comment.comment_text;
    },
  },
};
</script>
