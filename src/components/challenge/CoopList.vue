<template>
  <div class="coop-room-box">
    <span v-if="tabState == 1">
      <span v-for="(room, i) in getters.getRooms" :key="i">
        <span v-if="searchText == null || room.title.indexOf(searchText) != -1">
          <coop-item :room="room" :tabState="tabState" />
        </span>
      </span>
    </span>
    <span v-else>
      <span v-for="(room, i) in getters.getRooms" :key="i">
        <span v-if="searchText == null || room.title.indexOf(searchText) != -1">
          <coop-item
            v-if="
              (tabState == 2 && !room.isStarted) ||
              (tabState == 3 && room.isStarted)
            "
            :room="room"
            :tabState="tabState"
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
  components: {
    CoopItem,
  },
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    return { store, getters };
  },
};
</script>

<style scoped></style>
