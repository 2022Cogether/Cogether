<template>
  <div class="container">
    <div class="name fs-2">COGETHER</div>
    <div class="d-flex justify-content-center">
      <form id="login" class="input-group" action="">
        <input type="text" class="input" placeholder="Email" required />
        <input type="password" class="input" placeholder="Password" required />
        <button class="submit mt-2">Login</button>
      </form>
    </div>
    <div class="d-flex justify-content-center mt-3">
      <p>아직 회원이 아니신가요?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
      <router-link to="signup/"> 회원가입 </router-link>
    </div>
    <div class="social-icons mt-5">
      <img
        src="data:image/png;base64,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"
        alt="google"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "SignIn",
  setup() {
    function login() {}

    function register() {}
    return { login, register };
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
