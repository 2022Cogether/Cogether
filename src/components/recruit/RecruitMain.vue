<template>
  <div>
    <div class="searchbar">
      <input class="input-search" type="text" />
      <button class="btn-search">
        <font-awesome-icon
          class="searchicon"
          icon=" fa-solid fa-magnifying-glass"
        />
      </button>
    </div>
    <div class="button-box d-flex justify-content-evenly">
      <button type="button" class="btn" @click="onClickProject">
        <font-awesome-icon icon="fa-solid fa-handshake" />
        프로젝트
      </button>
      <button type="button" class="btn" @click="onClickStudy">
        <font-awesome-icon icon="fa-solid fa-pen-clip" />
        스터디
      </button>
      <button type="button" class="btn">
        <font-awesome-icon icon="fa-regular fa-bookmark" />
        스크랩
      </button>
    </div>
    <div class="d-flex">
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
    </div>
    <div v-show="currComp === 'project'">
      <div>
        <h2>People</h2>
        <PeopleList />
      </div>
      <div>
        <h2>Project</h2>
        <TeamList />
      </div>
    </div>
    <div v-show="currComp === 'study'">
      <div>
        <h2>Study</h2>
        <StudyList />
      </div>
    </div>
  </div>
  <a href="#/recruit/create" class="write-icon-box">
    <font-awesome-icon
      icon="fa-solid fa-plus"
      class="plus-icon align-self-start"
    />
    <font-awesome-icon icon="fa-solid fa-address-book" class="book-icon" />
  </a>
  <div>
    <h2>Test</h2>
    <a href="javacript:;" @click="getList">Test</a>
  </div>
</template>

<script>
import PeopleList from "@/components/recruit/PeopleList.vue";
import TeamList from "@/components/recruit/TeamList.vue";
import StudyList from "@/components/recruit/StudyList.vue";
import axios from "axios";

export default {
  name: "RecruitMain",
  data() {
    return {
      currComp: "project",
      list: [],
    };
  },
  components: {
    PeopleList,
    TeamList,
    StudyList,
  },
  mounted() {
    this.getList();
  },
  methods: {
    onClickProject() {
      this.currComp = "project";
    },
    onClickStudy() {
      this.currComp = "study";
    },
    async getList() {
      this.list = await this.api(
        "https://21af139c-67f3-4c14-9e7d-69f6a8cb75b9.mock.pstmn.io/",
        "get",
        {}
      );
      console.log(this.list);
    },
    async api(url, method, data) {
      return (
        await axios({
          methods: method,
          url: url,
          data: data,
        }).catch((e) => {
          console.log(e);
        })
      ).data;
    },
  },
};
</script>

<style scoped>
/* Searh Bar */
.searchbar {
  border: 3px solid black;
  width: fit-content;
  height: 40px;
  border-radius: 10px;
  overflow: hidden;
  margin: 20px auto;
  padding-top: 3px;
}

.input-search {
  border: none;
}

.input-search:focus {
  outline: none;
}

.btn-search {
  border: none;
  box-shadow: none;
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
.write-icon-box {
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

.plus-icon {
  font-size: 12px;
  padding-top: 10px;
}

.book-icon {
  font-size: 30px;
}
</style>
