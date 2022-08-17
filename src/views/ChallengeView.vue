<template>
  <div v-show="false">{{ connect() }}</div>
  <router-view></router-view>
</template>

<script>
import router from "@/router";
import { computed } from "vue";
import { useStore } from "vuex";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
export default {
  name: "ChallengeView",
  components: {},
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const roomId = computed(() => getters.value.getRoomId);

    //방 소켓연결sub(create) 및 들어왔다고 send (get 갱신)
    function connect() {
      if (roomId.value == null) {
        return;
      }
      const serverURL = "https://i7a801.p.ssafy.io:8080";
      let socket = new SockJS(serverURL);
      this.stompClientCoop = Stomp.over(socket);
      console.log("협력 소켓 연결을 시도합니다.");
      this.stompClientCoop.connect(
        {},
        () => {
          console.log("협력 소켓 연결 성공");
          this.stompClientCoop.subscribe(
            "/send/coop/" + roomId.value,
            (res) => {
              const mode = JSON.parse(res.body).mode;
              console.log("협력소켓받음!!!!!!" + mode);
              //입장: 0
              if (mode == 0) {
                console.log("소켓받음: 입장");
                store.dispatch("getCoopMembers", roomId.value);
                store.dispatch("getDetailCoopRoom", roomId.value);
              }
              //퇴장: 1
              else if (mode == 1) {
                console.log("소켓받음: 퇴장");
                store.dispatch("getCoopMembers", roomId.value);
                store.dispatch("getDetailCoopRoom", roomId.value);
              }
              //시작: 2
              else if (mode == 2) {
                console.log("소켓받음: 시작");
                store.dispatch("getDetailCoopRoom", roomId.value);
              }
              //연장: 3
              else if (mode == 3) {
                console.log("소켓받음: 연장");
                store.dispatch("getDetailCoopRoom", roomId.value);
              }
              //종료: 4
              else if (mode == 4) {
                console.log("소켓받음: 종료");
                router.push({ name: "ChallengeMain" });
              }
            }
          );
        },
        (error) => {
          console.log("소켓 연결 실패", error);
        }
      );
      store.commit("SET_STOMP_CLIENT_COOP", this.stompClientCoop);
    }

    return { connect };
  },
};
</script>
<style></style>
