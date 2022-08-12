<template>
  <div class="container">
    <div class="name fs-2">COGETHER</div>
    <div class="d-flex justify-content-center">
      <form id="login" class="input-group" @submit.prevent="sendEmail">
        <input
          v-model="email"
          type="email"
          class="input p-1"
          placeholder="Email"
          @keyup="checkValidEmail"
          required
        />
        <div v-if="!isValidEmail" class="text-danger">
          <p>올바른 이메일 주소를 입력해주세요</p>
        </div>
        <button class="submit mt-2">Send PW</button>
      </form>
    </div>
    <div class="d-flex justify-content-center mt-3">
      <div class="d-flex justify-content-between" style="width: 70%">
        <div>
          <p>아직 회원이 아니신가요?</p>
        </div>
        <div>
          <router-link to="/sign/signup"> 회원가입 </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from "vue";
import { useStore } from "vuex";
import router from "@/router";

export default {
  name: "PassWordSeek",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);

    const email = ref("");
    const isValidEmail = ref(true);
    const checkValidEmail = () => {
      isValidEmail.value = getters.value.getEmailPattern.test(email.value);
    };

    function sendEmail() {
      if (isValidEmail.value) {
        store.dispatch("getTempPassWord", email.value);
        if (store.getters.getBooleanValue) {
          router.push({ name: "SignIn" });
        }
      } else {
        alert("올바른 형식의 이메일을 입력해주세요!");
      }
    }

    return { email, isValidEmail, checkValidEmail, sendEmail };
  },
};
</script>

<style scoped>
.container {
  border: 1px solid #bdbdbd;
  border-radius: 10px;
  width: 50%;
  height: 500px;
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
