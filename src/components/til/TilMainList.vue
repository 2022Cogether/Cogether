<template>
  <div class="til-list">
    <TilMainItem v-for="til in tilList" :key="til.pk" :til="til" />
  </div>
  <TilDetail v-if="isOpen" class="isModal" />
  <!-- 나중에 TIL create창으로 URL 추가 -->
  <router-link :to="{ name: 'TilCreate' }">
    <button class="icon-body">
      <font-awesome-icon icon="fa-solid fa-pen-clip" class="pen-icon" />
    </button>
  </router-link>
</template>

<script>
import { useStore } from "vuex";
import { computed, onBeforeUnmount, onMounted } from "vue";
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

    const tilList = computed(() => {
      return store.getters.getTilList;
    });

    const modalNum = computed(() => {
      return getters.value.getOpenTil;
    });
    const isOpen = computed(() => {
      return modalNum.value != -1;
    });

    // created 할 때 한 번 발생하고, 이후로 끝까지 스크롤하면 계속 실행되어 til list에 추가하는 방식
    const getTilList = () => {
      store.dispatch("fetchTilList", { userId: 1 });
    };
    // const eraseTilList = () => {
    //   store.dispatch("removeTilList");
    // };

    // 페이지가 생성될 때 || 페이지에서 나가기 직전 list를 지움
    // 현재 오작동이 잦아서 고민 중..
    // eraseTilList();
    onBeforeUnmount(() => {
      // eraseTilList();
    });
    // 페이지가 Created 될 때 list 가져옴
    getTilList();

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
      getTilList,
      // eraseTilList,
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
