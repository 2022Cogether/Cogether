<template>
  <!-- 경쟁모드 -->
  <div class="compete">
    <div class="compete-nav-name fs-3">경쟁모드</div>
    <div class="compete-box round">
      <div class="row">
        <div class="col-3"></div>
        <div class="col-6 text-center">
          <div class="compete-box-title fs-3">LET'S CODE</div>
          <div v-if="getters.getIsCompeteStarted == false">
            <div class="compete-box-content fs-6">도전하세요!</div>
            <button class="btn-compete-box fs-6" @click="btnCompete">GO</button>
          </div>
          <div v-else>
            <div class="compete-box-content fs-6">시작시간: {{ now() }}</div>
            <button class="btn-compete-box fs-6" @click="btnCompete">
              STOP
            </button>
          </div>
        </div>
        <div class="col-3 text-end">
          <div class="compete-total">Total</div>
          <div class="compete-ranking">현재 순위</div>
          <div class="compete-people">참여인원</div>
        </div>
      </div>
    </div>
  </div>
  <!-- 협력모드 -->
  <div class="coop">
    <div class="coop-nav">
      <div class="coop-nav-name fs-3">협력모드</div>
      <div class="searchbar">
        <input class="input-search" type="text" />
        <button class="btn-search">
          <font-awesome-icon
            class="searchicon"
            icon=" fa-solid fa-magnifying-glass"
          />
        </button>
      </div>
      <div class="coop-tabs">
        <button class="coop-tab">전체</button>
        <button class="coop-tab">시작전</button>
        <button class="coop-tab">진행중</button>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
export default {
  name: "ChallengeView",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    function now() {
      let today = new Date();
      store.commit("SET_COMPETE_START_TIME", today);
      today.setHours(today.getHours() + 9);
      return today.toISOString().substring(11, 19);
    }
    function btnCompete() {
      if (getters.value.getIsCompeteStarted == true) {
        let today = new Date();
        today = new Date(today - getters.value.getCompeteStartTime);
        today.setHours(today.getHours() - 15);
        console.log(today.toISOString().substring(0, 19));
        // console.log(today.toISOString().substring(0, 19));
      }

      store.commit(
        "SET_IS_COMPETE_STARTED",
        !getters.value.getIsCompeteStarted
      );
    }
    // const btnCompete = () =>
    //   store.commit(
    //     "SET_IS_COMPETE_STARTED",
    //     !getters.value.getIsCompeteStarted
    //   );

    return { store, getters, btnCompete, now };
  },
  components: {},
};
</script>
<style scoped>
@media (max-width: 1000px) {
  .coop-tab {
    font-size: 0.5rem;
  }
}

button {
  background-color: white;
}

.round {
  border: 1px solid black;
  border-radius: 10px;
}

.compete {
  margin: 1%;
  margin-bottom: 5%;
}

.coop {
  margin: 1%;
}

.compete-box {
  position: relative;
  height: 150px;
}

.compete-box-title {
  font-family: "Nanum Gothic", sans-serif;
  margin-top: 3%;
}

.compete-box-content {
  margin-top: 1%;
}

.btn-compete-box {
  margin-top: 4%;
  width: 25%;
  border-radius: 10px;
  border: 1px solid black;
}

.compete-nav-name {
  display: inline-block;
  font-family: "Nanum Gothic", sans-serif;
}

.compete-total {
  margin-right: 10px;
}

.compete-ranking {
  margin-right: 10px;
}

.compete-people {
  position: absolute;
  bottom: 5px;
  right: 10px;
}

.coop-nav {
  margin-bottom: 10px;
}

.coop-tab {
  border: 1px solid black;
  display: inline-block;
  width: 30%;
  text-align: center;
  margin-right: 3%;
  border-radius: 20px;
}

.coop-tabs {
  display: inline-block;
  float: right;
  width: 40%;
}

.coop-nav-name {
  display: inline-block;
  font-family: "Nanum Gothic", sans-serif;
}

.searchbar {
  position: relative;
  display: inline-block;
  width: 30%;
  margin-left: 5%;
  border-radius: 10px;
}

.input-search {
  position: relative;
  bottom: 0%;
  width: 100%;
  border: 1px solid black;
  border-radius: 10px;
  padding-left: 5%;
  padding-right: 20%;
  margin-right: 1%;
}

.btn-search {
  position: absolute;
  top: 0%;
  height: 16px;
  margin-top: 2px;
  border: 0;
  border-top: 0px;
  border-radius: 0px;
  background-color: white;
  right: 4%;
}
</style>
