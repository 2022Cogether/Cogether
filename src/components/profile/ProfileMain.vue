<template>
  <!-- Til Craete 버튼 -->
  <router-link :to="{ name: 'TilCreate' }">
    <button class="icon-body">
      <font-awesome-icon icon="fa-solid fa-pen-clip" class="pen-icon" />
    </button>
  </router-link>
  <!-- Profile -->
  <router-link
    v-if="!isMyProfile"
    class="btn btn-success"
    sytle="opacity: 0.5"
    :to="{ name: 'profileEdit', params: { userId: this.$route.params.userId } }"
  >
    프로필 수정
  </router-link>
  <div class="profile-info-container1 d-flex flex-column align-items-center">
    <div class="profile-img-box">
      <img :src="profileUser.img_url" alt="profile image" class="profile-img" />
    </div>
    <div class="username-box d-flex">
      <img src="@/assets/gold_badge.png" alt="badge img" class="badge-img" />
      <h3 class="username">{{ profileUser.nickname }}</h3>
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
    <p class="user-introduction">{{ profileUser.intro }}</p>
    <div class="follow-box d-flex">
      <p class="follower">팔로워 1</p>
      <p class="follow">팔로우 10</p>
    </div>
    <div v-if="!isMyProfile" class="container mb-3">
      <div class="row d-flex justify-content-between">
        <div
          v-if="profileUser.isFollow"
          class="btn btn-success col-6"
          @click="unfollow"
        >
          언팔로우
        </div>
        <div v-else class="btn btn-success col-6" @click="follow">팔로우</div>
        <div class="btn btn-secondary col-6">메세지</div>
        <div class="btn btn-warning col-12 mt-3 text-white" @click="followOpen">
          팔로우/팔로잉 리스트
        </div>
      </div>
    </div>
    <div v-else class="container mb-3 d-flex justify-content-center">
      <div class="btn btn-success col-12 text-white" @click="followOpen">
        팔로우/팔로잉 리스트
      </div>
    </div>
    <ProfileFollow
      v-if="isFollowOpen"
      class="isModal"
      @closeModal="closeModal"
    />
  </div>
  <!-- Tech Stack -->
  <div class="techstack-container">
    <h4>사용하는 기술</h4>
    <div class="d-flex flex-wrap" sytle="min-height: 30px;">
      <div
        v-for="(lang, idx) in userLangSkills"
        :key="idx"
        class="techstack-box"
      >
        <img
          :src="
            'https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/' +
            lang +
            '-original.svg'
          "
          alt="techstack image"
          class="techstack-img"
        />
      </div>
    </div>
  </div>
  <!-- Webpage Link -->
  <div class="webpage-link-container">
    <h4>개인 웹페이지 링크</h4>
    <div class="d-flex flex-wrap">
      <div v-for="(webSmList, i) in webIconUrl" :key="i">
        <!-- vue3에서는 v-if가 v-for 보다 우선 순위 높다고 함 -->
        <div
          v-if="profileUser[webSmList[2]]"
          class="webpage-link-box"
          :onclick="'location.href=' + profileUser[webSmList[2]]"
        >
          <img
            :src="webSmList[1]"
            :alt="'webpage' + webSmList[0] + 'icon'"
            class="webpage-link-img"
          />
        </div>
        <div v-else class="webpage-link-box opacity-50">
          <img
            :src="webSmList[1]"
            :alt="'webpage' + webSmList[0] + 'icon'"
            class="webpage-link-img"
          />
        </div>
      </div>
    </div>
  </div>
  <!-- Search Bar -->
  <div class="searchbar">
    <input
      class="input-search"
      type="text"
      v-model="searchWord"
      @keyup.enter.prevent="onSubmit"
    />
    <button @click="onSubmit" class="btn-search">
      <font-awesome-icon
        icon="fa-solid fa-magnifying-glass"
        class="searchicon"
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
    <ProfileTil
      v-for="til in tilList"
      :key="til.pk"
      :til="til"
      @click="setNum(til.pk)"
    />
  </div>
  <TilDetail v-if="isTilOpen" class="isModal" />
</template>

<script>
<link
  rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
/>;
import ProfileTil from "./ProfileTil.vue";
import ProfileFollow from "./ProfileFollow.vue";
import TilDetail from "@/components/til/TilDetail.vue";

import { ref, getters, computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";

export default {
  name: "ProfileMain",
  components: {
    ProfileTil,
    TilDetail,
    ProfileFollow,
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    // const getters = computed(() => store.getters);
    const userId = route.params.userId;
    const isLoggedIn = store.getters.isLoggedIn;

    // TIL 검색 창
    const searchWord = ref("");
    const onSubmit = () => {
      const payload = {
        keyword: searchWord.value,
        userId: profileUser.id,
      };
      console.log(payload);
      store.dispatch("searchTil", payload);
    };

    // Skill set
    // if (!getters.value.getSkillSet) {
    //   store.dispatch("takeSkillSet");
    // }

    // 웹페이지 아이콘 url
    const webIconUrl = [
      [
        "github",
        "https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/github-original.svg",
        "git_url",
      ],
      [
        "velog",
        "https://velog.velcdn.com/images/velog/profile/9aa07f66-5fcd-41f4-84f2-91d73afcec28/green%20favicon.png",
        "velog_url",
      ],
      [
        "tistory",
        "data:image/png;base64,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",
        "tistory_url",
      ],
      ["notion", "https://img.icons8.com/ios/500/notion.png", "notion_url"],
      ["etc", "https://pic.onlinewebfonts.com/svg/img_229126.png", "etc_url"],
    ];

    // 내 프로필인지 아닌지 판단하고 알맞는 자료를 가져와 profile User 변수에 넣기
    const isMyProfile = store.getters.getLoginUserId == userId;

    if (!isMyProfile) {
      store.dispatch("fetchAnothertUser", userId);
    }
    const profileUser = isMyProfile
      ? store.getters.getCurrentUser
      : store.getters.getAnotherUser;

    // TilList 가져오기
    const getTilList = () => {
      store.dispatch("fetchTilList", { userId: userId });
    };

    // 페이지가 Created 될 때 list 가져옴
    getTilList();
    const tilList = store.getters.getTilList;

    // 모달 창
    const modalNum = computed(() => {
      return store.getters.getOpenTil;
    });
    const isTilOpen = computed(() => {
      return modalNum.value != -1;
    });
    const setNum = (tilNum) => {
      store.dispatch("fetchOpenTil", {
        tilId: tilNum,
        userId: store.getters.getCurrentUser,
      });
    };

    // 팔로우
    const follow = () => {
      const payload = {
        toID: store.getters.getLoginUserId,
        fromId: profileUser.id,
      };
      store.dispatch("follow", payload);
      if (getters.value.getBooleanValue) {
        profileUser.isFollow = !profileUser.isFollow;
      }
    };
    // 언팔로우
    const unfollow = () => {
      const payload = {
        toID: store.getters.getLoginUserId,
        fromId: profileUser.id,
      };
      store.dispatch("unfollow", payload);
      if (getters.value.getBooleanValue) {
        profileUser.isFollow = !profileUser.isFollow;
      }
    };

    // 팔로우 모달창 열기/닫기
    const isFollowOpen = ref(false);
    const followOpen = () => {
      isFollowOpen.value = true;
    };
    const closeModal = () => {
      isFollowOpen.value = false;
    };

    return {
      isLoggedIn,
      isMyProfile,
      profileUser,
      tilList,
      searchWord,
      onSubmit,
      webIconUrl,

      modalNum,
      isTilOpen,
      setNum,

      follow,
      unfollow,
      isFollowOpen,
      followOpen,
      closeModal,
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
  margin-right: 5px;
  margin-left: 5px;
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
  margin-right: 5px;
  margin-left: 5px;
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
/* TIL Create 아이콘 */
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

.pen-icon {
  font-size: 30px;
}

/* Modal 창 */
.isModal {
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
}
</style>
