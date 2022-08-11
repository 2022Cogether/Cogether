<template>
  <div
    class="modal fade"
    id="personDetailInfo"
    tabindex="-1"
    aria-labelledby="personDetailInfoLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header justify-content-space-evenly">
          <div v-if="projectPerson.scrap" class="bookmark-icon-box">
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
              <h5 class="modal-title" id="personDetailInfoLabel">
                {{ projectPerson.userNickname }}
              </h5>
              <p>{{ projectPerson.title }}</p>
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
          <div class="user-introduction">
            <p>
              {{ projectPerson.content }}
            </p>
          </div>
        </div>
        <div
          class="modal-footer justify-content-center"
          data-bs-dismiss="modal"
          aria-label="Close"
        >
          <button type="button" class="btn">
            <font-awesome-icon icon="fa-solid fa-comments" />
            DM 보내기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["projectPerson"],
};
</script>

<style scoped>
/* Modal */
.modal-content {
  background-color: #eff7f6;
}

.dropdown > button {
  margin-right: 10px;
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
  width: 100%;
  height: 100%;
  object-fit: cover;
  margin: 5px auto;
}

.profile-detail-box {
  margin-left: 80px;
}

.profile-detail-info > h5 {
  font-size: 20px;
  font-weight: 700;
}

.tech-icon-container {
  margin-top: 5px;
}

.user-introduction {
  background-color: #2a9d8f;
  border: 1px solid white;
  border-radius: 10px;
  width: 300px;
  margin: 20px auto 10px;
  padding: 10px;
}

.modal-header {
  border: 0;
}

.modal-footer > .btn {
  background-color: #2a9d8f;
  color: #fff;
}

.list-group-item {
  border-left: 0px;
  border-right: 0px;
}

.list-group-item:hover {
  background-color: #c1ebe6;
}
</style>
