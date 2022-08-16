<template>
  <img :src="props.til.imgUrl[0].imgUrl" alt="My TIL image" />
</template>

<script>
export default {
  name: "ProfileTil",
  props: {
    til: Object,
  },
  setup(props) {
    console.log("props til", props.til);
    return { props };
  },
};
</script>

<style scoped>
img {
  width: 200px;
  height: 200px;
  margin: 10px;
}
</style>
