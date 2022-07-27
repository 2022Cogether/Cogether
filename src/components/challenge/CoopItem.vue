<template>
  <div class="coop-room-item round">
    <a href="#"
      ><img class="coop-profile" src="@/assets/logo.png" alt="로고"
    /></a>
    <span class="coop-title fs-5">방제목</span>
    <span class="coop-time fs-6">분</span>
    <div class="coop-bottom">
      <span class="coop-people">/ 명</span>
      <span class="icon-people"></span>
    </div>
    <button
      class="btn-coop-enter"
      data-bs-toggle="modal"
      data-bs-target="#coopRoomDetail"
    >
      입장
    </button>
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
          <div class="profile-detail-box d-flex justify-content-center">
            <img src="@/assets/logo.png" alt="profile image" />
            <div class="profile-detail-info">
              <h5 class="modal-title" id="personDetailInfoLabel">꼬꼬</h5>
              <p>나 좀 데려가...</p>
              <div class="tech-icon-box">
                <img
                  class="tech-icon"
                  src="@/assets/devicon/javascript-original.svg"
                  alt="tech icon"
                />
              </div>
            </div>
          </div>
          <div class="user-introduction">
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do e
            </p>
          </div>
        </div>
        <div class="modal-footer justify-content-center">
          <button
            type="button"
            class="btn btn-primary"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="btnEnter"
          >
            <font-awesome-icon icon="fa-solid fa-comments" />
            DM 보내기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";
export default {
  name: "CoopItem",
  setup() {
    function btnEnter() {
      router.push({ name: "CoopRoom" });
    }

    return { btnEnter };
  },
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
  background-color: #dbdbda;
}
/* ///Modal/// */

.btn-coop-room-dropdown {
  border: 0px;
  background-color: transparent;
}
.tech-icon-box {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background-color: #d8f3dc;
  margin-bottom: 7px;
}

.tech-icon {
  display: block;
  width: 13px;
  height: 13px;
  margin: 0 auto;
}

h3 {
  font-size: 15px;
  font-weight: 700;
  margin-bottom: 4px;
}

p {
  font-size: 12px;
  margin: 0;
}

.bookmark-icon {
  font-size: 25px;
  padding: 0 10px;
}

li {
  padding: 0;
}

.dropdown > button {
  margin-right: 10px;
}

.profile-detail-box > img {
  width: 60px;
  height: 60px;
  margin-right: 15px;
  margin-top: 5px;
}

.profile-detail-info > h5 {
  font-size: 20px;
  font-weight: 700;
}

.user-introduction {
  background-color: gray;
  border: 1px solid white;
  border-radius: 10px;
  width: 300px;
  margin: 30px auto 10px;
}
</style>
