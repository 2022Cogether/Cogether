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
          <input
            type="checkbox"
            class="checkbox my-3"
            @click="checkTerm"
          /><span>Terms and conditions</span>
          <div class="mt-2 d-flex justify-content-between">
            <button class="submit" @click="changePage">Next</button>
            <button class="submit" @click="register">Register</button>
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
            v-for="langSkill in langSkills"
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
            <button class="submit" @click="register">Register</button>
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
      <img
        src="data:image/png;base64,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"
        alt="google"
      />
    </div>
  </div>
</template>

<script>
<link
  rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
/>;
import { ref } from "vue";

export default {
  name: "SignUp",
  setup() {
    const isPageOne = ref(true);
    const changePage = () => {
      isPageOne.value = !isPageOne.value;
    };

    const email = ref("");
    const isValidEmail = ref(true);
    const checkValidEmail = () => {
      const emailPattern =
        /^(?=[a-zA-Z0-9@._%+-]{6,254}$)[a-zA-Z0-9._%+-]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,8}[a-zA-Z]{2,63}$/;
      isValidEmail.value = emailPattern.test(email.value);
    };

    const password = ref("");
    const password2 = ref("");

    const isPwdValid = ref(true);
    const checkPwdValid = () => {
      const pwdPattern =
        /[a-zA-Z]+(?=.*\d)(?=.*[a-z])(?=.*[~!@#$]).[a-zA-Z\d~!@#$]{7,14}$/;
      isPwdValid.value = pwdPattern.test(password.value);
    };
    const isPwdSame = ref(true);
    const checkPwdSame = () => {
      isPwdSame.value = password.value == password2.value;
    };

    const nickName = ref("");
    const isNickValid = ref(true);
    const checkNickValid = () => {
      const nickPattern = /^[ㄱ-ㅎ가-힣a-zA-Z\d./_]{2,15}$/;
      isNickValid.value = nickPattern.test(nickName.value);
    };

    const isCheckTerm = ref(false);
    const checkTerm = () => {
      isCheckTerm.value = !isCheckTerm.value;
    };

    const langSkills = ref([]);
    const delLangSkills = (val) => {
      if (langSkills.value.includes(val)) {
        langSkills.value = langSkills.value.filter(
          (element) => element !== val
        );
      } else {
        alert("입력되지 않은 스킬입니다!");
      }
    };
    const addLangSkills = (val) => {
      if (typeof val == "object") {
        if (langSkills.value.includes(val.target.value)) {
          alert("이미 입력된 스킬입니다!");
        } else {
          langSkills.value.push(val.target.value);
        }
      } else {
        if (langSkills.value.includes(val)) {
          alert("이미 입력된 스킬입니다!");
        } else {
          langSkills.value.push(val);
        }
      }
    };

    const langSet = [
      "C",
      "C++",
      "Python",
      "Java",
      "JavaScript",
      "Spring",
      "Django",
      "R",
    ];

    function register() {
      if (
        isValidEmail.value &&
        isPwdValid &&
        isPwdSame &&
        isNickValid &&
        isCheckTerm
      ) {
        const credentials = {
          email: email.value,
          password: password.value,
          nickName: nickName.value,
          langSkills: langSkills.value,
        };
        console.log(credentials);
      }
    }

    return {
      isPageOne,
      changePage,
      email,
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
      checkNickValid,
      isCheckTerm,
      checkTerm,
      langSkills,
      delLangSkills,
      addLangSkills,
      register,
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
