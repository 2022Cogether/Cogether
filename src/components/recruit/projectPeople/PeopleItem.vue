<template>
  <li class="list-group-item d-flex">
    <!-- 프로필사진 -->
    <div class="profile-img-box">
      <img class="profile-img" src="@/assets/logo.png" alt="profile image" />
    </div>
    <!-- 이름과 제목 -->
    <div
      class="user-info flex-fill"
      data-bs-toggle="modal"
      data-bs-target="#personDetailInfo"
    >
      <h3>{{ projectPerson.userNickname }}</h3>
      <p>{{ projectPerson.title }}</p>
    </div>

    <!-- 기술스택아이콘 -->
    <div class="tech-icon-container">
      <div
        class="tech-icon-box"
        v-for="(skillstack, i) in projectPerson.userSkillList"
        :key="i"
      >
        <img
          class="tech-icon"
          :src="
            `https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/` +
            skillstack.skillName +
            `-original.svg`
          "
          alt="img"
        />
      </div>
    </div>
    <!-- 삭제 드롭다운 -->
    <div v-if="projectPerson.userId == getters.getLoginUserId" class="dropdown">
      <button
        class="btn-dropdown"
        type="button"
        data-bs-toggle="dropdown"
        aria-expanded="false"
      >
        <font-awesome-icon
          class="dropdown-icon"
          icon="fa-solid fa-ellipsis-vertical"
        />
      </button>
      <ul class="dropdown-menu">
        <li>
          <button class="dropdown-item" @click="deletePerson">삭제</button>
        </li>
      </ul>
    </div>
    <!-- 스크랩 -->
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
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import Swal from "sweetalert2";
import router from "@/router";
export default {
  name: "PeopleItem",
  props: ["projectPerson"],
  setup(props) {
    const store = useStore();
    const getters = computed(() => store.getters);
    const state = reactive({
      bookmark: props.projectPerson.scrap,
    });

    function bookmarkCheck() {
      state.bookmark = !state.bookmark;
      if (state.bookmark) {
        store.dispatch("setProjectPersonScrap", props.projectPerson.huntingId);
      } else {
        store.dispatch(
          "deleteProjectPersonScrap",
          props.projectPerson.huntingId
        );
      }
    }

    async function deletePerson() {
      await Swal.fire({
        title: "정말 삭제하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "확인",
        cancelButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          //취소하고 이동할 페이지
          store.dispatch("deleteProjectPerson", props.projectPerson.huntingId);
          const Toast = Swal.mixin({
            toast: true,
            position: "bottom-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
              toast.addEventListener("mouseenter", Swal.stopTimer);
              toast.addEventListener("mouseleave", Swal.resumeTimer);
            },
          });
          Toast.fire({
            icon: "success",
            title: "글이 삭제되었습니다.",
          });
          router.push({ name: "RecruitMain" });
        }
      });
    }
    return { bookmarkCheck, state, getters, deletePerson };
  },
  components: {},
};
</script>

<style scoped>
/* 북마크 */
.bookmark-icon:hover {
  cursor: pointer;
}
.bookmark-icon-solid {
  font-size: 25px;
  padding: 0 10px;
  color: #e63946;
}
/* 기술스택 */
.tech-icon-container {
  position: absolute;
  right: 40px;
  bottom: 0px;
}

.tech-icon-box {
  display: inline-block;
  text-align: center;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background-color: #d8f3dc;
  margin-left: 5px;
  margin-bottom: 7px;
}

.tech-icon {
  width: 15px;
  height: 15px;
  margin-bottom: 5px;
}
/* 드롭다운 */
.btn-dropdown {
  background-color: transparent;
  border: 0px;
  margin-right: 0px;
}

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

/* 유저정보 */
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

.bookmark-icon {
  font-size: 25px;
  padding: 0 10px;
}

li {
  padding: 0;
}
</style>
