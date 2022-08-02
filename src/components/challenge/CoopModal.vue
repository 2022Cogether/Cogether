<template>
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
                {{ room.host }}
              </h5>
              <div class="modal-box d-flex">
                <div class="modal-people">
                  {{ room.curPeople }} / {{ room.maxPeople }} 명
                </div>
                <div class="modal-time">{{ room.time }}분</div>
              </div>
            </div>
          </div>
          <div class="room-content">
            <p>
              {{ room.content }}
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
export default {
  name: "CoopModal",
  props: ["room"],
};
</script>

<style scoped>
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
