import http from "@/api/http";

export const followStore = {
  state: {
    myFollowingList: [
      // {
      //   id: 3,
      //   email: "wldusdl1023@naver.com1",
      // },
      // {
      //   id: 4,
      //   email: "wldusdl1023@naver.com11",
      // },
    ],
    myFollowerList: [
      // {
      //   id: 3,
      //   email: "wldusdl1023@naver.com1",
      // },
      // {
      //   id: 4,
      //   email: "wldusdl1023@naver.com11",
      // },
    ],

    followingNumber: 0,
    followerNumber: 0,
  },

  getters: {
    getMyFollowingList(state) {
      return state.myFollowingList;
    },
    getMyFollowerList(state) {
      return state.myFollowerList;
    },
    getFollowingNumber(state) {
      return state.followingNumber;
    },
    getFollowerNumber(state) {
      return state.followerNumber;
    },
  },

  mutations: {
    SET_FOLLOWING_LIST: (state, data) => (state.myFollowingList = data),
    SET_FOLLOWER_LIST: (state, data) => (state.myFollowerList = data),
    SET_FOLLOWING_NUMBER: (state, data) => (state.followingNumber = data),
    SET_FOLLOWER_NUMBER: (state, data) => (state.followerNumber = data),
  },

  actions: {
    fetchFollowingList({ getters, commit }, userId) {
      http
        .get("following/" + userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_FOLLOWING_LIST", res.data);
          console.log("팔로잉 리스트 불러오기완료");
        })
        .catch((err) => {
          alert("팔로잉 리스트 불러오기 실패");
          console.log("팔로잉 리스트 불러오기 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },

    fetchFollowerList({ getters, commit }, userId) {
      http
        .get("follower/" + userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_FOLLOWER_LIST", res.data);
          console.log("팔로워 리스트 불러오기완료");
        })
        .catch((err) => {
          alert("팔로워 리스트 불러오기 실패");
          console.log("팔로워 리스트 불러오기 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },

    fetchFollowingNumber({ getters, commit }, userId) {
      http
        .get("following/lists/" + userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_FOLLOWING_NUMBER", res.data);
          console.log("팔로잉 수 체크 완료");
        })
        .catch((err) => {
          alert("팔로잉 수 체크 실패");
          console.log("팔로잉 수 불러오기 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },

    fetchFollowerNumber({ getters, commit }, userId) {
      http
        .get("follower/lists/" + userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_FOLLOWER_NUMBER", res.data);
          console.log("팔로워 수 체크 완료");
        })
        .catch((err) => {
          alert("팔로워 수 체크 실패");
          console.log("팔로워 수 불러오기 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },

    // 팔로우 언팔로우
    async follow({ commit, getters }, payload) {
      await http
        .post("follow/", payload, {
          headers: getters.authHeader,
        })
        .then(() => {
          commit("SET_BOOLEANVALUE");
          console.log("팔로우 완료");
        })
        .catch((err) => {
          alert("팔로우 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },

    unfollow({ commit, getters }, userId) {
      http
        .delete("follow/" + userId, {
          headers: getters.authHeader,
        })
        .then(() => {
          commit("SET_BOOLEANVALUE");
          console.log("언팔로우 완료");
        })
        .catch((err) => {
          alert("언팔로우 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },
  },
};
