import { createApp } from "vue";
import store from './stores'
import App from "./App.vue";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import router from "./router";
import * as API from "@/api";

import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";

// Nucleo Icons
import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";

import materialKit from "./material-kit";

const app = createApp(App);

app.use(ElementPlus);
app.use(router);
app.use(materialKit);
app.use(mavonEditor);
app.use(store);
app.provide('store',store)
app.provide('router',router)
// console.log(this.$store)
app.config.globalProperties.API = API;
app.mount("#app");
