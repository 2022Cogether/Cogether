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
        .get("sign/skills/" + userId, {
          headers: { Authorization: getters.authHeader },
        })
        .then((res) => {
          if (res.data.status === 200) {
            commit("SET_USER_SKILLSET", res.data);
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          alert("유저 스킬리스트 에러입니다.");
          console.error(err.response.data);
        });
    },

    // 서버에서 Skill Set을 받고 store의 스킬 셋을 업데이트함
    // takeSkillSet({ commit, getters }) {
    //   http
    //     .get("sign/skill/", {
    //       headers: { Authorization: getters.authHeader },
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
