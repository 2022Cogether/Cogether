import http from "@/api/http";

export const challengeStore = {
  state: {
    //변수 선언
    isCompeteStarted: false,
    competeStartTime: null,
    isCoopRoomExpand: true,
    competeTotal: " - ",
    competeRank: " - ",
    competePeople: null,
    rooms: [],
    room: null,
    roomId: null,
    roomUserId: null,
    timeStarted: false,
    competedInterval: false,
    enterCoop: false,
    coopMembers: [],
    coopMemberId: null,
    stompClientCoop: null,
  },
  getters: {
    //변수 호출
    getStompClientCoop(state) {
      return state.stompClientCoop;
    },
    getCoopMemberId(state) {
      return state.coopMemberId;
    },
    getCoopMembers(state) {
      return state.coopMembers;
    },
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
    getRoom(state) {
      return state.room;
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
    getRoomUserId(state) {
      return state.roomUserId;
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
    SET_STOMP_CLIENT_COOP(state, stompClientCoop) {
      state.stompClientCoop = stompClientCoop;
    },
    SET_COOP_MEMBER_ID(state, coopMemberId) {
      state.coopMemberId = coopMemberId;
    },
    SET_IS_COMPETE_STARTED(state, isCompeteStarted) {
      state.isCompeteStarted = isCompeteStarted;
    },
    SET_COMPETE_START_TIME(state, competeStartTime) {
      state.competeStartTime = competeStartTime;
    },
    SET_IS_COOP_ROOM_EXPAND(state, isCoopRoomExpand) {
      state.isCoopRoomExpand = isCoopRoomExpand;
    },
    SET_ROOM(state, room) {
      state.room = room;
    },
    SET_ROOMS(state, rooms) {
      state.rooms = rooms;
    },
    SET_ROOM_ID(state, roomId) {
      state.roomId = roomId;
    },
    SET_ROOM_USER_ID(state, roomUserId) {
      state.roomUserId = roomUserId;
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
    SET_COOP_MEMBERS(state, coopMembers) {
      state.coopMembers = coopMembers;
    },
  },
  actions: {
    getCompeteInfo({ commit, getters }) {
      http
        .get("/liveComp", {
          headers: getters.authHeader,
        })
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
    sendCompeteTime({ commit, getters }, id) {
      const userid = {
        userId: id,
      };
      http
        .patch("/liveComp", userid, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    //협력방만들기
    async createCoopRoom({ commit, getters }, param) {
      const room = {
        userId: getters.getLoginUserId,
        maxMemNum: param.personnel,
        duration: parseInt(param.hour) * 60 + parseInt(param.min),
        title: param.title,
        content: param.content,
        inProgress: false,
      };
      await http
        .post("/livecoop", room, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
          commit("SET_ROOM_ID", data.liveCoopId);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async deleteCoopRoom({ commit, getters }, id) {
      await http
        .delete("/livecoop/" + id, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getCoopRooms({ commit, getters }) {
      http
        .get("/livecoop/list", {
          headers: getters.authHeader,
        })
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
    async getDetailCoopRoom({ commit, getters }, roomId) {
      await http
        .get("livecoop/" + roomId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          commit("SET_ROOM", data);
          console.log("방상세정보가져오기 성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async CreateCoopMember({ commit, getters }, data) {
      console.log(commit);
      return await new Promise((res, rej) => {
        http
          .post("livecoop/member", data, {
            headers: getters.authHeader,
          })
          .then(({ data }) => {
            console.log("방입장 성공");
            console.log(data);
            res(data);
          })
          .catch((e) => {
            console.log("에러: " + e);
            rej(e);
          });
      });
    },
    async getCoopMembers({ commit, getters }, liveCoopId) {
      await http
        .get("livecoop/member/list/" + liveCoopId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("방멤버 가져오기 성공");
          console.log(data);
          commit("SET_COOP_MEMBERS", data.liveCoopMembers);
          commit("SET_COOP_MEMBER_ID", data.myLiveCoopMemberId);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async deleteCoopMember({ commit, getters }, coopMemberId) {
      console.log(commit);
      await http
        .delete("livecoop/member/" + coopMemberId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("협력멤버 삭제 성공");
          console.log(data);
          commit("SET_COOP_MEMBER_ID", null);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
  },
  modules: {},
};
