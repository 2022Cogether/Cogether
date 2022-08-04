<template>
  <div class="container">
    <div class="name fs-2">COGETHER</div>
    <div class="d-flex justify-content-center">
      <form id="login" class="input-group" @submit.prevent="goLogin">
        <input
          v-model="email"
          type="text"
          class="input"
          placeholder="Email"
          required
        />
        <input
          v-model="password"
          type="password"
          class="input"
          placeholder="Password"
          required
        />
        <button class="submit mt-2">Login</button>
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
    <div class="d-flex justify-content-center mt-3">
      <div class="d-flex justify-content-between" style="width: 70%">
        <div>
          <p>비밀번호를 잊으셨나요?</p>
        </div>
        <div>
          <router-link :to="{ name: 'PassWordSeek' }">
            비밀번호 찾기
          </router-link>
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
import { ref } from "vue";
import { useStore } from "vuex";

export default {
  name: "SignIn",
  setup() {
    const $store = useStore();
    const email = ref("");
    const password = ref("");

    function goLogin() {
      const credentials = {
        email: email.value,
        password: password.value,
      };

      $store.dispatch("login", credentials);
    }

    return { email, password, goLogin };
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
