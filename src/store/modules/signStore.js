import http from "@/api/http";

export const signStore = {
  state: {
    // http에서 성공/실패 여부가 component 로컬 변수에 영향을 줄 때 쓸 변수
    booleanValue: false,

    // frontend에서 이메일, 비번, 닉네임의 유효성을 검사할 정규표현식
    emailPattern:
      /^(?=[a-zA-Z0-9@._%+-]{6,254}$)[a-zA-Z0-9._%+-]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,8}[a-zA-Z]{2,63}$/,
    pwdPattern:
      /[a-zA-Z]+(?=.*\d)(?=.*[a-z])(?=.*[~!@#$]).[a-zA-Z\d~!@#$]{7,14}$/,
    nickPattern: /^[ㄱ-ㅎ가-힣a-zA-Z\d./_]{2,15}$/,

    // 로그인 되면 access token 추가 / 로그아웃 되면 token 제거
    token: localStorage.getItem("access_TOKEN") || "",
    // token: 1, //cors에러때문에 각 메뉴로 못 갈 때 이용 (test용)

    loginUserId: localStorage.getItem("userId") || "",
    // loginUserId: 1, //cors에러때문에 각 메뉴로 못 갈 때 이용 (test용)
    // 현재 유저 정보(id, 닉네임 등)이 저장될 state
    currentUser: {},

    // 다른 유저 정보(다른 유저 profile에 접속할 경우)
    anotherUser: {},
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
    getLoginUserId(state) {
      return state.loginUserId;
    },
    getToken(state) {
      return state.token;
    },
    // 인증키로 헤더 세팅 (장고 때 만든 거라 spring에서 다를 수 있음)
    authHeader(state) {
      return { ACCESS_TOKEN: state.token };
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

    SET_TOKEN: (state, token) => (state.token = token),
    SET_LOGIN_USERID: (state, userid) => (state.loginUserId = userid),

    SET_CURRENT_USER: (state, userData) => (state.currentUser = userData),
    SET_ANOTHER_USER: (state, userData) => (state.anotherUser = userData),
  },
  actions: {
    saveAccess({ commit }, accessToken) {
      commit("SET_TOKEN", accessToken);
      localStorage.setItem("access_TOKEN", accessToken); // 로컬 저장소 필요?
    },

    saveUserId({ commit }, userId) {
      commit("SET_LOGIN_USERID", userId);
      localStorage.setItem("userId", userId);
    },

    async login({ commit, dispatch }, credentials) {
      await http
        .post("sign/signin/", credentials)
        .then(({ data }) => {
          console.log(commit);
          const access_TOKEN = data.access_TOKEN;
          const refresh_TOKEN = data.refresh_TOKEN;
          const userId = data.userId;

          dispatch("saveAccess", access_TOKEN); // 엑세스 토큰 갱신
          localStorage.setItem("refresh_TOKEN", refresh_TOKEN); // 리플레시 토큰 갱신

          dispatch("saveUserId", userId);
          dispatch("fetchCurrentUser", userId); // 현재 사용자 정보 추가
          commit("SET_BOOLEANVALUE");
        })
        .catch((err) => {
          console.error(err.response.data);
        });
    },

    async register({ commit, dispatch }, credentials) {
      await http
        .post("sign/signup/", credentials)
        .then(({ data }) => {
          console.log(commit);
          const access_TOKEN = data.access_TOKEN;
          const refresh_TOKEN = data.refresh_TOKEN;
          const userId = data.userId;

          dispatch("saveAccess", access_TOKEN);
          localStorage.setItem("refresh_TOKEN", refresh_TOKEN); // 리플레시 토큰 갱신

          dispatch("saveUserId", userId);
          dispatch("fetchCurrentUser", userId);
          commit("SET_BOOLEANVALUE");
        })
        .catch((err) => {
          console.error(err.response.data);
        });
    },

    fetchCurrentUser({ commit, getters }, userId) {
      if (getters.isLoggedIn) {
        http
          .get("user/" + userId, {
            headers: getters.authHeader,
          })
          .then((res) => commit("SET_CURRENT_USER", res.data))
          .catch((err) => {
            alert("현 사용자 정보 저장 중 에러 발생");
            console.error(err.response.data);
            if (err.response.status === 401) {
              commit("SET_TOKEN", "");
              commit("SET_LOGIN_USERID", "");
            }
          });
      }
    },

    fetchAnothertUser({ commit, getters }, userId) {
      if (getters.isLoggedIn) {
        http
          .get("user/" + userId, {
            headers: getters.authHeader,
          })
          .then((res) => commit("SET_ANOTHER_USER", res.data))
          .catch((err) => {
            alert("다른 사용자 정보 불러오는 중 에러 발생");
            console.error(err.response.data);
          });
      }
    },

    // 비밀번호 검증
    certifyPassword({ commit, getters }, password) {
      http
        .post(
          "verify/password",
          { password: password },
          {
            headers: getters.authHeader,
          }
        )
        .then(() => {
          console.log("비밀번호 검증 성공");
          commit("SET_BOOLEANVALUE");
        })
        .catch((err) => {
          alert("비밀번호 검증 실패");
          console.error(err.response.data);
        });
    },

    // 이메일로 임시비밀번호 전송
    getTempPassWord({ commit, getters }, email) {
      http
        .post(
          "find/password",
          { email: email },
          {
            headers: getters.authHeader,
          }
        )
        .then(() => {
          alert("임시 비밀번호를 발급했습니다, 로그인해 주세요!");
          commit("SET_BOOLEANVALUE");
        })
        .catch((err) => {
          alert("에러로 임시 비밀번호를 보내지 못했습니다!");
          console.error(err.response.data);
        });
    },

    // 비밀번호 변경
    changePassword({ getters }, newPassword) {
      http
        .put(
          "user/password/",
          {
            password: newPassword,
          },
          {
            headers: getters.authHeader,
          }
        )
        .then(() => {
          alert("비밀번호를 변경했습니다!");
        })
        .catch((err) => {
          alert("비밀번호 변경 에러입니다.");
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
            alert("이미 가입된 이메일입니다!");
            console.log("중복된 이메일!");
          }
        })
        .catch((e) => {
          alert("이메일 체크에 실패하였습니다!");
          console.log("에러: " + e);
        });
    },

    // 인증 코드 전송
    async postCode({ commit, getters }, email) {
      await http
        .post(
          "sign/verify",
          {
            email: email,
          },
          {
            headers: getters.authHeader,
          }
        )
        .then(() => {
          console.log("인증 번호 전송 완료");
          commit("SET_BOOLEANVALUE");
        })
        .catch((e) => {
          alert("인증 코드를 보내지 못했습니다!");
          console.log("에러: " + e);
        });
    },

    // 인증 코드 검증
    async sendCode({ commit, getters }, payload) {
      await http
        .get("sign/verify", {
          params: payload,
          headers: getters.authHeader,
        })
        .then(() => {
          console.log("인증 번호 검증 완료");
          commit("SET_BOOLEANVALUE");
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },

    logout({ commit, getters }) {
      http
        .get("sign/signout", {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.data.verified) {
            localStorage.removeItem("access_TOKEN");
            localStorage.removeItem("refresh_TOKEN");
            localStorage.removeItem("userId");
            alert("성공적으로 logout!");
            commit("SET_CURRENT_USER", {});
            commit("SET_TOKEN", "");
            commit("SET_LOGIN_USERID", "");
            commit("SET_BOOLEANVALUE");
          } else {
            alert("인증 실패로 logout 실패!");
          }
        })
        .catch((err) => {
          alert("실패적으로 logout!");
          console.error(err.response);
        });
    },

    resign({ commit, getters }) {
      http
        .put("sign/resign/", {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.data.status === 200) {
            alert("회원 탈퇴되었습니다!");
            localStorage.removeItem("access_TOKEN");
            localStorage.removeItem("refresh_TOKEN");
            localStorage.removeItem("userId");
            commit("SET_CURRENT_USER", {});
            commit("SET_TOKEN", "");
            commit("SET_LOGIN_USERID", "");
            commit("SET_BOOLEANVALUE");
          } else {
            alert("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          alert("회원 탈퇴 에러입니다.");
          console.error(err.response.data);
        });
    },

    // Profile 업데이트
    updateProfile({ dispatch, getters, state }, payload) {
      http
        .put("user/info/", payload, {
          headers: getters.authHeader,
        })
        .then(() => {
          dispatch("fetchCurrentUser", state.loginUserId);
        })
        .catch((err) => {
          alert("회원 정보 수정 에러입니다.");
          console.error(err.response.data);
        });
    },

    // accessToken 재요청
    refreshToken({ commit }) {
      //accessToken 만료로 재발급 후 재요청시 비동기처리로는 제대로 처리가 안되서 promise로 처리함
      return new Promise((resolve, reject) => {
        http
          .post("sign/token", null, {
            headers: { REFRESH_TOKEN: localStorage.getItem("REFRESH_TOKEN") },
          })
          .then((res) => {
            commit("saveAccess", res.data.access_TOKEN);
            resolve(res.data.access_TOKEN);
          })
          .catch((err) => {
            console.log("refreshToken error : ", err.config);
            reject(err.config.data);
          });
      });
    },
  },
};
