<script setup>
import { onMounted, onUnmounted, reactive, ref } from "vue";

//example components
import NavbarDefault from "../../ui/navbars/NavbarDefault.vue";
import DefaultFooter from "../../ui/footers/FooterDefault.vue";
import Header from "../../ui/Header.vue";
import RotatingCard from "../../ui/cards/rotatingCards/RotatingCard.vue";
import RotatingCardFront from "../../ui/cards/rotatingCards/RotatingCardFront.vue";
import RotatingCardBack from "../../ui/cards/rotatingCards/RotatingCardBack.vue";
//Vue Material Kit 2 components
import MaterialSocialButton from "@/components/MaterialSocialButton.vue";

// sections
import PresentationCounter from "./Sections/PresentationCounter.vue";
import BuiltByDevelopers from "./Components/BuiltByDevelopers.vue";
import PresentationTestimonials from "./Sections/PresentationTestimonials.vue";

//images
import vueMkHeader from "@/assets/img/vue-mk-header.png";
import wavesWhite from "@/assets/img/waves-white.svg";
import logo from "@/assets/img/logo_Text.png";

// Backend
import { reqGetArticleByTid } from "@/api";

const getArticleByTid = async (tid) => {
  const result = await reqGetArticleByTid(tid);
  if (result.data.code === 200) {
    return result.data.data;
  } else {
    return [];
  }
};

//hooks
const body = document.getElementsByTagName("body")[0];

let list1 = reactive([]);
let list2 = reactive([]);
let list3 = reactive([]);
let list4 = reactive([]);
let list5 = reactive([]);

onMounted(async () => {
  body.classList.add("presentation-page");
  body.classList.add("bg-gray-200");
  list1.push(...(await getArticleByTid(24)));
  list2.push(...(await getArticleByTid(25)));
  list3.push(...(await getArticleByTid(23)));
  list4.push(...(await getArticleByTid(26)));
  list5.push(...(await getArticleByTid(27)));
});
onUnmounted(() => {
  body.classList.remove("presentation-page");
  body.classList.remove("bg-gray-200");
});
</script>

<template>
  <div class="container position-sticky z-index-sticky top-0">
    <div class="row">
      <div class="col-12">
        <NavbarDefault :sticky="true" />
      </div>
    </div>
  </div>

  <Header>
    <div
      class="page-header min-vh-75"
      :style="`background-image: url(${vueMkHeader})`"
      loading="lazy"
    >
      <div class="container">
        <div class="row">
          <div class="col-lg-7 text-center mx-auto position-relative">
            <h1
              class="text-white pt-3 mt-n5 me-2"
              :style="{ display: 'inline-block ' }"
            >
              LeetTest考研刷题网
            </h1>
            <p class="lead text-white px-5 mt-3" :style="{ fontWeight: '500' }">
              读万卷书，行万里路，胸中脱去尘浊，自然丘壑内营。
            </p>
          </div>
        </div>
      </div>
    </div>
  </Header>

  <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6">
    <PresentationCounter />
    <!--    <PresentationInformation />-->
    <!--    <PresentationExample :data="data" />-->
    <!--    <PresentationPages />-->

    <div class="row" style="margin-left: 10%; margin-right: 10%">
      <div class="col-lg-4 ms-auto me-auto p-lg-3 mt-lg-0 mt-3">
        <RotatingCard>
          <RotatingCardFront
            image="https://images.unsplash.com/photo-1569683795645-b62e50fbf103?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80"
            icon="touch_app"
            title="招生公告"
            description="快来看看最新消息吧~"
          />

          <RotatingCardBack
            image="https://images.unsplash.com/photo-1498889444388-e67ea62c464b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1365&q=80"
            description=""
            title=""
            :links="list1"
            id="list1"
          >
          </RotatingCardBack>
        </RotatingCard>
      </div>
      <div class="col-lg-4 ms-auto me-auto p-lg-3 mt-lg-0 mt-3">
        <RotatingCard>
          <RotatingCardFront
            image="https://images.unsplash.com/photo-1569683795645-b62e50fbf103?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80"
            icon="touch_app"
            title="研招新闻"
            description="快来看看最新消息吧~"
          />

          <RotatingCardBack
            image="https://images.unsplash.com/photo-1498889444388-e67ea62c464b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1365&q=80"
            :links="list2"
            id="list2"
          />
        </RotatingCard>
      </div>
      <div class="col-lg-4 ms-auto me-auto p-lg-3 mt-lg-0 mt-3">
        <RotatingCard>
          <RotatingCardFront
            image="https://images.unsplash.com/photo-1569683795645-b62e50fbf103?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80"
            icon="touch_app"
            title="报考指南"
            description="快来看看最新消息吧~"
          />

          <RotatingCardBack
            image="https://images.unsplash.com/photo-1498889444388-e67ea62c464b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1365&q=80"
            :links="list3"
            id="list3"
          />
        </RotatingCard>
      </div>
    </div>
    <div class="container mt-5">
      <div
        class="page-header py-6 py-md-5 my-sm-3 mb-3 border-radius-xl"
        :style="{
          backgroundImage: `url(${wavesWhite})`,
        }"
        loading="lazy"
      >
        <span class="mask bg-gradient-dark"></span>
        <div class="container">
          <div class="row">
            <div class="d-flex justify-content-center p-5">
              <div class="col-lg-8 ms-lg-5 text-center">
                <img :src="logo" alt="" style="width: 40%" />
                <h3 class="text-white">至今为止，从未体验过的全新刷题工具</h3>

                <p class="text-white text-md">
                  在LeetTest上，你可以通过各种题型和难度级别的练习，快速提升自己的考研能力。LeetTest的刷题功能非常强大，它提供了大量的高质量考研题目，涵盖了研究生考试各科目的重点考点，包括数学、英语、政治等。除此之外，LeetTest还为用户提供了个性化的题目推荐和智能错题本功能，帮助用户更加高效地复习和巩固知识点。不论你是初学者还是已经具有一定考研基础的考生，LeetTest都能为你提供切实可行的刷题方案和优质的学习资源，帮助你取得更好的考研成绩。
                </p>

                <a
                  href="register"
                  class="btn btn-sm mb-0 bg-gradient-success px-5 py-3 mt-4"
                  style="font-size: 25px"
                  >现在注册!</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div>
      <BuiltByDevelopers
        title="研招答疑"
        subtitle="详细内容"
        :links="list4"
        id="list4"
        url="https://raw.githubusercontent.com/creativetimofficial/public-assets/master/soft-ui-design-system/assets/img/desktop.jpg"
      />
      <BuiltByDevelopers
        title="考研大纲"
        subtitle="详细内容"
        :links="list5"
        id="list5"
        url="https://images.unsplash.com/photo-1505664194779-8beaceb93744?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3870&q=80"
      />
    </div>

    <PresentationTestimonials />

    <div class="py-5">
      <div class="container">
        <div class="row">
          <div class="col-lg-5 ms-auto">
            <h4 class="mb-1">感谢您的支持</h4>
            <p class="lead mb-0">欢迎参观我们的Github页面</p>
          </div>
          <div class="col-lg-5 me-lg-auto my-lg-auto text-lg-end mt-5">
            <MaterialSocialButton
              route="https://github.com/54dbd/LeetTest"
              component="github"
              color="github"
              label="github"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
  <DefaultFooter />
</template>

<script>
import { defineComponent, onMounted, onUnmounted } from "vue";
import { useAppStore } from "@/stores";

export default defineComponent({
  setup() {
    // 从本地存储中读取数据并保存到Pinia状态中
    const loadData = () => {
      const content = localStorage.getItem("content");
      if (content) {
        useAppStore.content = content;
      }
    };

    // 保存Pinia状态到本地存储中
    const saveData = () => {
      localStorage.setItem("content", useAppStore.content);
    };

    onMounted(() => {
      // 组件挂载时从本地存储中读取数据
      loadData();
    });

    onUnmounted(() => {
      // 组件销毁时保存Pinia状态到本地存储中
      saveData();
    });

    return { useAppStore };
  },
});
</script>
