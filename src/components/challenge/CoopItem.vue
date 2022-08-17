<template>
  <div class="coop-room-item round">
    <a href="#"
      ><img class="coop-profile" src="@/assets/logo.png" alt="로고"
    /></a>
    <span class="coop-title fs-5">{{ room.title }}</span>
    <span class="coop-time fs-6">{{ room.duration }}분</span>
    <div class="coop-bottom">
      <span class="coop-people"
        >{{ room.nowMemNum }} / {{ room.maxMemNum }} 명</span
      >
      <span class="icon-people"></span>
    </div>
    <!-- 재입장 -->
    <button
      v-if="room.userId == getters.getLoginUserId"
      class="btn-coop-enter"
      data-bs-toggle="modal"
      data-bs-target="#coopRoomDetail"
      @click="setModal"
    >
      재입장
    </button>
    <!-- 입장 -->
    <button
      v-else-if="!room.inProgress"
      class="btn-coop-enter"
      data-bs-toggle="modal"
      data-bs-target="#coopRoomDetail"
      @click="setModal"
    >
      입장
    </button>
    <!-- 진행중 -->
    <button v-else-if="room.inProgress" class="btn-coop-enter">진행중</button>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "vue";
export default {
  name: "CoopItem",
  props: ["room", "index"],
  setup(props, { emit }) {
    function setModal() {
      emit("setModal", props.room, props.index);
    }
    const store = useStore();
    const getters = computed(() => store.getters);

    return { getters, setModal };
  },
  components: {},
};
</script>

<style scoped>
.round {
  border: 1px solid black;
  border-radius: 10px;
}
.coop-room-item {
  position: relative;
  display: inline-block;
  height: 150px;
  width: 48%;
  margin-bottom: 10px;
  margin-left: 1%;
  margin-right: 1%;
  padding: 2%;
  border: 1px solid #dbdbda;
  box-shadow: 0px 5px 3px -3px #bdbdbd;
  background-color: white;
}

.coop-profile {
  margin: auto;
  width: 10%;
}

.coop-title {
  margin-left: 3%;
}

.coop-time {
  float: right;
  margin-right: 2%;
}

.coop-bottom {
  position: absolute;
  bottom: 7%;
}

.btn-coop-enter {
  border: 1px solid #dbdbda;
  background-color: transparent;
  border-radius: 5px;
  position: absolute;
  bottom: 7%;
  right: 7%;
}

.btn-coop-enter:hover {
  background-color: #2a9d8f;
  color: white;
}
</style>
