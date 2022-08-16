<template>
  <div class="row nav-bar">
    <div class="col-3"></div>
    <div class="col-6 d-flex justify-content-center">
      <div class="text-center align-self-center logo-area">
        <span>
          <a href="#">
            <img
              class="logo"
              src="https://cogethera801.s3.ap-northeast-2.amazonaws.com/icon.png"
              alt="로고"
            />
            <img
              class="logotext"
              src="https://cogethera801.s3.ap-northeast-2.amazonaws.com/logo.png"
              alt="로고글자"
            />
          </a>
        </span>
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
        <div v-if="userId" class="dropdown-menu">
          {{ connect() }}
          <router-link
            class="dropdown-item"
            :to="{
              name: 'profile',
              params: { userId: userId },
            }"
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
import router from "@/router";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "NavBar",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const currentUser = store.getters.getCurrentUser;

    const userId = computed(() => {
      return getters.value.getLoginUserId;
    });
    const isLoggedIn = computed(() => {
      return getters.value.isLoggedIn;
    });

    function checkOkay() {
      console.log("CLICKED!!");
    }

    const logout = () => {
      const payload = {
        userId: getters.value.getLoginUserId,
        email: getters.value.getCurrentUser.email,
      };
      store.dispatch("logout", payload);
      if (store.getters.getBooleanValue) {
        router.push({ name: "mainview" });
      }
    };

    async function connect() {
      await store.dispatch("getChatRoomList", getters.value.getLoginUserId);
      const roomList = getters.value.getRoomList;
      const serverURL = "http://i7a801.p.ssafy.io:8080";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          for (const room of roomList) {
            this.stompClient.subscribe("/send/" + room.chatRoomId, (res) => {
              store.dispatch("getChatRoomList", getters.value.getLoginUserId);
              // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
              const data = JSON.parse(res.body);
              if (
                getters.value.getChatUserId == data.sendUserId ||
                data.sendUserId == getters.value.getLoginUserId
              ) {
                console.log("수신 메시지: ", res.body);
                const sendData = {
                  userId: getters.value.getLoginUserId,
                  chatRoomId: room.chatRoomId,
                  chatId: data.chatId,
                };
                console.log(sendData);
                store.dispatch("sendRecentReadMsg", sendData);
                store.commit("APPEND_RECV_LIST", data);
              }
            });
          }
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
      store.commit("SET_STOMP_CLIENT", this.stompClient);
      store.commit("SET_CONNECTED", this.connected);
    }

    return { isLoggedIn, currentUser, checkOkay, logout, userId, connect };
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
  width: 50px;
  margin-right: 20px;
  margin-top: 5px;
  margin-bottom: 5px;
}

.logotext {
  width: 250px;
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
