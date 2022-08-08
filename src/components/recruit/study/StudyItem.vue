<template>
  <div class="team-container">
    <div class="title-box d-flex justify-content-between">
      <div class="d-flex">
        <div class="profile-img-box">
          <img
            class="profile-img"
            src="@/assets/logo.png"
            alt="profile image"
          />
        </div>
        <h3>{{ studyTeam.title }}</h3>
      </div>
      <div v-if="state.bookmark" class="bookmark-icon-box">
        <font-awesome-icon
          @click="bookmarkCheck"
          icon="fa-solid fa-bookmark"
          class="bookmark-icon-solid"
        />
      </div>
      <div v-else>
        <font-awesome-icon
          @click="bookmarkCheck"
          icon="fa-regular fa-bookmark"
          class="bookmark-icon"
        />
      </div>
    </div>
    <p
      class="group-info"
      data-bs-toggle="modal"
      data-bs-target="#teamDetailInfo"
    >
      {{ studyTeam.content }}
    </p>
    <div
      class="info-box d-flex justify-content-between"
      data-bs-toggle="modal"
      data-bs-target="#teamDetailInfo"
    >
      <div class="tech-icon-box">
        <img
          class="tech-icon"
          src="@/assets/devicon/javascript-original.svg"
          alt="tech icon"
        />
      </div>
      <div class="detail-info-box d-flex">
        <p>시작 예정일</p>
        <span>{{ studyTeam.start.substring(0, 10) }}</span>
        <font-awesome-icon icon="fa-solid fa-people-group" class="group-icon" />
        <span>{{ studyTeam.cur_mem }} / {{ studyTeam.total_mem }}</span>
      </div>
    </div>
  </div>

  <!-- Modal -->
  <div
    class="modal fade"
    id="teamDetailInfo"
    tabindex="-1"
    aria-labelledby="teamDetailInfoLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header justify-content-space-evenly">
          <div v-if="bookmark" class="bookmark-icon-box">
            <font-awesome-icon
              @click="bookmarkCheck"
              icon="fa-solid fa-bookmark"
              class="bookmark-icon-solid"
            />
          </div>
          <div v-else>
            <font-awesome-icon
              @click="bookmarkCheck"
              icon="fa-regular fa-bookmark"
              class="bookmark-icon"
            />
          </div>
          <div class="d-flex align-items-center">
            <div class="dropdown">
              <button
                class="btn"
                type="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <font-awesome-icon icon="fa-solid fa-ellipsis-vertical" />
              </button>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">수정</a></li>
                <li><a class="dropdown-item" href="#">삭제</a></li>
              </ul>
            </div>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
        </div>
        <div class="modal-body">
          <div class="profile-detail-box d-flex">
            <div class="image-box">
              <img src="@/assets/logo.png" alt="profile image" />
            </div>
            <div class="profile-detail-info">
              <div class="d-flex">
                <h5 class="modal-title" id="teamDetailInfoLabel">꼬꼬</h5>
                <span class="date align-self-end">2022.07.27</span>
              </div>
              <p class="team-recruit-summary">
                웹 프로젝트 프론트엔드 팀원 구해요!
              </p>
            </div>
          </div>
          <div class="team-recruit-info-box">
            <div class="d-flex flex-column">
              <div class="d-flex justify-content-between info-box">
                <h5>시작예정일</h5>
                <p>2022.07.27</p>
                <h5>모집상태</h5>
                <p>3/4</p>
              </div>
              <div class="d-flex justify-content-between info-box">
                <h5>예상기간</h5>
                <p>1개월</p>
                <h5>진행방식</h5>
                <p>온라인</p>
              </div>
            </div>
            <div class="tech-stack-box d-flex">
              <h5>기술스택</h5>
              <div class="tech-icon-container d-flex">
                <div class="tech-icon-box">
                  <img
                    class="tech-icon"
                    src="@/assets/devicon/javascript-original.svg"
                    alt="tech icon"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="team-detail-introduction">
            <p>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Iste
              dolor dolorum recusandae, est quisquam sed iure voluptatem vero
              nesciunt laudantium in dolores molestias modi, maxime ipsum fugit
              libero unde repellat vel reprehenderit maiores porro. Eius, in!
              Nostrum accusamus exercitationem ratione sed, debitis corrupti
              tempore pariatur eligendi officia, adipisci commodi enim veritatis
              voluptate ea repellat illo accusantium sapiente provident
              doloremque esse quaerat nulla dolorum dolorem. Porro commodi,
              facilis tempore similique nihil omnis autem perspiciatis non
              possimus quod, at in, corrupti natus illo amet provident animi
              illum debitis esse excepturi. Eaque optio iusto laborum quam
              recusandae porro rem repellendus quaerat dicta aut?
            </p>
          </div>
        </div>
        <div class="modal-footer justify-content-center">
          <button
            type="button"
            class="btn"
            data-bs-dismiss="modal"
            aria-label="Close"
          >
            <font-awesome-icon icon="fa-solid fa-comments" />
            DM 보내기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
export default {
  name: "StudyItem",
  props: ["studyTeam"],
  setup(props) {
    const state = reactive({
      bookmark: props.studyTeam.scrap,
    });

    function bookmarkCheck() {
      state.bookmark = !state.bookmark;
    }

    return { bookmarkCheck, state };
  },
};
</script>

<style scoped>
.team-container {
  background-color: white;
  border: 1px solid #dbdbda;
  border-radius: 10px;
  box-shadow: 0px 5px 3px -3px #bdbdbd;
  margin-bottom: 10px;
}

.team-container:hover {
  background-color: #c1ebe6;
  border: 1px solid #2a9d8f;
}

/* Profile */
.profile-img-box {
  width: 50px;
  height: 50px;
  border-radius: 70%;
  overflow: hidden;
  margin: 10px;
  border: 3px solid gold;
}

.profile-img {
  width: 45px;
  height: 45px;
}

/* Title */
h3 {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 700;
  margin-top: 12px;
}

/* Bookmark Icon */
.bookmark-icon {
  margin-right: 10px;
  font-size: 1.7rem;
}

.bookmark-icon-solid {
  margin-right: 10px;
  font-size: 1.7rem;
  color: #e63946;
}

/* Group Info */
.group-info {
  margin: 0 10px 0;
  font-size: 14px;
}

/* Tech Stack */
.tech-icon-box {
  width: 30px;
  height: 30px;
  background-color: #c1ebe6;
  border-radius: 50%;
  margin: 10px;
}

.tech-icon {
  display: block;
  width: 15px;
  height: 15px;
  margin: 7px auto;
}

/* Detail Info */
.detail-info-box {
  margin: 10px;
}

.detail-info-box > p {
  font-weight: 700;
  font-size: 16px;
}
.detail-info-box > span {
  font-size: 14px;
  padding-left: 5px;
}

.group-icon {
  font-size: 22px;
  padding: 0 10px 0 20px;
}

/* Modal */
.modal-content {
  background-color: #eff7f6;
}

.image-box {
  width: 75px;
  height: 75px;
  border-radius: 70%;
  overflow: hidden;
  margin-right: 25px;
  border: 3px solid gold;
}

.image-box > img {
  width: 70px;
  height: 70px;
  margin: 5px auto;
}

.modal-header > .bookmark-icon {
  font-size: 25px;
  padding: 0 10px;
}

.modal-header {
  border: 0;
}

.modal-body {
  padding: 20px 80px;
}

.profile-detail-info {
  padding-top: 4px;
}

.modal-title {
  font-size: 25px;
  font-weight: 700;
}

.date {
  margin-left: 25px;
  font-size: 14px;
  color: gray;
}

.team-recruit-summary {
  font-size: 14px;
}

.team-recruit-info-box {
  background-color: #2a9d8f;
  border-radius: 10px;
  margin: 10px 0;
  padding: 10px;
}

.info-box > h5 {
  font-size: 14px;
  font-weight: 700;
  width: 80px;
  text-align: center;
  color: #fff;
}

.info-box > p {
  font-size: 12px;
  font-weight: 700;
  width: 100px;
  background-color: #c1ebe6;
  border-radius: 10px;
  text-align: center;
}

.tech-stack-box > h5 {
  font-size: 14px;
  font-weight: 700;
  text-align: center;
  color: #fff;
  margin-left: 8px;
  padding-top: 5px;
}

.tech-icon-container > .tech-icon-box {
  margin: 0 10px;
}

.team-detail-introduction {
  padding: 15px;
}

.modal-footer > .btn {
  background-color: #2a9d8f;
  color: #fff;
}
</style>
