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
          console.log("팔로잉 리스트 불러오기 실패");
          console.log("팔로잉 리스트 불러오기 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },

    async fetchFollowerList({ getters, commit }, userId) {
      await http
        .get("follower/" + userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_FOLLOWER_LIST", res.data);
          console.log("팔로워 리스트 불러오기완료");
        })
        .catch((err) => {
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
          console.log("팔로잉 수 체크 실패");
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
          console.log("팔로워 수 불러오기 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },

    // 팔로우 언팔로우
    follow({ getters }, userId) {
      http
        .post("follow/" + userId, null, {
          headers: getters.authHeader,
        })
        .then(() => {
          Toast.fire({
            icon: "success",
            title: "해당 유저를 팔로우 했습니다.",
          });
        })
        .catch((err) => {
          console.log("팔로우 실패");
          console.log(err.message);
          console.log(err.response);
        });
    },

    unfollow({ getters }, userId) {
      http
        .delete("follow/" + userId, {
          headers: getters.authHeader,
        })
        .then(() => {
          Toast.fire({
            icon: "success",
            title: "해당 유저의 팔로우를 취소했습니다.",
          });
        })
        .catch((err) => {
          console.log(err.message);
          console.log(err.response);
        });
    },
  },
};
