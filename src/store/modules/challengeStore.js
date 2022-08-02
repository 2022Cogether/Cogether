// import testaxios from "@/api/testaxios";
// import axios from "axios";

export const challengeStore = {
  state: {
    //변수 선언
    isCompeteStarted: false,
    competeStartTime: null,
    isCoopRoomExpand: true,
    competeTotal: "01:30:00",
    competeRank: 7,
    competePeople: 100,
    rooms: [
      {
        host: "jinhoe",
        title: "title",
        time: "60",
        curPeople: 4,
        maxPeople: 6,
        isStarted: false,
        content: "설명입니다.설명입니다.설명입니다.설명입니다.설명입니다.",
      },
      {
        host: "wlsghl",
        title: "title2",
        time: "50",
        curPeople: 4,
        maxPeople: 5,
        isStarted: true,
        content: "설명입니다2.",
      },
      {
        host: "rlawlsghl",
        title: "title3",
        time: "40",
        curPeople: 1,
        maxPeople: 6,
        isStarted: false,
        content: "설명입니다3.",
      },
    ],
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
    getRooms(state) {
      return state.rooms;
    },
    getCompeteTotal(state) {
      return state.competeTotal;
    },
    getCompeteRank(state) {
      return state.competeRank;
    },
    getCompetePeople(state) {
      return state.competePeople;
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
  },
  actions: {
    //경쟁 시간 보내기 (구현 예정)
    sendCompeteTime({ commit }, param) {
      console.log(commit);
      console.log(param);
    },
  },
  modules: {},
};
