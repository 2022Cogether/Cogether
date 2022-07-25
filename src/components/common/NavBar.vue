<template>
  <div class="row nav-bar">
    <div class="col-3"></div>
    <div class="col-6 d-flex justify-content-center">
      <div class="text-center align-self-center logo-area">
        <span>
          <img class="logo" src="../../assets/logo.png" alt="로고" />
        </span>
        <span> COGETHER </span>
      </div>
    </div>
    <div class="col-3 align-self-center">
      <font-awesome-icon
        class="fs-3 icons"
        icon="fa-solid fa-tower-broadcast"
      />
      <font-awesome-icon class="fs-3 icons" icon="fa-solid fa-bell" />

      <div class="dropdown">
        <a
          href=""
          class="dropdown-toggle"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <font-awesome-icon class="fs-3" icon="fa-solid fa-user" />
        </a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="">내 프로필</a>
          <a class="dropdown-item" href="">비밀번호 변경</a>
          <a class="dropdown-item" href="">로그아웃</a>
        </div>
      </div>
      <!-- <img
        src="https://placeimg.com/35/35/any"
        alt="profile image"
        class="profile-image"
      /> -->
    </div>
  </div>
</template>

<script>
export default {
  name: "NavBar",
  computed: {},
  methods: {
    checkOkay() {
      console.log("CLICKED!!");
    },
  },
};
</script>

<style scoped>
.logo-area {
  font-size: 2rem;
  font-weight: bold;
  padding-bottom: 5px;
}

.logo {
  width: 8%;
}

.dropdown {
  display: inline;
}

/* .dropdown:hover {
  background-color: #b7b7b7;
} */

.dropdown-toggle {
  color: white;
}

.dropdown-item {
  cursor: pointer;
}

.icons {
  margin-right: 0.9735rem;
}

.nav-bar {
  background-color: #2a9d8f;
  color: white;
  position: fixed;
  width: 100%;
  z-index: 1;
}
</style>
