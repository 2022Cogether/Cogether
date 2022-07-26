<template>
  <div class="team-container">
    <div class="title-box d-flex">
      <div class="profile-img-box">
        <img class="profile-img" src="@/assets/logo.png" alt="profile image" />
      </div>
      <h3>타이틀</h3>
      <font-awesome-icon icon="fa-regular fa-bookmark" class="bookmark-icon" />
    </div>
    <p>
      Lorem ipsum dolor sit, amet consectetur adipisicing elit. Eaque et
      dolorem, aliquam adipisci dolor architecto iusto numquam, porro corporis
      dicta officia? A, ullam? Repellat quidem voluptas at deserunt neque sit
      reiciendis odio officia ullam? Nesciunt repudiandae odio rem excepturi
      consectetur, eos qui, corrupti quod, officia explicabo voluptatibus! Nam,
      itaque minima!
    </p>
    <div class="info-box d-flex justify-content-between">
      <div class="tech-icon-box">
        <img
          class="tech-icon"
          src="@/assets/devicon/javascript-original.svg"
          alt="tech icon"
        />
      </div>
      <div class="detail-info-box d-flex">
        <p>시작 예정일</p>
        <span>2022.07.20</span>
        <font-awesome-icon icon="fa-solid fa-people-group" />
        <span>3/4</span>
      </div>
    </div>
  </div>
</template>

<script></script>

<style scoped>
.team-container {
  border: 3px solid black;
  border-radius: 20px;
}

.tech-icon-box {
  width: 30px;
  height: 30px;
  border: 1px solid black;
  border-radius: 50%;
}

.tech-icon {
  display: block;
  width: 20px;
  height: 20px;
  margin: 4px auto;
}
</style>
