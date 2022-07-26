<template>
  <div class="body">
    <nav-bar />
    <div class="row main-area">
      <left-bar class="left col-3" />
      <div class="center col-6">
        <router-view />
      </div>
      <right-bar class="right col-3" />
    </div>
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
}

.main-area {
  padding-top: 7vh;
  background-color: #fafafa;
}

.left,
.right {
  position: fixed;
}

nav-bar {
  height: 10vh;
}

.center {
  margin-left: 25vw;
}
</style>
