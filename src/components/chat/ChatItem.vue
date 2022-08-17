<template>
  <div class="chat-item">
    <div class="chat-profile">
      <a href="#">
        <img class="chat-picture" src="@/assets/logo.png" alt="로고" />
      </a>
    </div>
    <div class="chat-content" @click="clickItem">
      <div class="chat-name-box">
        <div class="chat-name">{{ chatRoom.chatMemberName }}</div>
        <div v-if="chatRoom.messageCnt > 0" class="msgCntCircle">
          {{ chatRoom.messageCnt }}
        </div>
      </div>
      <div class="chat-text">{{ chatRoom.lastMessage }}</div>
    </div>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
export default {
  name: "ChatItem",
  props: ["chatRoom"],
  setup(props) {
    // const state = reactive({
    //   lastMessage: props.chatRoom.lastMessage,
    // });
    // if (state.lastMessage.length >= 9) {
    //   state.lastMessage = state.lastMessage.substring(0, 9) + "...";
    // }
    const store = useStore();
    const getters = computed(() => store.getters);
    function clickItem() {
      store.commit("SET_CHAT_USER_ID", props.chatRoom.chatMemberUserId);
      store.commit("SET_CHAT_ROOM", props.chatRoom);
    }
    return { clickItem, getters, store };
  },
};
</script>

<style scoped>
.chat-profile {
  float: left;
  width: 20%;
  margin-right: 3%;
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
  margin-bottom: 5px;
  display: flex;
  align-items: center;
}

.chat-name {
  display: inline-block;
}
.msgCntCircle {
  display: inline-block;
  margin-left: 5px;
  height: 1.1rem;
  width: 1.1rem;
  border-radius: 50%;
  color: white;
  text-align: center;
  line-height: 1.1rem;
  background-color: red;
  font-size: 0.7rem;
}
</style>
