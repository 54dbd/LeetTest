import { createRouter, createWebHistory } from "vue-router";
import PresentationView from "../views/Presentation/PresentationView.vue";
import AboutView from "../views/LandingPages/AboutUs/AboutView.vue";
import ContactView from "../views/LandingPages/ContactUs/ContactView.vue";
import AuthorView from "../views/LandingPages/Author/AuthorView.vue";
import SignInBasicView from "../views/LandingPages/SignIn/BasicView.vue";
import SignUpBasicView from "../views/LandingPages/SignUp/SingUpView.vue";

import PageHeaders from "../layouts/sections/page-sections/page-headers/HeadersView.vue";
import PageFeatures from "../layouts/sections/page-sections/features/FeaturesView.vue";
import NavigationNavbars from "../layouts/sections/navigation/navbars/NavbarsView.vue";
import NavigationNavTabs from "../layouts/sections/navigation/nav-tabs/NavTabsView.vue";
import NavigationPagination from "../layouts/sections/navigation/pagination/PaginationView.vue";
import InputAreasInputs from "../layouts/sections/input-areas/inputs/InputsView.vue";
import InputAreasForms from "../layouts/sections/input-areas/forms/FormsView.vue";
import ACAlerts from "../layouts/sections/attention-catchers/alerts/AlertsView.vue";
import ACModals from "../layouts/sections/attention-catchers/modals/ModalsView.vue";
import ACTooltipsPopovers from "../layouts/sections/attention-catchers/tooltips-popovers/TooltipsPopoversView.vue";
import ElAvatars from "../layouts/sections/elements/avatars/AvatarsView.vue";
import ElBadges from "../layouts/sections/elements/badges/BadgesView.vue";
import ElBreadcrumbs from "../layouts/sections/elements/breadcrumbs/BreadcrumbsView.vue";
import ElButtons from "../layouts/sections/elements/buttons/ButtonsView.vue";
import ElButtonGroups from "../layouts/sections/elements/button-groups/ButtonGroupsView.vue";
import ElDropdowns from "../layouts/sections/elements/dropdowns/DropdownsView.vue";
import ElProgressBars from "../layouts/sections/elements/progress-bars/ProgressBarsView.vue";
import ElToggles from "../layouts/sections/elements/toggles/TogglesView.vue";
import ElTypography from "../layouts/sections/elements/typography/TypographyView.vue";
import PostArticleView from "@/views/LandingPages/PostArticle/PostArticleView.vue";
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
      path: "/pages/landing-pages/about-us",
      name: "about",
      component: AboutView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/pages/landing-pages/contact-us",
      name: "contactus",
      component: ContactView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/pages/landing-pages/author",
      name: "author",
      component: AuthorView,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/pages/landing-pages/post-article",
      name: "post-article",
      component: PostArticleView,
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
      path: "/sections/page-sections/page-headers",
      name: "page-headers",
      component: PageHeaders,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/page-sections/features",
      name: "page-features",
      component: PageFeatures,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/navigation/navbars",
      name: "navigation-navbars",
      component: NavigationNavbars,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/navigation/nav-tabs",
      name: "navigation-navtabs",
      component: NavigationNavTabs,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/navigation/pagination",
      name: "navigation-pagination",
      component: NavigationPagination,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/input-areas/inputs",
      name: "inputareas-inputs",
      component: InputAreasInputs,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/input-areas/forms",
      name: "inputareas-forms",
      component: InputAreasForms,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/attention-catchers/alerts",
      name: "ac-alerts",
      component: ACAlerts,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/attention-catchers/modals",
      name: "ac-modals",
      component: ACModals,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/attention-catchers/tooltips-popovers",
      name: "ac-tooltips-popovers",
      component: ACTooltipsPopovers,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/avatars",
      name: "el-avatars",
      component: ElAvatars,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/badges",
      name: "el-badges",
      component: ElBadges,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/breadcrumbs",
      name: "el-breadcrumbs",
      component: ElBreadcrumbs,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/buttons",
      name: "el-buttons",
      component: ElButtons,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/button-groups",
      name: "el-button-groups",
      component: ElButtonGroups,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/dropdowns",
      name: "el-dropdowns",
      component: ElDropdowns,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/progress-bars",
      name: "el-progress-bars",
      component: ElProgressBars,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/toggles",
      name: "el-toggles",
      component: ElToggles,
      meta: {
        keepAlive: false,
      },
    },
    {
      path: "/sections/elements/typography",
      name: "el-typography",
      component: ElTypography,
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
    if (to.path === "/login") {
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
      to.path.indexOf("/testlist") !== -1
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
