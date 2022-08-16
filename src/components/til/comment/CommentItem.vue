<template>
  <div class="comment-item">
    <div class="container">
      <div class="be-comment-block">
        <div class="be-comment">
          <div class="d-flex justify-content-between">
            <div>
              <span class="be-comment-name">
                <a href="#">{{ comment.userId }}</a>
              </span>
              <span class="be-comment-time">
                <i class="fa fa-clock-o"></i>
                {{ comment.created_at }}
              </span>
            </div>
            <div>
              <font-awesome-icon
                v-if="isCommenter && !isEdit"
                @click="onEdit"
                class="fa-lg m-1"
                icon="fa-solid fa-pen-to-square"
                style="cursor: pointer"
              />
              <font-awesome-icon
                v-if="isCommenter || isWriter"
                @click="deleteComment"
                class="fa-lg m-1"
                icon="fa-solid fa-rectangle-xmark"
                style="cursor: pointer"
              />
            </div>
          </div>
          <p v-if="!isEdit" class="be-comment-text">
            {{ comment.commentContent }}
          </p>
          <input
            v-else
            type="text"
            class="comment-input"
            @keyup.enter.prevent="editComment"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useStore } from "vuex";
import { ref, computed } from "vue";

export default {
  name: "CommentItem",
  props: {
    comment: Object,
    userId: Number,
    tilId: Number,
  },
  setup(props) {
    const store = useStore();

    const loginUserId = store.getters.getLoginUserId;

    // 유저 인증(isWrite -> 현재 유저가 TIL 글쓴이 isCommenter -> 현재 유저가 이 댓글 작성자)
    const isWriter = computed(() => {
      return props.userId == loginUserId;
    });
    const isCommenter = computed(() => {
      return props.comment.userId == loginUserId;
    });

    // 코멘트 지우기
    const deleteComment = () => {
      const payload = {
        commentId: props.comment.commentId,
        tilId: props.tilId,
      };
      store.dispatch("removeComments", payload);
    };

    // 코멘트 수정 중인지 여부
    const isEdit = ref(false);
    const onEdit = () => {
      isEdit.value = !isEdit.value;
    };

    // 코멘트 수정이 보내지면
    const editComment = (event) => {
      let payload = {};
      payload.comment = props.comment;
      payload.comment.content = event.target.value;
      payload.tilId = props.tilId;
      store.dispatch("updateComment", payload);
    };

    return {
      props,
      isWriter,
      isCommenter,
      deleteComment,
      isEdit,
      onEdit,
      editComment,
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

.comment-input {
  width: 97%;
  border-radius: 10px;
  border: 1px solid rgba(219, 219, 218, 1);
}
</style>
