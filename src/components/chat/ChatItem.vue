<template>
  <div class="chat-item">
    <div class="chat-profile">
      <a href="#">
        <img class="chat-picture" src="@/assets/logo.png" alt="로고" />
      </a>
    </div>
    <div class="chat-content">
      <div class="chat-name">이름</div>
      <div class="chat-text">내용</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatItem",
  setup() {
    return {};
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
</style>
