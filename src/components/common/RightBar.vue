<template>
  <div class="right-bar">
    <div class="icon-body">
      <font-awesome-icon icon="fa-solid fa-comments" class="chat-icon" />
    </div>
  </div>
</template>
<script>
export default {
  name: "RightBar",
};
</script>
<style scoped>
.right-bar {
  position: fixed;
  right: 0;
  height: 90vh;
}

.icon-body {
  width: 80px;
  height: 80px;
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
  font-size: 35px;
}
</style>
