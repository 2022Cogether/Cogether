<template>
  <div class="coop-room-box">
    <span v-if="tabState == 1">
      <span v-for="(room, i) in getters.getRooms" :key="i">
        <span v-if="searchText == null || room.title.indexOf(searchText) != -1">
          <coop-item
            :room="room"
            :tabState="tabState"
            :isEnterCoop="getters.getEnterCoop"
          />
        </span>
      </span>
    </span>
    <span v-else>
      <span v-for="(room, i) in getters.getRooms" :key="i">
        <span v-if="searchText == null || room.title.indexOf(searchText) != -1">
          <coop-item
            v-if="
              (tabState == 2 && !room.inProgress) ||
              (tabState == 3 && room.inProgress)
            "
            :room="room"
            :tabState="tabState"
            :isEnterCoop="getters.getEnterCoop"
          />
        </span>
      </span>
    </span>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import CoopItem from "@/components/challenge/CoopItem.vue";
export default {
  name: "CoopList",
  props: ["tabState", "searchText"],
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    store.dispatch("getCoopRooms", getters.value.getLoginUserId); //테스트
    return { store, getters };
  },
  components: {
    CoopItem,
  },
};
</script>

<style scoped>
.coop-room-box {
  max-height: 650px;
  overflow: scroll;
}

::-webkit-scrollbar {
  display: none;
}
</style>
