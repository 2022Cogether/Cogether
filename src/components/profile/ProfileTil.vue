<template>
  <img
    v-if="til.imgUrl.length != 0"
    :src="til.imgUrl[0].imgUrl"
    alt="My TIL image"
    @click="openModal"
  />
  <img v-else src="" alt="My TIL image" @click="openModal" />
  <TilDetail v-if="isOpen" @closeModal="closeModal" :util="til" />
</template>

<script>
import { ref } from "vue";
import TilDetail from "@/components/til/TilDetail.vue";

export default {
  name: "ProfileTil",
  components: {
    TilDetail,
  },
  props: {
    util: Object,
  },
  setup(props) {
    const til = ref(props.util);
    console.log(til.value);

    const isOpen = ref(false);
    const openModal = () => {
      isOpen.value = true;
    };
    const closeModal = () => {
      isOpen.value = false;
    };

    return { props, openModal, closeModal, isOpen, til };
  },
};
</script>

<style scoped>
img {
  width: 28%;
  height: auto;
  margin: 10px;
}
</style>
