import http from "@/api/http";

export const tilCommentStore = {
  state: {
    comments: [],
  },
  getters: {
    getComments(state) {
      return state.comments;
    },
  },
  mutations: {
    SET_COMMENTS: (state, commentList) => {
      state.comments = commentList;
    },
  },
  actions: {
    fetchComments({ commit, getters }, tilId) {
      http
        .get("til/comment/list/" + tilId, {
          headers: getters.authHeader,
        })
        .then((res) => commit("SET_COMMENTS", res.data))
        .catch((err) => console.error(err.response));
    },

    removeComments({ dispatch, getters }, payload) {
      http
        .delete("til/delete/" + payload.commentId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.status === 200) {
            alert("삭제 성공!");
            dispatch("fetchComments", payload.tilId);
          }
        })
        .catch((err) => {
          // alert("삭제 실패??");
          console.error(err.response.data);
        });
    },

    updateComment({ dispatch, getters }, payload) {
      http
        .put("til/comment", payload.comment, {
          headers: getters.authHeader,
        })
        .then(() => {
          dispatch("fetchComments", payload.tilId);
        })
        .catch((err) => console.error(err.response));
    },
  },
};
