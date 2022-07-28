<template>
  <div class="right-bar">
    <button class="icon-body" @click="showChatList">
      <font-awesome-icon icon="fa-solid fa-comments" class="chat-icon" />
    </button>
    <div v-if="getters.getIsChatShow" class="chat-body">
      <div class="chat-list-content">
        <chat-item v-for="(i, index) in 20" :key="index" />
      </div>
    </div>
  </div>
</template>
<script>
import { computed } from "vue";
import { useStore } from "vuex";
import ChatItem from "@/components/chat/ChatItem.vue";
export default {
  name: "RightBar",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    function showChatList() {
      store.commit("SET_IS_CHAT_SHOW", !getters.value.getIsChatShow);
    }
    return { showChatList, store, getters };
  },
  components: {
    ChatItem,
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

.chat-list-content {
  height: 100%;
  widows: 100%;
  overflow: auto;
}

::-webkit-scrollbar {
  display: none;
}
</style>
