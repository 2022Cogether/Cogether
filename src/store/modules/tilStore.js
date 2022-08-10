import axios from "axios";
import router from "@/router";

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
    // 기존 til 리스트에 새로 받은 리스트를 합침!
    ADD_TIL_LIST: (state, nextlist) => {
      state.tilList = state.tilList.concat(nextlist);
    },
    // main 페이지에 새로 들어갈 때 기존의 꽉찬 til 리스트를 비울 필요가 있음?
    CLEAN_TIL_LIST: (state) => {
      state.tilList = [];
    },
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
    fetchTil({ commit }, credentials) {
      axios
        .get("til/", credentials)
        .then((res) => {
          if (res.status === 200) {
            commit("SET_TIL", res.data);
          }
        })
        .catch((err) => {
          console.error(err.response.data);
        });
    },

    // 스크롤할 때마다 추가로 받는 방식이면 지금까지 받은 til 숫자를 알 필요가 있다고 생각함
    // 그래서 임의로 tilnum을 추가함
    fetchTilList({ commit }, payload) {
      // const tilNum = getters.getTilListLength;
      // 현재 가지고 있는 til 목록의 수를 받아 벡에서 그 다음 til 들을 로딩하려고 만든 변수인데..
      axios
        .get("til/list/" + payload.userId)
        .then((res) => {
          if (res.status === 200) {
            alert("새로 받아왔습니다!");
            commit("ADD_TIL_LIST", res.data);
          }
        })
        .catch((err) => {
          console.error(err.response.data);
          // commit("SET_AUTH_ERROR", err.response.data);
          // const errorMessage = [];
          // for (const errors in err.response.data) {
          //   for (const error of err.response.data[errors]) {
          //     if (!errorMessage.includes(error)) {
          //       errorMessage.push(error);
          //     }
          //   }
          // }
        });
    },

    removeTilList({ commit }) {
      alert("싹 지우고!");
      commit("CLEAN_TIL_LIST");
    },

    createTil({ dispatch, getters, state }, payload) {
      axios
        .post("til", payload, { headers: getters.authHeader })
        .then((res) => {
          router.push({
            name: "TilDetail",
            params: { tilPK: res.data.tilId },
          });

          const credentials = {
            tilId: res.data.tilId,
            userId: state.currentUser.id, // 이거 맞나?
          };

          dispatch("fetchOpenTil", credentials);
        })
        .catch((err) => console.error(err.response));
    },

    updateTil({ dispatch, getters, state }, payload) {
      axios
        .put("til/" + payload.pk, payload, {
          headers: getters.authHeader,
        }) // payload: Til 데이터
        .then((res) => {
          const credentials = {
            tilId: res.data.tilId,
            userId: state.currentUser.id, // 이거 맞나?
          };

          dispatch("fetchOpenTil", credentials);
        })
        .catch((err) => console.error(err.response));
    },

    removeTil({ commit, getters }, tilPK) {
      axios
        .delete("til/delete/" + tilPK, { headers: getters.authHeader })
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
          // commit("SET_AUTH_ERROR", err.response.data);
          // const errorMessage = [];
          // for (const errors in err.response.data) {
          //   for (const error of err.response.data[errors]) {
          //     if (!errorMessage.includes(error)) {
          //       errorMessage.push(error);
          //     }
          //   }
          // }
          // alert(errorMessage.join("\r\n"));
        });
    },

    likeTil({ commit, getters }, tilPk) {
      axios
        .post("til/like/" + tilPk, { headers: getters.authHeader })
        .then((res) => commit("SET_TIL", res.data))
        .catch((err) => console.error(err.response));
    },

    dislikeTil({ commit, getters }, tilPk) {
      axios
        .delete("til/like/" + tilPk, { headers: getters.authHeader })
        .then((res) => commit("SET_TIL", res.data))
        // TIL state 모델이 확정나면, 거기서 is_like에 해당할 속성을 직접 바꿀 예정
        .catch((err) => console.error(err.response));
    },

    searchTil({ commit }, payload) {
      // paylod => keyword, userId
      axios
        .get("til/search/", payload)
        .then((res) => commit("ADD_TIL_LIST", res.data))
        .catch((err) => console.error(err.response));
    },

    createComment({ commit, getters }, payload) {
      axios
        .post("/til/commment/create", payload, { headers: getters.authHeader })
        .then((res) => {
          commit("CLEAN_TIL_LIST");
          commit("SET_TIL", res.data);
        })
        .catch((err) => console.error(err.response));
    },
  },
};
