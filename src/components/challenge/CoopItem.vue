<template>
  <div class="coop-room-item round">
    <div class="item-header">
      <div class="profile-img-box">
        <a :href="`/#/profile/` + room.userId">
          <img class="profile-img" :src="room.userImgUrl" alt="로고"
        /></a>
      </div>
      <div class="coop-title fs-5">{{ room.title }}</div>
      <div class="coop-time fs-6">{{ room.duration }}분</div>
    </div>
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
    const store = useStore();
    const getters = computed(() => store.getters);
    function setModal() {
      store.commit("SET_ROOM_ID", props.room.id);
      emit("setModal", props.room, props.index);
    }

    return { getters, setModal };
  },
  components: {},
};
</script>

<style scoped>
.item-header {
  display: flex;
}
/* Profile Image */
.profile-img-box {
  width: 50px;
  height: 50px;
  border-radius: 70%;
  overflow: hidden;
}

.profile-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

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

.coop-title {
  margin-left: 3%;
}

.coop-time {
  position: absolute;
  right: 15px;
  /* float: right; */
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
  transition: all 0.15s linear;
}

.btn-coop-enter:hover {
  background-color: #2a9d8f;
  color: white;
}
</style>
