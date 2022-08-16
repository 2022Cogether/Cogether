<template>
  <div class="right-bar">
    <!-- 채팅아이콘 -->
    <button class="icon-body" @click="showChatList">
      <font-awesome-icon icon="fa-solid fa-comments" class="chat-icon" />
    </button>
    <!-- 채팅 열기 -->
    <div v-if="getters.getIsChatShow" class="chat-body">
      <!-- 채팅방 리스트 보이기 (톡방 들어가기 전) -->
      <div v-if="getters.getChatRoom == null" class="chat-content">
        <span v-for="(chatRoom, i) in getters.getRoomList" :key="i">
          <chat-item :chatRoom="chatRoom" />
        </span>
      </div>
      <!-- 톡방 들어간 후 -->
      <div v-else class="chat-content">
        <chat-room />
      </div>
    </div>
  </div>
</template>
<script>
import { computed } from "vue";
import { useStore } from "vuex";
import ChatItem from "@/components/chat/ChatItem.vue";
import ChatRoom from "@/components/chat/ChatRoom.vue";
export default {
  name: "RightBar",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    async function showChatList() {
      await store.dispatch("getChatRoomList", getters.value.getLoginUserId);
      store.commit("SET_CHAT_USER_ID", null);
      store.commit("SET_CHAT_ROOM", null);
      store.commit("SET_IS_CHAT_SHOW", !getters.value.getIsChatShow);
    }

    return { showChatList, store, getters };
  },
  components: {
    ChatItem,
    ChatRoom,
  },
};
</script>
<style scoped>
.right-bar {
  position: fixed;
  right: 0;
  height: 90vh;
}

.icon-body {
  border: 0px;
  width: 60px;
  height: 60px;
  background-color: #2a9d8f;
  color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 5px;
  right: 5px;
  box-shadow: 0px 3px rgba(0, 0, 0, 0.3);
}

.chat-icon {
  font-size: 30px;
}

.chat-body {
  padding: 1%;
  margin-left: 5%;
  margin-top: 5%;
  height: 95%;
  width: 90%;
  background-color: white;
  border-radius: 10px;
  border: 1px solid #bdbdbd;
  box-shadow: 3px 5px 3px -3px #bdbdbd;
}

.chat-content {
  height: 100%;
  width: 100%;
  overflow: auto;
}

::-webkit-scrollbar {
  display: none;
}
</style>
