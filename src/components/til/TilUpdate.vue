<template>
  <div class="box-exit">
    <button class="btn-exit" @click="exit">
      <font-awesome-icon class="icon-exit fs-3" icon="fa-solid fa-xmark" />
    </button>
  </div>
  <div>
    <div class="box-register d-flex justify-content-between">
      <button type="submit" class="btn-delete" @click="deleteTil">삭제</button>
      <button type="submit" class="btn-modify" @click="modifyTil">수정</button>
    </div>
    <div class="box-input1">
      <input
        v-model="state.title"
        class="title"
        type="text"
        placeholder="제목을 입력하세요.."
      />
    </div>

    <textarea
      v-model="state.content"
      class="coop-content form-control"
    ></textarea>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import router from "@/router";
import { reactive, computed } from "vue";
import { useStore } from "vuex";

export default {
  name: "TilUpdate",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const tilContent = getters.value.getTilContent;

    const state = reactive({
      //사용할 변수들 선언
      title: tilContent.tilTitle,
      content: tilContent.tilContent,
      multipartFiles: [],
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
          router.push({ name: "mainview" });
        }
      });
    }
    function deleteTil() {
      Swal.fire({
        title: "삭제하시겠습니까?",
        text: "삭제한 글은 복구할 수 없습니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "확인",
        cancelButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          //취소하고 이동할 페이지
          store.dispatch("mainview");
        }
      });
    }

    function modifyTil() {
      const data = {
        content: state.content,
        title: state.title,
        tilId: store.getters.getOpenTil,
      };

      //함수 작동 내용
      store.dispatch("updateTil", data);
      router.go();
      router.go(-1);
    }

    return { exit, modifyTil, tilContent, deleteTil, state };
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

.btn-modify {
  width: 100px;
  height: 30px;
  border: 0px;
  float: right;
  background-color: #dbdbdb;
  border-radius: 5px;
}

.btn-delete {
  width: 100px;
  height: 30px;
  border: 0px;
  float: left;
  background-color: #ff0303;
  color: white;
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

.file-label {
  margin-top: 30px;
  background-color: #5b975b;
  color: #fff;
  text-align: center;
  padding: 10px 0;
  width: 65%;
  border-radius: 6px;
  cursor: pointer;
}
.file {
  display: none;
}
</style>
