<script setup>
import { RouterLink } from "vue-router";
import { onMounted, ref, watch } from "vue";
import { useWindowsWidth } from "@/assets/js/useWindowsWidth";
import MaterialInput from "@/components/MaterialInput.vue";
// images
import ArrDark from "@/assets/img/down-arrow-dark.svg";
import DownArrWhite from "@/assets/img/down-arrow-white.svg";
import logo from "@/assets/img/logo_noText.png";
import store from "@/stores"; // 引入store实例
import * as api from "@/api";
import { getToken } from "@/utils/token";
import { ElMessage } from "element-plus";
import MaterialAvatar from "@/components/MaterialAvatar.vue";
import { reqSearchArticle } from "@/api";
const userName = store.state.user.userInfo.username;
const search = ref("");
let articleList = ref([]);
let titleList = ref([]);
const props = defineProps({
  action: {
    type: Object,
    route: String,
    color: String,
    label: String,
    default: () => ({
      route: "https://www.creative-tim.com/product/vue-material-kit",
      color: "bg-gradient-success",
      label: "Free Download",
    }),
  },
  transparent: {
    type: Boolean,
    default: false,
  },
  light: {
    type: Boolean,
    default: false,
  },
  dark: {
    type: Boolean,
    default: false,
  },
  sticky: {
    type: Boolean,
    default: false,
  },
  darkText: {
    type: Boolean,
    default: false,
  },
});

// set arrow  color
function getArrowColor() {
  if (props.transparent && textDark.value) {
    return ArrDark;
  } else if (props.transparent) {
    return DownArrWhite;
  } else {
    return ArrDark;
  }
}

// set text color
const getTextColor = () => {
  let color;
  if (props.transparent && textDark.value) {
    color = "text-dark";
  } else if (props.transparent) {
    color = "text-white";
  } else {
    color = "text-dark";
  }

  return color;
};

const firstCorrectedQuestion = ref();

const getFirstCorrectedAnswerTid = async () => {
  if (getToken()) {
    let result = await api.reqGetCorrectedTestById(
      store.state.user.userInfo.userId
    );

    if (result.data.code === 200) {
      if (result.data.data.length > 0) result = result.data.data[0].tid;
      else result = -1;
    } else {
      ElMessage.warning("系统异常~ " + result.data.msg);
    }

    firstCorrectedQuestion.value = result;
  }
};

// set nav color on mobile && desktop

let textDark = ref(props.darkText);
const { type } = useWindowsWidth();

if (type.value === "mobile") {
  textDark.value = true;
} else if (type.value === "desktop" && textDark.value === false) {
  textDark.value = false;
}

const searchArticle = async function () {
  const result = await reqSearchArticle({
    text: search.value,
    current: 1,
    size: 20,
  });
  if (result.data.code === 200) {
    articleList.value = result.data.data.articleBriefParams;
    titleList.value = articleList.value.map((item) => item.title);
    console.log(titleList.value);
  } else {
    ElMessage.warning("系统异常~ " + result.data.msg);
  }
};

watch(
  () => type.value,
  (newValue) => {
    textDark.value = newValue === "mobile";
  }
);
watch(search, () => {
  searchArticle();
});

onMounted(() => {
  getFirstCorrectedAnswerTid();
});
</script>
<template>
  <nav
    class="navbar navbar-expand-lg top-0"
    :class="{
      'z-index-3 w-100 shadow-none navbar-transparent position-absolute my-3':
        props.transparent,
      'my-3 blur border-radius-lg z-index-3 py-2 shadow py-2 start-0 end-0 mx-4 position-absolute mt-4':
        props.sticky,
      'navbar-light bg-white py-3': props.light,
      ' navbar-dark bg-gradient-dark z-index-3 py-3': props.dark,
    }"
  >
    <div
      :class="
        props.transparent || props.light || props.dark
          ? 'container'
          : 'container-fluid px-0'
      "
    >
      <img :src="logo" style="width: 3%" alt="" />

      <RouterLink
        class="navbar-brand d-none d-md-block"
        :class="[
          (props.transparent && textDark.value) || !props.transparent
            ? 'text-dark font-weight-bolder ms-sm-3'
            : 'text-white font-weight-bolder ms-sm-3',
        ]"
        :to="{ name: 'presentation' }"
        rel="tooltip"
        title="Designed and Coded by Creative Tim"
        data-placement="bottom"
      >
        LeetTest考研刷题网
      </RouterLink>
      <RouterLink
        class="navbar-brand d-block d-md-none"
        :class="
          props.transparent || props.dark
            ? 'text-white'
            : 'font-weight-bolder ms-sm-3'
        "
        to="/"
        rel="tooltip"
        title="Designed and Coded by Creative Tim"
        data-placement="bottom"
      >
        LeetTest考研刷题网
      </RouterLink>
      <a
        class="btn btn-sm bg-gradient-success mb-0 ms-auto d-lg-none d-block"
        href="/testlist"
        >刷题!</a
      >

      <button
        class="navbar-toggler shadow-none ms-2"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navigation"
        aria-controls="navigation"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon mt-2">
          <span class="navbar-toggler-bar bar1"></span>
          <span class="navbar-toggler-bar bar2"></span>
          <span class="navbar-toggler-bar bar3"></span>
        </span>
      </button>
      <div
        class="collapse navbar-collapse w-100 pt-3 pb-2 py-lg-0"
        id="navigation"
      >
        <MaterialInput
          v-model="search"
          placeholder="搜索文章"
          prepend-icon="search"
          type="text"
          icon="search"
          class="input-group-dynamic nav-item dropdown dropdown-hover mx-9"
          style="left: -9rem"
          aria-labelledby="dropdownMenuPages"
        >
          <div
            class="dropdown-menu dropdown-menu-animation ms-n3 dropdown-md p-3 border-radius-xl mt-0 mt-lg-3 searchResult"
            aria-labelledby="dropdownMenuPages"
          >
            <div class="row d-none d-lg-block">
              <div class="col-12 px-4 py-2">
                <div class="row">
                  <div class="position-relative">
                    <RouterLink
                      role="button"
                      class="border-radius-md nav-link ps-2 d-flex cursor-pointer align-items-center text-dark"
                      id="dropdownMenuPages"
                      aria-expanded="true"
                      v-for="(article, index) in articleList"
                      :key="index"
                      :to="'/article/?id=' + article.id"
                    >
                      <span>
                        {{ article.title }}
                      </span>
                    </RouterLink>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </MaterialInput>

        <ul class="navbar-nav navbar-nav-hover ms-auto align-items-center">
          <!--          账号-->
          <li class="nav-item dropdown dropdown-hover mx-2" v-if="!userName">
            <a
              role="button"
              class="nav-link ps-2 d-flex cursor-pointer align-items-center"
              :class="getTextColor()"
              id="dropdownMenuPages"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i
                class="material-icons opacity-6 me-2 text-md"
                :class="getTextColor()"
                >dashboard</i
              >
              账户
              <img
                :src="getArrowColor()"
                alt="down-arrow"
                class="arrow ms-2 d-lg-block d-none"
              />
              <img
                :src="getArrowColor()"
                alt="down-arrow"
                class="arrow ms-1 d-lg-none d-block ms-auto"
              />
            </a>
            <div
              class="dropdown-menu dropdown-menu-animation ms-n3 dropdown-md p-3 border-radius-xl mt-0 mt-lg-3"
              aria-labelledby="dropdownMenuPages"
            >
              <div class="row d-none d-lg-block">
                <div class="col-12 px-4 py-2">
                  <div class="row">
                    <div class="position-relative">
                      <div
                        class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-1"
                      >
                        账户
                      </div>
                      <RouterLink
                        :to="{ name: 'signin-basic' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>登录</span>
                      </RouterLink>
                      <RouterLink
                        :to="{ name: 'signup-basic' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>注册</span>
                      </RouterLink>
                    </div>
                  </div>
                </div>
              </div>
              <div class="d-lg-none">
                <div
                  class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-1"
                >
                  账户
                </div>
                <RouterLink
                  :to="{ name: 'signin-basic' }"
                  class="dropdown-item border-radius-md"
                >
                  <span>登录</span>
                </RouterLink>
                <RouterLink
                  :to="{ name: 'signup-basic' }"
                  class="dropdown-item border-radius-md"
                >
                  <span>注册</span>
                </RouterLink>
              </div>
            </div>
          </li>
          <!--          考研信息-->
          <li class="nav-item dropdown dropdown-hover mx-2">
            <a
              role="button"
              class="nav-link ps-2 d-flex cursor-pointer align-items-center"
              :class="getTextColor()"
              id="dropdownMenuPages"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i
                class="material-icons opacity-6 me-2 text-md"
                :class="getTextColor()"
                >article</i
              >
              考研信息
              <img
                :src="getArrowColor()"
                alt="down-arrow"
                class="arrow ms-2 d-lg-block d-none"
              />
              <img
                :src="getArrowColor()"
                alt="down-arrow"
                class="arrow ms-1 d-lg-none d-block ms-auto"
              />
            </a>
            <div
              class="dropdown-menu dropdown-menu-animation ms-n3 dropdown-md p-3 border-radius-xl mt-0 mt-lg-3"
              aria-labelledby="dropdownMenuPages"
            >
              <div class="row d-none d-lg-block">
                <div class="col-12 px-4 py-2">
                  <div class="row">
                    <div class="position-relative">
                      <div
                        class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-1"
                      >
                        考研信息
                      </div>
                      <RouterLink
                        :to="{ name: 'school-basic' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>34所高校</span>
                      </RouterLink>
                      <RouterLink
                        :to="{ name: 'scoreline-basic' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span> 考研分数线</span>
                      </RouterLink>
                      <RouterLink
                        :to="{ name: 'time-basic' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>考研时间线</span>
                      </RouterLink>
                    </div>
                  </div>
                </div>
              </div>
              <div class="d-lg-none">
                <div class="col-12 px-4 py-2">
                  <div class="row">
                    <div class="position-relative">
                      <div
                        class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-1"
                      >
                        考研信息
                      </div>
                      <RouterLink
                        :to="{ name: 'school-basic' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>34所高校</span>
                      </RouterLink>
                      <RouterLink
                        :to="{ name: 'scoreline-basic' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span> 考研分数线</span>
                      </RouterLink>
                      <RouterLink
                        :to="{ name: 'time-basic' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>考研时间线</span>
                      </RouterLink>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </li>
          <!--          github-->
          <li class="nav-item dropdown dropdown-hover mx-2">
            <a
              href="https://github.com/54dbd/LeetTest"
              class="nav-link d-flex cursor-pointer align-items-center"
            >
              <svg
                width="20px"
                height="20px"
                class="material-icons me-2 opacity-6"
                viewBox="0 0 24 24"
                aria-hidden="true"
                data-testid="GitHubIcon"
                :fill="props.transparent && '#fff'"
              >
                <path
                  d="M12 1.27a11 11 0 00-3.48 21.46c.55.09.73-.28.73-.55v-1.84c-3.03.64-3.67-1.46-3.67-1.46-.55-1.29-1.28-1.65-1.28-1.65-.92-.65.1-.65.1-.65 1.1 0 1.73 1.1 1.73 1.1.92 1.65 2.57 1.2 3.21.92a2 2 0 01.64-1.47c-2.47-.27-5.04-1.19-5.04-5.5 0-1.1.46-2.1 1.2-2.84a3.76 3.76 0 010-2.93s.91-.28 3.11 1.1c1.8-.49 3.7-.49 5.5 0 2.1-1.38 3.02-1.1 3.02-1.1a3.76 3.76 0 010 2.93c.83.74 1.2 1.74 1.2 2.94 0 4.21-2.57 5.13-5.04 5.4.45.37.82.92.82 2.02v3.03c0 .27.1.64.73.55A11 11 0 0012 1.27"
                ></path>
              </svg>
              Github
            </a>
          </li>
          <!--            用户头像-->
          <li class="nav-item dropdown dropdown-hover mx-2" v-if="userName">
            <a
              role="button"
              class="nav-link ps-2 d-flex cursor-pointer align-items-center"
              :class="getTextColor()"
              id="dropdownMenuPages"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <MaterialAvatar
                size="sm"
                border-radius="0.2px"
                class="shadow-xl position-relative z-index-2"
                :image="store.state.user.userInfo.userImg"
                alt="Avatar"
              />
              <div
                class="nav-link ps-2 d-flex cursor-pointer align-items-center"
              >
                {{ userName }}
              </div>
              <img
                :src="getArrowColor()"
                alt="down-arrow"
                class="arrow ms-2 d-lg-block d-none"
              />
              <img
                :src="getArrowColor()"
                alt="down-arrow"
                class="arrow ms-1 d-lg-none d-block ms-auto"
              />
            </a>
            <div
              class="dropdown-menu dropdown-menu-animation ms-n3 dropdown-md p-3 border-radius-xl mt-0 mt-lg-3"
              aria-labelledby="dropdownMenuPages"
            >
              <div class="row d-none d-lg-block">
                <div class="col-12 px-4 py-2">
                  <div class="row">
                    <div class="position-relative">
                      <div
                        class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-1"
                      >
                        用户
                      </div>
                      <RouterLink
                        :to="{ name: 'userCenter' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>个人中心</span>
                      </RouterLink>
                      <RouterLink
                        v-if="firstCorrectedQuestion !== -1"
                        @click="handleCorrected"
                        :to="{
                          name: 'test',
                          query: {
                            tid: firstCorrectedQuestion,
                            corrected: true,
                          },
                        }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>错题集</span>
                      </RouterLink>
                      <div
                        v-else
                        class="dropdown-item border-radius-md"
                        @click="noCorrectedTestHandle"
                      >
                        <span>错题集</span>
                      </div>
                      <a class="dropdown-item border-radius-md" @click="logout">
                        <span>注销</span>
                      </a>
                    </div>
                  </div>
                </div>
              </div>
              <div class="d-lg-none">
                <div class="col-12 px-4 py-2">
                  <div class="row">
                    <div class="position-relative">
                      <div
                        class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-1"
                      >
                        用户
                      </div>
                      <RouterLink
                        :to="{ name: 'userCenter' }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>个人中心</span>
                      </RouterLink>
                      <RouterLink
                        v-if="firstCorrectedQuestion !== -1"
                        @click="handleCorrected"
                        :to="{
                          name: 'test',
                          query: {
                            tid: firstCorrectedQuestion,
                            corrected: true,
                          },
                        }"
                        class="dropdown-item border-radius-md"
                      >
                        <span>错题集</span>
                      </RouterLink>
                      <div
                        v-else
                        class="dropdown-item border-radius-md"
                        @click="noCorrectedTestHandle"
                      >
                        <span>错题集</span>
                      </div>
                      <a class="dropdown-item border-radius-md" @click="logout">
                        <span>注销</span>
                      </a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </li>
        </ul>
        <ul class="navbar-nav d-lg-block d-none">
          <li class="nav-item">
            <a
              class="btn btn-sm mb-0"
              :class="action.color"
              href="/testlist"
              onclick="smoothToPricing('pricing-soft-ui')"
              style="font-size: 15px"
              >开刷!</a
            >
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- End Navbar -->
</template>
<script>
export default {
  data() {
    return {
      keyWord: "",
      centerDialogVisible: false,
      firstCorrectedQuestion: null,
    };
  },
  emits: ["refresh"],
  methods: {
    goSearch() {
      if (this.keyWord.trim() === "") {
        this.$message.warning("输入框不能为空~");
        return;
      }
      let location = {
        name: "search",
        params: {
          keyWord: this.keyWord,
          fromRouter: this.$route.path,
        },
      };
      //如果带有query参数也传过去
      if (this.$route.query) {
        location.query = this.$route.query;
      }
      this.$router.push(location);
      this.keyWord = "";
    },
    //退出登录
    async logout() {
      this.$confirm("即将退出登录, 是否继续?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const result = await store.dispatch("user/logout");
          if (result) {
            this.$message({
              type: "success",
              message: "已退出登录!",
            });
            await this.$router.push("/login");
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    register() {
      this.$router.push("/register");
    },
    noCorrectedTestHandle() {
      this.$notify.warning({
        title: "提示",
        message: "您还没有做过题哦~",
      });
    },
    handleCorrected() {
      this.$emit("refresh");
    },
  },
  computed: {
    username() {
      if (store.state.user.userInfo) return store.state.user.userInfo.username;
      return "";
    },
    userImg() {
      if (store.state.user.userInfo) return store.state.user.userInfo.userImg;
      return "";
    },
  },
};
</script>
<style scoped>
.searchResult {
  position: absolute;
  margin-left: 12rem;
  top: 100%;
  left: 0;
  width: 17rem;
  background-color: #fff;
  border: 1px solid #ccc;
  border-top: none;
  z-index: 999;
  max-height: 300px;
  overflow-y: auto;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  &::-webkit-scrollbar {
    display: none;
  }
}
</style>
