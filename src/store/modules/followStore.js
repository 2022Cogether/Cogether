import http from "@/api/http";

export const followStore = {
  state: {
    myFollowingList: [
      {
        id: 3,
        email: "wldusdl1023@naver.com1",
      },
      {
        id: 4,
        email: "wldusdl1023@naver.com11",
      },
    ],
    myFollowerList: [
      {
        id: 3,
        email: "wldusdl1023@naver.com1",
      },
      {
        id: 4,
        email: "wldusdl1023@naver.com11",
      },
    ],
  },

  getters: {
    getMyFollowingList(state) {
      return state.myFollowingList;
    },
    getMyFollowerList(state) {
      return state.myFollowerList;
    },
  },

  mutations: {
    SET_FOLLOWING_LIST: (state, data) => (state.myFollowingList = data),
    SET_FOLLOWER_LIST: (state, data) => (state.myFollowerList = data),
  },

  actions: {
    fetchFollowingList({ getters, commit }) {
      http
        .get("following/", {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("getMyFollowingList", res.data);
          console.log("팔로잉 리스트 불러오기완료");
        })
        .catch((err) => {
          alert("팔로잉 리스트 불러오기 실패");
          console.error(err.response.data);
        });
    },

    fetchFollowerList({ getters, commit }) {
      http
        .get("follower/", {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("getMyFollowerList", res.data);
          console.log("팔로워 리스트 불러오기완료");
        })
        .catch((err) => {
          alert("팔로워 리스트 불러오기 실패");
          console.error(err.response.data);
        });
    },

    // 팔로우 언팔로우
    follow({ commit, getters }, payload) {
      http
        .post("follow/", payload, {
          headers: getters.authHeader,
        })
        .then(() => {
          commit("SET_BOOLEANVALUE");
          console.log("팔로우 완료");
        })
        .catch((err) => {
          alert("팔로우 실패");
          console.error(err.response.data);
        });
    },

    unfollow({ commit, getters }, payload) {
      http
        .post("unfollow/", payload, {
          headers: getters.authHeader,
        })
        .then(() => {
          commit("SET_BOOLEANVALUE");
          console.log("언팔로우 완료");
        })
        .catch((err) => {
          alert("언팔로우 실패");
          console.error(err.response.data);
        });
    },
  },
};
