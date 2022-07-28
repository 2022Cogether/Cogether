export const chatStore = {
  state: {
    //변수 선언
    isShowChat: false,
  },
  getters: {
    //변수 호출
    getIsChatShow(state) {
      return state.isShowChat;
    },
  },
  mutations: {
    //변수값 수정
    SET_IS_CHAT_SHOW(state, isShowChat) {
      state.isShowChat = isShowChat;
    },
  },
  actions: {},
  modules: {},
};
