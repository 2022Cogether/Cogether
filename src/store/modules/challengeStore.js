import testaxios from "@/api/testaxios";
// import axios from "axios";

export const challengeStore = {
  state: {
    //변수 선언
    isCompeteStarted: false,
    competeStartTime: null,
    isCoopRoomExpand: true,
    competeTotal: 60,
    competeRank: 7,
    competePeople: 100,
    rooms: [],
    roomtId: null,
    timeStarted: false,
    competedInterval: false,
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
    getRoomId(state) {
      return state.roomId;
    },
    getTimeStarted(state) {
      return state.timeStarted;
    },
    getCompeteInterval(state) {
      return state.competedInterval;
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
    SET_ROOMS(state, rooms) {
      state.rooms = rooms;
    },
    SET_ROOM_ID(state, roomId) {
      state.roomId = roomId;
    },
    SET_COMPETE_RANK(state, a) {
      state.competeRank = a;
    },
    SET_TIME_STARTED(state, a) {
      state.timeStarted = a;
    },
    SET_COMPETE_INTERVAL(state, competedInterval) {
      state.competedInterval = competedInterval;
    },
  },
  actions: {
    //경쟁 시간 보내기 (구현 예정)
    sendCompeteTime({ commit }, param) {
      console.log(commit);
      console.log(param);
    },
    //협력방만들기 (구현 예정)
    createCoopRoom({ commit }, param) {
      const room = {
        host: "test",
        title: param.title,
        time: parseInt(param.hour) * 60 + parseInt(param.min),
        curPeople: 1,
        maxPeople: param.personnel,
        isStarted: false,
        content: param.content,
      };
      testaxios
        .post("/livecoop", room)
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    updateCoopRoom({ commit }, param) {
      const room = {
        host: "test",
        title: param.title,
        time: parseInt(param.hour) * 60 + parseInt(param.min),
        curPeople: 1,
        maxPeople: param.personnel,
        isStarted: false,
        content: param.content,
      };
      testaxios
        .put("/livecoop", room)
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    deleteCoopRoom({ commit }, id) {
      testaxios
        .delete("/livecoop/" + id)
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getCoopRooms({ commit }) {
      testaxios
        .get("livecoop/list")
        .then(({ data }) => {
          commit("SET_ROOMS", data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getDetailCoopRooms({ commit }, id) {
      testaxios
        .get("livecoop/" + id)
        .then(({ data }) => {
          commit("SET_ROOMS", data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    sendCompete() {
      console.log("1");
    },
  },
  modules: {},
};
