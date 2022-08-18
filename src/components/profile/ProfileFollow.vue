<template>
  <div class="modal d-flex justify-content-center" @click="closeModal">
    <div class="modal-card">
      <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
        <li class="nav-item" role="presentation">
          <button
            class="nav-link active"
            id="pills-followings-tab"
            data-bs-toggle="pill"
            data-bs-target="#pills-followings"
            type="button"
            role="tab"
            aria-controls="pills-followings"
            aria-selected="true"
          >
            Followings
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button
            class="nav-link"
            id="pills-followers-tab"
            data-bs-toggle="pill"
            data-bs-target="#pills-followers"
            type="button"
            role="tab"
            aria-controls="pills-followers"
            aria-selected="false"
          >
            Followers
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button
            class="nav-link"
            id="pills-nicksearch-tab"
            data-bs-toggle="pill"
            data-bs-target="#pills-nicksearch"
            type="button"
            role="tab"
            aria-controls="pills-nicksearch"
            aria-selected="false"
          >
            Nick Search
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button
            class="nav-link"
            id="pills-emailsearch-tab"
            data-bs-toggle="pill"
            data-bs-target="#pills-emailsearch"
            type="button"
            role="tab"
            aria-controls="pills-emailsearch"
            aria-selected="false"
          >
            Email Search
          </button>
        </li>
      </ul>
      <div class="tab-content" id="pills-tabContent">
        <div
          class="tab-pane fade show active"
          id="pills-followings"
          role="tabpanel"
          aria-labelledby="pills-followings-tab"
        >
          <ul v-for="following in followingList" :key="following.id">
            <!--router-link
              class="profile-data-list"
              style="cursor: pointer"
              :key="this.$route.path"
              :to="{ name: 'profile', params: { userId: following.id } }"
            -->
            <div
              class="profile-data-list"
              style="cursor: pointer"
              @click="goOut(following.id)"
            >
              {{ following.email }}
            </div>
            <font-awesome-icon
              v-if="isMyProfile"
              icon="fa-solid fa-rectangle-xmark"
              @click="unfollow(following.id, true)"
              style="cursor: pointer; margin-left: 2vw"
            />
          </ul>
        </div>
        <div
          class="tab-pane fade"
          id="pills-followers"
          role="tabpanel"
          aria-labelledby="pills-followers-tab"
        >
          <ul v-for="follower in followerList" :key="follower.id">
            <div
              class="profile-data-list"
              style="cursor: pointer"
              @click="goOut(follower.id)"
            >
              {{ follower.email }}
            </div>
            <font-awesome-icon
              v-if="follower.id == store.getters.getLoginUserId"
              icon="fa-solid fa-rectangle-xmark"
              @click="unfollow(props.userId, false)"
              style="cursor: pointer; margin-left: 2vw"
            />
          </ul>
        </div>

        <div
          class="tab-pane fade"
          id="pills-nicksearch"
          role="tabpanel"
          aria-labelledby="pills-nicksearch-tab"
        >
          <!-- 검색바 -->
          <div class="search-bar-block">
            <div class="search-bar">
              <input
                class="input-search"
                type="text"
                v-model="searchWordNick"
                @keyup.enter.prevent="onSubmitNick"
              />
              <button @click="onSubmitNick" class="btn-search">
                <font-awesome-icon
                  icon="fa-solid fa-magnifying-glass"
                  class="icon-search"
                />
              </button>
            </div>
          </div>

          <ul v-for="user in userListNick" :key="user.id">
            <div
              class="profile-data-list"
              style="cursor: pointer"
              @click="goOut(user.id)"
            >
              {{ user.nickName }}
            </div>
          </ul>
        </div>

        <div
          class="tab-pane fade"
          id="pills-emailsearch"
          role="tabpanel"
          aria-labelledby="pills-emailsearch-tab"
        >
          <!-- 검색바 -->
          <div class="search-bar-block">
            <div class="search-bar">
              <input
                class="input-search"
                type="text"
                v-model="searchWordEmail"
                @keyup.enter.prevent="onSubmitEmail"
              />
              <button @click="onSubmitEmail" class="btn-search">
                <font-awesome-icon
                  icon="fa-solid fa-magnifying-glass"
                  class="icon-search"
                />
              </button>
            </div>
          </div>

          <ul v-for="user in userListEmail" :key="user.id">
            <div
              class="profile-data-list"
              style="cursor: pointer"
              @click="goOut(user.id)"
            >
              {{ user.email }}
            </div>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { computed, ref } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import router from "@/router";
// import http from "@/api/http";
// import { useRoute } from "vue-router";

export default {
  name: "ProfileFollow",
  props: ["userId"],
  emits: ["closeModal", "minusFollower", "minusFollowing"],
  setup(props, { emit }) {
    const store = useStore();
    // const route = useRoute();
    const isMyProfile = computed(() => {
      return props.userId == store.getters.getLoginUserId;
    });
    const route = useRoute();
    const myId = store.getters.getLoginUserId;

    store.dispatch("fetchFollowingList", props.userId);
    store.dispatch("fetchFollowerList", props.userId);
    store.dispatch("findNickUser", "");
    store.dispatch("findEmailUser", "");

    // 리스트 받아오기
    let followingList = computed(() => {
      return store.getters.getMyFollowingList;
    });
    let followerList = computed(() => {
      return store.getters.getMyFollowerList;
    });
    let userListNick = computed(() => {
      return store.getters.getUserListNick;
    });
    let userListEmail = computed(() => {
      return store.getters.getUserListEmail;
    });

    // 모달 바깥을 클릭하면 모달을 닫게 하는 함수
    const closeModal = (event) => {
      if (
        !document
          .querySelector(".modal")
          .querySelector("." + event.target.className.split(" ")[0]) || // 클릭한 박스의 클래스가 modal-card라는 클래스의 하위 클래스인지 아닌지
        event.target.tagName == "LI"
      ) {
        emit("closeModal");
      }
    };

    // 언팔로우
    const unfollow = async (userId, isFromFollowing) => {
      console.log(userId);
      await store.dispatch("unfollow", userId);
      if (isFromFollowing) {
        await emit("minusFollowing");
        emit("closeModal");
      } else {
        await emit("minusFollower");
        emit("closeModal");
      }
    };

    // 프로필로
    const goProfile = (userID) => {
      router.push({
        name: "profile",
        params: { userId: userID },
      });
    };

    const goOut = (id) => {
      // window.open("http://localhost:8080/#/profile/" + id);
      router.go();
      router.push({
        name: "profile",
        params: { userId: id },
      });
    };

    // 검색어
    const searchWordNick = ref("");
    const searchWordEmail = ref("");

    const onSubmitNick = async () => {
      await store.dispatch("findNickUser", searchWordNick.value);
      console.log(userListNick.value);
    };
    const onSubmitEmail = () => {
      store.dispatch("findEmailUser", searchWordEmail.value);
    };

    return {
      store,
      props,
      route,
      myId,

      searchWordEmail,
      searchWordNick,
      onSubmitNick,
      onSubmitEmail,
      userListNick,
      userListEmail,

      isMyProfile,
      followingList,
      followerList,
      closeModal,
      unfollow,
      goProfile,
      goOut,
    };
  },
};
</script>

<style lang="scss" scoped>
.modal-card {
  background-color: white;
  width: 40vw;
  min-width: 250px;
  margin-top: 10vh;
  height: 80vh;
}

.search-bar-block {
  width: 100%;
  height: 40px;
  display: flex;
  justify-content: center;
}

.search-bar {
  border: 2px solid black;
  display: inline-block;
  width: 50%;
  border-radius: 10px;
}

.btn-search {
  width: 10%;
  border: 0px;
  border-radius: 10px;
  position: relative;
  top: 5%;
  left: 2%;
}

.input-search {
  width: 85%;
  border: 0px;
  border-radius: 10px 0px 0px 10px;

  padding-right: 3%;
  margin-right: 1%;
  line-height: 35px;
}

.icon-search {
  font-size: 1.5rem;
}

input:focus {
  outline: none;
}

a {
  text-decoration: none;
}

.nav-link {
  color: white !important;
  background-color: #2a9d8f !important;
  border-color: white !important;
}

.btn:active,
.active {
  color: #2a9d8f !important;
  background-color: white !important;
  border-color: white !important;
}
</style>
