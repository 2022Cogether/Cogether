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
      </ul>
      <div class="tab-content" id="pills-tabContent">
        <div
          class="tab-pane fade show active"
          id="pills-followings"
          role="tabpanel"
          aria-labelledby="pills-followings-tab"
        >
          <ul v-for="following in followingList" :key="following.id">
            <router-link
              class="profile-data-list"
              style="cursor: pointer"
              :key="$route.fullPath"
              :to="{ name: 'profile', params: { userId: following.id } }"
            >
              {{ following.id }}: {{ following.email }}
            </router-link>
            <font-awesome-icon
              v-if="isMyProfile"
              icon="fa-solid fa-rectangle-xmark"
              @click="unfollow(following.id)"
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
            <router-link
              class="profile-data-list"
              style="cursor: pointer"
              :key="$route.fullPath"
              :to="{ name: 'profile', params: { userId: myId } }"
            >
              {{ follower.id }}: {{ follower.email }}
            </router-link>
            <font-awesome-icon
              v-if="follower.id == store.getters.getLoginUserId"
              icon="fa-solid fa-rectangle-xmark"
              @click="unfollow(follower.id)"
              style="cursor: pointer; margin-left: 2vw"
            />
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import router from "@/router";
// import { useRoute } from "vue-router";

export default {
  name: "ProfileFollow",
  props: ["userId"],
  emits: ["closeModal"],
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

    // 리스트 받아오기
    let followingList = computed(() => {
      return store.getters.getMyFollowingList;
    });
    let followerList = computed(() => {
      return store.getters.getMyFollowerList;
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
    const unfollow = (userId) => {
      store.dispatch("unfollow", userId);
      // if (store.getters.getBooleanValue) {
      //   followingList = followingList.filter(
      //     (following) => following.id != userId
      //   );
      // }
    };

    // 프로필로
    const goProfile = (userID) => {
      router.push({
        name: "profile",
        params: { userId: userID },
      });
    };

    return {
      store,
      props,
      route,
      myId,

      isMyProfile,
      followingList,
      followerList,
      closeModal,
      unfollow,
      goProfile,
    };
  },
};
</script>

<style lang="scss" scoped>
.modal-card {
  background-color: white;
  width: 70vw;
  height: auto;
}
</style>
