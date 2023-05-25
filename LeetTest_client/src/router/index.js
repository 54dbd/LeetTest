import { createRouter, createWebHistory } from "vue-router";
import PresentationView from "../views/Presentation/PresentationView.vue";
import SignInBasicView from "../views/SignIn/BasicView.vue";
import SignUpBasicView from "../views/SignUp/SignUpView.vue";
import SchoolBasicView from "../views/Schools/SchoolView.vue";
import ScoreLineBasicView from "../views/ScoreLine/ScoreLineView.vue";
import SchoolDetailBasicView from "../views/SchoolDetail/SchoolDetailView.vue";
import TimeBasicView from "../views/Time/TimeView.vue";
import TeacherDetailBasicView from "../views/Teachers/TeacherDetailView.vue";
import ScoreLineDetailBasicView from "../views/ScoreLine/Sections/ScoreLineList.vue";

import { ElMessage } from "element-plus";
import Article from "@/views/Article/Article.vue";
import Test from "@/views/Test/Test.vue";
import TestList from "@/views/Testlist/TestList.vue";
import store from "@/stores"; // 引入store实例
import UserCenter from "@/views/UserCenter/UserCenter.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "presentation",
      component: PresentationView,
      meta: {
        keepAlive: true,
      },
    },
    {
      path: "/userCenter",
      name: "userCenter",
      component: UserCenter,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/article",
      name: "article",
      component: Article,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/test",
      name: "test",
      component: Test,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/testList",
      name: "testList",
      component: TestList,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/login",
      name: "signin-basic",
      component: SignInBasicView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/register",
      name: "signup-basic",
      component: SignUpBasicView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/scoreline",
      name: "scoreline-basic",
      component: ScoreLineBasicView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/scorelinedetail",
      name: "scorelinedetail-basic",
      component: ScoreLineDetailBasicView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/school",
      name: "school-basic",
      component: SchoolBasicView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/schoolDetail",
      name: "schoolDetail-basic",
      component: SchoolDetailBasicView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/teacherDetail",
      name: "teacherDetail-basic",
      component: TeacherDetailBasicView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/time",
      name: "time-basic",
      component: TimeBasicView,
      meta: {
        keepAlive: false,
      },
    },
  ],
});

import { getToken } from "@/utils/token";
// 配置全局前置路由守卫
router.beforeEach(async (to, from, next) => {
  if (getToken()) {
    //用户登录了还想去login组件
    if (to.path === "/login" || to.path === "/register") {
      await ElMessage({
        duration: 1000,
        message: "已经登录，不能再重复登录！",
      });
      console.log(getToken());
      next(from.path);
    } else {
      //判断
      if (store.state.user.username) {
        next();
      } else {
        //用户登录后获取用户信息
        try {
          const result = await store.dispatch("user/getUserInfo");
          if (result) {
            next();
          }
        } catch (e) {
          //token异常了，就清除token
          await store.dispatch("user/logout");
          console.log(e.message);
          next("/login");
        }
      }
    }
  } else {
    //未登录状态下
    if (
      to.path.indexOf("/post-article") !== -1 ||
      to.path.indexOf("/testlist") !== -1 ||
      to.path.indexOf("/userCenter") !== -1
    ) {
      ElMessage({
        duration: 1000,
        message: "当前尚未登录，请先登录",
      });
      next("/login");
    } else {
      next();
    }
  }
});

export default router;
