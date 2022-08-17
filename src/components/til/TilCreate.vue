<template>
  <div class="box-exit">
    <button class="btn-exit" @click="exit">
      <font-awesome-icon class="icon-exit fs-3" icon="fa-solid fa-xmark" />
    </button>
  </div>
  <form @submit.prevent="createTil">
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

    <textarea
      v-model="state.content"
      class="coop-content form-control"
    ></textarea>

    <div class="d-flex justify-content-center">
      <label class="file-label" for="imageFile">이미지 등록</label>
      <input
        @change="imgupload"
        class="file"
        id="imageFile"
        type="file"
        multiple
        accept="image/*"
      />
    </div>
  </form>
</template>

<script>
import Swal from "sweetalert2";
import router from "@/router";
import { reactive } from "vue";
import { useStore } from "vuex";

export default {
  name: "TilCreate",
  setup() {
    const store = useStore();

    const state = reactive({
      //사용할 변수들 선언
      title: "",
      content: "",
      multipartFiles: [],
    });

    function exit() {
      Swal.fire({
        title: "나가시겠습니까?",
        text: "작성 중인 글은 저장되지 않습니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#2a9d8f",
        cancelButtonColor: "#d33",
        confirmButtonText: "확인",
        cancelButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          //취소하고 이동할 페이지
          router.go(-1);
        }
      });
    }
    function imgupload(e) {
      let imageFile = e.target.files; // 업로드한 파일의 데이터가 여기있음.
      console.log(imageFile);
      // let url = URL.createObjectURL(imageFile[0]); // 파일의 필요한 데이터만을 url 변수에 넣음
      // console.log(url); // 확인
      for (let i = 0; i < imageFile.length; i++) {
        state.multipartFiles.push(imageFile[i]);
      }
    }
    function createTil() {
      const formData = new FormData();

      const data = {
        content: state.content,
        title: state.title,
        userId: store.getters.getLoginUserId,
      };

      formData.append(
        "data",
        new Blob([JSON.stringify(data)], {
          type: "application/json",
        })
      );

      // let fileArray = [];
      for (let i = 0; i < state.multipartFiles.length; i++) {
        // fileArray.push(state.multipartFiles[i]);
        formData.append("image", state.multipartFiles[i]);
      }

      for (var key of formData.keys()) {
        console.log(key);
      }
      for (var value of formData.values()) {
        console.log(value);
      }
      //함수 작동 내용
      store.dispatch("createTil", formData);
      if (store.getters.getBooleanValue) {
        router.go(-1);
      }
    }

    return { exit, createTil, imgupload, state };
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
