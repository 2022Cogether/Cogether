import http from "@/api/http";

export const rankingStore = {
  state: {
    tabStateA: null,
    tabStateB: null,
    tabStateC: null,
    rankingList: [],
  },
  getters: {
    getTabStateA(state) {
      return state.tabStateA;
    },
    getTabStateB(state) {
      return state.tabStateB;
    },
    getTabStateC(state) {
      return state.tabStateC;
    },
    getRankingList(state) {
      return state.rankingList;
    },
  },
  mutations: {
    SET_TAB_STATE_A(state, tabStateA) {
      state.tabStateA = tabStateA;
    },
    SET_TAB_STATE_B(state, tabStateB) {
      state.tabStateB = tabStateB;
    },
    SET_TAB_STATE_C(state, tabStateC) {
      state.tabStateC = tabStateC;
    },
    SET_RANKING_LIST(state, rankingList) {
      state.rankingList = rankingList;
    },
  },
  actions: {
    async getTilListAll({ commit, getters }, page) {
      console.log(commit);
      await http
        .get("ranking/til/" + page, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("랭킹til 전체 받아오기 성공");
          console.log(data);
          commit("SET_RANKING_LIST", data);
        })
        .catch((e) => {
          console.error(e);
        });
    },
    async getTilListFol({ commit, getters }, page) {
      console.log(commit);
      await http
        .get("ranking/til/follow/" + page, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("랭킹til 팔로우 받아오기 성공");
          console.log(data);
          commit("SET_RANKING_LIST", data);
        })
        .catch((e) => {
          console.error(e);
        });
    },
    async getLvListAll({ commit, getters }, page) {
      console.log(commit);
      await http
        .get("ranking/exp/" + page, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("랭킹 Lv 전체 받아오기 성공");
          console.log(data);
          commit("SET_RANKING_LIST", data);
        })
        .catch((e) => {
          console.error(e);
        });
    },
    async getLvListFol({ commit, getters }, page) {
      console.log(commit);
      await http
        .get("ranking/exp/follow/" + page, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("랭킹 Lv 팔로우 받아오기 성공");
          console.log(data);
          commit("SET_RANKING_LIST", data);
        })
        .catch((e) => {
          console.error(e);
        });
    },
    async getCompListAll({ commit, getters }, page) {
      console.log(commit);
      await http
        .get("ranking/total/" + page, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("랭킹 경쟁 전체 받아오기 성공");
          console.log(data);
          commit("SET_RANKING_LIST", data);
        })
        .catch((e) => {
          console.error(e);
        });
    },
    async getCompListFol({ commit, getters }, page) {
      console.log(commit);
      await http
        .get("ranking/total/follow/" + page, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("랭킹 경쟁 팔로우 받아오기 성공");
          console.log(data);
          commit("SET_RANKING_LIST", data);
        })
        .catch((e) => {
          console.error(e);
        });
    },
  },
  modules: {},
};
