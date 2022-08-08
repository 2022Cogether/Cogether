<template>
  <h2>Project</h2>
  <ul class="list-group list-group-flush">
    <!-- 글 없음 -->
    <span
      class="noData"
      v-if="tabState != 'scrap' && getters.getProjectTeams.length == 0"
    >
      등록된 글이 없습니다.
    </span>
    <!-- 스크랩 탭 -->
    <span v-if="tabState == 'scrap'">
      <span v-if="!checkScrap()"> 스크랩된 글이 없습니다.</span>
      <span v-for="(projectTeam, i) in getters.getProjectTeams" :key="i">
        <span v-if="projectTeam.scrap">
          <TeamItem :projectTeam="projectTeam" />
        </span>
      </span>
    </span>
    <!-- 프로젝트 탭 -->
    <span v-else>
      <span v-for="(projectTeam, i) in getters.getProjectTeams" :key="i">
        <TeamItem :projectTeam="projectTeam" />
      </span>
    </span>
  </ul>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import TeamItem from "@/components/recruit/projectTeam/TeamItem.vue";
export default {
  name: "TeamList",
  props: ["searchText", "tabState"],
  setup() {
    const store = useStore();
    store.dispatch("getProjectTeams", 1); //test userid
    const getters = computed(() => store.getters);

    function checkScrap() {
      for (const item of getters.value.getProjectTeams) {
        if (item.scrap) return true;
      }
      return false;
    }

    return { store, getters, checkScrap };
  },
  components: {
    TeamItem,
  },
};
</script>

<style scoped>
.list-group {
  max-height: 500px;
  overflow: auto;
}

.noData {
  max-height: 200px;
  /* border-top: 2px solid black; */
  /* border-bottom: 2px solid black; */
  margin-bottom: 30px;
}

::-webkit-scrollbar {
  display: none;
}
</style>
