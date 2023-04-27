<script setup>
import {
  computed,
  onActivated,
  onMounted,
  onUnmounted,
  reactive,
  ref,
} from "vue";
import MarkDown from "@/views/Article/Sections/MarkDown/MarkDown.vue";
import CommentText from "@/views/Article/Sections/CommentText/CommentText.vue";
import { getToken } from "@/utils/token";
import store from "@/stores";
import Clipboard from "clipboard";

//example components
import DefaultNavbar from "../../examples/navbars/NavbarDefault.vue";
import DefaultFooter from "../../examples/footers/FooterDefault.vue";

//image
import bg0 from "@/assets/img/bg9.jpg";

//dep
import Typed from "typed.js";
import { useRoute, useRouter } from "vue-router";
import { ElMessage, ElNotification } from "element-plus";

import * as api from "@/api";

// //sections
// import Information from "./Sections/AboutInformation.vue";
// import AboutTeam from "./Sections/AboutTeam.vue";
// import Featuring from "./Sections/AboutFeaturing.vue";
// import Newsletter from "./Sections/AboutNewsletter.vue";

// Data
const router = useRouter();
const route = useRoute();
const content = ref("");
const comments = ref("");
const title = ref("");
const likesCount = ref(0);
const isLiked = ref(false);
const repostCount = ref(0);
const favoriteCount = ref(0);
const isFavorite = ref(false);
const historyCount = ref(1);
const commentCount = ref(0);
const commentList = ref([]);
const tagList = ref([]);
const author = ref({});
const create_time = ref("");
const model_time = ref("");
const placeholder = ref("想说点什么？评论支持markdown语法。");
const dialogVisible = ref(false);
const replyText = ref("");
const commentParam = ref({
  userId: store.state.user.userInfo.userId,
  articleId: route.query.id,
  parentId: 0,
  toUserId: 0,
});
const page = ref({
  current: 1,
  size: 10,
});
const pages = ref(0);
const total = ref(0);
const moreArticle = ref([]);
const tagNameList = ref([]);
const url = ref("1");

onActivated(async () => {
  getArticle();
  getComment();
  getMoreArticle();
  getTagNameList();
});

// Methods
const submit_click = async () => {
  // 先判断是否登录
  if (!getToken()) {
    ElMessage.warning("未登录，请先登录~");
    await router.push("/login");
    return;
  }
  const result = await api.reqAddParentComment({
    userId: store.state.user.userInfo.userId,
    articleId: route.query.id,
    parentId: 0,
    toUserId: 0,
    content: comment_text,
  });
  if (result.data.code === 200) {
    this.$refs.my_comment.success_submit("评论成功", 1500);
    await getComment();
  } else {
    ElMessage.error("系统异常~ " + result.data.msg);
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
};

//点击评论按钮后，触发的事件
const submit_son_click = async () => {
  // 先判断是否登录
  if (!getToken()) {
    ElMessage.warning("未登录，请先登录~");
    await router.push("/login");
    return;
  }
  if (replyText.value === "") {
    ElMessage.warning("评论区不能为空~");
    return;
  }
  commentParam.value.content = replyText;
  const result = await api.reqAddParentComment(commentParam);
  if (result.data.code === 200) {
    ElMessage.success("评论成功~");
  } else {
    ElMessage.error("系统异常~ " + result.data.msg);
  }
  replyText.value = "";
  dialogVisible.value = false;
  await getComment();
};

const giveup_son_click = () => {
  dialogVisible.value = false;
};

const reply = (comment, index, comment_) => {
  dialogVisible.value = true;
  if (index === 0) {
    commentParam.value.parentId = comment.id;
    commentParam.value.toUserId = comment.author.userId;
    commentParam.value.username = comment.author.username;
  } else {
    commentParam.value.parentId = comment_.id;
    commentParam.value.username = comment.author.username;
  }
};

const getArticle = async () => {
  const result = await api.reqGetArticleById(route.query.id);
  if (result.data.code === 200) {
    title.value = result.data.data.article.title;
    content.value = result.data.data.article.content;
    author.value = result.data.data.author;
    tagList.value = result.data.data.tagsList;
    await getMoreArticle();
    create_time.value = result.data.data.article.createTime;
    model_time.value = result.data.data.article.modifyTime;
    likesCount.value = result.data.data.likesCount;
    repostCount.value = result.data.data.repostCount;
    favoriteCount.value = result.data.data.favoriteCount;
    historyCount.value = result.data.data.historyCount;
  } else {
    ElMessage.error("系统异常~ " + result.data.msg);
    await router.push({ path: "/404" });
  }
};

const getAUCondition = async () => {
  const result = await api.reqGetAUCondition({
    aid: route.query.id,
    uid: store.state.user.userInfo.userId,
  });
  if (result.data.code === 200) {
    isLiked.value = result.data.data.isLiked;
    isFavorite.value = result.data.data.isFavorit;
  } else {
    ElMessage.error(result.data.msg);
  }
};

const getComment = async () => {
  const result = await api.reqGetCommentById({
    articleId: route.query.id,
    page: page,
    userId: store.state.user.userInfo.userId,
  });
  if (result.data.code === 200) {
    commentCount.value = result.data.data.commentCount;
    commentList.value = result.data.data.commentVoList;
    pages.value = result.data.data.pages;
    total.value = result.data.data.total;
  } else {
    ElMessage.error("系统异常~ " + result.data.msg);
  }
};

const addCommentLike = async (comment) => {
  if (!getToken()) {
    await ElMessage.warning("当前尚未登录，请先登录...");
    await router.push("/login");
  }
  if (comment.isLiked) {
    const result = await api.reqRevokeCommentLike({
      cid: comment.id,
      uid: store.state.user.userInfo.userId,
    });
    if (result.data.code === 200) {
      ElNotification.warning({
        title: "警告",
        message: "取消成功~",
      });
      await getComment();
    } else {
      ElMessage.error("系统异常~ " + result.data.msg);
    }
    return;
  }
  const result = await api.reqAddCommentLike({
    aid: route.query.id,
    cid: comment.id,
    uid: store.state.user.userInfo.userId,
  });
  if (result.data.code === 200) {
    ElNotification.success({
      title: "成功",
      message: "点赞成功~",
    });
    await getComment();
  } else {
    ElMessage.error("系统异常~ " + result.data.msg);
  }
};

const handleSizeChange = (size) => {
  page.value.size = size;
  getComment();
};

const handleCurrentChange = (current) => {
  page.value.current = current;
  getComment();
};

const addLikes = async () => {
  if (!getToken()) {
    ElMessage.warning("当前尚未登录，请先登录");
    await router.push("/login");
    return;
  }
  if (!isLiked.value) {
    const result = await api.addLikes({
      aid: route.query.id,
      uid: store.state.user.userInfo.userId,
    });
    if (result.data.code === 200) {
      isLiked.value = true;
      likesCount.value = result.data.data.likeCount;
      ElNotification.success({
        title: "成功",
        message: "点赞成功！",
      });
    } else {
      ElNotification.error({
        title: "错误",
        message: "点赞失败~ " + result.data.msg,
      });
    }
  } else {
    const result = await api.revokeLikes({
      aid: route.query.id,
      uid: store.state.user.userInfo.userId,
    });
    if (result.data.code === 200) {
      isLiked.value = false;
      likesCount.value = result.data.data.likeCount;
      ElNotification.warning({
        title: "警告",
        message: "已取消点赞~",
      });
    } else {
      ElNotification.error({
        title: "错误",
        message: "取消失败~ " + result.data.msg,
      });
    }
  }
};

const addFavorite = async () => {
  if (!getToken()) {
    ElMessage.warning("当前尚未登录，请先登录");
    await router.push("/login");
    return;
  }
  if (!isFavorite.value) {
    const result = await api.addFavorite({
      aid: route.query.id,
      uid: store.state.user.userInfo.userId,
    });
    if (result.data.code === 200) {
      isFavorite.value = true;
      favoriteCount.value = result.data.data.favoriteCount;
      ElNotification.success({ title: "成功", message: "收藏成功！" });
    } else {
      ElNotification.error({
        title: "失败",
        message: "收藏失败~" + result.data.msg,
      });
    }
  } else {
    const result = await api.revokeFavorite({
      aid: route.query.id,
      uid: store.state.user.userInfo.userId,
    });
    if (result.data.code === 200) {
      isFavorite.value = false;
      favoriteCount.value = result.data.data.favoriteCount;
      ElNotification.warning({ title: "警告", message: "已取消收藏~" });
    } else {
      ElNotification.error({
        title: "失败",
        message: "取消收藏失败~" + result.data.msg,
      });
    }
  }
};

const addRepost = async () => {
  /* const url = this.$refs.url;
   url.select(); // 选择对象
   document.execCommand("Copy"); // 执行浏览器复制命令
   alert("已复制好，可贴粘。");*/
  /*const result = await api.addRepost({
    aid: route.query.id,
    uid: store.user.userInfo.userId
  })
  if (result.data.code === 200) {
    this.repostCount = result.data.data.repostCount
  }*/
};

const getMoreArticle = async () => {
  const result = await api.reqSearchArticle({
    text: tagList.value[0].tagName,
    current: 1,
    size: 5,
  });
  if (result.data.code === 200) {
    moreArticle.value = result.data.data.articleBriefParams;
  } else {
    ElMessage.error(result.data.msg);
  }
};

const getTagNameList = async () => {
  const result = await api.reqGetTagNameList();
  if (result.data.code === 200) {
    tagNameList.value = result.data.data;
  }
};

const comment_text = computed(() => {
  //获取子组件的评论内容。
  return this.$refs.my_comment.insert_comment.comment_text;
});

const body = document.getElementsByTagName("body")[0];
//hooks
onMounted(() => {
  getArticle();
  getAUCondition();
  getComment();
  api.reqAddHistory({
    aid: route.query.id,
    uid: store.state.user.userInfo.userId,
  });
  getTagNameList();
  body.classList.add("about-us");
  body.classList.add("bg-gray-200");

  if (document.getElementById("typed")) {
    // eslint-disable-next-line no-unused-vars
    let typed = new Typed("#typed", {
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
