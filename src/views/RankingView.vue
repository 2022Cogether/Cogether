<template>
  <div class="big-btn d-flex justify-content-evenly">
    <button type="button" class="btn btn-lg">전체</button>
    <button type="button" class="btn btn-lg">팔로잉</button>
  </div>
  <div class="small-btn d-flex">
    <button type="button" class="btn btn-sm">TIL</button>
    <button type="button" class="btn btn-sm">LV</button>
    <button type="button" class="btn btn-sm">경쟁</button>
  </div>
  <div class="dropdown d-flex justify-content-end">
    <button
      class="btn btn-sm dropdown-toggle"
      type="button"
      data-bs-toggle="dropdown"
      aria-expanded="false"
    >
      기간
    </button>
    <ul class="dropdown-menu">
      <li><a class="dropdown-item" href="#">주간</a></li>
      <li><a class="dropdown-item" href="#">월간</a></li>
      <li><a class="dropdown-item" href="#">누적</a></li>
    </ul>
  </div>
  <RankingList />
</template>

<script>
import RankingList from "@/components/ranking/RankingList.vue";

export default {
  components: {
    RankingList,
  },
};
</script>

<style scoped>
button {
  color: #000;
  /* border: 3px solid #2a9d8f; */
  background-color: #c1ebe6;
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
}

button:hover {
  color: #fff;
  background-color: #2a9d8f;
}

.big-btn {
  margin: 20px;
}

.big-btn > .btn {
  width: 150px;
  height: 50px;
  font-size: 20px;
}

.small-btn {
  margin: 60px 0 0 20px;
}

.small-btn > .btn {
  width: 60px;
  height: 30px;
  font-size: 12px;
  border-radius: 30px;
  margin: 0 0 10px 10px;
}
</style>
