import { getToken, setToken, rmToken } from "@/utils/token";
import { reqGetUserInfo } from "@/api";
import { defineStore } from "pinia";

export const useUserStore = defineStore({
  id: "user",
  state: () => ({
    token: getToken(),
    userInfo: {},
  }),
  getters: {},
  actions: {
    async register(data) {
      const result = await this.$API.reqRegister(data);
      if (result.data.code === 200) {
        return true;
      } else {
        throw new Error("注册失败！" + result.data.msg);
      }
    },
    async login(data) {
      const result = await this.$API.reqLogin(data);
      if (result.data.code === 200 && result.data.data !== null) {
        this.LOGIN(result.data.data.TOKEN);
        return true;
      } else {
        throw new Error("登录失败！" + result.data.msg);
      }
    },
    async getUserInfo() {
      let result = await reqGetUserInfo();
      if (result.data.code === 200) {
        this.GETUSERINFO(result.data);
        return true;
      } else {
        throw new Error("FailToGetUserInfo");
      }
    },
    async logout() {
      try {
        this.CLEAR();
        return true;
      } catch (e) {
        throw new Error("退出失败~");
      }
    },
  },
  mutations: {
    LOGIN(state, token) {
      state.token = token || "";
      setToken(token);
    },
    GETUSERINFO(state, userInfo) {
      state.userInfo = userInfo.data || {};
    },
    CLEAR(state) {
      state.token = "";
      rmToken();
      state.userInfo = {};
    },
  },
});


// import { getToken, setToken, rmToken } from "@/utils/token";
// import { reqGetUserInfo } from "@/api";
//
// const actions = {
//   async register({ commit }, data) {
//     const result = await this.$API.reqRegister(data);
//     if (result.data.code === 200) {
//       return true;
//     } else {
//       return Promise.reject(new Error("注册失败！" + result.data.msg));
//     }
//   },
//   async login(context, data) {
//     const result = await this.$API.reqLogin(data);
//     if (result.data.code === 200 && result.data.data !== null) {
//       context.commit("LOGIN", result.data.data.TOKEN);
//       setToken(result.data.data.TOKEN);
//       return true;
//     } else {
//       return Promise.reject(new Error("登录失败！" + result.data.msg));
//     }
//   },
//   async getUserInfo({ commit }) {
//     let result = await reqGetUserInfo();
//     if (result.data.code === 200) {
//       commit("GETUSERINFO", result.data);
//       return true;
//     } else {
//       return Promise.reject(new Error("FailToGetUserInfo"));
//     }
//   },
//   async logout({ commit }) {
//     try {
//       commit("CLEAR");
//       return true;
//     } catch (e) {
//       return Promise.reject(new Error("退出失败~"));
//     }
//   },
// };
// const mutations = {
//   LOGIN(state, token) {
//     state.token = token || "";
//   },
//   GETUSERINFO(state, userInfo) {
//     state.userInfo = userInfo.data || {};
//   },
//   CLEAR(state) {
//     state.token = "";
//     rmToken();
//     state.userInfo = {};
//   },
// };
// const state = {
//   token: getToken(),
//   userInfo: {},
// };
// const getters = {};
//
// export const user = {
//   namespaced: true,
//   actions,
//   mutations,
//   state,
//   getters,
// };
