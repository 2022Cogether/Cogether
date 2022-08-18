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
import { ref } from "vue";
import CommentItem from "@/components/til/comment/CommentItem.vue";

export default {
  name: "CommentList",
  components: {
    CommentItem,
  },
  // emits: ["delComm"],
  props: {
    comments: Array,
    userId: Number,
    tilId: Number,
  },
  setup(props) {
    const commentsList = ref(props.comments);
    // watch(
    //   props.comments,
    //   () => {
    //     commentsList.value = props.comments.value;
    //     console.log(commentsList);
    //   },
    //   { immediate: true }
    // );

    const delComm = (event, tilCommentId) => {
      event;
      for (let i = 0; i < commentsList.value.length; i++) {
        if (commentsList.value[i].tilCommentId == tilCommentId) {
          commentsList.value.splice(i, 1);
        }
      }
    };
    return { props, delComm, commentsList };
  },
};
</script>

<style></style>
