<template>
  <div class="til-list">
    <TilMainItem v-for="(item, idx) in 20" :key="idx" v-bind:idx="idx" />
  </div>
  <!-- 나중에 TIL create창으로 URL 추가 -->
  <a href="">
    <button class="icon-body" @click="showChatList">
      <font-awesome-icon icon="fa-solid fa-pen-clip" class="pen-icon" />
    </button>
  </a>
</template>

<script>
import TilMainItem from "@/components/til/TilMainItem.vue";

export default {
  name: "TilMainList",
  components: {
    TilMainItem,
  },
};
</script>

<style scoped>
.til-list {
  margin-top: 5%;
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
  right: 25vw;
  box-shadow: 0px 3px rgba(0, 0, 0, 0.3);
}

.pen-icon {
  font-size: 30px;
}
</style>
