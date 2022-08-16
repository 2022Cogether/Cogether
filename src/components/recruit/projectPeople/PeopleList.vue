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
          <div class="profile-detail-box d-flex">
            <!-- 프로필사진 -->
            <div class="image-box">
              <img src="@/assets/logo.png" alt="profile image" />
            </div>
            <!-- 이름 및 한줄소개 -->
            <div class="profile-detail-info">
              <h5 class="modal-title" id="personDetailInfoLabel">
                {{ emitPerson.userNickname }}
              </h5>
              <p>{{ emitPerson.title }}</p>
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
</style>
