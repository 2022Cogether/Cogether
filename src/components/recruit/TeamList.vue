<template>
  <h2>Project</h2>
  <ul class="list-group list-group-flush">
    <TeamItem />
    <TeamItem />
    <TeamItem />
    <TeamItem />
    <TeamItem />
  </ul>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import TeamItem from "@/components/recruit/TeamItem.vue";
export default {
  name: "TeamList",
  props: ["searchText"],
  setup() {
    const store = useStore();
    store.dispatch("getProjectTeams");
    const getters = computed(() => store.getters);
    return { store, getters };
  },
  components: {
    TeamItem,
  },
};
</script>

<style>
.list-group {
  max-height: 500px;
  overflow: auto;
}

::-webkit-scrollbar {
  display: none;
}
</style>
