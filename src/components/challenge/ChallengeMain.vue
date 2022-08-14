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
            <div class="compete-box-content fs-6">
              시작시간 {{ getters.getCompeteStartTime }}
            </div>
            <button class="btn-compete-box fs-6" @click="btnCompete">
              STOP
            </button>
          </div>
        </div>
        <div class="col-3 text-end">
          <div class="compete-total">
            Total: {{ getters.getCompeteTotal }}분
          </div>
          <div class="compete-ranking">
            Rank: {{ getters.getCompeteRank }}등
          </div>
          <!-- <div class="compete-people">
            참여인원: {{ getters.getCompetePeople }}
          </div> -->
        </div>
      </div>
    </div>
  </div>
  <!-- 협력모드 -->
  <div class="coop">
    <div class="coop-nav">
      <div class="coop-nav-name fs-3">협력모드</div>
      <div class="searchbar">
        <input class="input-search" type="text" v-model="state.tempText" />
        <button class="btn-search" @click="search">
          <font-awesome-icon
            class="searchicon"
            icon=" fa-solid fa-magnifying-glass"
          />
        </button>
      </div>
      <div class="coop-tabs">
        <button class="coop-tab" @click="btnTab1">전체</button>
        <button class="coop-tab" @click="btnTab2">시작전</button>
        <button class="coop-tab" @click="btnTab3">진행중</button>
      </div>
    </div>
    <coop-list :tabState="state.tabState" :searchText="state.searchText" />
  </div>
  <button class="icon-body" @click="coopCreate">
    <font-awesome-icon icon="fa-solid fa-gamepad" class="gamepad-icon" />
  </button>
</template>

<script>
import { computed, reactive } from "vue";
import { useStore } from "vuex";
import router from "@/router";
import CoopList from "@/components/challenge/CoopList.vue";
import Swal from "sweetalert2";
export default {
  name: "ChallengeView",
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    store.dispatch("getCompeteInfo", getters.value.getLoginUserId);
    const state = reactive({
      tabState: 1,
      tempText: null,
      searchText: null,
    });

    function now() {
      let today = new Date();
      today.setHours(today.getHours() + 9);
      today = today.toISOString().substring(11, 19);
      store.commit("SET_COMPETE_START_TIME", today);
    }

    function btnCompete() {
      if (!getters.value.getCompeteInterval) {
        //interval 중복 실행 방지
        store.commit("SET_COMPETE_INTERVAL", true);
        const interval = setInterval(() => {
          if (getters.value.getIsCompeteStarted) {
            //compete 켜진 상태면 보내기
            store.dispatch("sendCompeteTime", getters.value.getLoginUserId);
            store.dispatch("getCompeteInfo", getters.value.getLoginUserId);
          }
          if (
            !getters.value.getIsCompeteStarted ||
            getters.value.getLoginUserId == ""
          ) {
            //compete 꺼진 상태면 보내지 않기
            store.commit("SET_COMPETE_INTERVAL", false); //interval상태값 끄기
            clearInterval(interval);
          }
        }, 1000);
      }
      //compete상태 전환
      store.commit(
        "SET_IS_COMPETE_STARTED",
        !getters.value.getIsCompeteStarted
      );
    }

    function btnTab1() {
      state.tabState = 1;
    }

    function btnTab2() {
      state.tabState = 2;
    }

    function btnTab3() {
      state.tabState = 3;
    }

    function search() {
      state.searchText = state.tempText;
    }

    function coopCreate() {
      if (!getters.value.getEnterCoop) {
        router.push({ name: "CoopCreate" });
      } else {
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
          icon: "warning",
          title: "현재 참여 중인 방이 있습니다.",
        });
      }
    }
    return {
      store,
      getters,
      btnCompete,
      now,
      state,
      btnTab1,
      btnTab2,
      btnTab3,
      search,
      coopCreate,
    };
  },
  components: {
    CoopList,
  },
};
</script>
<style scoped>
@media (max-width: 1000px) {
  .coop-tab {
    font-size: 0.5rem;
  }
}

button {
  background-color: transparent;
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

.coop-tab:hover {
  background-color: #2a9d8f;
  color: white;
}

.compete-box {
  position: relative;
  height: 150px;
  border: 1px solid #dbdbda;
  box-shadow: 0px 5px 3px -3px #bdbdbd;
  background-color: #c1ebe6;
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
  border-radius: 5px;
  border: 0px;
  background-color: white;
}

.btn-compete-box:hover {
  background-color: #2a9d8f;
  color: white;
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
  background-color: transparent;
}

.btn-search {
  position: absolute;
  top: 0%;
  height: 16px;
  margin-top: 2px;
  border: 0;
  border-top: 0px;
  border-radius: 0px;
  background-color: transparent;
  right: 4%;
}

.icon-body {
  border: 0px;
  width: 60px;
  height: 60px;
  background-color: #2a9d8f;
  color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 5px;
  right: 25vw;
  box-shadow: 0px 3px rgba(0, 0, 0, 0.3);
}

.gamepad-icon {
  font-size: 30px;
}
</style>
