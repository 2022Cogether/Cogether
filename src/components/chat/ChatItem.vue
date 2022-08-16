<template>
  <div class="chat-item">
    <div class="chat-profile">
      <a href="#">
        <img class="chat-picture" src="@/assets/logo.png" alt="로고" />
      </a>
    </div>
    <div class="chat-content" @click="clickItem">
      <div class="chat-name">{{ chatRoom.chatMemberName }}</div>
      <div class="chat-text">{{ chatRoom.lastMessage }}</div>
      <p>{{ chatRoom.messageCnt }}</p>
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
</style>
