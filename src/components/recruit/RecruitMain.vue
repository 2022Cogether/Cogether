<template>
  <div>
    <!-- 검색바 -->
    <div class="searchbar">
      <input class="input-search" type="text" v-model="state.tempText" />
      <button class="btn-search" @click="search">
        <font-awesome-icon
          class="searchicon"
          icon=" fa-solid fa-magnifying-glass"
        />
      </button>
    </div>
    <!-- 버튼 -->
    <div class="button-box d-flex justify-content-evenly">
      <button type="button" class="btn" @click="btnTab1">
        <font-awesome-icon icon="fa-solid fa-handshake" />
        프로젝트
      </button>
      <button type="button" class="btn" @click="btnTab2">
        <font-awesome-icon icon="fa-solid fa-pen-clip" />
        스터디
      </button>
      <button type="button" class="btn" @click="btnTab3">
        <font-awesome-icon icon="fa-regular fa-bookmark" />
        스크랩
      </button>
      <button type="button" class="btn" @click="btnTab4">
        <font-awesome-icon icon="fa-regular fa-bookmark" />
        내 글
      </button>
    </div>
    <!-- 기술스택 필터 -->
    <!-- <div class="d-flex">
      <div class="icon-container">
        <img
          src="@/assets/devicon/javascript-original.svg"
          alt="JS Icon"
          class="icons"
        />
      </div>
      <button class="plus-icon-box">
        <font-awesome-icon icon="fa-solid fa-plus" class="scra" />
      </button>
    </div> -->
    <!-- 프로젝트  -->
    <div v-if="state.tabState === 'project'">
      <div class="list-box">
        <PeopleList :searchText="state.searchText" :tabState="state.tabState" />
      </div>
      <div class="list-box">
        <TeamList :searchText="state.searchText" :tabState="state.tabState" />
      </div>
    </div>
    <!-- 스터디 -->
    <div v-if="state.tabState === 'study'">
      <StudyList :searchText="state.searchText" :tabState="state.tabState" />
    </div>
    <!-- 스크랩 -->
    <div v-if="state.tabState === 'scrap'">
      <div class="list-box">
        <PeopleList :searchText="state.searchText" :tabState="state.tabState" />
      </div>
      <div class="list-box">
        <TeamList :searchText="state.searchText" :tabState="state.tabState" />
      </div>
      <div class="list-box">
        <StudyList :searchText="state.searchText" :tabState="state.tabState" />
      </div>
    </div>
    <!-- 내가쓴글 -->
    <div v-if="state.tabState === 'my'">
      <div class="list-box">
        <PeopleList :searchText="state.searchText" :tabState="state.tabState" />
      </div>
      <div class="list-box">
        <TeamList :searchText="state.searchText" :tabState="state.tabState" />
      </div>
      <div class="list-box">
        <StudyList :searchText="state.searchText" :tabState="state.tabState" />
      </div>
    </div>
    <!-- 글쓰기 -->
    <button class="icon-body" @click="recruitCreate">
      <font-awesome-icon icon="fa-solid fa-address-book" class="book-icon" />
    </button>
  </div>
</template>

<script>
import { computed, reactive } from "vue";
import { useStore } from "vuex";
import router from "@/router";
import PeopleList from "@/components/recruit/projectPeople/PeopleList.vue";
import TeamList from "@/components/recruit/projectTeam/TeamList.vue";
import StudyList from "@/components/recruit/study/StudyList.vue";

export default {
  name: "RecruitMain",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const state = reactive({
      tabState: "project",
      tempText: null,
      searchText: null,
    });
    function btnTab1() {
      state.tabState = "project";
    }
    function btnTab2() {
      state.tabState = "study";
    }
    function btnTab3() {
      state.tabState = "scrap";
    }
    function btnTab4() {
      state.tabState = "my";
    }

    function recruitCreate() {
      router.push({ name: "RecruitCreate" });
    }
    function search() {
      state.searchText = state.tempText;
    }
    return {
      store,
      getters,
      state,
      btnTab1,
      btnTab2,
      recruitCreate,
      search,
      btnTab3,
      btnTab4,
    };
  },
  components: {
    PeopleList,
    TeamList,
    StudyList,
  },
};
</script>

<style scoped>
/* Searh Bar */
.searchbar {
  display: flex;
  background-color: white;
  border: 1px solid black;
  width: fit-content;
  height: 40px;
  border-radius: 10px;
  overflow: hidden;
  margin: 20px auto;
}

.input-search {
  justify-content: center;
  border: none;
}

.input-search:focus {
  outline: none;
}

.btn-search {
  border: none;
  background-color: inherit;
}

/* Buttons */
.button-box {
  padding: 10px;
}

.btn {
  color: #2a9d8f;
  border: 2px solid #2a9d8f;
}

.btn:hover {
  color: #fff;
  background-color: #2a9d8f;
}

/* Icon Containers */
.icon-container {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background-color: #d8f3dc;
  margin: 5px 0 15px;
}

.icons {
  display: block;
  width: 18px;
  height: 18px;
  margin: 7px auto;
}

.plus-icon-box {
  width: 25px;
  height: 25px;
  border-radius: 30%;
  background-color: #d8f3dc;
  margin: 8px 0 0 10px;
  border: 0;
}

.plus-icon-box:hover {
  background-color: #2a9d8f;
}

/* Boards */
h2 {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
}

/* Write Icon */
.icon-body {
  border: 0px;
  width: 60px;
  height: 60px;
  background-color: #2a9d8f;
  color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 5px;
  right: 25vw;
  box-shadow: 0px 3px rgba(0, 0, 0, 0.3);
}

.book-icon {
  font-size: 30px;
}

.list-box {
  margin-bottom: 50px;
}
</style>
