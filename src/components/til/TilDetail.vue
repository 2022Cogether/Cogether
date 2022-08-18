<template>
  <div class="modal d-flex justify-content-center" @click="closeModal">
    <div class="modal-card">
      <!-- TilMainItem.vue에서 많이 가져옴 -->
      <div class="til-item">
        <!-- 왼쪽: 프로필, 제목, 글쓴이, 시간/ 오른쪽: 드랍다운 -->
        <div class="til-header">
          <div class="profile-body">
            <font-awesome-icon
              v-if="!til.userImg"
              class="fs-3"
              icon="fa-solid fa-user"
            />
            <img v-else :src="til.userImg" class="fs-3" style="width: 100%" />
          </div>
          <div class="til-title">
            {{ til.tilTitle }}
          </div>
          <div class="til-info">
            <span class="til-user">{{ til.userNickname }}</span>
            <!-- created_at을 통해서 시간 계산 v-if나 함수로 1일 이하면 시간으로 표시 -->
            <span v-if="fromCreated < 24" class="til-time">
              {{ fromCreated }}시간 전
            </span>
            <span v-else class="til-time">
              {{ parseInt(fromCreated / 24) }}일 전
            </span>
          </div>

          <div v-if="isWriter" class="dropdown">
            <a
              href=""
              class="dropdown-toggle"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <font-awesome-icon
                icon="fa-solid fa-ellipsis-vertical"
                class="fs-3 til-dropdown-icon"
              />
            </a>
            <div class="dropdown-menu">
              <router-link
                class="dropdown-item"
                :to="{
                  name: 'TilUpdate',
                  params: { tilPk: til.tilId },
                }"
                >내용 수정</router-link
              >
              <div class="dropdown-item" @click="deleteTil">삭제</div>
            </div>
          </div>
        </div>
        <!-- 첨부 이미지 캐러셀 -->
        <div class="til-body">
          <div
            id="carouselExampleIndicatorsForDetail"
            class="carousel slide"
            data-bs-ride="false"
          >
            <div class="carousel-indicators">
              <button
                v-for="(image, i) in til.imgUrl"
                :key="i"
                type="button"
                data-bs-target="#carouselExampleIndicatorsForDetail"
                :data-bs-slide-to="i"
                :class="[i == 0 ? 'active' : '']"
              ></button>
            </div>
            <div
              class="carousel-inner"
              v-for="(image, i) in til.imgUrl"
              :key="i"
            >
              <div :class="['carousel-item', i == 0 ? 'active' : '']">
                <img :src="image.imgUrl" class="d-block w-100 car-image" />
              </div>
            </div>
            <button
              class="carousel-control-prev"
              type="button"
              data-bs-target="#carouselExampleIndicatorsForDetail"
              data-bs-slide="prev"
            >
              <span
                class="carousel-control-prev-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button
              class="carousel-control-next"
              type="button"
              data-bs-target="#carouselExampleIndicatorsForDetail"
              data-bs-slide="next"
            >
              <span
                class="carousel-control-next-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
        <!-- 4단 구성 / 좋아요, 스크랩/ 좋아요 몇개 / 내용 / 댓글 개수 -->
        <div class="til-footer">
          <!-- 좋아요 버튼 -->
          <div id="main-content" class="like-button">
            <div>
              <!-- 나중에 id에 til id 넣고 밑에 label 태그의 for랑 맞추면 개별 하트 효과 및 데이터 전송 가능 -->
              <input
                type="checkbox"
                id="checkbox"
                ref="checkbox"
                :checked.prop="isLike"
                @click="sendLike"
              />
              <label for="checkbox">
                <svg
                  id="heart-svg"
                  viewBox="467 392 58 57"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <g
                    id="Group"
                    fill="none"
                    fill-rule="evenodd"
                    transform="translate(467 392)"
                  >
                    <path
                      d="M29.144 20.773c-.063-.13-4.227-8.67-11.44-2.59C7.63 28.795 28.94 43.256 29.143 43.394c.204-.138 21.513-14.6 11.44-25.213-7.214-6.08-11.377 2.46-11.44 2.59z"
                      id="heart"
                      fill="#AAB8C2"
                    />
                    <circle
                      id="main-circ"
                      fill="#E2264D"
                      opacity="0"
                      cx="29.5"
                      cy="29.5"
                      r="1.5"
                    />

                    <g id="grp7" opacity="0" transform="translate(7 6)">
                      <circle id="oval1" fill="#9CD8C3" cx="2" cy="6" r="2" />
                      <circle id="oval2" fill="#8CE8C3" cx="5" cy="2" r="2" />
                    </g>

                    <g id="grp6" opacity="0" transform="translate(0 28)">
                      <circle id="oval1" fill="#CC8EF5" cx="2" cy="7" r="2" />
                      <circle id="oval2" fill="#91D2FA" cx="3" cy="2" r="2" />
                    </g>

                    <g id="grp3" opacity="0" transform="translate(52 28)">
                      <circle id="oval2" fill="#9CD8C3" cx="2" cy="7" r="2" />
                      <circle id="oval1" fill="#8CE8C3" cx="4" cy="2" r="2" />
                    </g>

                    <g id="grp2" opacity="0" transform="translate(44 6)">
                      <circle id="oval2" fill="#CC8EF5" cx="5" cy="6" r="2" />
                      <circle id="oval1" fill="#CC8EF5" cx="2" cy="2" r="2" />
                    </g>

                    <g id="grp5" opacity="0" transform="translate(14 50)">
                      <circle id="oval1" fill="#91D2FA" cx="6" cy="5" r="2" />
                      <circle id="oval2" fill="#91D2FA" cx="2" cy="2" r="2" />
                    </g>

                    <g id="grp4" opacity="0" transform="translate(35 50)">
                      <circle id="oval1" fill="#F48EA7" cx="6" cy="5" r="2" />
                      <circle id="oval2" fill="#F48EA7" cx="2" cy="2" r="2" />
                    </g>

                    <g id="grp1" opacity="0" transform="translate(24)">
                      <circle id="oval1" fill="#9FC7FA" cx="2.5" cy="3" r="2" />
                      <circle id="oval2" fill="#9FC7FA" cx="7.5" cy="2" r="2" />
                    </g>
                  </g>
                </svg>
              </label>
            </div>
          </div>
          <!-- 좋아요 갯수를 Til로 가늠하는 방법 필요 -->
          <span class="like-count">좋아요 {{ likeNum }}개</span>
          <!-- v-if: "is_Current_User_Like_This_TIL?" 등으로 sendlike/senddislike 바꾸어야 할 듯 <- currentUser 완성 뒤 -->
          <div class="til-content">
            {{ til.tilContent }}
          </div>
        </div>
        <!-- 댓글창 -->
        <div class="til-comment">
          <h1 class="comments-title">Comments ({{ commentList.length }})</h1>
          <CommentList
            :comments="commentList"
            :userId="til.userId"
            :tilId="til.tilId"
            @delComm="delComm"
          />
          <input
            type="text"
            class="til-comment-input"
            @keyup.enter="onSubmit"
            v-model="commentContent"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CommentList from "@/components/til/comment/CommentList.vue";

import { computed, ref } from "vue";
import { useStore } from "vuex";
import router from "@/router";

export default {
  name: "TilDetail",
  props: {
    util: Object,
  },
  emits: ["closeModal", "likeFromDetail"],
  components: {
    CommentList,
  },
  setup(props, { emit }) {
    const store = useStore();
    const getters = computed(() => store.getters);

    const tilContent = ref({});
    const commentList = ref([]);
    const commentContent = ref("");
    const isLike = ref(true);
    const likeNum = ref(0);
    const initLike = ref(true);

    const til = ref(props.util);
    const fromCreated = parseInt(
      (new Date() - new Date(til.value.createdAt)) / 3600000
    );

    // 사용자가 글쓴이인지 아닌지 확인
    const isWriter = ref(false);

    (async () => {
      const credentials = {
        tilId: til.value.tilId,
        userId: getters.value.getLoginUserId,
      };
      await store.dispatch("fetchTil", credentials);

      commentList.value = til.value.commentList;

      console.log(til.value.like);
      isLike.value = til.value.like;
      likeNum.value = til.value.likeCnt;
      if (isLike.value) {
        initLike.value = true;
      } else {
        initLike.value = false;
      }

      isWriter.value = computed(() => {
        return til.value.userId == store.getters.getLoginUserId;
      }).value;
    })();

    // Til 삭제
    const deleteTil = () => {
      store.dispatch("removeTil", til.value.tilId);
      router.go();
    };

    // 좋아요/좋아요 취소
    const sendLike = () => {
      if (!isLike.value) {
        store.dispatch("likeTil", til.value.tilId);
        likeNum.value = likeNum.value + 1;
      } else {
        store.dispatch("dislikeTil", til.value.tilId);
        likeNum.value = likeNum.value - 1;
      }
      // store.commit("SET_TIL_LIKE");
      isLike.value = !isLike.value;
      emit("likeFromDetail");
    };

    const onSubmit = async () => {
      const payload = {
        tilId: til.value.tilId,
        content: commentContent.value,
        userId: store.getters.getLoginUserId,
      };
      await store.dispatch("createComment", payload);
      til.value = computed(() => {
        return getters.value.getTilContent;
      }).value;
      console.log(til.value);
      commentList.value = til.value.commentList;
      commentContent.value = "";
    };

    // 모달 바깥을 클릭하면 모달을 닫게 하는 함수
    const closeModal = async (event) => {
      if (
        !document
          .querySelector(".modal")
          .querySelector("." + event.target.className.split(" ")[0]) // 클릭한 박스의 클래스가 modal-card라는 클래스의 하위 클래스인지 아닌지
      ) {
        emit("closeModal");
      }
    };

    const delComm = async () => {
      await store.dispatch("fetchTil", { tilId: store.getters.getOpenTil });
      til.value = computed(() => {
        return getters.value.getTilContent;
      }).value;
      console.log(til.value);
      commentList.value = til.value.commentList;
    };

    return {
      til,
      isLike,
      isWriter,
      deleteTil,
      tilContent,
      commentContent,
      commentList,
      sendLike,
      onSubmit,
      closeModal,
      initLike,
      likeNum,
      delComm,
      fromCreated,
    };
  },
};
</script>

<style lang="scss" scoped>
.car-imag {
  width: 80%;
  max-width: 100px;
}

.modal-card {
  background-color: white;
  width: 40vw;
  min-width: 250px;
  height: auto;
}

.til-item {
  position: relative;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
  height: auto;
  margin-bottom: 20px;
  border: 2px solid rgba(219, 219, 218, 0.8);
  border-radius: 10px;
  background-color: white;
}

.til-header {
  position: relative;
  background-color: white;
  height: 15%;
  margin-bottom: 15vh;
  border-radius: 10px 10px 0px 0px;
}

.til-body {
  background-color: rgb(41, 39, 39);
  height: auto;
}

.til-footer {
  height: auto;
  position: relative;
  background-color: white;
}

.til-comment {
  position: relative;
  height: auto;
  margin-left: 12px;
  margin-top: 3vh;
  background-color: white;
}

.til-comment-input {
  width: 97%;
  border-radius: 10px;
  border: 1px solid rgba(219, 219, 218, 1);
  margin-bottom: 5vh;
}

.profile-body {
  position: absolute;
  top: 25px;
  left: 10px;
  width: 50px;
  height: 50px;
  background-color: grey;
  color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.til-title {
  position: absolute;
  left: 70px;
  top: 25px;
  width: 70%;
  font-size: 1.1rem;
  font-weight: bold;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.til-info {
  position: absolute;
  left: 70px;
  top: 50px;
}

.til-user {
  margin-right: 10px;
}

.til-dropdown-icon {
  cursor: pointer;
  display: inline;
  color: black;
}

.dropdown {
  display: inline;
  position: absolute;
  right: 2%;
  top: 27%;
}

.dropdown-toggle::after {
  border: none;
}

.like-button {
  margin-left: 10px;
}

.like-count {
  position: absolute;
  left: 50px;
  top: 10px;
  font-weight: bold;
}

.icon-bookmark {
  position: absolute;
  right: 10px;
  top: 10px;
  font-size: 1.5rem;
}

.til-content {
  position: relative;
  height: auto;
  overflow: auto;
  width: 97%;
  left: 10px;
  font-size: 1rem;
  display: -webkit-box;
}

// 좋아요 버튼 모양, 크기, 애니메이션 관련
svg {
  cursor: pointer;
  overflow: visible;
  width: 45px;
  #heart {
    transform-origin: center;
    animation: animateHeartOut 0.3s linear forwards;
  }
  #main-circ {
    transform-origin: 29.5px 29.5px;
  }
}

.comments-title {
  font-size: 16px;
  color: #262626;
  margin-bottom: 15px;
  font-family: "Conv_helveticaneuecyr-bold";
}

#checkbox {
  display: none;
}

#checkbox:checked + label svg {
  #heart {
    transform: scale(0.2);
    fill: #e2264d;
    animation: animateHeart 0.3s linear forwards 0.25s;
  }
  #main-circ {
    transition: all 2s;
    animation: animateCircle 0.3s linear forwards;
    opacity: 1;
  }
  #grp1 {
    opacity: 1;
    transition: 0.1s all 0.3s;
    #oval1 {
      transform: scale(0) translate(0, -30px);
      transform-origin: 0 0 0;
      transition: 0.5s transform 0.3s;
    }
    #oval2 {
      transform: scale(0) translate(10px, -50px);
      transform-origin: 0 0 0;
      transition: 1.5s transform 0.3s;
    }
  }
  #grp2 {
    opacity: 1;
    transition: 0.1s all 0.3s;
    #oval1 {
      transform: scale(0) translate(30px, -15px);
      transform-origin: 0 0 0;
      transition: 0.5s transform 0.3s;
    }
    #oval2 {
      transform: scale(0) translate(60px, -15px);
      transform-origin: 0 0 0;
      transition: 1.5s transform 0.3s;
    }
  }
  #grp3 {
    opacity: 1;
    transition: 0.1s all 0.3s;
    #oval1 {
      transform: scale(0) translate(30px, 0px);
      transform-origin: 0 0 0;
      transition: 0.5s transform 0.3s;
    }
    #oval2 {
      transform: scale(0) translate(60px, 10px);
      transform-origin: 0 0 0;
      transition: 1.5s transform 0.3s;
    }
  }
  #grp4 {
    opacity: 1;
    transition: 0.1s all 0.3s;
    #oval1 {
      transform: scale(0) translate(30px, 15px);
      transform-origin: 0 0 0;
      transition: 0.5s transform 0.3s;
    }
    #oval2 {
      transform: scale(0) translate(40px, 50px);
      transform-origin: 0 0 0;
      transition: 1.5s transform 0.3s;
    }
  }
  #grp5 {
    opacity: 1;
    transition: 0.1s all 0.3s;
    #oval1 {
      transform: scale(0) translate(-10px, 20px);
      transform-origin: 0 0 0;
      transition: 0.5s transform 0.3s;
    }
    #oval2 {
      transform: scale(0) translate(-60px, 30px);
      transform-origin: 0 0 0;
      transition: 1.5s transform 0.3s;
    }
  }
  #grp6 {
    opacity: 1;
    transition: 0.1s all 0.3s;
    #oval1 {
      transform: scale(0) translate(-30px, 0px);
      transform-origin: 0 0 0;
      transition: 0.5s transform 0.3s;
    }
    #oval2 {
      transform: scale(0) translate(-60px, -5px);
      transform-origin: 0 0 0;
      transition: 1.5s transform 0.3s;
    }
  }
  #grp7 {
    opacity: 1;
    transition: 0.1s all 0.3s;
    #oval1 {
      transform: scale(0) translate(-30px, -15px);
      transform-origin: 0 0 0;
      transition: 0.5s transform 0.3s;
    }
    #oval2 {
      transform: scale(0) translate(-55px, -30px);
      transform-origin: 0 0 0;
      transition: 1.5s transform 0.3s;
    }
  }
  #grp2 {
    opacity: 1;
    transition: 0.1s opacity 0.3s;
  }
  #grp3 {
    opacity: 1;
    transition: 0.1s opacity 0.3s;
  }
  #grp4 {
    opacity: 1;
    transition: 0.1s opacity 0.3s;
  }
  #grp5 {
    opacity: 1;
    transition: 0.1s opacity 0.3s;
  }
  #grp6 {
    opacity: 1;
    transition: 0.1s opacity 0.3s;
  }
  #grp7 {
    opacity: 1;
    transition: 0.1s opacity 0.3s;
  }
}

@keyframes animateCircle {
  40% {
    transform: scale(10);
    opacity: 1;
    fill: #dd4688;
  }
  55% {
    transform: scale(11);
    opacity: 1;
    fill: #d46abf;
  }
  65% {
    transform: scale(12);
    opacity: 1;
    fill: #cc8ef5;
  }
  75% {
    transform: scale(13);
    opacity: 1;
    fill: transparent;
    stroke: #cc8ef5;
    stroke-width: 0.5;
  }
  85% {
    transform: scale(17);
    opacity: 1;
    fill: transparent;
    stroke: #cc8ef5;
    stroke-width: 0.2;
  }
  95% {
    transform: scale(18);
    opacity: 1;
    fill: transparent;
    stroke: #cc8ef5;
    stroke-width: 0.1;
  }
  100% {
    transform: scale(19);
    opacity: 1;
    fill: transparent;
    stroke: #cc8ef5;
    stroke-width: 0;
  }
}

@keyframes animateHeart {
  0% {
    transform: scale(0.2);
  }
  40% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}

@keyframes animateHeartOut {
  0% {
    transform: scale(1.4);
  }
  100% {
    transform: scale(1);
  }
}
</style>
