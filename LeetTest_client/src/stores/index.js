import { defineStore } from "pinia";
import bootstrap from "bootstrap/dist/js/bootstrap.min.js";
import { user } from "@/stores/User";

export const useAppStore = defineStore("storeId", {
  state: () => ({
    bootstrap,
    user,
  }),
});
