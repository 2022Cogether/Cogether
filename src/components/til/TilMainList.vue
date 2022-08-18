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

    const searchWord = ref("");

    const onSubmit = async () => {
      if (searchWord.value) {
        await store.dispatch("searchTil", searchWord.value);
      } else {
        await store.dispatch("fetchTilList");
      }
      tilList.value = computed(() => {
        return store.getters.getTilList;
      }).value;
    };

    return {
      modalNum,
      isOpen,
      tilList,

      searchWord,
      onSubmit,
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

input:focus {
  outline: none;
}
</style>
