<template>
  <div class="team-container">
    <div class="d-flex justify-content-between">
      <div
        class="head d-flex"
        data-bs-toggle="modal"
        data-bs-target="#teamDetailInfo"
        @click="setModal"
      >
        <div class="profile-img-box">
          <img
            class="profile-img"
            src="@/assets/logo.png"
            alt="profile image"
          />
        </div>
        <div class="title fs-3">{{ projectTeam.title }}</div>
      </div>
      <div class="d-flex">
        <!-- 삭제 드롭다운 -->
        <div
          v-if="projectTeam.userId == getters.getLoginUserId"
          class="dropdown"
        >
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
              <button class="dropdown-item" @click="deleteTeam">삭제</button>
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
        <div v-else class="bookmark-icon-box">
          <font-awesome-icon
            @click="bookmarkCheck"
            icon="fa-regular fa-bookmark"
            class="bookmark-icon"
          />
        </div>
      </div>
    </div>
    <p
      class="group-info"
      data-bs-toggle="modal"
      data-bs-target="#teamDetailInfo"
      @click="setModal"
    >
      {{ projectTeam.content }}
    </p>
    <div
      class="info-box d-flex justify-content-between"
      data-bs-toggle="modal"
      data-bs-target="#teamDetailInfo"
      @click="setModal"
    >
      <!-- 기술스택아이콘 -->
      <div class="tech-icon-container">
        <div
          class="tech-icon-box"
          v-for="(skillstack, i) in projectTeam.skillList"
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
      <div class="detail-info-box d-flex">
        <p>시작 예정일</p>
        <span>{{ projectTeam.start.substring(0, 10) }}</span>
        <font-awesome-icon icon="fa-solid fa-people-group" class="group-icon" />
        <span>{{ projectTeam.cur_mem }} / {{ projectTeam.total_mem }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import Swal from "sweetalert2";
import router from "@/router";
export default {
  name: "TeamIteam",
  props: ["projectTeam"],
  setup(props, { emit }) {
    function setModal() {
      emit("setModal", props.projectTeam);
    }
    const store = useStore();
    const getters = computed(() => store.getters);
    const state = reactive({
      bookmark: props.projectTeam.scrap,
    });

    function bookmarkCheck() {
      state.bookmark = !state.bookmark;
      if (state.bookmark) {
        store.dispatch("setProjectTeamScrap", props.projectTeam.projectId);
      } else {
        store.dispatch("deleteProjectTeamScrap", props.projectTeam.projectId);
      }
    }

    async function deleteTeam() {
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
          store.dispatch("deleteProjectTeam", props.projectTeam.projectId);
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

    return { bookmarkCheck, state, deleteTeam, getters, setModal };
  },
};
</script>

<style scoped>
.head {
  width: 90%;
}

/* 북마크 */
.bookmark-icon:hover {
  cursor: pointer;
}
.bookmark-icon-box {
  float: right;
}

/* 드롭다운 */
.dropdown {
  margin-right: 10px;
}

.btn-dropdown {
  background-color: transparent;
  border: 0px;
  margin-right: 0px;
  float: right;
}

.team-container {
  background-color: white;
  border: 1px solid #dbdbda;
  border-radius: 10px;
  box-shadow: 0px 5px 3px -3px #bdbdbd;
  margin-bottom: 10px;
}

.team-container:hover {
  background-color: #c1ebe6;
  border: 1px solid #2a9d8f;
}

/* Profile */
.profile-img-box {
  width: 50px;
  height: 50px;
  border-radius: 70%;
  overflow: hidden;
  margin: 10px;
  border: 3px solid gold;
}

.profile-img {
  width: 45px;
  height: 45px;
}

/* Title */
.title {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  margin-top: 12px;
}

/* Bookmark Icon */
.bookmark-icon {
  margin-right: 10px;
  font-size: 1.7rem;
}

.bookmark-icon-solid {
  margin-right: 10px;
  font-size: 1.7rem;
  color: #e63946;
}

/* Group Info */
.group-info {
  margin: 0 10px 0;
  font-size: 14px;
}

/* 기술스택 */
.tech-icon-container {
  position: relative;
  left: 10px;
  margin-top: 15px;
}

.tech-icon-box {
  display: inline-block;
  text-align: center;
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background-color: #d8f3dc;
  margin-left: 5px;
  margin-bottom: 7px;
}

.tech-icon {
  margin-top: 5px;
  width: 23px;
  height: 23px;
}

/* Detail Info */
.detail-info-box {
  margin: 10px;
}

.detail-info-box > p {
  font-weight: 700;
  font-size: 16px;
}
.detail-info-box > span {
  font-size: 14px;
  padding-left: 5px;
}

.group-icon {
  font-size: 22px;
  padding: 0 10px 0 20px;
}
</style>
