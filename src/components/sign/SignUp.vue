<template>
  <div class="container">
    <div class="name fs-2">COGETHER</div>
    <div class="d-flex justify-content-center">
      <div id="login" class="input-group">
        <div v-show="isPageOne">
          <input
            v-model="email"
            type="email"
            class="input"
            placeholder="Email"
            @keyup="checkValidEmail"
            required
          />
          <div v-if="!isValidEmail" class="text-danger">
            <p>올바른 이메일 주소를 입력해주세요</p>
          </div>
          <div>
            <div v-if="isEmailChecked" class="btn active">인증 완료!</div>
            <div
              v-else
              class="btn btn-checks disable"
              @click.prevent="certifyEmail"
            >
              이메일 중복확인
            </div>
          </div>

          <input
            v-model="password"
            type="password"
            class="input"
            placeholder="Password"
            @keyup="checkPwdValid"
            required
          />
          <div v-if="!isPwdValid" class="text-danger">
            <p>비밀번호 형식을 지켜주세요</p>
          </div>
          <input
            v-model="password2"
            type="password"
            class="input"
            placeholder="Password Confirm"
            @keyup="checkPwdSame"
            required
          />
          <div v-if="!isPwdSame" class="text-danger">
            <p>동일한 비밀번호를 입력해주세요</p>
          </div>

          <input
            v-model="nickName"
            type="text"
            class="input"
            placeholder="Nick Name"
            @keyup="checkNickValid"
            required
          />
          <div v-if="!isNickValid" class="text-danger">
            <p>유효한 닉네임을 입력해주세요</p>
          </div>
          <div>
            <div v-if="isNickChecked" class="btn active">인증 완료!</div>
            <div
              v-else
              class="btn btn-checks disable"
              @click.prevent="certifyNickName"
            >
              닉네임 중복확인
            </div>
          </div>

          <input
            id="terms"
            type="checkbox"
            class="checkbox my-3"
            @click="checkTerm"
          /><label for="terms">이메일 정보 제공에 동의합니다.</label>
          <div class="mt-2 d-flex justify-content-between">
            <button class="submit" @click="changePage">Next</button>
            <button class="submit" @click="goRegister">Register</button>
          </div>
        </div>
        <div v-show="!isPageOne">
          <!-- 기술스택 검색 자동완성 여기부터 -->
          <div class="searchbar">
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
          <div class="row d-flex justify-content-around mt-5">
            <div class="col-5" v-for="lang in basicLangSet" :key="lang.id">
              <div
                class="btn m-1 w-100"
                style="background-color: #2a9d8f; color: white"
                @click="addLangSkills(lang)"
              >
                {{ lang }}
              </div>
            </div>
          </div>
          <div class="mt-2 d-flex justify-content-between">
            <button class="submit" @click="changePage">Prev</button>
            <button class="submit" @click="goRegister">Register</button>
          </div>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-center mt-3">
      <div class="d-flex justify-content-between" style="width: 70%">
        <div>
          <p>혹시 회원이신가요?</p>
        </div>
        <div>
          <router-link to="/sign/signin" class="link"> 로그인 </router-link>
        </div>
      </div>
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
import skills from "@/assets/skills.js";
import router from "@/router";
import Swal from "sweetalert2";

export default {
  name: "SignUp",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);

    const isPageOne = ref(true);
    const changePage = () => {
      isPageOne.value = !isPageOne.value;
    };

    const email = ref("");
    const isValidEmail = ref(true);
    const isEmailChecked = ref(false);
    const checkValidEmail = () => {
      isValidEmail.value = getters.value.getEmailPattern.test(email.value);
      if (isEmailChecked.value) {
        isEmailChecked.value = false;
      }
    };
    const certifyEmail = async () => {
      if (isValidEmail.value) {
        await store.dispatch("checkEmail", email.value);
        if (getters.value.getBooleanValue) {
          isEmailChecked.value = true;
        }
      }
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

    const password = ref("");
    const password2 = ref("");

    const isPwdValid = ref(true);
    const checkPwdValid = () => {
      isPwdValid.value = getters.value.getPwdPattern.test(password.value);
    };

    const isPwdSame = ref(true);
    const checkPwdSame = () => {
      isPwdSame.value = password.value == password2.value;
    };

    const nickName = ref("");
    const isNickValid = ref(true);
    const isNickChecked = ref(false);
    const checkNickValid = () => {
      isNickValid.value = getters.value.getNickPattern.test(nickName.value);
      if (isNickChecked.value) {
        isNickChecked.value = false;
      }
    };
    const certifyNickName = async () => {
      if (isNickValid.value) {
        await store.dispatch("checkNickName", nickName.value);
        if (getters.value.getBooleanValue) {
          isNickChecked.value = true;
        }
      }
    };

    const isCheckTerm = ref(false);
    const checkTerm = () => {
      isCheckTerm.value = !isCheckTerm.value;
    };

    const userLangSkills = ref([]);
    const delLangSkills = (val) => {
      if (userLangSkills.value.includes(val)) {
        userLangSkills.value = userLangSkills.value.filter(
          (element) => element !== val
        );
      } else {
        alert("입력되지 않은 스킬입니다!");
      }
    };

    // created?
    // if (!getters.value.getSkillSet) {
    //   store.dispatch("takeSkillSet");
    // }
    const basicLangSet = getters.value.getBasicSkillSet;

    async function goRegister() {
      if (
        isValidEmail.value &&
        isPwdValid &&
        isPwdSame &&
        isNickValid &&
        isNickChecked &&
        isCheckTerm &&
        !!email.value &&
        !!password.value &&
        !!nickName.value
      ) {
        const credentials = {
          email: email.value,
          password: password.value,
          nickname: nickName.value,
          skills: userLangSkills.value,
        };

        await store.dispatch("register", credentials);
        if (getters.value.getBooleanValue) {
          Toast.fire({
            icon: "success",
            title: "회원가입이 성공되었습니다.",
          });
        } else {
          Toast.fire({
            icon: "error",
            title: "회원가입이 실패되었습니다.",
          });
        }

        router.push({ name: "mainview" });
      } else {
        Toast.fire({
          icon: "warning",
          title: "입력되지 않은 정보가 있습니다.",
        });
      }
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

    return {
      searchSkillAdd,
      skillInput,
      result,
      submitAutoComplete,
      isPageOne,
      changePage,
      email,
      isEmailChecked,
      certifyEmail,
      password,
      password2,
      nickName,
      isPwdValid,
      checkPwdValid,
      isPwdSame,
      checkPwdSame,
      isValidEmail,
      checkValidEmail,
      isNickValid,
      isNickChecked,
      checkNickValid,
      certifyNickName,
      isCheckTerm,
      checkTerm,
      userLangSkills,
      delLangSkills,
      addLangSkills,
      goRegister,
      basicLangSet,
    };
  },
};
</script>

<style scoped>
/* 검색바 */
.searchbar {
  position: relative;
  display: inline-block;
  width: 60%;
}
.input-search {
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
.btn-search {
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

.container {
  border: 1px solid #bdbdbd;
  border-radius: 10px;
  width: 50%;
  min-width: 300px;
  height: auto;
  position: relative;
  margin: 10% auto;
  background: white;
  padding: 5px;
}

.name {
  margin-top: 10%;
  margin-bottom: 10%;
  text-align: center;
}

.button-wrap {
  width: 230px;
  margin: 35px auto;
  position: relative;
  box-shadow: 0 0 600px 9px #fcae8f;
  border-radius: 30px;
}
.togglebtn {
  padding: 10px 30px;
  cursor: pointer;
  background: transparent;
  border: 0;
  outline: none;
  position: relative;
}

.social-icons {
  text-align: center;
}

.social-icons img {
  width: 30px;
  cursor: pointer;
}

.input-group {
  width: 70%;
}

.input {
  width: 100%;
  padding: 10px 0;
  margin: 5px 0;
  border: none;
  border-bottom: 1px solid #999;
  outline: none;
  background: transparent;
}

.submit {
  width: 40%;
  padding: 5px 10px;
  background: linear-gradient(to right, #2a9d8f, #c1ebe6);
  border: 0px;
  outline: none;
  border-radius: 30px;
}

.btn-checks {
  font-size: 0.7rem;
  border: 1px solid #dbdbdb;
  background-color: #dbdbdb;
  padding: 3px;
}

.link {
  text-decoration: none;
}
</style>
