import axios from "axios";

export const tilCommentStore = {
  state: {},
  getters: {
    getComments(state) {
      return state.tilContent.comments;
    },
  },
  mutations: {
    SET_COMMENTS: (state, commentList) => {
      state.tilContent.comments = commentList;
    },
  },
  actions: {
    fetchComments({ commit, getters }, tilId) {
      axios
        .get("til/comment/list/" + tilId, {
          headers: { Authorization: getters.authHeader },
        })
        .then((res) => commit("SET_COMMENTS", res.data))
        .catch((err) => console.error(err.response));
    },

    removeComments({ dispatch, commit, getters }, payload) {
      axios
        .delete("til/delete/" + payload.commentId, {
          headers: { Authorization: getters.authHeader },
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
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          // alert(errorMessage.join("\r\n"));
        });
    },

    updateComment({ dispatch, getters }, payload) {
      axios
        .put("til/comment", payload.comment, {
          headers: { Authorization: getters.authHeader },
        })
        .then(() => {
          dispatch("fetchComments", payload.tilId);
        })
        .catch((err) => console.error(err.response));
    },
  },
};
