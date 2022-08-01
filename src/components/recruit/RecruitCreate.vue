<template>
  <div class="box-exit">
    <button class="btn-exit" @click="exit">
      <font-awesome-icon class="icon-exit fs-3" icon="fa-solid fa-xmark" />
    </button>
  </div>
  <div class="dropdown">
    <button
      class="btn btn-secondary dropdown-toggle"
      type="button"
      data-bs-toggle="dropdown"
      aria-expanded="false"
    >
      프로젝트 등록
    </button>
    <ul class="dropdown-menu">
      <li>
        <a class="dropdown-item" href="#/recruit/studycreate">스터디 등록</a>
      </li>
      <li>
        <a class="dropdown-item" href="#/recruit/personcreate">개인 등록</a>
      </li>
    </ul>
  </div>
  <form @submit="createCoop">
    <div class="box-register">
      <button type="submit" class="btn-register">등록</button>
    </div>
    <div class="box-input1">
      <input
        v-model="state.title"
        class="title"
        type="text"
        placeholder="제목을 입력하세요.."
      />
    </div>
    <form class="row">
      <div class="input-group mb-3">
        <label class="input-group-text" for="startDate">시작예정일</label>
        <input type="date" class="form-control" id="startDate" />
      </div>
      <div class="input-group mb-3">
        <label class="input-group-text" for="currMem">현재 인원수</label>
        <input type="number" class="form-control" id="currMem" />
      </div>
      <div class="input-group mb-3">
        <label class="input-group-text" for="totalMem">전체 인원수</label>
        <input type="number" class="form-control" id="totalMem" />
      </div>
      <div class="input-group mb-3">
        <label class="input-group-text" for="totalMem">예상 기간</label>
        <input type="text" class="form-control" id="totalMem" />
      </div>
      <div class="input-group mb-3">
        <label class="input-group-text" for="totalMem">진행 방식</label>
        <select v-model="state.personnel" class="form-select" id="personnel">
          <option selected>선택</option>
          <option value="2">온라인</option>
          <option value="3">오프라인</option>
        </select>
      </div>
      <div class="tech-icon-container d-flex">
        <label class="input-group-text" for="techStack">사용 기술</label>
        <div class="tech-icon-box">
          <img
            class="tech-icon"
            src="@/assets/devicon/javascript-original.svg"
            alt="tech icon"
          />
        </div>
      </div>
    </form>

    <textarea
      v-model="state.content"
      class="coop-content form-control"
    ></textarea>
  </form>
</template>

<script>
import Swal from "sweetalert2";
import router from "@/router";
import { reactive } from "vue";
export default {
  name: "RecruitCreate",
  setup() {
    const state = reactive({
      //사용할 변수들 선언
      title: null,
      personnel: null,
      hour: null,
      min: null,
      content: null,
    });
    function exit() {
      Swal.fire({
        title: "나가시겠습니까?",
        text: "작성 중인 글은 저장되지 않습니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "확인",
        cancelButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          //취소하고 이동할 페이지
          router.push({ name: "RecruitMain" });
        }
      });
    }
    function createCoop() {
      //확인
      console.log(state.title);
      console.log(state.personnel);
      console.log(state.hour);
      console.log(state.min);
      console.log(state.content);
      //함수 작동 내용
    }

    return { exit, createCoop, state };
  },
  components: {},
};
</script>

<style scoped>
.coop-content {
  width: 100%;
  height: 60vh;
}

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

.box-exit {
  height: 50px;
}

.box-register {
  height: 40px;
}
.btn-exit {
  border: 0px;
  background-color: transparent;
  float: right;
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
</style>
