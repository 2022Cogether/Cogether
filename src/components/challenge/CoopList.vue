<template>
  <div class="coop-room-box">
    <coop-item v-for="(i, index) in 10" :key="index" />
  </div>
</template>

<script>
import CoopItem from "@/components/challenge/CoopItem.vue";
export default {
  name: "CoopList",
  components: {
    CoopItem,
  },
};
</script>

<style></style>
