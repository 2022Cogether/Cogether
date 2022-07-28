export const chatStore = {
  state: {
    //변수 선언
    isShowChat: false,
    chatUser: null,
  },
  getters: {
    //변수 호출
    getIsChatShow(state) {
      return state.isShowChat;
    },
    getChatUser(state) {
      return state.chatUser;
    },
  },
  mutations: {
    //변수값 수정
    SET_IS_CHAT_SHOW(state, isShowChat) {
      state.isShowChat = isShowChat;
    },
    SET_CHAT_USER(state, chatUser) {
      state.chatUser = chatUser;
    },
  },
  actions: {
    //함수동작
  },
  modules: {},
};
