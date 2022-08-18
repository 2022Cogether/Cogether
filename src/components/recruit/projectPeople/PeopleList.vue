<template>
  <h2>People</h2>
  <ul class="list-group list-group-flush">
    <!-- 글 없음 -->
    <span
      class="noData"
      v-if="tabState != 'scrap' && getters.getProjectPeople.length == 0"
    >
      등록된 글이 없습니다.
    </span>
    <!-- 스크랩 탭 -->
    <span v-if="tabState == 'scrap'">
      <span v-if="!checkScrap()"> 스크랩된 글이 없습니다.</span>
      <span v-for="(projectPerson, i) in getters.getProjectPeople" :key="i">
        <span
          v-if="
            searchText == null ||
            projectPerson.title.indexOf(searchText) != -1 ||
            projectPerson.userNickname.indexOf(searchText) != -1
          "
        >
          <span v-if="projectPerson.scrap">
            <PeopleItem :projectPerson="projectPerson" @setModal="setModal" />
          </span>
        </span>
      </span>
    </span>
    <!--  내가 작성한 글 -->
    <span v-else-if="tabState == 'my'">
      <span v-for="(projectPerson, i) in getters.getProjectPeople" :key="i">
        <span
          v-if="
            searchText == null ||
            projectPerson.title.indexOf(searchText) != -1 ||
            projectPerson.userNickname.indexOf(searchText) != -1
          "
        >
          <PeopleItem :projectPerson="projectPerson" @setModal="setModal" />
        </span>
      </span>
    </span>
    <!-- 프로젝트 탭 -->
    <span v-else>
      <span v-for="(projectPerson, i) in getters.getProjectPeople" :key="i">
        <span
          v-if="
            searchText == null ||
            projectPerson.title.indexOf(searchText) != -1 ||
            projectPerson.userNickname.indexOf(searchText) != -1
          "
        >
          <PeopleItem :projectPerson="projectPerson" @setModal="setModal" />
        </span>
      </span>
    </span>
  </ul>
  <!-- 모달 -->
  <div
    v-if="emitPerson != null"
    class="modal fade"
    id="personDetailInfo"
    tabindex="-1"
    aria-labelledby="personDetailInfoLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <!-- 모달닫기버튼 -->
        <div class="modal-header justify-content-space-evenly">
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="profile-detail-box">
            <!-- 프로필사진 -->
            <div class="profile-box d-flex">
              <div class="profile-img-box">
                <img
                  class="profile-img"
                  :src="emitPerson.userImgurl"
                  alt="profile image"
                />
              </div>
              <div class="profile-name" id="personDetailInfoLabel">
                {{ emitPerson.userNickname }}
              </div>
            </div>
            <!-- 이름 및 한줄소개 -->
            <div class="profile-detail-info">
              <div>{{ emitPerson.title }}</div>
            </div>
          </div>
          <!-- 내용 -->
          <div class="user-introduction">
            <p>
              {{ emitPerson.content }}
            </p>
          </div>
        </div>
        <!-- DM 보내기 -->
        <div
          class="modal-footer justify-content-center"
          data-bs-dismiss="modal"
          aria-label="Close"
        >
          <button type="button" class="btn" @click="sendDM">
            <font-awesome-icon icon="fa-solid fa-comments" />
            DM 보내기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PeopleItem from "@/components/recruit/projectPeople/PeopleItem.vue";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import router from "@/router";
export default {
  name: "PeopleList",
  props: ["searchText", "tabState"],
  setup(props) {
    const store = useStore();
    const getters = computed(() => store.getters);

    if (props.tabState == "my") {
      store.dispatch("getMyProjectPeople", getters.value.getLoginUserId);
    } else {
      store.dispatch("getProjectPeople", getters.value.getLoginUserId);
    }

    function checkScrap() {
      for (const item of getters.value.getProjectPeople) {
        if (item.scrap) return true;
      }
      return false;
    }

    // 모달창emit관련
    let emitPerson = ref("");
    function setModal(data) {
      emitPerson.value = data;
    }

    async function sendDM() {
      await store.dispatch("createChatRoom", emitPerson.value.userId);
      store.dispatch("getChatRoomList", getters.value.getLoginUserId);
      store.commit("SET_IS_CHAT_SHOW", !getters.value.getIsChatShow);
      router.push({ name: "RecruitMain" });
    }

    return { store, getters, checkScrap, emitPerson, setModal, sendDM };
  },
  components: {
    PeopleItem,
  },
};
</script>

<style scoped>
.modal-body {
  padding-top: 0px;
}

.list-group {
  max-height: 210px;
  overflow: auto;
}

.noData {
  max-height: 200px;
  /* border-top: 2px solid black; */
  /* border-bottom: 2px solid black; */
  margin-bottom: 30px;
}

::-webkit-scrollbar {
  display: none;
}

/* Modal */
.modal-content {
  background-color: #eff7f6;
}

.profile-img-box {
  width: 65px;
  height: 65px;
  border-radius: 70%;
  overflow: hidden;
  margin: 10px;
  text-align: center;
}

.profile-img {
  width: 100%;
  height: 100%;
}

.profile-detail-box {
  margin-left: 80px;
  width: 300px;
}

.profile-name {
  margin-left: 5px;
  margin-top: 20px;
  font-size: 20px;
  font-weight: 700;
}

.profile-detail-info {
  width: 90%;
  margin: auto;
}

.user-introduction {
  background-color: #2a9d8f;
  border: 1px solid white;
  color: white;
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

.btn {
  transition: all 0.2s linear;
}
.btn:hover {
  transform: scale(1.1);
}
</style>
