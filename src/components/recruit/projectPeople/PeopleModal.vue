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
              <h5 class="modal-title" id="personDetailInfoLabel">꼬꼬</h5>
              <p>나 좀 데려가...</p>
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
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do e
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
export default {};
</script>

<style></style>
