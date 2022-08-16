<template>
  <div class="comment-list">
    <comment-item
      v-for="comment in props.comments"
      :userId="props.userId"
      :tilId="props.tilId"
      :comment="comment"
      :key="comment.commentId"
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
  props: {
    comments: Array,
    userId: Number,
    tilId: Number,
  },
  setup(props) {
    return { props };
  },
};
</script>

<style>
.comment-list {
  border: 1px solid blue;
}
</style>
