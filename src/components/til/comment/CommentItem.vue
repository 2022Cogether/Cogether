<template>
  <div class="comment-item">
    <div class="container">
      <div class="be-comment-block">
        <div class="be-comment">
          <div class="d-flex justify-content-between">
            <div>
              <span class="be-comment-name">
                <router-link
                  :to="{
                    name: 'profile',
                    params: { userId: comment.userId },
                  }"
                  style="font-weight: bold; color: black; text-decoration: none"
                  >{{ comment.userNickname }}
                </router-link>
              </span>
              <span v-if="fromCreated < 10" class="be-comment-time">
                <i class="fa fa-clock-o"></i>
                방금전
              </span>
              <span
                v-if="fromCreated < 34 && fromCreated > 9"
                class="be-comment-time"
              >
                <i class="fa fa-clock-o"></i>
                {{ fromCreated - 9 }}시간 전
              </span>
              <span v-if="fromCreated > 33" class="be-comment-time">
                <i class="fa fa-clock-o"></i>
                {{ parseInt((fromCreated - 9) / 24) }}일 전
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
            {{ commentContent }}
          </p>
          <input
            v-else
            :value="comment.content"
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
import { ref, computed, watch } from "vue";

export default {
  name: "CommentItem",
  props: {
    comment: Object,
    userId: Number,
    tilId: Number,
  },
  emits: ["delComm", "editComm"],
  setup(props, { emit }) {
    const store = useStore();

    const loginUserId = store.getters.getLoginUserId;
    const commentContent = ref(props.comment.content);

    watch(props.comment.content, () => {
      commentContent.value = props.comment.content;
    });

    // 유저 인증(isWrite -> 현재 유저가 TIL 글쓴이 isCommenter -> 현재 유저가 이 댓글 작성자)
    const isWriter = computed(() => {
      return props.userId == loginUserId;
    });
    const isCommenter = computed(() => {
      return props.comment.userId == loginUserId;
    });

    const fromCreated = parseInt(
      (new Date() - new Date(props.comment.createdAt)) / 3600000
    );

    // console.log(props.comment.value.content);

    // 코멘트 지우기
    const deleteComment = async (event) => {
      console.log("event", event);
      const payload = {
        commentId: props.comment.tilCommentId,
        tilId: props.tilId,
      };
      await store.dispatch("removeComments", payload);
      emit("delComm", event, props.comment.tilCommentId);
    };

    // 코멘트 수정 중인지 여부
    const isEdit = ref(false);
    const onEdit = () => {
      isEdit.value = !isEdit.value;
    };

    // 코멘트 수정이 보내지면
    const editComment = (event) => {
      let payload = {
        content: event.target.value,
        tilCommentId: props.comment.tilCommentId,
      };
      store.dispatch("updateComment", payload);
      commentContent.value = event.target.value;
      onEdit();
      emit("editComm", event, props.comment.tilCommentId, commentContent.value);
    };

    return {
      props,
      commentContent,
      isWriter,
      isCommenter,
      deleteComment,
      isEdit,
      onEdit,
      editComment,
      fromCreated,
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
  color: #393b40;
  display: block;
  background: #73d0c5;
  border: 1px solid #edeff2;
  padding: 15px 20px 20px 20px;
}

.comment-input {
  width: 97%;
  border-radius: 10px;
  border: 1px solid rgba(219, 219, 218, 1);
}
</style>
