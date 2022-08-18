<template>
  <div class="big-btn d-flex justify-content-evenly">
    <button type="button" @click="tabA1" class="btn btn-lg">전체</button>
    <button type="button" @click="tabA2" class="btn btn-lg">팔로잉</button>
  </div>
  <div class="small-btn d-flex">
    <button type="button" @click="tabB1" class="btn btn-sm">TIL</button>
    <button type="button" @click="tabB2" class="btn btn-sm">LV</button>
    <button type="button" @click="tabB3" class="btn btn-sm">경쟁</button>
  </div>
  <div class="dropdown d-flex justify-content-end">
    <button
      class="btn btn-sm dropdown-toggle"
      type="button"
      data-bs-toggle="dropdown"
      aria-expanded="false"
    >
      {{ state.tabC }}
    </button>
    <ul class="dropdown-menu">
      <li @click="tabC1" class="dropdown-item">누적</li>
      <li @click="tabC2" class="dropdown-item">주간</li>
      <li @click="tabC3" class="dropdown-item">월간</li>
    </ul>
  </div>
  <RankingList :tabA="state.tabA" :tabB="state.tabB" :tabC="state.tabC" />
</template>

<script>
import RankingList from "@/components/ranking/RankingList.vue";
import { reactive } from "vue";
import { useStore } from "vuex";
export default {
  setup() {
    const store = useStore();
    const state = reactive({
      page: 1,
      tabA: "전체",
      tabB: "TIL",
      tabC: "누적",
    });
    store.dispatch("getTilListAll", state.page);
    function getData() {
      if (state.tabA == "전체") {
        if (state.tabB == "TIL") {
          console.log("전체TIL");
          store.dispatch("getTilListAll", state.page);
        } else if (state.tabB == "LV") {
          console.log("전체LV");
          store.dispatch("getLvListAll", state.page);
        } else if (state.tabB == "경쟁") {
          console.log("전체경쟁");
          store.dispatch("getCompListAll", state.page);
        }
      }
      //팔로잉
      else if (state.tabA == "팔로잉") {
        if (state.tabB == "TIL") {
          console.log("팔로잉TIL");
          store.dispatch("getTilListFol", state.page);
        } else if (state.tabB == "LV") {
          console.log("팔로잉LV");
          store.dispatch("getLvListFol", state.page);
        } else if (state.tabB == "경쟁") {
          console.log("팔로잉경쟁");
          store.dispatch("getCompListFol", state.page);
        }
      }
    }
    function tabA1() {
      state.tabA = "전체";
      getData();
    }
    function tabA2() {
      state.tabA = "팔로잉";
      getData();
    }
    function tabB1() {
      state.tabB = "TIL";
      getData();
    }
    function tabB2() {
      state.tabB = "LV";
      getData();
    }
    function tabB3() {
      state.tabB = "경쟁";
      getData();
    }
    function tabC1() {
      state.tabC = "누적";
      getData();
    }
    function tabC2() {
      state.tabC = "주간";
      getData();
    }
    function tabC3() {
      state.tabC = "월간";
      getData();
    }

    return {
      state,
      getData,
      tabA1,
      tabA2,
      tabB1,
      tabB2,
      tabB3,
      tabC1,
      tabC2,
      tabC3,
    };
  },
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

.dropdown-item:hover {
  cursor: pointer;
}
</style>
