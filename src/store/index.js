import { createStore } from "vuex";
import { challengeStore } from "@/store/modules/challengeStore.js";
import { chatStore } from "@/store/modules/chatStore.js";
import { signStore } from "@/store/modules/signStore.js";
import { tilStore } from "@/store/modules/tilStore.js";
import { tilCommentStore } from "@/store/modules/tilCommentStore.js";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    challengeStore,
    chatStore,
    signStore,
    tilStore,
    tilCommentStore,
  },
});
