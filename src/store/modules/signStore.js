import axios from "axios";
import router from "@/router";

export const signStore = {
  state: {
    booleanValue: false,

    skillSet: [
      "C",
      "C++",
      "Python",
      "Java",
      "JavaScript",
      "Spring",
      "Django",
      "R",
    ],

    emailPattern:
      /^(?=[a-zA-Z0-9@._%+-]{6,254}$)[a-zA-Z0-9._%+-]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,8}[a-zA-Z]{2,63}$/,
    pwdPattern:
      /[a-zA-Z]+(?=.*\d)(?=.*[a-z])(?=.*[~!@#$]).[a-zA-Z\d~!@#$]{7,14}$/,
    nickPattern: /^[ㄱ-ㅎ가-힣a-zA-Z\d./_]{2,15}$/,
    token: localStorage.getItem("token") || "",
  },
  getters: {
    getBooleanValue(state) {
      const temp = state.booleanValue;
      if (state.booleanValue) {
        state.booleanValue = false;
      }
      return temp;
    },
    isLoggedIn(state) {
      return !!state.token;
    },
    getEmailPattern(state) {
      return state.emailPattern;
    },
    getPwdPattern(state) {
      return state.pwdPattern;
    },
    getNickPattern(state) {
      return state.nickPattern;
    },
    getSkillSet(state) {
      return state.skillSet;
    },
  },
  mutations: {
    SET_BOOLEANVALUE: (state) => {
      state.booleanValue = true;
    },

    SET_SKILLSET: (state, skills) => {
      for (const skill in skills) {
        state.skillSet.push(skill);
      }
    },
  },
  actions: {
    login({ commit, dispatch }, credentials) {
      axios
        .post("/sign/signin/", credentials)
        .then((res) => {
          alert("로그인 성공!");
          const token = res.data.key;
          dispatch("saveToken", token);
          dispatch("fetchCurrentUser");
          commit("RESET_AUTH_ERROR");
          router.push({ name: "ProfileMain" });
        })
        .catch((err) => {
          alert("로그인 실패!");
          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },

    register({ commit, dispatch }, credentials) {
      axios
        .post("/sign/signup/", credentials)
        .then((res) => {
          alert("회원가입 성공!");
          const token = res.data.key;
          dispatch("saveToken", token);
          dispatch("fetchCurrentUser");
          commit("RESET_AUTH_ERROR");
          router.push({ name: "ProfileMain" });
        })
        .catch((err) => {
          alert("회원가입 실패!");
          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },

    // fetchCurrentUser({ commit, getters, dispatch }) {
    //   /*
    //   GET: 사용자가 로그인 했다면(토큰이 있다면)
    //     currentUserInfo URL로 요청보내기
    //       성공하면
    //         state.cuurentUser에 저장
    //       실패하면(토큰이 잘못되었다면)
    //         기존 토큰 삭제
    //         LoginView로 이동
    //   */
    //   if (getters.isLoggedIn) {
    //     axios({
    //       url: drf.accounts.currentUserInfo(),
    //       method: 'get',
    //       headers: getters.authHeader,
    //     })
    //       .then(res => commit('SET_CURRENT_USER', res.data))
    //       .catch(err => {
    //         if (err.response.status === 401) {
    //           dispatch('removeToken')
    //           router.push({ name: 'login' })
    //         }
    //       })
    //   }
    // },

    takePassWord({ commit }, email) {
      axios
        .post("/sign/passwordseek/", email)
        .then((res) => {
          if (res.data.status === 200) {
            alert("비밀번호를 성공적으로 보냈습니다!");
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          if (err.response.status === 404) {
            alert("가입되지 않은 이메일입니다.");
          } else if (err.response.status === 500) {
            alert("서버 에러입니다.");
          } else {
            alert("그 외 에러입니다.");
          }

          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },

    takeSkillSet({ commit }) {
      axios
        .post("/sign/skill/")
        .then((res) => {
          if (res.data.status === 200) {
            alert("스킬 셋을 성공적으로 받았습니다!");
            commit("SET_SKILLSET", res.data);
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          alert("스킬셋 에러입니다.");

          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },

    checkNickName({ commit }, nickName) {
      axios
        .post("/sign/nickname/", nickName)
        .then((res) => {
          if (res.data.status === 200) {
            alert("가능한 닉네임입니다!");
            commit("SET_BOOLEANVALUE");
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          if (err.response.status === 404) {
            alert("이미 사용하고 있는 닉네임입니다.");
          } else if (err.response.status === 500) {
            alert("서버 에러입니다.");
          } else {
            alert("그 외 에러입니다.");
          }

          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },

    checkEmail({ commit }, email) {
      axios
        .post("/sign/email/", email)
        .then((res) => {
          if (res.data.status === 200) {
            alert("가능한 이메일입니다!");
            commit("SET_BOOLEANVALUE");
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          if (err.response.status === 404) {
            alert("이미 사용하고 있는 이메일입니다.");
          } else if (err.response.status === 500) {
            alert("서버 에러입니다.");
          } else {
            alert("그 외 에러입니다.");
          }

          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },

    changePassword({ commit }, pwSet) {
      axios
        .post("/sign/passwordchange/", pwSet)
        .then((res) => {
          if (res.data.status === 200) {
            alert("비밀번호를 변경했습니다!");
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          alert("에러입니다.");

          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },

    logout({ commit }, password) {
      axios
        .post("/sign/signout/", password)
        .then((res) => {
          if (res.data.status === 200) {
            alert("회원 탈퇴되었습니다!");
            router.push({ name: "mainview" });
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          alert("에러입니다.");

          console.error(err.response.data);
          commit("SET_AUTH_ERROR", err.response.data);
          const errorMessage = [];
          for (const errors in err.response.data) {
            for (const error of err.response.data[errors]) {
              if (!errorMessage.includes(error)) {
                errorMessage.push(error);
              }
            }
          }
          alert(errorMessage.join("\r\n"));
        });
    },
  },
};
