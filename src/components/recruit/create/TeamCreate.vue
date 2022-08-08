<template>
  <!-- 등록버튼 -->
  <div class="box-register">
    <button type="button" class="btn-register" @click="createRcruitTeam">
      등록
    </button>
  </div>
  <div class="box-input1">
    <input
      v-model="state.title"
      class="title"
      type="text"
      placeholder="제목을 입력하세요.."
    />
  </div>
  <div class="row">
    <div class="input-group mb-3">
      <label class="input-group-text" for="startDate">시작예정일</label>
      <input
        type="date"
        class="form-control"
        id="startDate"
        min=""
        v-model="state.startDate"
      />
    </div>
    <div class="input-group mb-3">
      <label class="input-group-text" for="curPeople">현재인원수</label>
      <select v-model="state.curPeople" class="form-select" id="curPeople">
        <option disabled>선택</option>
        <option value="1">1명</option>
        <option value="2">2명</option>
        <option value="3">3명</option>
        <option value="4">4명</option>
        <option value="5">5명</option>
        <option value="6">6명</option>
        <option value="7">7명</option>
        <option value="8">8명</option>
        <option value="9">9명</option>
      </select>
    </div>
    <div class="input-group mb-3">
      <label class="input-group-text" for="maxPeople">전체인원수</label>
      <select v-model="state.maxPeople" class="form-select" id="maxPeople">
        <option disabled>선택</option>
        <option value="2">2명</option>
        <option value="3">3명</option>
        <option value="4">4명</option>
        <option value="5">5명</option>
        <option value="6">6명</option>
        <option value="7">7명</option>
        <option value="8">8명</option>
        <option value="9">9명</option>
        <option value="10">10명</option>
      </select>
    </div>
    <div class="input-group mb-3">
      <label class="input-group-text" for="process">진행 방식</label>
      <select v-model="state.process" class="form-select" id="process">
        <option value="미정" selected>미정</option>
        <option value="온라인">온라인</option>
        <option value="오프라인">오프라인</option>
      </select>
    </div>
    <div class="tech-icon-container d-flex">
      <label class="input-group-text" for="techStack">사용 기술</label>
      <div class="tech-icon-box" id="techStack">
        <img
          class="tech-icon"
          src="@/assets/devicon/javascript-original.svg"
          alt="tech icon"
        />
      </div>
    </div>
  </div>

  <textarea
    v-model="state.content"
    class="team-content form-control"
  ></textarea>
</template>

<script>
import Swal from "sweetalert2";
import router from "@/router";
import { reactive } from "vue";
import { useStore } from "vuex";
export default {
  name: "TeamCreate",
  props: ["tab"],
  setup(props) {
    const store = useStore();
    const state = reactive({
      title: null,
      startDate: null,
      curPeople: null,
      maxPeople: null,
      content: null,
      process: "미정",
      useStack: [],
    });

    function createRcruitTeam() {
      if (
        !state.title ||
        !state.startDate ||
        !state.curPeople ||
        !state.maxPeople ||
        !state.content ||
        !state.process ||
        !state.useStack
      ) {
        return;
      }

      if (props.tab == 1) {
        store.dispatch("createProjectTeam", state);
      } else if (props.tab == 2) {
        store.dispatch("createStudyTeam", state);
      }
      //sweetalert
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
        title: "글이 작성되었습니다.",
      });
      //페이지 이동
      router.push({ name: "RecruitMain" });
    }

    return { state, createRcruitTeam };
  },
};
</script>

<style scoped>
.input-group,
.input-group-text,
.form-select {
  height: 40px;
  float: left;
}

.input-group {
  margin-right: 5%;
  width: 30%;
}

.title {
  width: 100%;
  border-radius: 5px;
}

.box-register {
  height: 40px;
}

.btn-register {
  width: 100px;
  height: 30px;
  border: 0px;
  float: right;
  background-color: #dbdbdb;
  border-radius: 5px;
}

.title {
  height: 50px;
  border: 1px solid #dbdbdb;
}

.box-input1 {
  margin-bottom: 10px;
}

/* Tech Stack */
.tech-icon-box {
  width: 30px;
  height: 30px;
  background-color: #c1ebe6;
  border-radius: 50%;
  margin: 10px;
}

.tech-icon {
  display: block;
  width: 15px;
  height: 15px;
  margin: 7px auto;
}

.team-content {
  height: 50vh;
}

::-webkit-scrollbar {
  display: none;
}
</style>
