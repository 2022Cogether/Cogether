<template>
  <img
    v-if="props.til.imgUrl.length != 0"
    :src="props.til.imgUrl[0].imgUrl"
    alt="My TIL image"
  />
  <img v-else src="" alt="My TIL image" />
</template>

<script>
export default {
  name: "ProfileTil",
  props: {
    til: Object,
  },
  setup(props) {
    return { props };
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
