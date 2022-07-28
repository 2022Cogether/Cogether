<template>
  <div class="left-bar d-flex flex-column justify-content-between">
    <div class="menu container">
      <a href="">
        <div class="row">
          <div class="col-4">
            <font-awesome-icon
              icon="fa-solid fa-house"
              class="left-bar-icon fa-3x"
            />
          </div>
          <div class="col-8 align-self-center menu-text">홈</div>
        </div>
      </a>
      <a href="#/recruit">
        <div class="row">
          <div class="col-4">
            <font-awesome-icon
              icon="fa-solid fa-people-roof"
              class="left-bar-icon fa-3x"
            />
          </div>
          <div class="col-8 align-self-center menu-text">구인</div>
        </div>
      </a>
      <a href="#/challenge">
        <div class="row">
          <div class="col-4">
            <font-awesome-icon
              icon="fa-solid fa-gamepad"
              class="left-bar-icon fa-3x"
            />
          </div>
          <div class="col-8 align-self-center menu-text">챌린지</div>
        </div>
      </a>
      <!-- <a href="">
        <div class="row">
          <div class="col-4">
            <font-awesome-icon
              icon="fa-solid fa-clipboard-question"
              class="left-bar-icon fa-3x"
            />
          </div>
          <div class="col-8 align-self-center menu-text">Q&A</div>
        </div>
      </a> -->
      <a href="#/ranking">
        <div class="row">
          <div class="col-4">
            <font-awesome-icon
              icon="fa-solid fa-trophy"
              class="left-bar-icon fa-3x"
            />
          </div>
          <div class="col-8 align-self-center menu-text">랭킹</div>
        </div>
      </a>
    </div>
    <div class="footer">
      <a href="" class="about">About</a><a href="" class="terms">Terms</a>
      <p class="copyright">2022 COGETHER</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "LeftBar",
};
</script>

<style scoped>
a {
  text-decoration: none;
}

.left-bar {
  position: fixed;
  left: 0;
  height: 90vh;
}

.left-bar-icon {
  margin-top: 15px;
  margin-bottom: 15px;
  padding-left: 30px;
  color: black;
}

.row:hover {
  background-color: #e6e6e6;
}

.menu-text {
  color: black;
}

.copyright {
  margin-bottom: 0;
}

.about {
  margin-right: 20px;
  color: #b7b7b7;
  font-size: 0.7rem;
}

.terms {
  text-decoration: none;
  color: #b7b7b7;
  font-size: 0.7rem;
}

.footer {
  margin-left: 15px;
  color: #b7b7b7;
  font-size: 0.7rem;
}

@media (max-width: 1400px) {
  .menu-text {
    display: none;
  }
}
</style>
