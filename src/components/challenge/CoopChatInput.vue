<template>
  <div class="box-chat">
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
    const state = reactive({
      chatRoomId: props.room.chatRoomId,
      userId: getters.value.getLoginUserId,
      message: "",
      tempMsg: "",
      recvList: computed(() => getters.value.getRecvList),
    });
    store.dispatch("getChatList", state.chatRoomId);
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

.box-talk {
  max-height: calc(60vh - 90px);
  overflow: auto;
  display: flex;
  flex-direction: column-reverse;
}
.box-talk::-webkit-scrollbar {
  display: none;
}
</style>
