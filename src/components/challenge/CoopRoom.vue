<template>
  <div class="room-nav">
    <!-- <button @click="timerStart">타이머</button> -->
    <!-- <div></div> -->
    <span class="title fs-2"> {{ room.title }}</span>
    <span class="time fs-5 mx-3">{{ room.time }}분 남음</span>
    <span class="tab1">
      <button class="btn-time-expand">연장</button>
      <button class="btn-exit">종료</button>
      <button class="btn-start">시작</button>
    </span>
    <div class="tab2">
      <button class="btn-detail">DETAIL</button>
      <button class="btn-code">CODE</button>
    </div>
  </div>
  <div class="container-code">
    <div class="box-content">
      <div class="code-nav">
        <button v-show="state.isExpand" class="btn-people">사람</button>
        <button v-show="state.isExpand" class="btn-people">사람</button>
        <button v-show="state.isExpand" class="btn-people">사람</button>
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
      <div class="box-chat"></div>
      <div class="inputbar">
        <textarea class="input" cols="30" rows="4"></textarea>
        <button class="input-submit">
          <font-awesome-icon icon="fa-solid fa-paper-plane" />
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import { computed, reactive, ref } from "vue";
import { useStore } from "vuex";
import testaxios from "@/api/testaxios";
export default {
  name: "CoopRoom",
  setup() {
    const state = reactive({
      isExpand: true,
    });
    const room = reactive({
      id: null,
      host: null,
      title: null,
      time: null,
      curPeople: null,
      maxPeople: null,
      isStarted: null,
      content: null,
    });
    const roomNo = ref(router.currentRoute.value.params.roomNo);
    testaxios
      .get(`/room/${roomNo.value}`)
      .then(({ data }) => {
        room.id = data.id;
        room.host = data.host;
        room.title = data.title;
        room.time = data.time;
        room.curPeople = data.curPeople;
        room.maxPeople = data.maxPeople;
        room.isStarted = data.isStrated;
        room.content = data.content;
      })
      .catch((e) => {
        console.log("에러: " + e);
      });
    const store = useStore();
    const getters = computed(() => store.getters);
    function changeExpand() {
      state.isExpand = !state.isExpand;
    }

    // var Timer = ref(null);
    // var TimeCounter = ref(180);
    // var TimerStr = ref("03:00")l
    // function timerStart() {}
    return { store, getters, state, changeExpand, room };
  },
  components: {},
};
</script>

<style scoped>
.btn-time-expand,
.btn-exit,
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
.box-chat {
  height: calc(60vh - 70px);
  width: 100%;
  background-color: white;
}

.inputbar {
  margin-top: 10px;
  width: 100%;
  background-color: white;
}

.input-submit {
  float: right;
  border: 0px;
  background-color: transparent;
  width: 30px;
}

.input {
  border: 0px;
  width: calc(100% - 30px);
  max-height: 120px;
  resize: none;
  overflow-y: hidden;
}

.input:focus {
  outline: none;
}
</style>
