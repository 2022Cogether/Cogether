import http from "@/api/http.js";

export const chatStore = {
  state: {
    //변수 선언
    isShowChat: false,
    chatRoom: null,
    chatUserId: null,
    stompClient: null,
    recvList: [],
    roomList: [],
  },
  getters: {
    //변수 호출
    getIsChatShow(state) {
      return state.isShowChat;
    },
    getChatRoom(state) {
      return state.chatRoom;
    },
    getChatUserId(state) {
      return state.chatUserId;
    },
    getStompClient(state) {
      return state.stompClient;
    },
    getRecvList(state) {
      return state.recvList;
    },
    getRoomList(state) {
      return state.roomList;
    },
  },
  mutations: {
    //변수값 수정
    SET_IS_CHAT_SHOW(state, isShowChat) {
      state.isShowChat = isShowChat;
    },
    SET_CHAT_ROOM(state, chatRoom) {
      state.chatRoom = chatRoom;
    },
    SET_CHAT_USER_ID(state, chatUserId) {
      state.chatUserId = chatUserId;
    },
    SET_STOMP_CLIENT(state, stompClient) {
      state.stompClient = stompClient;
    },
    SET_RECV_LIST(state, recvList) {
      state.recvList = recvList;
    },
    APPEND_RECV_LIST(state, data) {
      state.recvList.unshift(data);
    },
    SET_ROOM_LIST(state, roomList) {
      state.roomList = roomList;
    },
  },
  actions: {
    //함수동작
    async getChatRoomList({ commit, getters }) {
      await http
        .get("chat/room/list", {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("채팅방리스트받기성공");
          console.log(data);
          commit("SET_ROOM_LIST", data.chatRooms);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async createChatRoom({ commit, getters }, userId) {
      console.log("채팅방만들기");
      console.log(commit);
      console.log(userId);
      await http
        .post(
          "chat/room",
          { chatMemberUserId: userId },
          {
            headers: getters.authHeader,
          }
        )
        .then(({ data }) => {
          console.log("채팅방만들기성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async getChatList({ commit, getters }, roomId) {
      console.log(commit);
      await http
        .get("chat/list/" + roomId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("채팅리스트가져오기성공");
          console.log(data);
          commit("SET_RECV_LIST", data.chats);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async sendRecentReadMsg({ commit, getters }, sendData) {
      console.log(commit);
      await http
        .patch("chat/member", sendData, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("최근읽은메세지전송성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
  },
  modules: {},
};
