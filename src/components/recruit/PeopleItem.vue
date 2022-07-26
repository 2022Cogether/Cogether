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
    data-bs-backdrop="static"
    data-bs-keyboard="false"
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
                class="btn btn-primary"
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
          <div class="profile-detail-box d-flex justify-content-center">
            <img src="@/assets/logo.png" alt="profile image" />
            <div class="profile-detail-info">
              <h5 class="modal-title" id="personDetailInfoLabel">꼬꼬</h5>
              <p>나 좀 데려가...</p>
              <div class="tech-icon-box">
                <img
                  class="tech-icon"
                  src="@/assets/devicon/javascript-original.svg"
                  alt="tech icon"
                />
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
          <button type="button" class="btn btn-primary">
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

.user-info {
  margin-top: 10px;
}

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
  margin: 0 auto;
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

.bookmark-icon {
  font-size: 25px;
  padding: 0 10px;
}

li {
  padding: 0;
}

.dropdown > button {
  margin-right: 10px;
}

.profile-detail-box > img {
  width: 60px;
  height: 60px;
  margin-right: 15px;
  margin-top: 5px;
}

.profile-detail-info > h5 {
  font-size: 20px;
  font-weight: 700;
}

.user-introduction {
  background-color: gray;
  border: 1px solid white;
  border-radius: 10px;
  width: 300px;
  margin: 30px auto 10px;
}
</style>
