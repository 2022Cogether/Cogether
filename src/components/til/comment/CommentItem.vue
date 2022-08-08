<template>
  <div class="comment-item">
    <div class="container">
      <div class="be-comment-block">
        <div class="be-comment">
          <div class="d-flex justify-content-between">
            <div>
              <span class="be-comment-name">
                <a href="#">{{ comment.user_id }}</a>
              </span>
              <span class="be-comment-time">
                <i class="fa fa-clock-o"></i>
                {{ comment.created_at }}
              </span>
            </div>
            <font-awesome-icon
              @click="deleteComment"
              class="fa-lg"
              icon="fa-solid fa-rectangle-xmark"
            />
          </div>
          <p class="be-comment-text">
            {{ comment.content }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useStore } from "vuex";
import { computed } from "vue";

export default {
  name: "CommentItem",
  props: {
    comment: Object,
    userId: Number,
    tilPk: Number,
  },
  setup(props) {
    const store = useStore();

    const currentUser = store.getters.getCurrentUser;

    const isWriter = computed(() => {
      return currentUser.pk == props.userId;
    });
    const isCommenter = computed(() => {
      return currentUser.pk == props.comment.user_id;
    });

    const deleteComment = () => {
      const payload = {
        commentId: props.comment.pk,
        tilId: props.tilPk,
      };

      store.dispatch("removeComments", payload);
    };

    return {
      isWriter,
      isCommenter,
      deleteComment,
    };
  },
};
</script>
<style>
.comment-item {
  width: 97%;
}

.be-comment-block {
  border: 1px solid #edeff2;
  border-radius: 2px;
  border: 1px solid #ffffff;
}

.be-comment-content span {
  display: inline-block;
  width: 49%;
  margin-bottom: 15px;
}

.be-comment-name {
  font-size: 13px;
  font-family: "Conv_helveticaneuecyr-bold";
}

.be-comment-content a {
  color: #383b43;
}

.be-comment-content span {
  display: inline-block;
  width: 49%;
  margin-bottom: 15px;
}

.be-comment-time {
  text-align: right;
}

.be-comment-time {
  font-size: 11px;
  color: #b4b7c1;
}

.be-comment-text {
  font-size: 13px;
  line-height: 18px;
  color: #7a8192;
  display: block;
  background: #f6f6f7;
  border: 1px solid #edeff2;
  padding: 15px 20px 20px 20px;
}
</style>
