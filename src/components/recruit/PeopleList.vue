<template>
  <ul class="list-group list-group-flush">
    <PeopleItem />
    <PeopleItem />
    <PeopleItem />
    <PeopleItem />
    <PeopleItem />
    <PeopleItem />
    <PeopleItem />
    <PeopleItem />
  </ul>
</template>

<script>
import PeopleItem from "@/components/recruit/PeopleItem.vue";
export default {
  components: {
    PeopleItem,
  },
};
</script>

<style scoped>
ul {
  height: 200px;
  border-top: 2px solid black;
  border-bottom: 2px solid black;
  margin-bottom: 30px;
  overflow: auto;
}

::-webkit-scrollbar {
  display: none;
}
</style>
