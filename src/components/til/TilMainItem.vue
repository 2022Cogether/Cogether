<template>
  <div class="til-item">
    <!-- 왼쪽: 프로필, 제목, 글쓴이, 시간/ 오른쪽: 드랍다운 -->
    <div class="til-header">
      <div class="profile-body">
        <font-awesome-icon class="fs-3" icon="fa-solid fa-user" />
      </div>
      <!-- 클릭하면 detail 모달이 나오게 할 수 있을까? -->
      <div class="til-title" @click="setNum">
        {{ til.title }}
      </div>
      <div class="til-info">
        <span class="til-user">삐약이</span>
        <span class="til-time">1 시간 전</span>
      </div>

      <div class="dropdown">
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
        <!-- 나중에 사용자가 게시글 작성자일 경우만 뜨게 설정하기 -->
        <div class="dropdown-menu">
          <a class="dropdown-item" href="">내용 수정</a>
          <a class="dropdown-item" href="">삭제</a>
        </div>
      </div>
    </div>
    <!-- 첨부 이미지 캐러셀 -->
    <div class="til-body">
      <div
        id="carouselExampleIndicators"
        class="carousel slide"
        data-bs-ride="false"
      >
        <div class="carousel-indicators">
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="0"
            class="active"
            aria-current="true"
            aria-label="Slide 1"
          ></button>
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="1"
            aria-label="Slide 2"
          ></button>
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="2"
            aria-label="Slide 3"
          ></button>
        </div>
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="@/assets/test1.jpg" class="d-block w-100" alt="..." />
          </div>
          <div class="carousel-item">
            <img src="@/assets/test2.jpg" class="d-block w-100" alt="..." />
          </div>
          <div class="carousel-item">
            <img src="@/assets/test3.jpg" class="d-block w-100" alt="..." />
          </div>
        </div>
        <button
          class="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleIndicators"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleIndicators"
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
          <input type="checkbox" id="checkbox" />
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
      <span class="like-count"> 좋아요 0개 </span>
      <!-- <div v-if="bookmarked" class="bookmark">
        <font-awesome-icon
          @click="bookmarkCheck"
          icon="fa-solid fa-bookmark"
          class="icon-bookmark"
        />
      </div>
      <div v-else>
        <font-awesome-icon
          @click="bookmarkCheck"
          icon="fa-regular fa-bookmark"
          class="icon-bookmark"
        />
      </div> -->
      <div class="til-content">
        {{ til.content }}
      </div>
    </div>
    <!-- 댓글 입력창 -->
    <div class="til-comment">
      <input type="text" class="til-comment-input" />
    </div>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  name: "TilMainItem",
  props: {
    til: Object,
  },
  setup(props, { emit }) {
    const store = useStore();
    const getters = computed(() => store.getters);

    function setNum() {
      const tilNum = props.til.pk;
      store.dispatch("fetchOpenTil", {
        tilId: tilNum,
        userId: getters.value.getCurrentUser,
      });
      emit("detailChange", tilNum);
    }

    return {
      setNum,
    };
  },
  data() {
    return {
      onDetailModal: false,
      bookmarked: 0,
    };
  },
  methods: {
    // 나중에 DB랑 연계해서 스크랩 상태인지 아닌지로 method 변경
    bookmarkCheck() {
      if (this.bookmarked) {
        this.bookmarked = 0;
      } else {
        this.bookmarked = 1;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.til-item {
  position: relative;
  margin-left: auto;
  margin-right: auto;
  width: 80%;
  height: 700px;
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
  height: 65%;
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
  box-shadow: 0px 3px rgba(0, 0, 0, 0.3);
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
  cursor: pointer;
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
  position: absolute;
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
