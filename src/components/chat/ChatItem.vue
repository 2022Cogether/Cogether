<template>
  <div class="chat-item">
    <div class="chat-profile">
      <a href="#">
        <img class="chat-picture" src="@/assets/logo.png" alt="로고" />
      </a>
    </div>
    <div class="chat-content" @click="clickItem">
      <div class="chat-name">이름</div>
      <div class="chat-text">내용</div>
    </div>
  </div>
</template>

<script>
import { computed, reactive } from "vue";
import { useStore } from "vuex";
export default {
  name: "ChatItem",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const state = reactive({
      chatUser: getters.value.chatUser,
    });
    function clickItem() {
      console.log("event");
      store.commit("SET_CHAT_USER", 1);
      console.log(getters.value.chatUser);
    }
    return { clickItem, getters, store, state };
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
