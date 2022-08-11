<template>
  <h2>People</h2>
  <ul class="list-group list-group-flush" @click="openModal">
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
            <PeopleItem :projectPerson="projectPerson" />
          </span>
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
          <PeopleItem :projectPerson="projectPerson" />
        </span>
      </span>
    </span>
  </ul>
  <!-- 모달 -->
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
  <!-- <people-modal
    v-if="
      getters.getIsOpenRecruitModal && getters.getRecruitModalType == 'people'
    "
    :projectPerson="getters.getProjectPeople[0]"
  /> -->
</template>

<script>
// import PeopleModal from "@/components/recruit/projectPeople/PeopleModal.vue";
import PeopleItem from "@/components/recruit/projectPeople/PeopleItem.vue";
import { computed } from "vue";
import { useStore } from "vuex";
export default {
  name: "PeopleList",
  props: ["searchText", "tabState"],
  setup() {
    const store = useStore();
    const getters = computed(() => store.getters);
    store.dispatch("getProjectPeople", getters.value.getLoginUserId);

    function checkScrap() {
      for (const item of getters.value.getProjectPeople) {
        if (item.scrap) return true;
      }
      return false;
    }

    function openModal() {}
    return { store, getters, checkScrap, openModal };
  },
  components: {
    PeopleItem,
    // PeopleModal,
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
</style>
