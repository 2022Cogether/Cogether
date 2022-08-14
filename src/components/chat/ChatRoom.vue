<template>
  <!-- 톡방 들어간 후 -->
  <div class="chat-nav">
    <!-- 프로필사진 -->
    <div class="chat-profile">
      <a href="#">
        <img class="chat-picture" src="@/assets/logo.png" alt="로고" />
      </a>
    </div>
    <!-- 이름 및 나가기 버튼-->
    <div class="chat-name">이름</div>
    <button class="btn-exit" @click="exit">
      <font-awesome-icon icon="fa-solid fa-arrow-left" />
    </button>
  </div>
  <hr />
  <div class="box-talk">
    <div v-for="(item, idx) in recvList" :key="idx">
      <p>유저이름: {{ item.userName }}</p>
      <p>내용: {{ item.content }}</p>
    </div>
  </div>
  <!-- 입력창 -->
  <div class="inputbar">
    <textarea
      class="input"
      v-model="message"
      cols="30"
      rows="4"
      @keypress="sendMessage"
    ></textarea>
    <button class="input-submit" @click="sendMessage">
      <font-awesome-icon icon="fa-solid fa-paper-plane" />
    </button>
  </div>
</template>

<script>
//채팅
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
//여기까지
// import { computed } from "vue";
import { useStore } from "vuex";
export default {
  data() {
    return {
      userName: "테스트임시",
      message: "",
      recvList: [],
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    console.log("디스!");
    console.log(this);
    this.connect();
  },
  methods: {
    sendMessage(e) {
      if (
        (e.type == "click" &&
          this.userName !== "" &&
          this.message.trim() != "") ||
        (!e.shiftKey &&
          e.keyCode === 13 &&
          this.userName !== "" &&
          this.message.trim() != "")
      ) {
        this.send();
        this.message = "";
      }
    },
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          userName: this.userName,
          content: this.message,
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = "http://localhost:8079";
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
          this.stompClient.subscribe("/send", (res) => {
            console.log("구독으로 받은 메시지 입니다.", res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
  name: "ChatRoom",
  setup() {
    console.log("셋업임");
    // console.log(userName);
    const store = useStore();
    // const getters = computed(() => store.getters);

    function exit() {
      store.commit("SET_CHAT_USER", null);
    }
    return { exit };
  },
};
</script>

<style scoped>
.chat-content {
  float: left;
  width: 77%;
}

.chat-content:hover {
  cursor: pointer;
}

.chat-picture {
  border-radius: 70%;
  border: 1px solid #dbdbdb;
  margin: 3%;
  width: 100%;
}

.chat-item {
  display: flex;
  align-items: center;
}

.chat-profile {
  float: left;
  width: 15%;
  margin-right: 3%;
}

.chat-picture {
  border-radius: 70%;
  border: 1px solid #dbdbdb;
  margin: 3%;
  width: 100%;
}

.chat-name {
  float: left;
}

.btn-exit {
  border: 0px;
  background-color: transparent;
  float: right;
}

.chat-nav {
  display: inline-block;
  width: 100%;
}

hr {
  margin: 3px;
}

.box-talk {
  height: calc(100% - 180px);
}

.inputbar {
  border: 1px solid #bdbdbd;
  margin-top: 10px;
  width: 100%;
  background-color: white;
  border-radius: 10px;
}

.input {
  border-radius: 10px;
  border: 0px;
  width: calc(100% - 30px);
  max-height: 95px;
  resize: none;
}

.input:focus {
  outline: none;
}

.input-submit {
  float: right;
  border: 0px;
  background-color: transparent;
  width: 30px;
}
</style>
