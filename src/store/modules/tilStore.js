import http from "@/api/http";
import Swal from "sweetalert2";

const Toast = Swal.mixin({
  toast: true,
  position: "bottom-end",
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.addEventListener("mouseenter", Swal.stopTimer);
    toast.addEventListener("mouseleave", Swal.resumeTimer);
  },
});

export const tilStore = {
  state: {
    openTil: -1, // 모달창으로 디테일이 열릴 til의 번호, -1이면 안 열린 상태!
    tilList: [],
    tilContent: {},
    newCommentId: -1,
  },
  getters: {
    getOpenTil(state) {
      return state.openTil;
    },
    getTilList(state) {
      return state.tilList;
    },
    getTilListLength(state) {
      return state.tilList.length;
    },
    getTilContent(state) {
      return state.tilContent;
    },
    getNewCommentId(state) {
      return state.newCommentId;
    },

    // 코멘트
    // getComments(state) {
    //   for (let i; i < state.tilContent.commentList.length; i++) {
    //     if (state.tilContent.commentList[i].tilCommentId == state.commentId) {
    //       return state.tilContent.commentList[i];
    //     }
    //   }
    // },
  },
  mutations: {
    SET_OPEN_TIL: (state, tilNum) => {
      state.openTil = tilNum;
    },
    SET_TIL_LIST: (state, nextlist) => {
      state.tilList = nextlist;
    },
    // main 페이지에 새로 들어갈 때 기존의 꽉찬 til 리스트를 비울 필요가 있음?
    // CLEAN_TIL_LIST: (state) => {
    //   state.tilList = [];
    // },
    SET_TIL: (state, payload) => {
      state.tilContent = payload;
    },

    // TIL like <-> dislike
    SET_TIL_LIKE: (state) => {
      state.tilContent.like = !state.tilContent.like;
    },
    SET_TILLIST_LIKE: (state, tilPk) => {
      state.tilList.forEach((element, idx) => {
        if (element.pk == tilPk) {
          state.tilList[idx].isLike = !state.tilList[idx].isLike;
        }
      });
    },

    SET_NEW_COMMENT_ID: (state, commentId) => {
      state.newCommentId = commentId;
    },

    // 코멘트
    // SET_COMMENT_ID: (state, commentId) => {
    //   state.commentId = commentId;
    // },

    // ADD_COMMENT: (state, comment) => {
    //   state.tilContent.commentList.push(comment);
    // },
    PUT_COMMENT: (state, commentLoad) => {
      for (let i = 0; i < state.tilContent.commentList.length; i++) {
        if (
          state.tilContent.commentList[i].tilCommentId == commentLoad.commentId
        ) {
          state.tilContent.commentList[i].content = commentLoad.content;
          break;
        }
      }
    },
  },
  actions: {
    // 피드 상세 조회할 tilId를 store에 세팅
    fetchOpenTil({ commit }, credentials) {
      // alert("여기는?");
      commit("SET_OPEN_TIL", credentials.tilId);
    },

    // 피드 상세 조회
    async fetchTil({ commit, getters }, credentials) {
      if (credentials.tilId != -1) {
        await http
          .get("til/" + credentials.tilId, {
            headers: getters.authHeader,
          })
          .then((res) => {
            if (res.status === 200) {
              commit("SET_TIL", res.data);
            }
          })
          .catch((err) => {
            console.error(err.response.data);
          });
      }
    },

    async fetchTilList({ commit, getters }) {
      await http
        .get("til/list/", {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.status === 200) {
            console.log("전체 목록 새로 받아왔습니다!");
            console.log("til 전체 목록", res.data);
            commit("SET_TIL_LIST", res.data);
          }
        })
        .catch((err) => {
          console.error(err.response.data);
        });
    },
    async fetchMyTilList({ commit, getters }, userId) {
      await http
        .get("til/list/my/" + userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          console.log("내 TIL");
          console.log(res);
          if (res.status === 200) {
            console.log("개인 목록 새로 받아왔습니다!");
            commit("SET_TIL_LIST", res.data);
            console.log(res.data);
          }
        })
        .catch((err) => {
          console.log("내 리스트가 에러라고??");
          console.error(err.response.data);
        });
    },

    async createTil({ commit }, payload) {
      await http
        .post("til", payload, {
          headers: {
            ACCESS_TOKEN: localStorage.getItem("access_TOKEN"),
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          console.log("til 생성 response");
          console.log(res.data.id);
          commit("SET_BOOLEANVALUE");
        })
        .catch((err) => {
          console.log("TIL 생성 오류!");
          console.error(err.response);
        });
    },

    async updateTil({ dispatch, getters }, payload) {
      await http
        .put("til/", payload, {
          headers: getters.authHeader,
        })
        .then(() => {
          const credentials = {
            tilId: payload.tilId,
          };

          dispatch("fetchOpenTil", credentials);
          dispatch("fetchTil", credentials);
        })
        .catch((err) => console.error(err.response));
    },

    async removeTil({ commit, getters }, tilPk) {
      await http
        .delete("til/" + tilPk, {
          headers: getters.authHeader,
        })
        .then(() => {
          Toast.fire({
            icon: "success",
            title: "글을 삭제했습니다.",
          });
          commit("SET_OPEN_TIL", -1);
          commit("SET_TIL", {});
          commit("SET_BOOLEANVALUE");
        })
        .catch((err) => {
          console.log("삭제 실패");
          console.log(err);
        });
    },

    likeTil({ commit, getters }, tilId) {
      http
        .post(
          "til/like/",
          {
            tilId: tilId,
            userId: getters.getLoginUserId,
          },
          {
            headers: getters.authHeader,
          }
        )
        .then((res) => commit("SET_TIL", res.data))
        .catch((err) => console.error(err.response));
    },

    dislikeTil({ commit, getters }, tilId) {
      http
        .delete("til/like/" + tilId, {
          headers: getters.authHeader,
        })
        .then((res) => commit("SET_TIL", res.data))
        .catch((err) => console.error(err.response));
    },

    async searchTil({ commit, getters }, keyword) {
      await http
        .get("til/search/" + keyword, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_TIL_LIST", res.data);
        })
        .catch((err) => console.error(err.response));
    },
    async searchMyTil({ commit, getters }, payload) {
      // paylod => keyword, userId
      await http
        .get("til/search/my/", {
          params: payload,
          headers: getters.authHeader,
        })
        .then((res) => commit("SET_TIL_LIST", res.data))
        .catch((err) => console.error(err.response));
    },

    // TIL 코멘트
    async createComment({ commit, getters }, payload) {
      await http
        .post("/til/comment", payload, {
          headers: getters.authHeader,
        })
        .then(async (res) => {
          commit("SET_NEW_COMMENT_ID", res.data.id);
        })
        .catch((err) => console.error(err.response));
    },

    removeComments({ getters }, payload) {
      http
        .delete("til/comment/" + payload.commentId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.status === 200) {
            Toast.fire({
              icon: "success",
              title: "댓글을 삭제했습니다.",
            });
          }
        })
        .catch((err) => {
          console.log("삭제 실패");
          console.error(err.response.data);
        });
    },

    updateComment({ commit, getters }, payload) {
      console.log("payload", payload);
      http
        .put("til/comment", payload, {
          headers: getters.authHeader,
        })
        .then((res) => {
          Toast.fire({
            icon: "success",
            title: "댓글을 수정했습니다.",
          });
          console.log("update res data", res.data.id);
          commit("PUT_COMMENT", {
            commentId: res.data.id,
            content: payload.content,
          });
        })
        .catch((err) => {
          console.log("수정 실패");
          console.error(err.response);
        });
    },
  },
};
