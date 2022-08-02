<template>
  <div class="container">
    <div class="name fs-2">COGETHER</div>
    <div class="d-flex justify-content-center">
      <form id="login" class="input-group" @submit.prevent="changePwd">
        <input
          v-model="password"
          type="password"
          class="input"
          placeholder="Current Password"
          @keyup="checkCurPwdValid"
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
          v-model="newPassword"
          type="password"
          class="input"
          placeholder="New Password"
          @keyup="checkNewPwdValid"
          required
        />
        <div v-if="!isNewPwdValid" class="text-danger">
          <p>비밀번호 형식을 지켜주세요</p>
        </div>
        <button class="submit mt-2">Send PW</button>
      </form>
    </div>
  </div>
</template>

<script>
import { ref, computed } from "vue";
import { useStore } from "vuex";

export default {
  name: "PassWordChange",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);

    const password = ref("");
    const password2 = ref("");
    const newPassword = ref("");

    const isPwdValid = ref(true);
    const isNewPwdValid = ref(true);

    const checkCurPwdValid = () => {
      isPwdValid.value = getters.value.getPwdPattern.test(password.value);
    };
    const checkNewPwdValid = () => {
      isNewPwdValid.value = getters.value.getPwdPattern.test(newPassword.value);
    };

    const isPwdSame = ref(true);
    const checkPwdSame = () => {
      isPwdSame.value = password.value == password2.value;
    };

    const changePwd = () => {
      const pwSet = {
        password: password.value,
        newPassword: newPassword.value,
      };
      store.dispatch("changePassword", pwSet);
    };

    return {
      password,
      password2,
      newPassword,
      isPwdSame,
      isPwdValid,
      isNewPwdValid,
      checkCurPwdValid,
      checkNewPwdValid,
      checkPwdSame,
      changePwd,
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
