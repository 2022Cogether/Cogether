<template>
  <li
    class="list-group-item d-flex"
    data-bs-toggle="modal"
    data-bs-target="#personDetailInfo"
  >
    <div class="profile-img-box">
      <img class="profile-img" src="@/assets/logo.png" alt="profile image" />
    </div>
    <div class="user-info flex-fill">
      <h3>꼬꼬</h3>
      <p>나 좀 데려가...</p>
    </div>
    <div class="align-self-end">
      <div class="tech-icon-box">
        <img
          class="tech-icon"
          src="@/assets/devicon/javascript-original.svg"
          alt="TechStack Icons"
        />
      </div>
    </div>
    <font-awesome-icon icon="fa-regular fa-bookmark" class="bookmark-icon" />
  </li>
  <li
    class="list-group-item d-flex"
    data-bs-toggle="modal"
    data-bs-target="#personDetailInfo"
  >
    <div class="profile-img-box">
      <img class="profile-img" src="@/assets/logo.png" alt="profile image" />
    </div>
    <div class="user-info flex-fill">
      <h3>꼬꼬</h3>
      <p>나 좀 데려가...</p>
    </div>
    <div class="d-flex align-self-end">
      <div class="tech-icon-box">
        <img
          class="tech-icon"
          src="@/assets/devicon/javascript-original.svg"
          alt="TechStack Icons"
        />
      </div>
    </div>
    <font-awesome-icon icon="fa-regular fa-bookmark" class="bookmark-icon" />
  </li>
  <li
    class="list-group-item d-flex"
    data-bs-toggle="modal"
    data-bs-target="#personDetailInfo"
  >
    <div class="profile-img-box">
      <img class="profile-img" src="@/assets/logo.png" alt="profile image" />
    </div>
    <div class="user-info flex-fill">
      <h3>꼬꼬</h3>
      <p>나 좀 데려가...</p>
    </div>
    <div class="align-self-end">
      <div class="tech-icon-box">
        <img
          class="tech-icon"
          src="@/assets/devicon/javascript-original.svg"
          alt="TechStack Icons"
        />
      </div>
    </div>
    <font-awesome-icon icon="fa-regular fa-bookmark" class="bookmark-icon" />
  </li>

  <!-- Modal -->
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
          <font-awesome-icon
            icon="fa-regular fa-bookmark"
            class="bookmark-icon"
          />
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
        <div class="modal-footer justify-content-center">
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

<style scoped>
/* Profile Image */
.profile-img-box {
  width: 50px;
  height: 50px;
  border-radius: 70%;
  overflow: hidden;
  margin: 5px 10px 10px 10px;
  border: 3px solid gold;
}

.profile-img {
  width: 45px;
  height: 45px;
}

/* User Information */
.user-info {
  margin-top: 10px;
}

h3 {
  font-size: 15px;
  font-weight: 700;
  margin-bottom: 4px;
}

p {
  font-size: 12px;
  margin: 0;
}

/* Tech Stack */
.tech-icon-box {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background-color: #d8f3dc;
  margin-bottom: 7px;
}

.tech-icon {
  display: block;
  width: 13px;
  height: 13px;
  margin: 5px auto;
}

.bookmark-icon {
  font-size: 25px;
  padding: 0 10px;
}

li {
  padding: 0;
}

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
  width: 70px;
  height: 70px;
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
</style>
