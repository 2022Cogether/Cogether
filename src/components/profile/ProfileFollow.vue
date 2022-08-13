<template>
  <div class="modal d-flex justify-content-center" @click="closeModal">
    <div class="modal-card">
      <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
        <li class="nav-item" role="presentation">
          <button
            class="nav-link active"
            id="pills-followings-tab"
            data-bs-toggle="pill"
            data-bs-target="#pills-followings"
            type="button"
            role="tab"
            aria-controls="pills-followings"
            aria-selected="true"
          >
            Followings
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button
            class="nav-link"
            id="pills-followers-tab"
            data-bs-toggle="pill"
            data-bs-target="#pills-followers"
            type="button"
            role="tab"
            aria-controls="pills-followers"
            aria-selected="false"
          >
            Followers
          </button>
        </li>
      </ul>
      <div class="tab-content" id="pills-tabContent">
        <div
          class="tab-pane fade show active"
          id="pills-followings"
          role="tabpanel"
          aria-labelledby="pills-followings-tab"
        >
          ...
        </div>
        <div
          class="tab-pane fade"
          id="pills-followers"
          role="tabpanel"
          aria-labelledby="pills-followers-tab"
        >
          ...
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  name: "ProfileFollow",
  props: {},
  emits: ["closeModal"],
  setup(props, { emit }) {
    const store = useStore();
    const getters = computed(() => store.getters);
    props;
    getters;

    // 모달 바깥을 클릭하면 모달을 닫게 하는 함수
    const closeModal = (event) => {
      if (
        !document
          .querySelector(".modal")
          .querySelector("." + event.target.className.split(" ")[0]) // 클릭한 박스의 클래스가 modal-card라는 클래스의 하위 클래스인지 아닌지
      ) {
        emit("closeModal");
      }
    };

    return {
      closeModal,
    };
  },
};
</script>

<style lang="scss" scoped>
.modal-card {
  background-color: white;
  width: 70vw;
  height: auto;
}
</style>
