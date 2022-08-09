<template>
  <div class="row nav-bar">
    <div class="col-3"></div>
    <div class="col-6 d-flex justify-content-center">
      <div class="text-center align-self-center logo-area">
        <span>
          <a href="#">
            <img class="logo" src="../../assets/logo.png" alt="로고" />
          </a>
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

      <div v-if="isLoggedIn" class="dropdown">
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
          <router-link
            class="dropdown-item"
            :to="{ name: 'profile', params: { userId: 1 } }"
            >내 프로필<!-- 추후 userId를 currentUesr.pk 등으로 변경 -->
          </router-link>
          <router-link class="dropdown-item" :to="{ name: 'PassWordChange' }"
            >비밀번호 변경
          </router-link>
          <a class="dropdown-item" @click.prevent="logout">로그아웃</a>
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
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  name: "NavBar",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);

    const currentUser = store.getters.getCurrentUser;

    const isLoggedIn = getters.value.isLoggedIn;

    function checkOkay() {
      console.log("CLICKED!!");
    }

    const logout = () => {
      store.dispatch("logout", 1);
    };

    return { isLoggedIn, currentUser, checkOkay, logout };
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
