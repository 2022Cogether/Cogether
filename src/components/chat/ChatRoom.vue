<template>
  <!-- 톡방 들어간 후 -->
  <div class="chat-nav">
    <!-- 프로필사진 -->
    <div class="chat-profile">
      <a :href="`/#/profile/` + getters.getChatRoom.chatMemberUserId">
        <img
          class="chat-picture"
          :src="getters.getChatRoom.chatMemberImage"
          alt="로고"
        />
      </a>
    </div>
    <!-- 이름 및 나가기 버튼-->
    <div class="chat-name">{{ getters.getChatRoom.chatMemberName }}</div>
    <button class="btn-exit" @click="exit">
      <font-awesome-icon icon="fa-solid fa-arrow-left" />
    </button>
  </div>
  <hr />
  <div class="box-talk">
    <div v-for="(item, idx) in state.recvList" :key="idx">
      <div v-if="item.sendUserId == state.userId" class="my">
        {{ item.message }}
      </div>
      <div v-else class="other">
        {{ item.message }}
      </div>
    </div>
  </div>
  <!-- 입력창 -->
  <div class="inputbar">
    <textarea
      class="input"
      v-model="state.message"
      cols="30"
      rows="4"
      @keypress="sendMessage"
      @keyup="initMessage"
    ></textarea>
    <button class="input-submit" @click="sendMessage">
      <font-awesome-icon icon="fa-solid fa-paper-plane" />
    </button>
  </div>
</template>

<script>
import { computed, reactive } from "vue";
import { useStore } from "vuex";
export default {
  name: "ChatRoom",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    store.dispatch("getChatList", getters.value.getChatRoom.chatRoomId);
    const state = reactive({
      chatRoomId: getters.value.getChatRoom.chatRoomId,
      userId: getters.value.getLoginUserId,
      message: "",
      recvList: computed(() => getters.value.getRecvList),
    });
    async function exit() {
      await store.dispatch("getChatRoomList", getters.value.getLoginUserId);
      store.commit("SET_CHAT_USER_ID", null);
      store.commit("SET_CHAT_ROOM", null);
    }

    function send() {
      console.log("송신메세지:" + state.message);
      if (
        getters.value.getStompClient &&
        getters.value.getStompClient.connected
      ) {
        const msg = {
          chatRoomId: state.chatRoomId,
          sendUserId: state.userId,
          message: state.message,
        };
        getters.value.getStompClient.send("/receive", JSON.stringify(msg), {});
      }
    }

    function sendMessage(e) {
      if (
        (e.type == "click" &&
          state.userName !== "" &&
          state.message.trim() != "") ||
        (!e.shiftKey &&
          e.keyCode === 13 &&
          state.userName !== "" &&
          state.message.trim() != "")
      ) {
        send();
        state.message = "";
      }
    }

    function initMessage(e) {
      if (!e.shiftKey && e.keyCode == 13) {
        console.log("초기화");
        state.message = "";
      }
    }
    return { exit, state, sendMessage, send, getters, initMessage };
  },
};
</script>

<style scoped>
.box-talk {
  max-height: 450px;
  overflow: auto;
  /* -webkit-flex-direction: column-reverse; */
  display: flex;
  flex-direction: column-reverse;
}
.box-talk::-webkit-scrollbar {
  display: none;
}

.chat-content {
  float: left;
  width: 77%;
}

.chat-content:hover {
  cursor: pointer;
}

.chat-picture {
  border-radius: 70%;
  border: 1px solid #dbdbdb;
  margin: 3%;
  width: 100%;
}

.chat-item {
  display: flex;
  align-items: center;
}

.chat-profile {
  float: left;
  width: 50px;
  height: 50px;
  margin-right: 3%;
  overflow: hidden;
}

.chat-picture {
  border-radius: 70%;
  border: 1px solid #dbdbdb;
  margin: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.chat-name {
  float: left;
}

.btn-exit {
  border: 0px;
  background-color: transparent;
  float: right;
}

.chat-nav {
  display: inline-block;
  width: 100%;
}

hr {
  margin: 3px;
}

.box-talk {
  height: calc(100% - 180px);
}

.inputbar {
  border: 1px solid #bdbdbd;
  margin-top: 10px;
  width: 100%;
  background-color: white;
  border-radius: 10px;
}

.input {
  border-radius: 10px;
  border: 0px;
  width: calc(100% - 30px);
  max-height: 95px;
  resize: none;
}

.input:focus {
  outline: none;
}

.input-submit {
  float: right;
  border: 0px;
  background-color: transparent;
  width: 30px;
}

.my {
  float: right;
  background-color: yellow;
  border-radius: 20px;
  padding: 7px;
  margin-bottom: 5px;
}

.other {
  float: left;
  background-color: #c1ebe6;
  border-radius: 20px;
  padding: 7px;
  margin-bottom: 8px;
}
</style>
