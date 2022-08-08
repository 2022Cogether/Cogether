import http from "@/api/http";
import router from "@/router";

export const signStore = {
  state: {
    // http에서 성공/실패 여부가 component 로컬 변수에 영향을 줄 때 쓸 변수
    booleanValue: false,

    // store에 저장되는 스킬 셋 목록, 서버를 통해 생성/업데이트 될 예정
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

    // frontend에서 이메일, 비번, 닉네임의 유효성을 검사할 정규표현식
    emailPattern:
      /^(?=[a-zA-Z0-9@._%+-]{6,254}$)[a-zA-Z0-9._%+-]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,8}[a-zA-Z]{2,63}$/,
    pwdPattern:
      /[a-zA-Z]+(?=.*\d)(?=.*[a-z])(?=.*[~!@#$]).[a-zA-Z\d~!@#$]{7,14}$/,
    nickPattern: /^[ㄱ-ㅎ가-힣a-zA-Z\d./_]{2,15}$/,

    // 로그인 되면 token 추가 / 로그아웃 되면 token 제거
    token: localStorage.getItem("token") || "",

    // 현재 유저 정보(id, 닉네임 등)이 저장될 state
    currentUser: {},
  },
  getters: {
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

    // 인증키로 헤더 세팅 (장고 때 만든 거라 spring에서 다를 수 있음)
    authHeader(state) {
      return { Authorization: `token ${state.token}` };
    },
    getCurrentUser(state) {
      return state.currentUser;
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

    SET_CURRENT_USER: (state, userData) => (state.currentUser = userData),
  },
  actions: {
    saveToken({ commit }, token) {
      commit("SET_TOKEN", token);
      localStorage.setItem("token", token); // 로컬 저장소 필요?
    },

    login({ commit, dispatch }, credentials) {
      http
        .post("sign/signin/", credentials)
        .then((res) => {
          alert("로그인 성공!");
          const token = res.data.key;
          dispatch("saveToken", token); // 토큰 갱신
          dispatch("fetchCurrentUser"); // 현재 사용자 정보 추가(미구현)
          commit("RESET_AUTH_ERROR"); // 로그인 오류시 발생할 수 있는 오류 정보 수정(미구현)
          router.go(); // 일단 새로고침하여 메인 페이지 이동하게 해놓음
        })
        .catch((err) => {
          alert("로그인 실패!");

          // 이하 코드는 전 프로젝트에서 쓰던 에러 표현 식
          // 나(박홍철)이 쓴 코드에서 중복되며 최종 버젼에는 삭제할 수 있음
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
      http
        .post("sign/signup/", credentials)
        .then((res) => {
          alert("회원가입 성공!");
          const token = res.data.key;
          dispatch("saveToken", token);
          dispatch("fetchCurrentUser");
          commit("RESET_AUTH_ERROR");
          router.push({ name: "mainview" });
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

    // 아직 currentUser state에 무엇을 넣을지 확신을 못해 주석 처리 중
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
    //     http({
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

    // 이메일을 받고 이메일로 가입한 유저가 있으면 새 비밀번호를 보냄
    takePassWord({ commit }, email) {
      http
        .post("user/password", { email: email })
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

    // 서버에서 Skill Set을 받고 store의 스킬 셋을 업데이트함
    takeSkillSet({ commit }) {
      http
        .get("user/skill/") // api 필요 없음 AWS에서 사용!
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

    // 닉네임 중복 체크
    checkNickName({ commit }, nickName) {
      http
        .get("user/verify/" + nickName)
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

    // 이메일 중복 체크
    checkEmail({ commit }, email) {
      console.log("이메일 체크");
      const data = {
        email: email,
      };
      console.log(typeof email + ": " + email);
      http
        .get("user/verify/email", data)
        .then(({ data }) => {
          console.log(commit);
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
      // .then((res) => {
      //   if (res.data.status === 200) {
      //     alert("가능한 이메일입니다!");
      //     commit("SET_BOOLEANVALUE");
      //   } else {
      //     alert("200이 아닌 다른 값이 반환되었습니다");
      //   }
      // })
      // .catch((err) => {
      //   if (err.response.status === 404) {
      //     alert("이미 사용하고 있는 이메일입니다.");
      //   } else if (err.response.status === 500) {
      //     alert("서버 에러입니다.");
      //   } else {
      //     alert("그 외 에러입니다.");
      //   }

      //   console.error(err.response.data);
      //   commit("SET_AUTH_ERROR", err.response.data);
      //   const errorMessage = [];
      //   for (const errors in err.response.data) {
      //     for (const error of err.response.data[errors]) {
      //       if (!errorMessage.includes(error)) {
      //         errorMessage.push(error);
      //       }
      //     }
      //   }
      //   alert(errorMessage.join("\r\n"));
      // });
    },

    // 변경된 비밀 번호를 받고 서버에 보내 수정
    // 기존 비밀번호도 받아서 이 비밀번호가 유효하면 새 비밀번호로 변경되는 것을 가정함
    changePassword({ commit, getters }, pwSet) {
      http
        .put(
          "user/password/",
          {
            password: pwSet.password,
            newPassword: pwSet.newPassword,
          },
          {
            headers: getters.authHeader,
          }
        )
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

    logout({ getters }) {
      http
        .post("user/signout/", { headers: getters.authHeader })
        .then(() => {
          localStorage.removeItem("token");
          alert("성공적으로 logout!");
          router.push({ name: "mainview" });
          router.go(); // 새로고침 한 번 더해야 로그인 창이됨...지울 수 있을까
        })
        .catch((err) => {
          alert("실패적으로 logout!");
          console.error(err.response);
        });
    },

    resign({ commit, getters }, password) {
      http
        .put(
          "user/resign/",
          { password: password },
          { headers: getters.authHeader }
        )
        .then((res) => {
          if (res.data.status === 200) {
            alert("회원 탈퇴되었습니다!");
            localStorage.removeItem("token");
            router.push({ name: "mainview" });
            router.go();
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
