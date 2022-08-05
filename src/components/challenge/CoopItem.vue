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
    <button
      v-if="!room.inProgress"
      id="1"
      @click="openModal"
      class="btn-coop-enter"
      data-bs-toggle="modal"
      data-bs-target="#coopRoomDetail"
    >
      입장
    </button>
    <button v-if="room.inProgress" class="btn-coop-enter">진행중</button>
  </div>

  <!-- Modal -->
  <div
    class="modal fade"
    id="coopRoomDetail"
    tabindex="-1"
    aria-labelledby="coopRoomDetailLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header justify-content-space-evenly">
          <div class="d-flex align-items-center">
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
        </div>
        <div class="modal-body">
          <div class="room-detail-box d-flex">
            <div class="image-box">
              <img src="@/assets/logo.png" alt="profile image" />
            </div>
            <div class="room-detail-info">
              <h5 class="modal-title" id="coopRoomDetailLabel">
                <!-- {{ room.host }} -->
              </h5>
              <div class="modal-box d-flex">
                <div class="modal-people">
                  <!-- {{ room.curPeople }} / {{ room.maxPeople }} 명 -->
                </div>
                <!-- <div class="modal-time">{{ room.time }}분</div> -->
                <div class="modal-time"></div>
              </div>
            </div>
          </div>
          <div class="room-content">
            <p>
              <!-- {{ room.content }} -->
            </p>
          </div>
        </div>
        <div class="modal-footer justify-content-center">
          <button
            type="button"
            class="btn"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="btnEnter(getters.getRoomId)"
          >
            참여하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import jQuery from "jquery";
import { useStore } from "vuex";
import { computed } from "vue";
import Swal from "sweetalert2";
export default {
  name: "CoopItem",
  props: ["room", "tabState"],
  setup(props) {
    const store = useStore();
    const getters = computed(() => store.getters);
    const $ = jQuery;
    function btnEnter(roomId) {
      if (getters.value.getEnterCoop) {
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
        Toast.fire({
          icon: "error",
          title: "현재 참여 중인 방이 있습니다.",
        });
        return;
      }
      router.push({
        name: "CoopRoom",
        params: { roomNo: roomId },
      });
    }
    function openModal() {
      store.commit("SET_ROOM_ID", props.room.id);
      $(".modal .modal-body .modal-title").html(props.room.title);
      $(".modal .modal-body .modal-people").html(
        props.room.nowMemNum + " / " + props.room.maxMemNum
      );
      $(".modal .modal-body .modal-time").html(props.room.duration + "분");
      $(".modal .modal-body .room-content p").html(props.room.content);
    }
    return { btnEnter, openModal, getters };
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
/* Modal */
.modal-content {
  background-color: #eff7f6;
}

.dropdown > button {
  margin-right: 10px;
}

.image-box {
  width: 75px;
  height: 75px;
  border-radius: 70%;
  overflow: hidden;
  margin-right: 25px;
  border: 3px solid gold;
}

.image-box > img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  margin: 5px auto;
}

.room-detail-box {
  margin-left: 80px;
}
.room-detail-info {
  margin-top: 10px;
}

.room-detail-info > h5 {
  font-size: 20px;
  font-weight: 700;
}

.room-content {
  /* background-color: #2a9d8f; */
  /* border: 1px solid white; */
  /* border-radius: 10px; */
  width: 300px;
  margin: 20px auto 10px;
  padding: 10px;
  word-wrap: break-word;
}

.modal-header {
  border: 0;
}

.modal-footer > .btn {
  background-color: #2a9d8f;
  color: #fff;
}

.modal-time {
  margin-left: 30px;
}
</style>
