<script setup>
import { computed, onActivated, onMounted, onUnmounted, ref } from "vue";
import MarkDown from "@/views/Article/Sections/MarkDown/MarkDown.vue";
import { getToken } from "@/utils/token";
import store from "@/stores";

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
const page = ref({
  current: 1,
  size: 10,
});
const pages = ref(0);
const total = ref(0);
const moreArticle = ref([]);
const tagNameList = ref([]);


onActivated(async () => {
  getArticle();
  getComment();
  getMoreArticle();
  getTagNameList();
});

const toggleIconLike = () => {
  const icon = document.querySelector('.icon-like')
  icon.classList.toggle('text-warning')
}
const toggleIconFav = () => {
  const icon = document.querySelector('.icon-fav')
  icon.classList.toggle('text-danger')
}

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
      toggleIconLike();
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
      toggleIconLike();
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
      toggleIconFav();
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
      toggleIconFav();
      ElNotification.warning({ title: "警告", message: "已取消收藏~" });
    } else {
      ElNotification.error({
        title: "失败",
        message: "取消收藏失败~" + result.data.msg,
      });
    }
  }
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
      <button
        class="btn bg-gradient-primary btn-icon btn-lg m-3"
        type="button"
        @click="addLikes"
      >
        <div class="d-flex align-items-center">
          <i class="material-icons me-2 icon-like" aria-hidden="true">favorite</i>
          点赞
        </div>
      </button>
      <button
        class="btn bg-gradient-warning btn-icon btn-lg m-3"
        type="button"
        @click="addFavorite"
      >
        <div class="d-flex align-items-center">
          <i class="material-icons me-2 icon-fav" aria-hidden="true">star</i>
          收藏
        </div>
      </button>
    </div>
  </div>
  <DefaultFooter />
</template>
