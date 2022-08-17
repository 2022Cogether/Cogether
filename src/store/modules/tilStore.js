import http from "@/api/http";

export const tilStore = {
  state: {
    openTil: -1, // 모달창으로 디테일이 열릴 til의 번호, -1이면 안 열린 상태!
    tilList: [],
    tilContent: {},
    // commentId: -1,
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
      state.tilContent.isLike = !state.tilContent.isLike;
    },
    SET_TILLIST_LIKE: (state, tilPk) => {
      state.tilList.forEach((element, idx) => {
        if (element.pk == tilPk) {
          state.tilList[idx].isLike = !state.tilList[idx].isLike;
        }
      });
    },

    // 코멘트
    // SET_COMMENT_ID: (state, commentId) => {
    //   state.commentId = commentId;
    // },

    // ADD_COMMENT: (state, comment) => {
    //   state.tilContent.commentList.push(comment);
    // },
    PUT_COMMENT: (state, commentLoad) => {
      for (let i; i < state.tilContent.commentList.length; i++) {
        if (
          state.tilContent.commentList[i].tilCommentId == commentLoad.commentId
        ) {
          state.tilContent.commentList[i].content = commentLoad.content;
          break;
        }
      }
    },
    // DEL_COMMENT: (state, commentId) => {
    //   console.log("commentList", state.tilContent.commentList);
    //   for (let i = 0; i < state.tilContent.commentList.length; i++) {
    //     if (state.tilContent.commentList[i].tilCommentId == commentId) {
    //       delete state.tilContent.commentList[i];
    //       alert("댓글 삭제 성공!");
    //       console.log("commentList", state.tilContent.commentList);
    //       break;
    //     }
    //   }
    // },
  },
  actions: {
    // 피드 상세 조회할 tilId를 store에 세팅
    fetchOpenTil({ commit, dispatch }, credentials) {
      // alert("여기는?");
      commit("SET_OPEN_TIL", credentials.tilId);
      dispatch("fetchTil", credentials);
    },

    // 피드 상세 조회
    fetchTil({ commit, getters }, credentials) {
      if (credentials.tilId != -1) {
        http
          .get("til/" + credentials.tilId, {
            headers: getters.authHeader,
          })
          .then((res) => {
            console.log(res.data);
            console.log(res.status);
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
    async fetchMyTilList({ commit, getters }) {
      await http
        .get("til/list/my/", {
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
          alert("내 리스트가 에러라고??");
          console.error(err.response.data);
        });
    },

    // removeTilList({ commit }) {
    //   alert("싹 지우고!");
    //   commit("CLEAN_TIL_LIST");
    // },

    createTil({ commit }, payload) {
      http
        .post("til", payload, {
          headers: {
            ACCESS_TOKEN: localStorage.getItem("access_TOKEN"),
            "Content-Type": "multipart/form-data",
            // ...payload.getHeaders(),
          },
        })
        .then((res) => {
          console.log("til 생성 response");
          console.log(res.data.id);
          commit("SET_BOOLEANVALUE");
          // commit("SET_OPEN_TIL", res.data.tilId);

          // const credentials = {
          //   tilId: res.data.tilId,
          //   userId: state.loginUserId,
          // };

          // dispatch("fetchOpenTil", credentials);
        })
        .catch((err) => {
          alert("TIL 생성 오류!");
          console.log("TIL 생성 오류!");
          console.error(err.response);
        });
    },

    updateTil({ dispatch, getters }, payload) {
      http
        .put("til/", payload, {
          headers: getters.authHeader,
        })
        .then(() => {
          console.log("됐나?");
          const credentials = {
            tilId: payload.tilId,
          };

          dispatch("fetchOpenTil", credentials);
        })
        .catch((err) => console.error(err.response));
    },

    removeTil({ commit, getters }, tilPk) {
      http
        .delete("til/" + tilPk, {
          headers: getters.authHeader,
        })
        .then(() => {
          alert("삭제 성공!");
          commit("SET_OPEN_TIL", -1);
          commit("SET_TIL", {});
          commit("SET_BOOLEANVALUE");
        })
        .catch((err) => {
          alert("삭제 실패??");
          console.log(err);
        });
    },

    likeTil({ commit, getters }, tilPk) {
      http
        .post(
          "til/like/",
          {
            tilPk: tilPk,
          },
          {
            headers: getters.authHeader,
          }
        )
        .then((res) => commit("SET_TIL", res.data))
        .catch((err) => console.error(err.response));
    },

    dislikeTil({ commit, getters }, tilPk) {
      http
        .delete(
          "til/like/",
          {
            tilPk: tilPk,
          },
          {
            headers: getters.authHeader,
          }
        )
        .then((res) => commit("SET_TIL", res.data))
        // TIL state 모델이 확정나면, 거기서 is_like에 해당할 속성을 직접 바꿀 예정
        .catch((err) => console.error(err.response));
    },

    async searchTil({ commit, getters }, keyword) {
      // paylod => keyword, userId
      await http
        .get("til/search/" + keyword, {
          headers: getters.authHeader,
        })
        .then((res) => commit("SET_TIL_LIST", res.data))
        .catch((err) => console.error(err.response));
    },
    async searchMyTil({ commit, getters }, keyword) {
      // paylod => keyword, userId
      await http
        .get("til/search/my/" + keyword, {
          headers: getters.authHeader,
        })
        .then((res) => commit("SET_TIL_LIST", res.data))
        .catch((err) => console.error(err.response));
    },

    // TIL 코멘트
    createComment({ dispatch, getters }, payload) {
      http
        .post("/til/comment", payload, {
          headers: getters.authHeader,
        })
        .then(() => {
          // commit("ADD_COMMENT", {
          //   ...payload,
          //   tilCommentId: res.data.id,
          // });
          // console.log(state.tilContent.commentList);
          dispatch("fetchTil", {
            tilId: getters.getOpenTil,
          });
        })
        .catch((err) => console.error(err.response));
    },

    removeComments({ getters, dispatch }, payload) {
      http
        .delete("til/comment/" + payload.commentId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.status === 200) {
            alert("삭제 성공!");
            // dispatch("fetchTil", { tilId: payload.tilId });
            // console.log("res.data", res.data);
            // commit("DEL_COMMENT", res.data.id);
            dispatch("fetchTil", {
              tilId: getters.getOpenTil,
            });
          }
        })
        .catch((err) => {
          alert("삭제 실패??");
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
          alert("수정 성공!");
          console.log("update res data", res.data.id);
          commit("PUT_COMMENT", {
            commentId: res.data.id,
            content: payload.content,
          });
        })
        .catch((err) => {
          alert("수정 실패??");
          console.error(err.response);
        });
    },
  },
};
