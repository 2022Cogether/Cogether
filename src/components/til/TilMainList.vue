<template>
  <div class="til-list">
    <TilMainItem v-for="(item, idx) in 20" :key="idx" v-bind:idx="idx" />
  </div>
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

<style>
.til-list {
  margin-top: 5%;
}
</style>
