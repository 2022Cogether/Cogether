import testaxios from "@/api/testaxios";

export const challengeStore = {
  state: {
    //변수 선언
    isCompeteStarted: false,
    competeStartTime: null,
    isCoopRoomExpand: true,
    test: [], //test 나중에 삭제
  },
  getters: {
    //변수 호출
    getIsCompeteStarted(state) {
      return state.isCompeteStarted;
    },
    getCompeteStartTime(state) {
      return state.competeStartTime;
    },
    getIsCoopRoomExpand(state) {
      return state.isCoopRoomExpand;
    },
    //test 나중에 삭제
    gettest(state) {
      return state.test;
    },
  },
  mutations: {
    //변수값 수정
    SET_IS_COMPETE_STARTED(state, isCompeteStarted) {
      state.isCompeteStarted = isCompeteStarted;
    },
    SET_COMPETE_START_TIME(state, competeStartTime) {
      state.competeStartTime = competeStartTime;
    },
    SET_IS_COOP_ROOM_EXPAND(state, isCoopRoomExpand) {
      state.isCoopRoomExpand = isCoopRoomExpand;
    },
    //test 나중에 삭제
    Mutest(state, test) {
      state.test = test;
    },
  },
  actions: {
    //test 나중에 삭제
    test({ commit }, param) {
      testaxios
        .get("/" + param)
        .then(({ data }) => {
          console.log("성공:" + commit);
          commit("Mutest", data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
  },
  modules: {},
};
