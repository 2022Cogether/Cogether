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
        <img
          src="@/assets/login_button.png"
          alt="로그인"
          style="
            max-width: 8vw;
            margin-left: 5vw;
            margin-top: 2vh;
            margin-bottom: 1vh;
          "
        />
      </form>
    </div>
    <div class="d-flex justify-content-center mt-3">
      <div class="d-flex justify-content-between" style="width: 70%">
        <div>
          <p>아직 회원이 아니신가요?</p>
        </div>
        <div>
          <router-link class="link" to="/sign/signup">
            <img
              src="@/assets/register_button.png"
              alt="회원가입"
              style="max-width: 6vw; margin-bottom: 1vh"
          /></router-link>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-center mt-3">
      <div class="d-flex justify-content-between" style="width: 70%">
        <div>
          <p>비밀번호를 잊으셨나요?</p>
        </div>
        <div>
          <router-link class="link" :to="{ name: 'PassWordSeek' }">
            <img
              src="@/assets/password_seek_button.png"
              alt="비밀번호 찾기"
              style="max-width: 6vw; margin-bottom: 1vh"
          /></router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, ref } from "vue";
import { useStore } from "vuex";
import Swal from "sweetalert2";
import router from "@/router";

export default {
  name: "SignIn",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);

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

    const email = ref("");
    const password = ref("");

    async function goLogin() {
      const credentials = {
        email: email.value,
        password: password.value,
      };

      await store.dispatch("login", credentials);
      if (!getters.value.getBooleanValue) {
        Toast.fire({
          icon: "warning",
          title: "아이디 또는 비밀번호를 잘못 입력했습니다.",
        });
      } else {
        router.push({
          name: "profile",
          params: { userId: store.getters.getLoginUserId },
        });
      }
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

.link {
  text-decoration: none;
}
</style>
