<template>
  <div class="til-list">
    <TilMainItem v-for="(item, idx) in 20" :key="idx" v-bind:idx="idx" />
  </div>
  <TilDetail v-if="isOpen" />
  <!-- 나중에 TIL create창으로 URL 추가 -->
  <a href="">
    <button class="icon-body" @click="showChatList">
      <font-awesome-icon icon="fa-solid fa-pen-clip" class="pen-icon" />
    </button>
  </a>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "vue";
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

    const modalNum = getters.value.getOpenTil;
    const isOpen = modalNum != -1;

    // created 할 때 한 번 발생하고, 이후로 끝까지 스크롤하면 계속 실행되어 til list에 추가하는 방식
    const getTilList = () => {
      store.dispatch("fetchTilList", { userId: 1 });
    };
    const eraseTilList = () => {
      store.dispatch("removeTilList");
    };

    eraseTilList();
    getTilList();

    // 참조: https://renatello.com/check-if-a-user-has-scrolled-to-the-bottom-in-vue-js/
    const scroll = () => {
      window.onscroll = () => {
        let bottomOfWindow =
          Math.max(
            window.pageYOffset,
            document.documentElement.scrollTop,
            document.body.scrollTop
          ) +
            window.innerHeight >
          document.documentElement.offsetHeight;

        if (bottomOfWindow) {
          getTilList();
        }
      };
    };

    return {
      getTilList,
      eraseTilList,
      scroll,
      modalNum,
      isOpen,
    };
  },
  mounted() {
    this.scroll();
  },
};
</script>

<style scoped>
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
