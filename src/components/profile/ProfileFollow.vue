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
            <font-awesome-icon
              icon="fa-solid fa-rectangle-xmark"
              @click="unfollow(following.id)"
            />
            <li @click="goProfile(following.id)" style="cursor: pointer">
              {{ following.id }}: {{ following.email }}
            </li>
          </ul>
        </div>
        <div
          class="tab-pane fade"
          id="pills-followers"
          role="tabpanel"
          aria-labelledby="pills-followers-tab"
        >
          <ul v-for="follower in followerList" :key="follower.id">
            <li @click="goProfile(follower.id)" style="cursor: pointer">
              {{ follower.id }}: {{ follower.email }}
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useStore } from "vuex";
import router from "@/router";

export default {
  name: "ProfileFollow",
  props: {},
  emits: ["closeModal"],
  setup(props, { emit }) {
    const store = useStore();
    // props;

    store.dispatch("fetchFollowingList");
    store.dispatch("fetchFollowerList");

    let followingList = store.getters.getMyFollowingList;
    let followerList = store.getters.getMyFollowerList;

    // 모달 바깥을 클릭하면 모달을 닫게 하는 함수
    const closeModal = (event) => {
      if (
        !document
          .querySelector(".modal")
          .querySelector("." + event.target.className.split(" ")[0]) // 클릭한 박스의 클래스가 modal-card라는 클래스의 하위 클래스인지 아닌지
      ) {
        emit("closeModal");
      }
    };

    // 언팔로우
    const unfollow = (userId) => {
      const payload = {
        toID: store.getters.getLoginUserId,
        fromId: userId,
      };
      store.dispatch("unfollow", payload);
      if (store.getters.getBooleanValue) {
        followingList = followingList.filter(
          (following) => following.id != userId
        );
      }
    };

    // 프로필로
    const goProfile = (userID) => {
      router.push({
        name: "profile",
        params: { userId: userID },
      });
    };

    return {
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
