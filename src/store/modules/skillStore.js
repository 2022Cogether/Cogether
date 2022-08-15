import http from "@/api/http";
// import router from "@/router";

export const skillStore = {
  state: {
    // store에 저장되는 스킬 셋 목록, 서버를 통해 생성/업데이트 될 예정
    basicSkillSet: [
      "c",
      "cplusplus",
      "python",
      "java",
      "javascript",
      "spring",
      "react",
      "r",
    ],

    userSkills: [],
  },

  getters: {
    getBasicSkillSet(state) {
      return state.basicSkillSet;
    },
    getUserSkills(state) {
      return state.userSkills;
    },
  },

  mutations: {
    SET_USER_SKILLSET: (state, skills) => {
      state.userSkills = skills;
    },
  },

  actions: {
    // 유저 보유 스킬리스트 조회
    takeUserSkillSet({ commit, getters }, userId) {
      http
        .get("skills/" + userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_USER_SKILLSET", res.data);
        })
        .catch((err) => {
          alert("유저 스킬리스트 에러입니다.");
          console.log("유저 스킬리스트 받아오기 에러");
          console.log(err.message);
          console.log(err.response);
        });
    },

    plusUserSkillSet({ getters }, payload) {
      http
        .post("skills", payload, {
          headers: getters.authHeader,
        })
        .then(() => {})
        .catch((err) => {
          alert("유저 스킬리스트 추가 에러입니다.");
          console.log("유저 스킬리스트 추가하기 에러");
          console.log(err.message);
          console.log(err.response);
        });
    },

    minusUserSkillSet({ getters }, payload) {
      http
        .delete("skills/" + getters.getLoginUserId, {
          data: payload,
          headers: getters.authHeader,
        })
        .then(() => {})
        .catch((err) => {
          alert("유저 스킬리스트 삭제 에러입니다.");
          console.log("유저 스킬리스트 삭제하기 에러");
          console.log(err.message);
          console.log(err.response);
        });
    },

    // 서버에서 Skill Set을 받고 store의 스킬 셋을 업데이트함
    // takeSkillSet({ commit, getters }) {
    //   http
    //     .get("sign/skill/", {
    //       headers: getters.authHeader,
    //     }) // api 필요 없음 AWS에서 사용!
    //     .then((res) => {
    //       if (res.data.status === 200) {
    //         alert("스킬 셋을 성공적으로 받았습니다!");
    //         commit("SET_SKILLSET", res.data);
    //       } else {
    //         alert("200이 아닌 다른 값이 반환되었습니다");
    //       }
    //     })
    //     .catch((err) => {
    //       alert("스킬셋 에러입니다.");
    //       console.error(err.response.data);
    //     });
    // },
  },
};
