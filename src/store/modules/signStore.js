import http from "@/api/http";
import router from "@/router";
import axios from "axios";

export const signStore = {
  state: {
    // http에서 성공/실패 여부가 component 로컬 변수에 영향을 줄 때 쓸 변수
    booleanValue: false,

    // store에 저장되는 스킬 셋 목록, 서버를 통해 생성/업데이트 될 예정
    skillSet: [
      "c",
      "cplusplus",
      "python",
      "java",
      "javascript",
      "spring",
      "react",
      "r",
    ],

    // frontend에서 이메일, 비번, 닉네임의 유효성을 검사할 정규표현식
    emailPattern:
      /^(?=[a-zA-Z0-9@._%+-]{6,254}$)[a-zA-Z0-9._%+-]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,8}[a-zA-Z]{2,63}$/,
    pwdPattern:
      /[a-zA-Z]+(?=.*\d)(?=.*[a-z])(?=.*[~!@#$]).[a-zA-Z\d~!@#$]{7,14}$/,
    nickPattern: /^[ㄱ-ㅎ가-힣a-zA-Z\d./_]{2,15}$/,

    // 로그인 되면 token 추가 / 로그아웃 되면 token 제거
    token: localStorage.getItem("token") || "",

    loginUserId: localStorage.getItem("userId") || "",
    // 현재 유저 정보(id, 닉네임 등)이 저장될 state
    currentUser: {},

    // 다른 유저 정보(다른 유저 profile에 접속할 경우)
    anotherUser: {},
  },
  getters: {
    getSkills(state) {
      return state.skills;
    },
    getBooleanValue(state) {
      const temp = state.booleanValue;
      if (state.booleanValue) {
        // false가 기본 상태이므로 true가 되면 다시 false로 바꿈
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
    getLoginUserId(state) {
      return state.loginUserId;
    },
    getToken(state) {
      return state.token;
    },
    // 인증키로 헤더 세팅 (장고 때 만든 거라 spring에서 다를 수 있음)
    authHeader(state) {
      return { Authorization: `token ${state.token}` };
    },
    getCurrentUser(state) {
      return state.currentUser;
    },
    getAnotherUser(state) {
      return state.anotherUser;
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

    SET_TOKEN: (state, token) => (state.token = token),
    SET_LOGIN_USERID: (state, userid) => (state.loginUserId = userid),

    SET_CURRENT_USER: (state, userData) => (state.currentUser = userData),
    SET_ANOTHER_USER: (state, userData) => (state.anotherUser = userData),
  },
  actions: {
    saveToken({ commit }, token) {
      commit("SET_TOKEN", token);
      localStorage.setItem("token", token); // 로컬 저장소 필요?
    },

    saveUserId({ commit }, userId) {
      commit("SET_LOGIN_USERID", userId);
      localStorage.setItem("userId", userId); // 로컬 저장소 필요?
    },

    async login({ commit, dispatch }, credentials) {
      await http
        .post("sign/signin/", credentials)
        .then(({ data }) => {
          console.log(commit);
          const token = data.access_TOKEN; //리프레쉬 토큰은?? jwt관련 미완성임
          const userId = data.userId;
          dispatch("saveToken", token); // 토큰 갱신
          dispatch("saveUserId", userId);
          dispatch("fetchCurrentUser", userId); // 현재 사용자 정보 추가
          commit("SET_BOOLEANVALUE");
          // commit("RESET_AUTH_ERROR"); // 로그인 오류시 발생할 수 있는 오류 정보 수정(미구현)
          router.go(); // 일단 새로고침하여 메인 페이지 이동하게 해놓음
        })
        .catch((err) => {
          console.error(err.response.data);
        });
    },

    async register({ commit, dispatch }, credentials) {
      await http
        .post("sign/signup/", credentials)
        .then((res) => {
          console.log(commit);
          const token = res.data.access_TOKEN; //리프레쉬 토큰은?? jwt관련 미완성임
          dispatch("saveToken", token);
          dispatch("saveUserId", res.data.userId);
          dispatch("fetchCurrentUser", res.data.userId);
          commit("SET_BOOLEANVALUE");
          // commit("RESET_AUTH_ERROR");
        })
        .catch((err) => {
          console.error(err.response.data);
        });
    },

    fetchCurrentUser({ commit, getters }, userId) {
      if (getters.isLoggedIn) {
        http
          .get("user/" + userId)
          .then((res) => commit("SET_CURRENT_USER", res.data))
          .catch((err) => {
            alert("현 사용자 정보 저장 중 에러 발생");
            if (err.response.status === 401) {
              commit("SET_TOKEN", "");
              commit("SET_LOGIN_USERID", "");
              router.push({ name: "login" });
            }
          });
      }
    },

    fetchAnothertUser({ commit, getters }, userId) {
      if (getters.isLoggedIn) {
        http
          .get("user/" + userId)
          .then((res) => commit("SET_ANOTHER_USER", res.data))
          .catch((err) => {
            alert("현 사용자 정보 저장 중 에러 발생");
            if (err.response.status === 401) {
              commit("SET_TOKEN", "");
              commit("SET_LOGIN_USERID", "");
              router.push({ name: "login" });
            }
          });
      }
    },

    // 이메일을 받고 이메일로 가입한 유저가 있으면 새 비밀번호를 보냄
    takePassWord({ commit }, email) {
      http
        .post("sign/password", { email: email })
        .then((res) => {
          if (res.data.status === 200) {
            alert("비밀번호를 성공적으로 보냈습니다!");
            console.log(commit);
            router.push({ name: "SignIn" });
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
          // commit("SET_AUTH_ERROR", err.response.data);
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

    // 서버에서 Skill Set을 받고 store의 스킬 셋을 업데이트함
    takeSkillSet({ commit }) {
      http
        .get("sign/skill/") // api 필요 없음 AWS에서 사용!
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
        });
    },

    // 닉네임 중복 체크
    async checkNickName({ commit }, nickName) {
      await http
        .get("verify/nickname/" + nickName)
        .then(({ data }) => {
          if (!data) {
            console.log("사용가능한 닉네임!");
            commit("SET_BOOLEANVALUE");
          } else {
            console.log("중복된 닉네임!");
          }
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },

    // 이메일 중복 체크
    async checkEmail({ commit }, email) {
      console.log("이메일 체크");
      await http
        .get("verify/email/" + email)
        .then(({ data }) => {
          if (!data) {
            console.log("사용가능한 이메일!");
            commit("SET_BOOLEANVALUE");
          } else {
            console.log("중복된 이메일!");
          }
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },

    // 변경된 비밀 번호를 받고 서버에 보내 수정
    // 기존 비밀번호도 받아서 이 비밀번호가 유효하면 새 비밀번호로 변경되는 것을 가정함
    changePassword({ getters }, pwSet) {
      axios
        .put("sign/password/", {
          password: pwSet.password,
          newPassword: pwSet.newPassword,
        })
        .then((res) => {
          if (res.data.status === 200) {
            alert("비밀번호를 변경했습니다!");
            getters.isLoggedIn;
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          alert("비밀번호 변경 에러입니다.");
          console.error(err.response.data);
        });
    },

    logout({ commit }, userId) {
      http
        .get("sign/signout/" + userId)
        .then(() => {
          localStorage.removeItem("token");
          localStorage.removeItem("userId");
          alert("성공적으로 logout!");
          commit("SET_CURRENT_USER", {});
          commit("SET_TOKEN", "");
          commit("SET_LOGIN_USERID", "");
          router.push({ name: "mainview" });
          router.go(); // 새로고침 한 번 더해야 로그인 창이됨...지울 수 있을까
        })
        .catch((err) => {
          alert("실패적으로 logout!");
          console.error(err.response);
        });
    },

    resign({ commit }, password) {
      http
        .put("sign/resign/", { password: password })
        .then((res) => {
          if (res.data.status === 200) {
            alert("회원 탈퇴되었습니다!");
            localStorage.removeItem("token");
            localStorage.removeItem("userId");
            commit("SET_CURRENT_USER", {});
            commit("SET_TOKEN", "");
            commit("SET_LOGIN_USERID", "");
            router.push({ name: "mainview" });
            router.go();
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          alert("회원 탈퇴 에러입니다.");
          console.error(err.response.data);
        });
    },
  },
};
