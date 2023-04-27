import { defineStore } from "pinia";
import bootstrap from "bootstrap/dist/js/bootstrap.min.js";
//引入Vuex
import { createStore } from "vuex";
import { user } from "@/stores/User";

export const useAppStore = defineStore("storeId", {
  state: () => ({
    bootstrap,
  }),
});
//对外暴露store类的一个实例对象
const store = createStore({
  modules: {
    user,
  },
});
export default store
