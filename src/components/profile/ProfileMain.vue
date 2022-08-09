<template>
  <!-- Profile -->
  <div class="profile-info-container1 d-flex flex-column align-items-center">
    <div class="profile-img-box">
      <img src="@/assets/logo.png" alt="profile image" class="profile-img" />
    </div>
    <div class="username-box d-flex">
      <img src="@/assets/gold_badge.png" alt="badge img" class="badge-img" />
      <h3 class="username">삐약</h3>
    </div>
  </div>
  <div class="exp-bar d-flex justify-content-center align-items-center">
    <p class="level">lv. 2</p>
    <div class="progress">
      <div
        class="progress-bar"
        role="progressbar"
        aria-label="Basic example"
        style="width: 50%"
        aria-valuenow="50"
        aria-valuemin="0"
        aria-valuemax="100"
      ></div>
    </div>
  </div>
  <div class="profile-info-container2 d-flex flex-column align-items-center">
    <p class="user-introduction">리액트 마스터가 되겠어!</p>
    <div class="follow-box d-flex">
      <p class="follower">팔로워 1</p>
      <p class="follow">팔로우 10</p>
    </div>
  </div>
  <!-- Tech Stack -->
  <div class="techstack-container">
    <h4>사용하는 기술</h4>
    <div class="techstack-box">
      <img
        src="@/assets/devicon/javascript-original.svg"
        alt="techstack image"
        class="techstack-img"
      />
    </div>
  </div>
  <!-- Webpage Link -->
  <div class="webpage-link-container">
    <h4>개인 웹페이지 링크</h4>
    <div class="webpage-link-box">
      <img
        src="@/assets/logo.png"
        alt="webpage link icon"
        class="webpage-link-img"
      />
    </div>
  </div>
  <!-- Search Bar -->
  <div class="searchbar">
    <input class="input-search" type="text" />
    <button class="btn-search">
      <font-awesome-icon
        class="searchicon"
        icon=" fa-solid fa-magnifying-glass"
      />
    </button>
  </div>
  <!-- TIL -->
  <router-link
    :to="{
      name: 'TilList',
      params: { userId: this.$route.params.userId },
    }"
    class="h4 til-title"
    >My TIL
  </router-link>
  <div class="til-container d-flex flex-wrap justify-content-evenly">
    <ProfileTil v-for="til in tilList" :key="til.pk" :til="til" />
  </div>
</template>

<script>
import ProfileTil from "./ProfileTil.vue";
import { useStore } from "vuex";

export default {
  name: "ProfileMain",
  components: {
    ProfileTil,
  },
  setup() {
    const store = useStore();
    const currentUser = store.getters.getCurrentUser;

    const getTilList = () => {
      store.dispatch("fetchTilList", { userId: currentUser.id });
    };

    // 페이지가 Created 될 때 list 가져옴
    getTilList();

    const tilList = store.getters.getTilList;

    return {
      tilList,
    };
  },
};
</script>

<style scoped>
/* Profile */
.profile-info-container1 {
  margin-top: 40px;
}

.profile-img-box {
  width: 120px;
  height: 120px;
  border-radius: 70%;
  overflow: hidden;
  border: 5px solid gold;
}

.profile-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.badge-img {
  width: 25px;
  height: 25px;
}

.username-box {
  margin: 10px 0;
}

.username {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  margin-left: 10px;
}

.level {
  margin: 0;
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  font-size: 13px;
  margin-right: 10px;
}

.progress {
  width: 300px;
  height: 10px;
}

.progress-bar {
  background-color: #2a9d8f;
}

.profile-info-container2 {
  margin-top: 10px;
  font-weight: 700;
}

.follower {
  margin-right: 20px;
}

/* Tech Stack */
.techstack-container {
  margin: 0 0 30px 20px;
}

h4 {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  font-size: 16px;
}

.techstack-box {
  width: 30px;
  height: 30px;
  border-radius: 70%;
  overflow: hidden;
}

.techstack-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Webpage Link */
.webpage-link-container {
  margin: 0 0 30px 20px;
}

.webpage-link-box {
  width: 30px;
  height: 30px;
  border-radius: 70%;
  overflow: hidden;
}

.webpage-link-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

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

/* TIL */
.til-title {
  margin-left: 30px;
}
</style>
