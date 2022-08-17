<template>
  <div class="til-list">
    <TilMainItem v-for="til in tilList.tilList" :key="til.tilId" :util="til" />
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
import { computed, ref } from "vue";
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

    const tilList = ref([]);

    (async () => {
      await store.dispatch("fetchTilList");
      tilList.value = computed(() => {
        return store.getters.getTilList;
      }).value;
      console.log("으앙", tilList.value);
    })();

    const modalNum = computed(() => {
      return getters.value.getOpenTil;
    });
    const isOpen = computed(() => {
      return modalNum.value != -1;
    });

    return {
      scroll,
      modalNum,
      isOpen,
      tilList,
    };
  },
};
</script>

<style scoped>
.isModal {
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.3);
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
