<template>
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
      <div class="til-title" @click="openModal">
        {{ til.tilTitle }}
      </div>
      <div class="til-info">
        <span class="til-user">{{ til.userNickname }}</span>
        <span v-if="fromCreated < 10" class="til-time">방금전</span>
        <span v-if="fromCreated < 34 && fromCreated > 9" class="til-time">
          {{ fromCreated - 9 }}시간 전
        </span>
        <span v-if="fromCreated > 33" class="til-time">
          {{ parseInt((fromCreated - 9) / 24) }}일 전
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
          <div class="dropdown-item" @click="goUpdate">내용 수정</div>
          <div class="dropdown-item" @click="deleteTil">삭제</div>
        </div>
      </div>
    </div>
    <!-- 첨부 이미지 캐러셀 -->
    <div class="til-body">
      <div
        :id="'carouselExampleIndicatorsForDetail' + til.tilId"
        class="carousel slide"
        data-bs-ride="false"
      >
        <div class="carousel-indicators">
          <button
            v-for="(image, i) in til.imgUrl"
            :key="i"
            type="button"
            :data-bs-target="'#carouselExampleIndicatorsForDetail' + til.tilId"
            :data-bs-slide-to="i"
            :class="[i == 0 ? 'active' : '']"
          ></button>
        </div>
        <div class="carousel-inner" v-for="(image, i) in til.imgUrl" :key="i">
          <div :class="['carousel-item', i == 0 ? 'active' : '']">
            <img :src="image.imgUrl" class="d-block w-100" alt="..." />
          </div>
        </div>
        <button
          class="carousel-control-prev"
          type="button"
          :data-bs-target="'#carouselExampleIndicatorsForDetail' + til.tilId"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          :data-bs-target="'#carouselExampleIndicatorsForDetail' + til.tilId"
          data-bs-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
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
            :id="'checkbox' + til.tilId"
            :checked="til.like"
            @click="sendLike"
          />
          <label :for="'checkbox' + til.tilId">
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
      <span class="like-count"> 좋아요 {{ til.likeCnt }}개 </span>
      <div class="til-content">
        {{ til.tilContent }}
      </div>
    </div>
    <!-- 댓글 입력창 -->
    <div class="til-comment my-2">
      <input
        type="text"
        class="til-comment-input"
        @keyup.enter.prevent="onSubmit"
        v-model="commentContent"
      />
    </div>
  </div>
  <TilDetail
    v-if="isOpen"
    @closeModal="closeModal"
    @likeFromDetail="likeFromDetail"
    :util="til"
  />
</template>

<script>
import Swal from "sweetalert2";
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import TilDetail from "@/components/til/TilDetail.vue";

export default {
  name: "TilMainItem",
  components: {
    TilDetail,
  },
  props: {
    util: Object,
  },
  setup(props) {
    const store = useStore();
    // const getters = computed(() => store.getters);
    const router = useRouter();

    const til = ref(props.util);
    console.log(til.value);
    const likeCnt = ref(til.value.likeCnt);

    // 사용자가 글쓴이인지 아닌지 확인
    const isWriter = computed(() => {
      return til.value.userId == store.getters.getLoginUserId;
    });

    const commentContent = ref("");

    const openModal = async () => {
      // const tilNum = til.value.tilId;
      // const credentials = {
      //   tilId: tilNum,
      //   userId: getters.value.getLoginUserId,
      // };
      // await store.dispatch("fetchOpenTil", credentials);
      isOpen.value = true;
    };

    // 좋아요/좋아요 취소
    const sendLike = () => {
      if (!til.value.like) {
        store.dispatch("likeTil", til.value.tilId);
        til.value.likeCnt = til.value.likeCnt + 1;
      } else {
        store.dispatch("dislikeTil", til.value.tilId);
        til.value.likeCnt = til.value.likeCnt - 1;
      }
      til.value.like = !til.value.like;
    };

    const onSubmit = () => {
      const payload = {
        tilId: til.value.tilId,
        content: commentContent.value,
        userId: store.getters.getLoginUserId,
      };
      store.dispatch("createComment", payload);
      til.value.commentList.push({
        ...payload,
        userNickname: "당신",
        createdAt: new Date(),
        tilCommentId: store.getters.getNewCommentId,
      });
      commentContent.value = "";
    };

    function deleteTil() {
      Swal.fire({
        title: "삭제하시겠습니까?",
        text: "삭제한 글은 복구할 수 없습니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#2a9d8f",
        cancelButtonColor: "#d33",
        confirmButtonText: "확인",
        cancelButtonText: "취소",
      }).then(async (result) => {
        if (result.isConfirmed) {
          //취소하고 이동할 페이지
          await store.dispatch("removeTil", til.value.tilId);
          router.go();
        }
      });
    }

    const goUpdate = () => {
      store.dispatch("fetchOpenTil", { tilId: til.value.tilId });
      router.push({
        name: "TilUpdate",
        params: { tilPk: til.value.tilId },
      });
    };

    // const modalNum = computed(() => {
    //   return getters.value.getOpenTil;
    // });
    // const isOpen = computed(() => {
    //   return modalNum.value != -1;
    // });

    const isOpen = ref(false);
    const closeModal = () => {
      isOpen.value = false;
    };
    const likeFromDetail = () => {
      if (!til.value.like) {
        til.value.likeCnt = til.value.likeCnt + 1;
      } else {
        til.value.likeCnt = til.value.likeCnt - 1;
      }
      til.value.like = !til.value.like;
    };

    const fromCreated = parseInt(
      (new Date() - new Date(til.value.createdAt)) / 3600000
    );

    return {
      likeCnt,
      goUpdate,
      til,
      isWriter,
      commentContent,
      deleteTil,
      sendLike,
      openModal,
      onSubmit,

      // modalNum,
      isOpen,
      closeModal,
      likeFromDetail,
      fromCreated,
    };
  },
};
</script>

<style lang="scss" scoped>
.til-item {
  position: relative;
  margin-left: auto;
  margin-right: auto;
  width: 80%;
  height: auto;
  margin-bottom: 20px;
  border: 2px solid rgba(219, 219, 218, 0.8);
  border-radius: 10px;
}

.til-header {
  position: relative;
  background-color: white;
  height: 15%;
  border-radius: 10px 10px 0px 0px;
}

.til-body {
  background-color: rgb(41, 39, 39);
  height: auto;
}

.til-footer {
  height: 15%;
  position: relative;
}

.til-comment {
  height: 5%;
  margin-left: 12px;
}

.til-comment-input {
  width: 97%;
  border-radius: 10px;
  border: 1px solid rgba(219, 219, 218, 1);
}

.profile-body {
  position: relative;
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
  position: relative;
  left: 70px;
  bottom: 20px;
  width: 70%;
  font-size: 1.1rem;
  font-weight: bold;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  cursor: pointer;
}

.til-info {
  position: relative;
  left: 70px;
  bottom: 20px;
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
  width: 97%;
  position: relative;
  left: 10px;
  font-size: 0.7rem;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
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

input[id^="checkbox"] {
  display: none;
}

input[id^="checkbox"]:checked + label svg {
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
