<template>
  <h2>Study</h2>
  <ul class="list-group list-group-flush">
    <!-- 글 없음 -->
    <span
      class="noData"
      v-if="tabState != 'scrap' && getters.getStudyTeams.length == 0"
    >
      등록된 글이 없습니다.
    </span>
    <!-- 스크랩 탭 -->
    <span v-if="tabState == 'scrap'">
      <span v-if="!checkScrap()"> 스크랩된 글이 없습니다.</span>
      <span v-for="(studyTeam, i) in getters.getStudyTeams" :key="i">
        <span
          v-if="
            searchText == null ||
            studyTeam.title.indexOf(searchText) != -1 ||
            studyTeam.userNickname.indexOf(searchText) != -1
          "
        >
          <span v-if="studyTeam.scrap">
            <StudyItem :studyTeam="studyTeam" @setModal="setModal" />
          </span>
        </span>
      </span>
    </span>
    <!-- 내가 쓴 글 -->
    <span v-else-if="tabState == 'my'">
      <span v-for="(studyTeam, i) in getters.getStudyTeams" :key="i">
        <span
          v-if="
            searchText == null ||
            studyTeam.title.indexOf(searchText) != -1 ||
            studyTeam.userNickname.indexOf(searchText) != -1
          "
        >
          <StudyItem :studyTeam="studyTeam" @setModal="setModal" />
        </span>
      </span>
    </span>
    <!-- 스터디 탭 -->
    <span v-else>
      <span v-for="(studyTeam, i) in getters.getStudyTeams" :key="i">
        <span
          v-if="
            searchText == null ||
            studyTeam.title.indexOf(searchText) != -1 ||
            studyTeam.userNickname.indexOf(searchText) != -1
          "
        >
          <StudyItem :studyTeam="studyTeam" @setModal="setModal" />
        </span>
      </span>
    </span>
  </ul>
  <!-- 모달창 -->
  <div
    v-if="emitStudy != null"
    class="modal fade"
    id="studyDetailInfo"
    tabindex="-1"
    aria-labelledby="studyDetailInfoLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
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
            <div class="image-box">
              <img src="@/assets/logo.png" alt="profile image" />
            </div>
            <div class="profile-detail-info">
              <div class="d-flex">
                <h5 class="modal-title" id="teamDetailInfoLabel">
                  {{ emitStudy.userNickname }}
                </h5>
              </div>
            </div>
          </div>
          <div class="team-recruit-info-box">
            <div class="d-flex flex-column">
              <div class="d-flex justify-content-start info-box">
                <h5>진행방식</h5>
                <p>{{ emitStudy.online }}</p>

                <h5>모집상태</h5>
                <p>{{ emitStudy.cur_mem }} / {{ emitStudy.total_mem }}</p>
              </div>
              <div class="d-flex justify-content-start info-box">
                <h5>시작예정일</h5>
                <p>{{ emitStudy.start }}</p>
              </div>
            </div>
          </div>
          <div class="team-detail-introduction">
            <p>
              {{ emitStudy.content }}
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
import StudyItem from "@/components/recruit/study/StudyItem.vue";
import { computed, ref } from "vue";
import { useStore } from "vuex";
export default {
  name: "StudyList",
  props: ["searchText", "tabState"],
  setup(props) {
    const store = useStore();
    const getters = computed(() => store.getters);

    if (props.tabState == "my") {
      store.dispatch("getMyStudyTeams", getters.value.getLoginUserId);
    } else {
      store.dispatch("getStudyTeams", getters.value.getLoginUserId);
    }

    function checkScrap() {
      for (const item of getters.value.getStudyTeams) {
        if (item.scrap) return true;
      }
      return false;
    }

    // 모달창emit관련
    let emitStudy = ref("");
    function setModal(data) {
      emitStudy.value = data;
      emitStudy.value.start = data.start.substring(0, 10);
    }

    return { store, getters, checkScrap, setModal, emitStudy };
  },
  components: {
    StudyItem,
  },
};
</script>

<style scoped>
.list-group {
  max-height: 650px;
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

/* 모달창 */
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

.team-detail-introduction {
  padding: 15px;
}

.modal-footer > .btn {
  background-color: #2a9d8f;
  color: #fff;
}
</style>
