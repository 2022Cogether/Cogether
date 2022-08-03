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
          <option selected disabled>선택</option>
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
          <option selected disabled>시</option>
          <option value="0">0</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
        </select>
        <select v-model="state.min" class="form-select">
          <option selected disabled>분</option>
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
    ></textarea>
  </form>
</template>

<script>
import Swal from "sweetalert2";
import router from "@/router";
import { reactive } from "vue";
import { useStore } from "vuex";
export default {
  name: "CoopCreate",
  setup() {
    const store = useStore();
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
      if (
        !state.content ||
        !state.hour ||
        !state.min ||
        !state.personnel ||
        !state.title
      ) {
        return;
      }
      //actions axios 보내기
      store.dispatch("createCoopRoom", state);
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
        title: "방이 생성되었습니다.",
      });
      //페이지 이동
      router.push({ name: "ChallengeMain" });
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
</style>
