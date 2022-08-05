<template>
  <ul class="list-group list-group-flush">
    <TeamItem />
    <TeamItem />
    <TeamItem />
    <TeamItem />
    <TeamItem />
  </ul>
</template>

<script>
import TeamItem from "@/components/recruit/TeamItem.vue";

export default {
  components: {
    TeamItem,
  },
};
</script>

<style>
.list-group {
  max-height: 300px;
  overflow: auto;
}

::-webkit-scrollbar {
  display: none;
}
</style>
