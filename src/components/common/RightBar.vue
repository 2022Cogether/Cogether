<template>
  <div class="right-bar">
    <button class="icon-body" @click="showChatList">
      <font-awesome-icon icon="fa-solid fa-comments" class="chat-icon" />
    </button>
    <div v-if="getters.getIsChatShow" class="chat-body">
      <div v-if="getters.getChatUser == null" class="chat-content">
        <chat-item v-for="(i, index) in 20" :key="index" />
      </div>
      <div v-else class="chat-content">
        <div class="chat-nav">
          <div class="chat-profile">
            <a href="#">
              <img class="chat-picture" src="@/assets/logo.png" alt="로고" />
            </a>
          </div>
          <div class="chat-name">이름</div>
          <button class="btn-exit" @click="exit">
            <font-awesome-icon icon="fa-solid fa-arrow-left" />
          </button>
        </div>
        <hr />
        <div class="box-talk">ss</div>
        <div class="inputbar">
          <textarea class="input" cols="30" rows="4"></textarea>
          <button class="input-submit">
            <font-awesome-icon icon="fa-solid fa-paper-plane" />
          </button>
        </div>
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
    function exit() {
      store.commit("SET_CHAT_USER", null);
    }
    return { showChatList, store, getters, exit };
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

.chat-content {
  height: 100%;
  widows: 100%;
  overflow: auto;
}

::-webkit-scrollbar {
  display: none;
}

.chat-profile {
  float: left;
  width: 15%;
  margin-right: 3%;
}

.chat-picture {
  border-radius: 70%;
  border: 1px solid #dbdbdb;
  margin: 3%;
  width: 100%;
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
</style>
