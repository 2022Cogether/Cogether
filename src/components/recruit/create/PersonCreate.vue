<template>
  <!-- 등록버튼 -->
  <div class="box-register">
    <button type="button" class="btn-register" @click="createRecruitPerson">
      등록
    </button>
  </div>
  <!-- 한줄소개 -->
  <div class="box-input1">
    <input
      v-model="state.title"
      class="title"
      type="text"
      placeholder="한 줄 소개를 입력하세요.."
    />
  </div>
  <textarea
    v-model="state.content"
    class="person-content form-control"
  ></textarea>
</template>

<script>
import Swal from "sweetalert2";
import router from "@/router";
import { reactive, computed } from "vue";
import { useStore } from "vuex";
export default {
  name: "RecruitCreate",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const state = reactive({
      userId: getters.value.getLoginUserId,
      title: null,
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
    async function createRecruitPerson() {
      if (!state.title || !state.content) {
        return;
      }

      await store.dispatch("createProjectPerson", state);
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

    return { exit, state, createRecruitPerson };
  },
  components: {},
};
</script>

<style scoped>
.person-content {
  height: 50vh;
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
  width: 100%;
  border-radius: 5px;
  height: 50px;
  border: 1px solid #dbdbdb;
}

.box-input1 {
  margin-bottom: 10px;
}

::-webkit-scrollbar {
  display: none;
}
</style>
