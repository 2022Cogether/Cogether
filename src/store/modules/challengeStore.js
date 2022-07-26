export const challengeStore = {
  state: {
    //변수 선언
    isCompeteStarted: false,
    competeStartTime: null,
  },
  getters: {
    //변수 호출
    getIsCompeteStarted(state) {
      return state.isCompeteStarted;
    },
    getCompeteStartTime(state) {
      return state.competeStartTime;
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
  },
  actions: {},
  modules: {},
};
