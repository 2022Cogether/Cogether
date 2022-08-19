<template>
  <div class="comment-list">
    <comment-item
      v-for="comment in commentsList"
      :userId="props.userId"
      :tilId="props.tilId"
      :comment="comment"
      :key="comment.commentId"
      @delComm="delComm"
      @editComm="editComm"
    >
    </comment-item>
  </div>
</template>

<script>
import { computed } from "vue";
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
  emits: ["delComm"],
  setup(props, { emit }) {
    const commentsList = computed(() => props.comments);

    const delComm = (event, tilCommentId) => {
      event;
      emit("delComm", event, tilCommentId);
    };

    const editComm = (event, tilCommentId, updateComment) => {
      event;
      emit("editComm", event, tilCommentId, updateComment);
    };
    return { props, delComm, editComm, commentsList };
  },
};
</script>

<style></style>
