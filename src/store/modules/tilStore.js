// import axios from "axios";
// import router from "@/router";

export const tilStore = {
  state: {
    tilContent: {
      created_at: "2020-02-29",
      title: "til 1번글",
      content:
        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
      user_id: 1004,
    },
  },
  getters: {
    getTilContent(state) {
      return state.tilContent;
    },
  },
  mutations: {
    SET_BOOLEANVALUE: (state, payload) => {
      state.tilContent = payload;
    },
  },
  actions: {},
};
