<template>
  <div class="comment-list">
    <comment-item
      v-for="comment in commentsList"
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
      // console.log(tilCommentId);
      // for (let i = 0; i < commentsList.value.length; i++) {
      //   if (commentsList.value[i].tilCommentId == tilCommentId) {
      //     console.log(i);
      //     console.log(commentsList.value[i]);
      //     console.log(commentsList.value.splice(i, 1));
      //     break;
      //   }
      // }
      emit("delComm", event, tilCommentId);
    };
    return { props, delComm, commentsList };
  },
};
</script>

<style></style>
