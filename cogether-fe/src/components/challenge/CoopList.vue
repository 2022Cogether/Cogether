<template>
  <div class="coop-room-box">
    <span v-if="tabState == 1">
      <span v-for="(room, index) in getters.getRooms" :key="index">
        <span v-if="searchText == null || room.title.indexOf(searchText) != -1">
          <coop-item :room="room" :index="index" @setModal="setModal" />
        </span>
      </span>
    </span>
    <span v-else>
      <span v-for="(room, index) in getters.getRooms" :key="index">
        <span v-if="searchText == null || room.title.indexOf(searchText) != -1">
          <coop-item
            v-if="
              (tabState == 2 && !room.inProgress) ||
              (tabState == 3 && room.inProgress)
            "
            :room="room"
            :index="index"
            @setModal="setModal"
          />
        </span>
      </span>
    </span>
  </div>
  <!-- 모달창 -->
  <div
    v-if="emitCoop != null"
    class="modal fade"
    id="coopRoomDetail"
    tabindex="-1"
    aria-labelledby="coopRoomDetailLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header justify-content-space-evenly">
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="room-detail-box d-flex">
            <div class="profile-img-box">
              <img
                class="profile-img"
                :src="emitCoop.userImgUrl"
                alt="profile image"
              />
            </div>
            <div class="room-detail-info">
              <h5 class="modal-title" id="coopRoomDetailLabel">
                {{ emitCoop.userName }}
              </h5>
              <div class="modal-box d-flex">
                <div class="modal-people">
                  {{ emitCoop.nowMemNum }} / {{ emitCoop.maxMemNum }} 명
                </div>
                <div class="modal-time">{{ emitCoop.duration }}분</div>
                <div class="modal-time"></div>
              </div>
            </div>
          </div>
          <div class="room-content">
            <p>
              {{ emitCoop.content }}
            </p>
          </div>
        </div>
        <div class="modal-footer justify-content-center">
          <button
            type="button"
            class="btn"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="btnEnter"
          >
            참여하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CoopItem from "@/components/challenge/CoopItem.vue";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import router from "@/router";
import Swal from "sweetalert2";
export default {
  name: "CoopList",
  props: ["tabState", "searchText"],
  setup() {
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
    const store = useStore();
    const getters = computed(() => store.getters);
    store.dispatch("getCoopRooms", getters.value.getLoginUserId);
    const stompClientCoop = computed(() => store.getters.getStompClientCoop);

    let emitCoop = ref("");
    let index = ref("");
    function setModal(room, idx) {
      emitCoop.value = room;
      index.value = idx;
    }

    async function btnEnter() {
      const data = {
        userId: getters.value.getLoginUserId,
        liveCoopId: emitCoop.value.id,
      };
      //참여 중인 방 없으면 참가
      var flag = false;
      if (!getters.value.getEnterCoop) {
        await store.dispatch("CreateCoopMember", data).then((res) => {
          //인원이 꽉차면 방이 꽉 찼다고 alert
          if (res.liveCoopId == 0) {
            Toast.fire({
              icon: "warning",
              title: "방의 인원이 초과되었습니다.",
            });
            router.push({ name: "ChallengeMain" });
          } else {
            //입장했다고 알림
            const data = {
              liveCoopId: res.liveCoopId,
              mode: 0,
            };
            stompClientCoop.value.send(
              "/receive/coop",
              JSON.stringify(data),
              {}
            );
            flag = true;
            store.dispatch("getDetailCoopRoom", emitCoop.value.id);
          }
        });
        //참여 중인 방 있으면 첫번째 방만 참가
      } else if (index.value == 0) {
        store.dispatch("getDetailCoopRoom", emitCoop.value.id);
        flag = true;
        //그 외는 참가불가
      } else {
        Toast.fire({
          icon: "warning",
          title: "현재 참여 중인 방이 있습니다.",
        });
      }
      if (flag) {
        store.commit("SET_ROOM_ID", emitCoop.value.id);
        store.commit("SET_ROOM", emitCoop.value);
        router.push({
          name: "CoopRoom",
          params: { roomNo: emitCoop.value.id },
        });
      }
    }
    return { store, getters, btnEnter, setModal, emitCoop };
  },
  components: {
    CoopItem,
  },
};
</script>

<style scoped>
.coop-room-box {
  max-height: 650px;
  overflow: scroll;
}

::-webkit-scrollbar {
  display: none;
}

/* Modal */
.modal-content {
  background-color: #eff7f6;
}

.dropdown > button {
  margin-right: 10px;
}

.profile-img-box {
  width: 65px;
  height: 65px;
  border-radius: 70%;
  overflow: hidden;
  margin: 10px;
  text-align: center;
}

.profile-img {
  width: 100%;
  height: 100%;
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
  transition: all 0.2s linear;
}

.btn:hover {
  transform: scale(1.1);
}

.modal-time {
  margin-left: 30px;
}
</style>
