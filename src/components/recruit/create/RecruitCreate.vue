<template>
  <!-- 닫기 버튼 -->
  <div class="box-recruit">
    <button class="btn-exit" @click="exit">
      <font-awesome-icon class="icon-exit fs-3" icon="fa-solid fa-xmark" />
    </button>
    <!-- 카테고리 -->
    <div class="input-group mb-3">
      <select v-model="state.tab" class="form-select">
        <option value="1" selected>프로젝트 팀 등록</option>
        <option value="2">스터디 팀 등록</option>
        <option value="3">프로젝트 개인 등록</option>
      </select>
    </div>
  </div>
  <!-- 컴포넌트 -->
  <div v-if="state.tab == 1 || state.tab == 2">
    <team-create :tab="state.tab" />
  </div>
  <div v-else>
    <person-create />
  </div>
</template>

<script>
import Swal from "sweetalert2";
import router from "@/router";
import { reactive } from "vue";
import TeamCreate from "@/components/recruit/create/TeamCreate.vue";
import PersonCreate from "@/components/recruit/create/PersonCreate.vue";
export default {
  name: "RecruitCreate",
  setup() {
    const state = reactive({
      tab: 1,
      title: null,
      startDate: null,
      curPeople: null,
      maxPeople: null,
      period: null,
      process: null,
      useStack: null,
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

    return { exit, state };
  },
  components: { TeamCreate, PersonCreate },
};
</script>

<style scoped>
.input-group,
.form-select {
  height: 40px;
  float: left;
}

.input-group {
  margin-right: 5%;
  width: 30%;
}

.box-recruit {
  height: 40px;
}

.btn-exit {
  border: 0px;
  background-color: transparent;
  float: right;
}
</style>
