<template>
  <h2>Study</h2>
  <ul class="list-group list-group-flush">
    <StudyItem />
    <StudyItem />
    <StudyItem />
    <StudyItem />
    <StudyItem />
  </ul>
</template>

<script>
import StudyItem from "@/components/recruit/StudyItem.vue";

export default {
  name: "StudyList",
  props: ["searchText"],
  components: {
    StudyItem,
  },
};
</script>

<style></style>
