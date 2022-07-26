<template>
  <div class="body">
    <nav-bar />
    <div class="row main-area">
      <left-bar class="col-3" />
      <div class="col-6"></div>
      <right-bar class="col-3" />
    </div>
    <router-view />
  </div>
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";
import LeftBar from "@/components/common/LeftBar.vue";
import RightBar from "@/components/common/RightBar.vue";

export default {
  name: "App",
  components: {
    NavBar,
    LeftBar,
    RightBar,
  },
};
</script>

<style>
.body {
  font-family: "Noto Sans KR", sans-serif;
  background-color: #fafafa;
}

.main-area {
  position: fixed;
  top: 7vh;
  height: 90vh;
}

nav-bar {
  height: 10vh;
}
</style>
