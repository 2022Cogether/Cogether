<template>
  <div class="box-chat">
    <div class="box-talk">
      <div v-for="(item, idx) in state.recvList" :key="idx" class="chat">
        <div v-if="item.sendUserId == state.userId" class="my">
          <div class="myimg chat-profile">
            <img class="chat-picture" :src="item.sendUserImg" alt="로고" />
          </div>
          <div class="mychat">
            {{ item.message }}
          </div>
        </div>
        <div v-else class="other">
          <div class="otherimg chat-profile">
            <img class="chat-picture" :src="item.sendUserImg" alt="로고" />
          </div>
          <div class="otherchat">
            {{ item.message }}
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="inputbar">
    <textarea
      class="input"
      v-model="state.tempMsg"
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
  props: ["room"],
  setup(props) {
    const store = useStore();
    const getters = computed(() => store.getters);
    store.dispatch("getCoopChatList", props.room.chatRoomId);
    const state = reactive({
      chatRoomId: props.room.chatRoomId,
      userId: getters.value.getLoginUserId,
      message: "",
      tempMsg: "",
      recvList: computed(() => getters.value.getCoopRecvList),
    });
    function send() {
      console.log("송신메세지:" + state.message);
      if (
        getters.value.getStompClientCoopChat &&
        getters.value.getStompClientCoopChat.connected
      ) {
        const data = {
          chatRoomId: state.chatRoomId,
          sendUserId: state.userId,
          message: state.message,
        };
        getters.value.getStompClientCoopChat.send(
          "/receive",
          JSON.stringify(data),
          {}
        );
      }
    }

    function sendMessage(e) {
      state.message = state.tempMsg;
      if (
        (e.type == "click" && state.message.trim() != "") ||
        (!e.shiftKey && e.keyCode === 13 && state.message.trim() != "")
      ) {
        send();
        state.message = "";
      }
    }

    function initMessage(e) {
      if (!e.shiftKey && e.keyCode == 13) {
        console.log("초기화");
        state.tempMsg = "";
      }
    }

    return { send, sendMessage, initMessage, state };
  },
};
</script>

<style scoped>
.box-chat {
  height: calc(60vh - 70px);
  padding: 10px;
  width: 100%;
  background-color: white;
}

.inputbar {
  margin-top: 10px;
  width: 100%;
  background-color: white;
}

.input-submit {
  float: right;
  border: 0px;
  background-color: transparent;
  width: 30px;
}

.input {
  border: 0px;
  width: calc(100% - 30px);
  max-height: 120px;
  resize: none;
  overflow-y: hidden;
}

.input:focus {
  outline: none;
}

.mychat {
  float: right;
  background-color: yellow;
  border-radius: 20px;
  padding: 7px;
  margin-bottom: 5px;
}

.myimg {
  float: right;
  margin-left: 5px;
}

.otherchat {
  float: left;
  background-color: #c1ebe6;
  border-radius: 20px;
  padding: 7px;
  margin-bottom: 8px;
}

.otherimg {
  float: left;
  margin-right: 5px;
}

.box-talk {
  max-height: calc(60vh - 90px);
  overflow: auto;
  display: flex;
  flex-direction: column-reverse;
}
.box-talk::-webkit-scrollbar {
  display: none;
}

.chat-profile {
  width: 50px;
  height: 50px;

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

.chat {
  margin-bottom: 8px;
}
</style>
