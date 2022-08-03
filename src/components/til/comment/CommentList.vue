<template>
  <div class="comment-list">
    <comment-item
      v-for="comment in comments"
      :comment="comment"
      :key="comment.pk"
    >
    </comment-item>
  </div>
</template>

<script>
import CommentItem from "@/components/til/comment/CommentItem.vue";

export default {
  name: "CommentList",
  components: {
    CommentItem,
  },
  props: { comments: Array },
};
</script>

<style>
.comment-list {
  border: 1px solid blue;
}
</style>
