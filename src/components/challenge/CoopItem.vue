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
    <button class="btn-coop-enter" @click="btnEnter">입장</button>
  </div>
</template>

<script>
import Swal from "sweetalert2";

export default {
  name: "CoopItem",
  setup() {
    function btnEnter() {
      Swal.fire({
        html: `<img class="coop-profile" src="http://placeimg.com/640/480/any" alt="로고"
    /></a>  ss`,
      });
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
  position: absolute;
  bottom: 7%;
  right: 7%;
}
</style>
