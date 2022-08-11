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
  <!-- TilMainList -->
  <TilMainList />
</template>
<script>
import TilMainList from "@/components/til/TilMainList.vue";

import { computed, ref } from "vue";
import { useStore } from "vuex";

export default {
  name: "MainView",
  components: {
    TilMainList,
  },
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);

    const searchWord = ref("");

    const onSubmit = () => {
      const payload = {
        keyword: searchWord.value,
        userId: getters.value.getCurrentUser,
      };
      console.log(payload);
      store.dispatch("searchTil", payload);
    };

    return {
      searchWord,
      onSubmit,
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
</style>
