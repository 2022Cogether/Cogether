<template>
  <table class="table table-hover table-responsive">
    <thead>
      <tr>
        <th v-if="tabA == '전체'" scope="col">Ranking(전체)</th>
        <th v-if="tabA == '팔로잉'" scope="col">Ranking(팔로우)</th>
        <th scope="col">닉네임</th>
        <th v-if="tabB == 'TIL'" scope="col">TIL 개수</th>
        <th v-if="tabB == 'LV'" scope="col">LV(exp)</th>
        <th v-if="tabB == '경쟁'" scope="col">경쟁시간</th>
      </tr>
    </thead>
    <tbody class="table-group-divider">
      <tr class="myrank">
        <th scope="row">{{ data.myRank }}</th>
        <td>{{ data.userNickname }}</td>
        <td>{{ data.myContent }}</td>
      </tr>
      <tr v-for="(item, i) in list" :key="i">
        <th scope="row">{{ item.rank }}</th>
        <td>{{ item.userNickname }}</td>
        <td>{{ item.content }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
export default {
  props: ["tabA", "tabB", "tabC"],
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    const data = computed(() => getters.value.getRankingList);
    const list = computed(() => getters.value.getRankingList.rankList);
    return { list, data };
  },
  components: {},
};
</script>

<style scoped>
.myrank {
  background-color: #c1ebe6;
}
</style>
