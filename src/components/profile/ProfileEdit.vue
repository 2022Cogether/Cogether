<template>
  <div class="container mt-2">
    <h1>프로필 수정</h1>
    <div class="row">
      <div class="h5 col-3">프로필 사진</div>
      <div class="profile-img-box offset-1">
        <img :src="imgUrl" alt="profile image" class="profile-img" />
      </div>
    </div>
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
    <div class="row my-3">
      <div class="h5 col-3" style="display: inline-block; float: left">
        닉네임
      </div>
      <div class="input-group mb-3 offset-4">
        <input
          type="text"
          maxlength="15"
          style="background-color: #e2e2e2; border-radius: 5px"
          v-model="nickname"
          @keyup="checkNickValid"
        />
        <div class="input-group-append">
          <button
            class="btn"
            style="background-color: #e2e2e2; border: 1px solid"
            @click.prevent="certifyNickName"
          >
            중복 체크
          </button>
        </div>
      </div>
      <div v-if="!isNickValid" class="text-danger">
        <p>유효한 닉네임을 입력해주세요</p>
      </div>
    </div>
    <div class="row">
      <div class="h5 col-3" style="display: inline-block; float: left">
        한 줄 소개
      </div>
      <div class="input-group mb-3 offset-4">
        <input
          type="text"
          maxlength="150"
          size="50"
          style="background-color: #e2e2e2; border-radius: 5px"
          v-model="intro"
        />
      </div>
    </div>
    <div class="h5">사용 기술</div>
    <!-- Tech Stack -->
    <div class="techstack-container">
      <div class="d-flex flex-wrap" sytle="min-height: 30px;">
        <div
          v-for="(lang, idx) in userLangSkills"
          :key="idx"
          class="techstack-box d-flex align-items-start"
        >
          <img
            :src="
              'https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/' +
              lang +
              '-original.svg'
            "
            alt="techstack image"
            class="techstack-img"
          />
          <font-awesome-icon
            icon="fa-solid fa-circle-minus"
            class="mt-2"
            @click="delLangSkills(lang)"
          />
        </div>
        <font-awesome-icon
          icon="fa-solid fa-circle-plus"
          style="height: 50px; opacity: 0.5; margin-top: 10px"
          @click="changeTechModal"
        />
      </div>
    </div>
    <!-- 기술 수정 / 제거 모달 -->
    <div
      class="modal d-flex justify-content-center"
      v-if="isOnTechModal"
      @click="closeModal"
    >
      <div class="modal-card" style="overflow-y: scroll">
        <div class="searchbar-modal">
          <input
            class="input-search-modal"
            v-model="skillInput"
            @input="submitAutoComplete"
            type="text"
            @keyup.enter="addLangSkills"
            placeholder="기술을 입력해주세요"
          />
          <button class="btn-search-modal" @click="addLangSkills">
            <font-awesome-icon
              class="searchicon"
              icon=" fa-solid fa-magnifying-glass"
            />
          </button>
        </div>
        <div class="autocomplete disabled">
          <div
            class="skill"
            @click="searchSkillAdd(res)"
            style="cursor: pointer"
            v-for="(res, i) in result"
            :key="i"
          >
            {{ res }}
          </div>
        </div>
        <!-- 여기까지 -->
        <div
          class="img-box d-flex justify-content-between mt-2"
          v-for="langSkill in userLangSkills"
          :key="langSkill.id"
        >
          <img
            class="skill-img"
            :src="
              `https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/` +
              langSkill +
              `-original.svg`
            "
            alt="img"
          />
          <p>{{ langSkill }}</p>
          <div class="btn btn-secondary" @click="delLangSkills(langSkill)">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-x"
              viewBox="0 0 16 16"
            >
              <path
                d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"
              />
            </svg>
          </div>
        </div>
        <!-- <div class="row d-flex justify-content-around mt-5">
        <div class="col-5" v-for="lang in basicLangSet" :key="lang.id">
          <div
            class="btn m-1 w-100"
            style="background-color: #2a9d8f; color: white"
            @click="addLangSkills(lang)"
          >
            {{ lang }}
          </div>
        </div>
      </div> -->
      </div>
    </div>
    <div class="h5">웹페이지 링크</div>
    <div class="container my-2">
      <div class="col-3 h6" style="display: inline-block; float: left">
        Git Hub
      </div>
      <div class="offset-4">
        <input
          type="text"
          size="60"
          style="background-color: #e2e2e2; border-radius: 5px"
          v-model="gitUrl"
        />
      </div>
    </div>
    <div class="container my-2">
      <div class="col-3 h6" style="display: inline-block; float: left">
        Tistory
      </div>
      <div class="offset-4">
        <input
          type="text"
          size="60"
          style="background-color: #e2e2e2; border-radius: 5px"
          v-model="tistoryUrl"
        />
      </div>
    </div>
    <div class="container my-2">
      <div class="col-3 h6" style="display: inline-block; float: left">
        Velog
      </div>
      <div class="offset-4">
        <input
          type="text"
          size="60"
          style="background-color: #e2e2e2; border-radius: 5px"
          v-model="velogUrl"
        />
      </div>
    </div>
    <div class="container my-2">
      <div class="col-3 h6" style="display: inline-block; float: left">
        Notion
      </div>
      <div class="offset-4">
        <input
          type="text"
          size="60"
          style="background-color: #e2e2e2; border-radius: 5px"
          v-model="notionUrl"
        />
      </div>
    </div>
    <div class="container my-2">
      <div class="col-3 h6" style="display: inline-block; float: left">
        기타 링크
      </div>
      <div class="offset-4">
        <input
          type="text"
          size="60"
          style="background-color: #e2e2e2; border-radius: 5px"
          v-model="etcUrl"
        />
      </div>
    </div>
    <div class="mt-5 mb-3 d-flex justify-content-around">
      <button @click="edit" style="background-color: #2a9d8f; color: white">
        수정
      </button>
      <button @click="back" style="background-color: #2a9d8f; color: white">
        취소
      </button>
    </div>
  </div>
</template>

<script>
<link
  rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
/>;
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import router from "@/router";

import skills from "@/assets/skills.js";
import Swal from "sweetalert2";

export default {
  name: "ProfileEdit",
  setup() {
    const store = useStore();
    const route = useRoute();
    const userId = route.params.userId;
    const profileUser = ref({});

    const imgUrl = ref("");
    const nickname = ref("");
    const intro = ref("");
    const gitUrl = ref("");
    const tistoryUrl = ref("");
    const velogUrl = ref("");
    const notionUrl = ref("");
    const etcUrl = ref("");

    let originalSkillList = [];
    const userLangSkills = ref([]);

    (async () => {
      if (store.getters.getCurrentUser.id == undefined) {
        await store.dispatch("fetchCurrentUser", store.getters.getLoginUserId);
      }
      const profileUser = computed(() => {
        return store.getters.getCurrentUser;
      });
      console.log("프로필 값", profileUser.value);

      imgUrl.value = profileUser.value.imgUrl;
      nickname.value = profileUser.value.nickname;
      intro.value = profileUser.value.intro;
      gitUrl.value = profileUser.value.gitUrl;
      tistoryUrl.value = profileUser.value.tistoryUrl;
      velogUrl.value = profileUser.value.velogUrl;
      notionUrl.value = profileUser.value.notionUrl;
      etcUrl.value = profileUser.value.etcUrl;

      if (!store.getters.getUserSkills[0]) {
        await store.dispatch("takeUserSkillSet", store.getters.getLoginUserId);
      }
      const temp_list = computed(() => {
        return store.getters.getUserSkills;
      });
      for (let i = 0; i < temp_list.value.length; i++) {
        originalSkillList.push(temp_list.value[i]);
      }
      userLangSkills.value = temp_list.value;
    })();

    // 모달 바깥을 클릭하면 모달을 닫게 하는 함수
    const closeModal = (event) => {
      if (
        !document
          .querySelector(".modal")
          .querySelector("." + event.target.className.split(" ")[0]) // 클릭한 박스의 클래스가 modal-card라는 클래스의 하위 클래스인지 아닌지
      ) {
        isOnTechModal.value = false;
      }
    };

    //기술스택 및 자동완성관련 함수들
    const isOnTechModal = ref(false);
    const changeTechModal = () => {
      isOnTechModal.value = !isOnTechModal.value;
    };

    //SWal 모달창
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

    const delLangSkills = (val) => {
      if (userLangSkills.value.includes(val)) {
        userLangSkills.value = userLangSkills.value.filter(
          (element) => element !== val
        );
      } else {
        alert("입력되지 않은 스킬입니다!");
      }
    };

    //기술스택 및 자동완성관련 함수들
    const skillInput = ref("");
    const result = ref("");
    function submitAutoComplete() {
      const autocomplete = document.querySelector(".autocomplete");
      //입력이 없으면 자동완성리스트 제거(null)
      if (skillInput.value == "") {
        result.value = null;
      } else if (skillInput.value) {
        //값이 입력되면 자동완성 리스트 보이기
        autocomplete.classList.remove("disabled");
        result.value = skills.filter((skill) => {
          //^는 모든 리스트 보이기, i는 대문자 > 소문자처리하는 파라미터
          return skill.match(new RegExp("^" + skillInput.value, "i"));
        });
      } else {
        autocomplete.classList.add("disabled");
      }
    }
    //자동완성 리스트에서 스킬 클릭하면 해당 값을 입력값에 할당
    function searchSkillAdd(res) {
      skillInput.value = res;
      submitAutoComplete();
    }

    const addLangSkills = (val) => {
      //search 아이콘 클릭일 때
      if (val.type == "click") {
        val.target.value = skillInput.value;
      }

      //상자클릭이 아닐 때 skill리스트에 해당 스킬이 있나 검사
      if (typeof val != "string" && skills.indexOf(val.target.value) < 0) {
        return;
      }

      if (typeof val == "object") {
        if (userLangSkills.value.includes(val.target.value)) {
          Toast.fire({
            icon: "error",
            title: "이미 입력된 스킬입니다.",
          });
        } else {
          userLangSkills.value.push(val.target.value);
          skillInput.value = null; //입력을 하면 입력값 초기화
          result.value = null; // 자동완성 리스트도 초기화
        }
      } else {
        if (userLangSkills.value.includes(val)) {
          Toast.fire({
            icon: "warning",
            title: "이미 입력된 스킬입니다.",
          });
        } else {
          userLangSkills.value.push(val);
          skillInput.value = null; //입력을 하면 입력값 초기화
          result.value = null; // 자동완성 리스트도 초기화
        }
      }
    };

    // 닉네임 중복 체크
    const getters = computed(() => store.getters);
    const isNickValid = ref(true);
    const isNickChecked = ref(false);
    const checkNickValid = () => {
      isNickValid.value = getters.value.getNickPattern.test(nickname.value);
      if (isNickChecked.value) {
        isNickChecked.value = false;
      }
    };
    const certifyNickName = async () => {
      if (isNickValid.value) {
        await store.dispatch("checkNickName", nickname.value);
        if (getters.value.getBooleanValue) {
          isNickChecked.value = true;
        }
      }
    };

    // 이미지 넣기
    let multipartFiles;
    function imgupload(e) {
      multipartFiles = e.target.files[0];
    }

    const back = () => {
      router.go(-1);
    };

    const edit = () => {
      let editUserSkills = [];
      for (let i = 0; i < userLangSkills.value.length; i++) {
        editUserSkills.push(userLangSkills.value[i]);
      }
      let originalSkillArray = [];
      for (let i = 0; i < originalSkillList.length; i++) {
        originalSkillArray.push(originalSkillList[i]);
      }

      const plusSkills = editUserSkills.filter((skill) => {
        return originalSkillArray.indexOf(skill) == -1;
      });
      const minusSkills = originalSkillArray.filter((skill) => {
        return editUserSkills.indexOf(skill) == -1;
      });
      console.log(plusSkills);
      console.log(minusSkills);

      store.dispatch("plusUserSkillSet", {
        email: profileUser.value.email,
        skills: plusSkills,
      });
      store.dispatch("minusUserSkillSet", {
        email: profileUser.value.email,
        skills: minusSkills,
      });

      const payload = {
        img_url: imgUrl.value,
        nickname: nickname.value,
        intro: intro.value,
        // userLangSkills: userLangSkills.value,
        git_url: gitUrl.value,
        tistory_url: tistoryUrl.value,
        velog_url: velogUrl.value,
        notion_url: notionUrl.value,
        etc_url: etcUrl.value,
      };
      console.log(payload);

      const formData = new FormData();
      formData.append("image", multipartFiles);
      store.dispatch("updateUserImage", formData);
      store.dispatch("updateProfile", payload);
      router.push({
        name: "profile",
        params: { userId: store.getters.getLoginUserId },
      });
    };

    return {
      userId,

      imgUrl,
      nickname,
      intro,
      gitUrl,
      tistoryUrl,
      velogUrl,
      notionUrl,
      etcUrl,

      back,
      edit,

      closeModal,
      isOnTechModal,
      changeTechModal,

      isNickValid,
      isNickChecked,
      checkNickValid,
      certifyNickName,

      // 이미지 넣기
      multipartFiles,
      imgupload,

      submitAutoComplete,
      result,
      skillInput,
      searchSkillAdd,
      addLangSkills,
      userLangSkills,
      originalSkillList,
      Toast,
      delLangSkills,
    };
  },
};
</script>

<style scoped>
.profile-img-box {
  width: 120px;
  height: 120px;
  border-radius: 70%;
  overflow: hidden;
  border: 5px solid gold;
}

/* Tech Stack */
.techstack-container {
  margin: 0 0 5px 5px;
}

h4 {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  font-size: 16px;
}

.techstack-box {
  width: auto;
  height: 70px;
  margin-right: 5px;
  margin-left: 5px;
  overflow: hidden;
}

.techstack-img {
  width: auto;
  height: 100%;
  object-fit: cover;
}

/* Tech Stack Modal */
.modal {
  height: 100vh;
  /* border: 1px solid #bdbdbd;
  border-radius: 10px;
  width: 50%;
  min-width: 300px;
  height: auto;
  margin: 10% auto;
  background: white;
  padding: 5px; */
}

.modal-card {
  /* background-color: white;
  width: 50vw;
  height: 70vh;
  margin-top: 15vh; */
  border: 1px solid #bdbdbd;
  border-radius: 10px;
  width: 50%;
  min-width: 300px;
  margin: 10% auto;
  background: white;
  padding: 5px;
}
/* 검색바 */
.searchbar-modal {
  position: relative;
  display: inline-block;
  width: 60%;
}
.input-search-modal {
  outline: none;
  position: relative;
  bottom: 0%;
  width: 100%;
  border: 0px;
  padding-left: 5%;
  padding-right: 20%;
  margin-right: 1%;
  background-color: transparent;
}
.btn-search-modal {
  position: absolute;
  top: 0%;
  height: 16px;
  margin-top: 2px;
  border: 0;
  border-top: 0px;
  border-radius: 0px;
  background-color: transparent;
  right: 4%;
}
/* 자동완성 */
.img-box {
  height: 40px;
}
.skill-img {
  max-width: 100%;
  max-height: 100%;
}
.skill {
  border-bottom: 1px solid #f4f0f0;
}
.autocomplete {
  position: absolute;
  z-index: 4;
  width: 60%;
  padding-left: 10px;
  border: 1px solid #dbdbdb;
  background-color: white;
}

/* 이미지 넣기 */
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
