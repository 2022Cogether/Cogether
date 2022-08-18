<template>
  <div class="comment-list">
    <comment-item
      v-for="comment in props.comments"
      :userId="props.userId"
      :tilId="props.tilId"
      :comment="comment"
      :key="comment.commentId"
      @delComm="delComm"
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
  emits: ["delComm"],
  props: {
    comments: Array,
    userId: Number,
    tilId: Number,
  },
  setup(props, { emit }) {
    const delComm = () => {
      emit("delComm");
    };
    return { props, delComm };
  },
};
</script>

<style></style>
