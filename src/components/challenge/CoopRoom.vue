<template>
  <div class="room-nav">
    <div v-show="false">{{ connect() }}</div>
    <!-- <button @click="timerStart">타이머</button> -->
    <!-- <div></div> -->
    <span class="title fs-2"> {{ room.title }}</span>
    <span class="time fs-5 mx-3">{{ room.duration }} 분</span>
    <span class="tab1">
      <button class="btn-exit" @click="roomExit">나가기</button>
      <button v-if="room.inProgress" class="btn-time-expand">연장</button>
      <button v-if="room.inProgress" class="btn-end">종료</button>
      <button v-else class="btn-start">시작</button>
    </span>
    <div class="tab2">
      <button class="btn-detail">DETAIL</button>
      <button class="btn-code">CODE</button>
    </div>
  </div>
  <div class="container-code">
    <div class="box-content">
      <div class="code-nav">
        <span v-for="(person, i) in members" :key="i">
          <button v-show="state.isExpand" class="btn-people">
            {{ person.userName }}
          </button>
        </span>
        <button
          v-show="!state.isExpand"
          @click="changeExpand"
          class="btn-expand"
        >
          <font-awesome-icon icon="fa-solid fa-expand" />
        </button>
        <button
          v-show="state.isExpand"
          @click="changeExpand"
          class="btn-compress"
        >
          <font-awesome-icon icon="fa-solid fa-compress" />
        </button>
        <button v-show="state.isExpand" class="btn-submit">제출</button>
      </div>
      <textarea
        v-show="state.isExpand"
        class="box-code form-control"
      ></textarea>
    </div>
  </div>
  <div class="container-chat">
    <div class="box-content">
      <coop-chat-input :room="room" />
    </div>
  </div>
</template>

<script>
import router from "@/router";
import { computed, reactive } from "vue";
import { useStore } from "vuex";
import Swal from "sweetalert2";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import CoopChatInput from "@/components/challenge/CoopChatInput.vue";
export default {
  name: "CoopRoom",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const roomId = router.currentRoute.value.params.roomNo;
    store.dispatch("getChatList", getters.value.getRoom.chatRoomId);
    store.dispatch("getCoopMembers", roomId);
    store.dispatch("getDetailCoopRoom", roomId);

    const members = computed(() => getters.value.getCoopMembers);
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
    const room = computed(() => getters.value.getRoom);
    const state = reactive({
      isExpand: true,
      chatRoomId: room.value.chatRoomId,
      userId: getters.value.getLoginUserId,
    });

    async function connect() {
      const serverURL = "https://i7a801.p.ssafy.io:8080";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log("협력채팅소켓 연결을 시도합니다.");
      this.stompClient.connect(
        {},
        () => {
          // 소켓 연결 성공
          console.log("협력채팅소켓 연결 성공");
          this.stompClient.subscribe("/send/" + state.chatRoomId, (res) => {
            const data = JSON.parse(res.body);
            console.log("수신 메시지: ", res.body);
            const sendData = {
              userId: getters.value.getLoginUserId,
              chatRoomId: state.chatRoomId,
              chatId: data.chatId,
            };
            console.log(sendData);
            store.commit("APPEND_RECV_LIST", data);
          });
        },
        (error) => {
          console.log("협력채팅소켓 연결 실패", error);
        }
      );
      store.commit("SET_STOMP_CLIENT", this.stompClient);
    }

    function changeExpand() {
      state.isExpand = !state.isExpand;
    }

    async function roomExit() {
      let flag = 0;
      if (room.value.userId == getters.value.getLoginUserId) {
        await Swal.fire({
          title: "정말 방을 나가시겠습니까?",
          text: "현재 있는 방이 삭제됩니다!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "확인",
          cancelButtonText: "취소",
        }).then((result) => {
          if (result.isConfirmed) {
            flag = 1;
            Toast.fire({
              icon: "success",
              title: "방이 삭제되었습니다.",
            });
          }
        });
      } else {
        await Swal.fire({
          title: "정말 방을 나가시겠습니까?",
          text: "",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "확인",
          cancelButtonText: "취소",
        }).then((result) => {
          if (result.isConfirmed) {
            flag = 2;
            Toast.fire({
              icon: "success",
              title: "방에서 퇴장하였습니다.",
            });
          }
        });
      }

      if (flag == 1) {
        await store.dispatch("deleteCoopRoom", room.value.id);
        //방 삭제되었다고 소켓알림
        const data = {
          liveCoopId: room.value.id,
          mode: 4,
        };
        getters.value.getStompClientCoop.send(
          "/receive/coop",
          JSON.stringify(data),
          {}
        );
      }
      //else 방장 제외 인원
      else if (flag == 2) {
        store.dispatch("deleteCoopMember", getters.value.getCoopMemberId);
        //방나갔다고 소켓알림
        const data = {
          liveCoopId: room.value.id,
          mode: 1,
        };
        getters.value.getStompClientCoop.send(
          "/receive/coop",
          JSON.stringify(data),
          {}
        );
      }
      //공통
      if (flag != 0) {
        store.dispatch("getCoopRooms", getters.value.getLoginUserId);
        router.push({ name: "ChallengeMain" });
      }
    }

    return {
      store,
      getters,
      state,
      changeExpand,
      room,
      roomExit,
      members,
      connect,
    };
  },
  components: { CoopChatInput },
};
</script>

<style scoped>
.btn-time-expand,
.btn-exit,
.btn-end,
.btn-start {
  border: 0px;
  border-radius: 10px;
  margin-right: 5px;
}

.tab1 {
  margin-left: 3%;
}
.tab2 {
  float: right;
  width: 20%;
  margin-top: 15px;
}

.btn-detail,
.btn-code {
  border: 0px;
  border-radius: 10px;
  margin-left: 5px;
  float: right;
}

.container-code {
  display: flex;
  align-items: center;
  background-color: #c1ebe6;
  border-radius: 10px;
  width: 50vw;
}

.box-content {
  margin: auto;
  width: 95%;
}

.box-code {
  margin-bottom: 20px;
  height: 400px;
  width: 100%;
  background-color: white;
}

.btn-submit,
.btn-expand,
.btn-compress {
  float: right;
}

.btn-expand,
.btn-compress {
  margin-bottom: 10px;
  background-color: transparent;
  border: 0px;
}

.btn-people {
  border-radius: 10px 10px 0px 0px;
  border: 0px;
  margin-left: 5px;
  margin-top: 10px;
}

.btn-submit {
  border-radius: 10px;
  border: 0px;
}

.code-nav {
  margin-top: 10px;
}

.container-chat {
  display: flex;
  align-items: center;
  margin-top: 20px;
  background-color: #c1ebe6;
  border-radius: 10px;
  height: 70vh;
  width: 50vw;
}
</style>
