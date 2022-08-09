<template>
  <h2>Study</h2>
  <ul class="list-group list-group-flush">
    <!-- 글 없음 -->
    <span
      class="noData"
      v-if="tabState != 'scrap' && getters.getStudyTeams.length == 0"
    >
      등록된 글이 없습니다.
    </span>
    <!-- 스크랩 탭 -->
    <span v-if="tabState == 'scrap'">
      <span v-if="!checkScrap()"> 스크랩된 글이 없습니다.</span>
      <span v-for="(studyTeam, i) in getters.getStudyTeams" :key="i">
        <span
          v-if="
            searchText == null ||
            studyTeam.title.indexOf(searchText) != -1 ||
            studyTeam.userNickname.indexOf(searchText) != -1
          "
        >
          <span v-if="studyTeam.scrap">
            <StudyItem :studyTeam="studyTeam" />
          </span>
        </span>
      </span>
    </span>
    <!-- 스터디 탭 -->
    <span v-else>
      <span v-for="(studyTeam, i) in getters.getStudyTeams" :key="i">
        <span
          v-if="
            searchText == null ||
            studyTeam.title.indexOf(searchText) != -1 ||
            studyTeam.userNickname.indexOf(searchText) != -1
          "
        >
          <StudyItem :studyTeam="studyTeam" />
        </span>
      </span>
    </span>
  </ul>
</template>

<script>
import StudyItem from "@/components/recruit/study/StudyItem.vue";
import { computed } from "vue";
import { useStore } from "vuex";
export default {
  name: "StudyList",
  props: ["searchText", "tabState"],
  setup() {
    const store = useStore();
    store.dispatch("getStudyTeams", 1); //test userid
    const getters = computed(() => store.getters);

    function checkScrap() {
      for (const item of getters.value.getStudyTeams) {
        if (item.scrap) return true;
      }
      return false;
    }

    return { store, getters, checkScrap };
  },
  components: {
    StudyItem,
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
