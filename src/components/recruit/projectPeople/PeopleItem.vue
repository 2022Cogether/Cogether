<template>
  <li class="list-group-item d-flex">
    <div class="profile-img-box">
      <img class="profile-img" src="@/assets/logo.png" alt="profile image" />
    </div>
    <div
      class="user-info flex-fill"
      data-bs-toggle="modal"
      data-bs-target="#personDetailInfo"
    >
      <h3>{{ projectPerson.userNickname }}</h3>
      <p>{{ projectPerson.title }}</p>
    </div>
    <div class="d-flex align-self-end">
      <div class="tech-icon-box">
        <img
          class="tech-icon"
          src="@/assets/devicon/javascript-original.svg"
          alt="TechStack Icons"
        />
      </div>
    </div>
    <div v-if="state.bookmark" class="bookmark-icon-box">
      <font-awesome-icon
        @click="bookmarkCheck"
        icon="fa-solid fa-bookmark"
        class="bookmark-icon-solid"
      />
    </div>
    <div v-else>
      <font-awesome-icon
        @click="bookmarkCheck"
        icon="fa-regular fa-bookmark"
        class="bookmark-icon"
      />
    </div>
  </li>
  <!-- 모달 -->
  <people-modal />
</template>

<script>
import PeopleModal from "@/components/recruit/projectPeople/PeopleModal.vue";
import { reactive } from "vue";
export default {
  name: "PeopleItem",
  props: ["projectPerson"],
  setup(props) {
    const state = reactive({
      bookmark: props.projectPerson.scrap,
    });

    function bookmarkCheck() {
      state.bookmark = !state.bookmark;
    }

    return { bookmarkCheck, state };
  },
  components: {
    PeopleModal,
  },
};
</script>

<style scoped>
/* Profile Image */
.profile-img-box {
  width: 50px;
  height: 50px;
  border-radius: 70%;
  overflow: hidden;
  margin: 5px 10px 10px 10px;
  border: 3px solid gold;
}

.profile-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* User Information */
.user-info {
  margin-top: 10px;
}

h3 {
  font-size: 15px;
  font-weight: 700;
  margin-bottom: 4px;
}

p {
  font-size: 12px;
  margin: 0;
}

/* Tech Stack */
.tech-icon-box {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background-color: #d8f3dc;
  margin-bottom: 7px;
}

.tech-icon {
  display: block;
  width: 13px;
  height: 13px;
  margin: 5px auto;
}

.bookmark-icon {
  font-size: 25px;
  padding: 0 10px;
}

.bookmark-icon-solid {
  font-size: 25px;
  padding: 0 10px;
  color: #e63946;
}

li {
  padding: 0;
}

/* Modal */
.modal-content {
  background-color: #eff7f6;
}

.dropdown > button {
  margin-right: 10px;
}

.image-box {
  width: 75px;
  height: 75px;
  border-radius: 70%;
  overflow: hidden;
  margin-right: 25px;
  border: 3px solid gold;
}

.image-box > img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  margin: 5px auto;
}

.profile-detail-box {
  margin-left: 80px;
}

.profile-detail-info > h5 {
  font-size: 20px;
  font-weight: 700;
}

.tech-icon-container {
  margin-top: 5px;
}

.user-introduction {
  background-color: #2a9d8f;
  border: 1px solid white;
  border-radius: 10px;
  width: 300px;
  margin: 20px auto 10px;
  padding: 10px;
}

.modal-header {
  border: 0;
}

.modal-footer > .btn {
  background-color: #2a9d8f;
  color: #fff;
}

.list-group-item {
  border-left: 0px;
  border-right: 0px;
}

.list-group-item:hover {
  background-color: #c1ebe6;
}
</style>
