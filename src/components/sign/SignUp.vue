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
            <div v-else class="btn disable" @click.prevent="certifyEmail">
              이메일 인증
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
            <div v-else class="btn disable" @click.prevent="certifyNickName">
              닉네임 인증
            </div>
          </div>

          <input
            id="terms"
            type="checkbox"
            class="checkbox my-3"
            @click="checkTerm"
          /><label for="terms">Terms and conditions</label>
          <div class="mt-2 d-flex justify-content-between">
            <button class="submit" @click="changePage">Next</button>
            <button class="submit" @click="goRegister">Register</button>
          </div>
        </div>
        <div v-show="!isPageOne">
          <input
            type="skill"
            class="input"
            placeholder="기술을 입력해주세요"
            @keyup.enter="addLangSkills"
            required
          />
          <div
            class="d-flex justify-content-between mt-2"
            v-for="langSkill in userLangSkills"
            :key="langSkill.id"
          >
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
          <div class="row d-flex justify-content-around">
            <div class="col-5" v-for="lang in langSet" :key="lang.id">
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
          <router-link to="/sign/signin"> 로그인 </router-link>
        </div>
      </div>
    </div>
    <div class="social-icons my-5">
      <div class="btn btn-success">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          fill="currentColor"
          class="bi bi-google"
          viewBox="0 0 16 16"
        >
          <path
            d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"
          />
        </svg>
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
    const certifyEmail = () => {
      store.dispatch("checkEmail", email.value);
      if (getters.value.getBooleanValue) {
        isEmailChecked.value = true;
      }
    };

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
    const certifyNickName = () => {
      store.dispatch("checkNickName", nickName.value);
      if (getters.value.getBooleanValue) {
        isNickChecked.value = true;
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
    const addLangSkills = (val) => {
      if (typeof val == "object") {
        if (userLangSkills.value.includes(val.target.value)) {
          alert("이미 입력된 스킬입니다!");
        } else {
          userLangSkills.value.push(val.target.value);
        }
      } else {
        if (userLangSkills.value.includes(val)) {
          alert("이미 입력된 스킬입니다!");
        } else {
          userLangSkills.value.push(val);
        }
      }
    };

    // created?
    if (!getters.value.getSkillSet) {
      store.dispatch("takeSkillSet");
    }
    const langSet = getters.value.getSkillSet;
    console.log(getters.value.getSkillSet);

    function goRegister() {
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
          nickName: nickName.value,
          skills: userLangSkills.value,
        };
        store.dispatch("register", credentials);
      } else {
        alert("입력되지 않은 정보가 있습니다!");
      }
    }

    return {
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
      langSet,
    };
  },
};
</script>

<style scoped>
.container {
  border: 1px solid #bdbdbd;
  border-radius: 10px;
  width: 50%;
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
</style>
