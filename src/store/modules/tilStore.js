import http from "@/api/http";

export const tilStore = {
  state: {
    openTil: -1, // 모달창으로 디테일이 열릴 til의 번호, -1이면 안 열린 상태!
    tilList: [
      {
        pk: 10,
        created_at: "2022-03-31",
        title: "til 마지막글",
        content:
          "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        user_id: 1004,
        isLike: true,
      },
      {
        pk: 100,
        created_at: "2022-03-30",
        title: "til 마지막에서 두번째 글",
        content:
          "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        user_id: 1004,
        isLike: false,
      },
      {
        pk: 1000,
        created_at: "2022-03-29",
        title: "til 마지막에서 세번째 글",
        content:
          "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        user_id: 1004,
        isLike: true,
      },
    ],
    tilContent: {
      pk: 5,
      created_at: "2020-02-29",
      title: "til 1번글",
      content:
        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
      user_id: 1004,
      isLike: true,
      comments: [
        {
          pk: 100,
          created_at: "2020-02-20",
          content: "굳굳",
          user_id: "2000",
        },
        {
          pk: 1000,
          created_at: "2022-02-20",
          content: "배드배드",
          user_id: "1004",
        },
      ],
    },
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
  },
  actions: {
    // 피드 상세 조회할 tilId를 store에 세팅
    fetchOpenTil({ commit, dispatch }, credentials) {
      commit("SET_OPEN_TIL", credentials.tilId);
      dispatch("fetchTil", credentials);
    },

    // 피드 상세 조회
    fetchTil({ commit, getters }, credentials) {
      http
        .get("til/", {
          params: credentials,
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
    },

    fetchTilList({ commit, getters }, payload) {
      http
        .get("til/list/" + payload.userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.status === 200) {
            console.log("전체 목록 새로 받아왔습니다!");
            commit("SET_TIL_LIST", res.data);
          }
        })
        .catch((err) => {
          console.error(err.response.data);
        });
    },
    fetchMyTilList({ commit, getters }, payload) {
      http
        .get("til/list/my/" + payload.userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.status === 200) {
            console.log("개인 목록 새로 받아왔습니다!");
            commit("SET_TIL_LIST", res.data);
          }
        })
        .catch((err) => {
          console.error(err.response.data);
        });
    },

    // removeTilList({ commit }) {
    //   alert("싹 지우고!");
    //   commit("CLEAN_TIL_LIST");
    // },

    createTil({ getters, commit }, payload) {
      http
        .post("til", payload, {
          headers: getters.authHeader,
        })
        .then((res) => {
          console.log("til 생성 response");
          console.log(res);
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
          console.error(err.response);
        });
    },

    updateTil({ dispatch, getters, state }, payload) {
      http
        .put("til/", payload, {
          headers: getters.authHeader,
        }) // payload: Til 데이터
        .then((res) => {
          const credentials = {
            tilId: res.data.tilId,
            userId: state.loginUserId,
          };

          dispatch("fetchOpenTil", credentials);
        })
        .catch((err) => console.error(err.response));
    },

    removeTil({ commit, getters }, tilPk) {
      http
        .delete("til/delete/" + tilPk, {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.status === 200) {
            alert("삭제 성공!");
            commit("SET_TIL", {});
            commit("SET_BOOLEANVALUE");
          }
        })
        .catch((err) => {
          alert("삭제 실패??");
          console.error(err.response.data);
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

    searchTil({ commit, getters }, payload) {
      // paylod => keyword, userId
      http
        .get("til/search", {
          params: payload,
          headers: getters.authHeader,
        })
        .then((res) => commit("SET_TIL_LIST", res.data))
        .catch((err) => console.error(err.response));
    },
    searchMyTil({ commit, getters }, payload) {
      // paylod => keyword, userId
      http
        .get("til/search/my", {
          params: payload,
          headers: getters.authHeader,
        })
        .then((res) => commit("SET_TIL_LIST", res.data))
        .catch((err) => console.error(err.response));
    },

    createComment({ commit, getters }, payload) {
      http
        .post("/til/commment", payload, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_TIL", res.data);
        })
        .catch((err) => console.error(err.response));
    },
  },
};
