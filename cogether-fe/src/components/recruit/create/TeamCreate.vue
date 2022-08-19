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
      <label class="input-group-text" for="start">시작예정일</label>
      <input
        type="date"
        class="form-control"
        id="start"
        min=""
        v-model="state.start"
      />
    </div>
    <div class="input-group mb-3">
      <label class="input-group-text" for="cur_mem">현재인원수</label>
      <select v-model="state.cur_mem" class="form-select" id="cur_mem">
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
      <label class="input-group-text" for="total_mem">전체인원수</label>
      <select v-model="state.total_mem" class="form-select" id="total_mem">
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
      <label class="input-group-text" for="online">진행 방식</label>
      <select v-model="state.online" class="form-select" id="online">
        <option value="미정" selected>미정</option>
        <option value="온라인">온라인</option>
        <option value="오프라인">오프라인</option>
      </select>
    </div>
  </div>
  <!-- 검색바 -->
  <div class="row">
    <div class="input-group-stack">
      <label class="input-group-text" for="techStack">사용 기술</label>
      <div class="searchbar" id="techStack">
        <input
          class="input-search"
          v-model="skillInput"
          @input="submitAutoComplete"
          type="text"
          @keyup.enter="addLangSkills"
          placeholder="기술을 입력해주세요"
        />
        <button class="btn-search" @click="addLangSkills">
          <font-awesome-icon
            class="searchicon"
            icon=" fa-solid fa-magnifying-glass"
          />
        </button>
      </div>
    </div>
  </div>
  <!-- 자동완성추천리스트 -->
  <div class="row">
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
    <!-- 기술스택 아이콘 -->
    <div class="tech-container">
      <button
        v-for="langSkill in state.skillList"
        :key="langSkill.id"
        class="tech-icon-box"
        @click="delLangSkills(langSkill)"
      >
        <img
          class="tech-icon"
          :src="
            `https://cogethera801.s3.ap-northeast-2.amazonaws.com/devicon/` +
            langSkill +
            `-original.svg`
          "
          alt="img"
        />
      </button>
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
import { reactive, computed, ref } from "vue";
import { useStore } from "vuex";
import skills from "@/assets/skills.js";
export default {
  name: "TeamCreate",
  props: ["tab"],
  setup(props) {
    const store = useStore();
    const getters = computed(() => store.getters);
    const state = reactive({
      userId: getters.value.getLoginUserId,
      start: null,
      total_mem: null,
      cur_mem: null,
      online: "미정",
      title: null,
      content: null,
      skillList: [],
    });

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

    async function createRcruitTeam() {
      if (
        !state.title ||
        !state.start ||
        !state.cur_mem ||
        !state.total_mem ||
        !state.content ||
        !state.online
      ) {
        Toast.fire({
          icon: "warning",
          title: "빈 칸이 존재합니다.",
        });
        return;
      }
      //날짜 시간처리
      state.start += "T00:00:00";

      if (props.tab == 1) {
        console.log("dd");
        await store.dispatch("createProjectTeam", state);
      } else if (props.tab == 2) {
        await store.dispatch("createStudyTeam", state);
      }
      Toast.fire({
        icon: "success",
        title: "글이 작성되었습니다.",
      });
      //페이지 이동
      router.push({ name: "RecruitMain" });
    }

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
      //skill리스트에 해당 스킬이 있나 검사
      val = val.target.value;
      if (skills.indexOf(val) < 0) {
        return;
      }
      //입력 중복 검사
      if (state.skillList.includes(val)) {
        Toast.fire({
          icon: "warning",
          title: "이미 입력된 스킬입니다.",
        });
      } else {
        state.skillList.push(val);
        skillInput.value = null; //입력을 하면 입력값 초기화
        result.value = null; // 자동완성 리스트도 초기화
      }
    };

    const delLangSkills = (val) => {
      if (state.skillList.includes(val)) {
        state.skillList = state.skillList.filter((element) => element !== val);
      }
    };

    return {
      state,
      createRcruitTeam,
      addLangSkills,
      delLangSkills,
      searchSkillAdd,
      skillInput,
      result,
      submitAutoComplete,
    };
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

.input-group-text {
  border-radius: 5px 0px 0px 5px;
}

.input-group {
  margin-right: 5%;
  width: 30%;
}

.title {
  width: 100%;
  height: 50px;
  border: 1px solid #dbdbdb;
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

.box-input1 {
  margin-bottom: 10px;
}

/* Tech Stack */
.tech-icon-box {
  display: inline-block;
  width: 40px;
  height: 40px;
  background-color: #c1ebe6;
  border: 0px;
  border-radius: 50%;
  margin: 10px 5px 0px 5px;
}

.tech-icon {
  width: 25px;
  height: 25px;
  margin: 7px auto;
}

.team-content {
  height: 50vh;
  margin-top: 20px;
}

/* 검색바 */
.input-group-stack {
  height: 40px;
  /* width: 50%; */
  /* float: left; */
  /* margin-right: 5%; */
}
.searchbar {
  position: relative;
  display: inline-block;
  border: 1px solid #dbdbdb;
  border-radius: 0px 5px 5px 0px;
  background-color: white;
  width: 12vw;
}

.input-search {
  outline: none;
  width: 100%;
  border: 0px;
  padding-left: 5%;
  padding-right: 20%;
  height: 38px;
  border-radius: 5px;
  /* position: relative; */
  /* bottom: 0%; */
  /* background-color: white; */
  /* float: left; */
}
.btn-search {
  position: absolute;
  margin-top: 7px;
  border: 0;
  background-color: transparent;
  right: 4%;
  /* top: 0%; */
}

/* 자동완성 */
.skill {
  border-bottom: 1px solid #f4f0f0;
}
.autocomplete {
  margin-left: 102px;
  position: absolute;
  z-index: 4;
  width: 12vw;
  padding-left: 10px;
  border: 1px solid #dbdbdb;
  border-top: 0px;
  background-color: white;
}

::-webkit-scrollbar {
  display: none;
}
</style>
