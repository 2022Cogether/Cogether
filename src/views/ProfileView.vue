<template>
  <router-view :key="viewKey" @click="updateViewKey"></router-view>
</template>
<script>
export default {
  name: "ProfileView",
  data() {
    return {
      viewKey: 0,
    };
  },
  methods: {
    updateViewKey() {
      this.viewKey += 1;
    },
  },
};
</script>
<style></style>
