<template>
  <h2>People</h2>
  <ul class="list-group list-group-flush">
    <!-- 글 없음 -->
    <span
      class="noData"
      v-if="tabState != 'scrap' && getters.getProjectPeople.length == 0"
    >
      등록된 글이 없습니다.
    </span>
    <!-- 스크랩 탭 -->
    <span v-if="tabState == 'scrap'">
      <span v-if="!checkScrap()"> 스크랩된 글이 없습니다.</span>
      <span v-for="(projectPerson, i) in getters.getProjectPeople" :key="i">
        <span
          v-if="
            searchText == null ||
            projectPerson.title.indexOf(searchText) != -1 ||
            projectPerson.userNickname.indexOf(searchText) != -1
          "
        >
          <span v-if="projectPerson.scrap">
            <PeopleItem :projectPerson="projectPerson" />
          </span>
        </span>
      </span>
    </span>
    <!-- 프로젝트 탭 -->
    <span v-else>
      <span v-for="(projectPerson, i) in getters.getProjectPeople" :key="i">
        <span
          v-if="
            searchText == null ||
            projectPerson.title.indexOf(searchText) != -1 ||
            projectPerson.userNickname.indexOf(searchText) != -1
          "
        >
          <PeopleItem :projectPerson="projectPerson" />
        </span>
      </span>
    </span>
  </ul>
</template>

<script>
import PeopleItem from "@/components/recruit/projectPeople/PeopleItem.vue";
import { computed } from "vue";
import { useStore } from "vuex";
export default {
  name: "PeopleList",
  props: ["searchText", "tabState"],
  setup() {
    const store = useStore();
    store.dispatch("getProjectPeople", 1); //test userid
    const getters = computed(() => store.getters);

    function checkScrap() {
      for (const item of getters.value.getProjectPeople) {
        if (item.scrap) return true;
      }
      return false;
    }
    return { store, getters, checkScrap };
  },
  components: {
    PeopleItem,
  },
};
</script>

<style scoped>
.list-group {
  max-height: 210px;
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
