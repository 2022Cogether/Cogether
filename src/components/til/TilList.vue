<template>
  <!-- 검색바 -->
  <div class="search-bar-block">
    <div class="search-bar">
      <input
        class="input-search"
        type="text"
        v-model="searchWord"
        @keyup.enter.prevent="onSubmit"
      />
      <button @click="onSubmit" class="btn-search">
        <font-awesome-icon
          icon="fa-solid fa-magnifying-glass"
          class="icon-search"
        />
      </button>
    </div>
  </div>
  <!-- TIL 리스트 -->
  <div class="til-list">
    <TilMainItem v-for="til in tilList" :key="til.pk" :til="til" />
  </div>
  <TilDetail v-if="isOpen" class="isModal" />
  <!-- TIL create -->
  <router-link :to="{ name: 'TilCreate' }">
    <button class="icon-body">
      <font-awesome-icon icon="fa-solid fa-pen-clip" class="pen-icon" />
    </button>
  </router-link>
</template>

<script>
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import { computed, onBeforeUnmount, onMounted, ref } from "vue";
import TilMainItem from "@/components/til/TilMainItem.vue";
import TilDetail from "@/components/til/TilDetail.vue";

export default {
  name: "TilMainList",
  components: {
    TilMainItem,
    TilDetail,
  },
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const route = useRoute();

    const userId = route.params.userId;

    const searchWord = ref("");

    const isLoggedIn = getters.value.isLoggedIn;

    const onSubmit = () => {
      const payload = {
        keyword: searchWord.value,
        userId: getters.value.getCurrentUser,
      };
      console.log(payload);
      store.dispatch("searchTil", payload);
    };

    const tilList = store.getters.getTilList;

    const modalNum = computed(() => {
      return getters.value.getOpenTil;
    });
    const isOpen = computed(() => {
      return modalNum.value != -1;
    });

    // created 할 때 한 번 발생하고, 이후로 끝까지 스크롤하면 계속 실행되어 til list에 추가하는 방식
    function getTilList() {
      store.dispatch("fetchTilList", { userId: userId });
    }
    const eraseTilList = () => {
      store.dispatch("removeTilList");
    };

    // 페이지가 Created 될 때 list 가져옴
    getTilList();
    // 페이지에서 나가기 직전 list를 지움
    onBeforeUnmount(() => {
      eraseTilList();
    });

    // // 참조: https://renatello.com/check-if-a-user-has-scrolled-to-the-bottom-in-vue-js/
    // // 스크롤 거의 하단에 오면 추가 리스트 받아고는 메소드
    // const scroll = () => {
    //   window.onscroll = () => {
    //     let bottomOfWindow =
    //       Math.max(
    //         window.pageYOffset,
    //         document.documentElement.scrollTop,
    //         document.body.scrollTop
    //       ) +
    //         window.innerHeight >
    //       document.documentElement.offsetHeight;

    //     if (bottomOfWindow) {
    //       if (window.location.href == "http://localhost:8080/#/") {
    //         getTilList();
    //       }
    //     }
    //   };
    // };

    // onMounted(() => {
    //   scroll();
    // });

    return {
      searchWord,
      isLoggedIn,
      onSubmit,
      getTilList,
      eraseTilList,
      scroll,
      modalNum,
      isOpen,
      tilList,
      onBeforeUnmount,
      onMounted,
    };
  },
};
</script>

<style scoped>
.search-bar-block {
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: center;
}

.search-bar {
  border: 1px solid black;
  background-color: #e6e6e6;
  display: inline-block;
  width: 50%;
  border-radius: 10px;
}

.btn-search {
  width: 10%;
  border: 0px;
  border-radius: 10px;
  background-color: #e6e6e6;
  position: relative;
  top: 5%;
  left: 2%;
}

.input-search {
  width: 85%;
  border: 0px;
  border-radius: 10px 0px 0px 10px;

  padding-right: 3%;
  margin-right: 1%;
  background-color: #e6e6e6;
  line-height: 55px;
}

.icon-search {
  font-size: 1.5rem;
}

.isModal {
  width: 100vw;
  height: 100vh;
  background-color: rgba(255, 255, 255, 0.5);
  display: flex;
}

.til-list {
  margin-top: 5%;
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

.pen-icon {
  font-size: 30px;
}
</style>
