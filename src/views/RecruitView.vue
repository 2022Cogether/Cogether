<template>
  <div class="row">
    <div class="col-3"></div>
    <div class="col-6">
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
        <button type="button" class="btn btn-outline-primary">
          <font-awesome-icon icon="fa-solid fa-handshake" />
          프로젝트
        </button>
        <button type="button" class="btn btn-outline-primary">
          <font-awesome-icon icon="fa-solid fa-pen-clip" />
          스터디
        </button>
        <button type="button" class="btn btn-outline-primary">
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
      <div>
        <h2>People</h2>
        <PeopleList />
      </div>
      <div>
        <h2>Project</h2>
        <TeamList />
      </div>
    </div>
    <div class="col-3"></div>
  </div>
</template>

<script>
import PeopleList from "@/components/recruit/PeopleList.vue";
import TeamList from "@/components/recruit/TeamList.vue";

export default {
  components: {
    PeopleList,
    TeamList,
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
</style>
