<template>
  <nav-bar />
  <router-view />
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";

export default {
  name: "App",
  components: {
    NavBar,
  },
};
</script>
<style lang="scss"></style>
