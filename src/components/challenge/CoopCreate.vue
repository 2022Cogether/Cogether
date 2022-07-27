<template>
  <div class="box-exit">
    <button class="btn-exit" @click="exit">
      <font-awesome-icon class="icon-exit fs-3" icon="fa-solid fa-xmark" />
    </button>
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
    <div class="box-input2">
      <div class="input-group mb-3">
        <label class="input-group-text" for="personnel">인원</label>
        <select v-model="state.personnel" class="form-select" id="personnel">
          <option selected>선택</option>
          <option value="2">2명</option>
          <option value="3">3명</option>
          <option value="4">4명</option>
          <option value="5">5명</option>
          <option value="6">6명</option>
        </select>
      </div>
      <div class="input-group mb-3">
        <label class="input-group-text" for="coop-time-hour">시간</label>
        <select v-model="state.hour" class="form-select">
          <option selected>시</option>
          <option value="1">0</option>
          <option value="2">1</option>
          <option value="3">2</option>
          <option value="4">3</option>
          <option value="5">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
        </select>
        <select v-model="state.min" class="form-select">
          <option selected>분</option>
          <option value="0">0</option>
          <option value="10">10</option>
          <option value="20">20</option>
          <option value="30">30</option>
          <option value="40">40</option>
          <option value="50">50</option>
        </select>
      </div>
    </div>
    <textarea
      v-model="state.content"
      class="coop-content form-control"
      aria-label="With textarea"
    ></textarea>
  </form>
</template>

<script>
import Swal from "sweetalert2";
import router from "@/router";
import { reactive } from "vue";
export default {
  name: "CoopCreate",
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
          router.push({ name: "ChallengeMain" });
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
}

.input-group {
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
</style>
