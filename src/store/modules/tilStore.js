import axios from "axios";
import router from "@/router";

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
    SET_TIL: (state, payload) => {
      state.tilContent = payload;
    },
  },
  actions: {
    removeTil({ commit }, tilPK) {
      axios
        .delete("/til/delete/", tilPK)
        .then((res) => {
          if (res.status === 200) {
            alert("삭제 성공!");
            commit("SET_TIL", {});
            router.push({ name: "mainview" });
          }
        })
        .catch((err) => {
          alert("삭제 실패??");
          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },

    likeTil({ commit, getters }, tilPk) {
      axios
        .post("/til/like/", tilPk, { headers: getters.authHeader })
        .then((res) => commit("SET_TIL", res.data))
        .catch((err) => console.error(err.response));
    },

    createComment({ commit, getters }, payload) {
      axios
        .post("/til/commment/create", payload, { headers: getters.authHeader })
        .then((res) => commit("SET_TIL", res.data))
        .catch((err) => console.error(err.response));
    },
  },
};
