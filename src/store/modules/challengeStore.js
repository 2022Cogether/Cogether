import http from "@/api/http";

export const challengeStore = {
  state: {
    //변수 선언
    isCompeteStarted: false,
    competeStartTime: null,
    isCoopRoomExpand: true,
    competeTotal: null,
    competeRank: null,
    competePeople: 100,
    rooms: [],
    roomId: null,
    timeStarted: false,
    competedInterval: false,
    enterCoop: false,
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
    getEnterCoop(state) {
      return state.enterCoop;
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
    SET_COMPETE_TIME(state, competeTotal) {
      state.competeTotal = competeTotal;
    },
    SET_ENTER_COOP(state, enterCoop) {
      state.enterCoop = enterCoop;
    },
  },
  actions: {
    getCompeteInfo({ commit }, id) {
      http
        .get("/livecoop/" + id)
        .then(({ data }) => {
          commit("SET_COMPETE_RANK", data.ranking);
          commit("SET_COMPETE_TIME", data.totalTime);
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    //경쟁 시간 보내기 (구현 예정)
    sendCompeteTime({ commit }, id) {
      const userid = {
        userId: id,
      };
      http
        .patch("/livecoop", userid)
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    //협력방만들기 (구현 예정)
    createCoopRoom({ commit }, param) {
      const room = {
        userId: 3,
        maxMemNum: param.personnel,
        duration: parseInt(param.hour) * 60 + parseInt(param.min),
        title: param.title,
        content: param.content,
        inProgress: false,
      };
      http
        .post("/livecoop", room)
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async deleteCoopRoom({ commit }, id) {
      await http
        .delete("/livecoop/" + id)
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getCoopRooms({ commit }, id) {
      http
        .get("/livecoop/list/" + id)
        .then(({ data }) => {
          console.log("방리스트받기성공");
          console.log(data);
          commit("SET_ROOMS", data.liveCoops);
          commit("SET_ENTER_COOP", data.enterCoop);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getDetailCoopRooms({ commit }, id) {
      http
        .get("livecoop/" + id)
        .then(({ data }) => {
          commit("SET_ROOMS", data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
  },
  modules: {},
};
