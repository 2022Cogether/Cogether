import http from "@/api/http";
import Swal from "sweetalert2";

const Toast = Swal.mixin({
  toast: true,
  position: "bottom-end",
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.addEventListener("mouseenter", Swal.stopTimer);
    toast.addEventListener("mouseleave", Swal.resumeTimer);
  },
});

export const signStore = {
  state: {
    // http에서 성공/실패 여부가 component 로컬 변수에 영향을 줄 때 쓸 변수
    booleanValue: false,

    // 프로필 주소 저장
    profilePathStock: "",

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

    // 유저들 리스트
    userListNcik: [],
    userList: [],
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
    getProfilePathStock(state) {
      return state.profilePathStock;
    },
    getUserListNick(state) {
      return state.userListNick;
    },
    getUserListEmail(state) {
      return state.userListEmail;
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

    SET_USER_LIST_NICK: (state, nextlist) => {
      state.userListNick = nextlist;
    },
    SET_USER_LIST_EMAIL: (state, nextlist) => {
      state.userListEmail = nextlist;
    },

    SET_PROFILE_PATH_STOCK: (state, newPath) => {
      state.getProfilePathStock = newPath;
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

    async login({ commit, dispatch, getters }, credentials) {
      console.log(credentials);
      await http
        .post("sign/signin/", credentials)
        .then(async ({ data }) => {
          // console.log(commit);
          const access_TOKEN = data.access_TOKEN;
          const refresh_TOKEN = data.refresh_TOKEN;
          const userId = data.userId;

          dispatch("saveAccess", access_TOKEN); // 엑세스 토큰 갱신
          localStorage.setItem("refresh_TOKEN", refresh_TOKEN); // 리플레시 토큰 갱신

          dispatch("saveUserId", userId);
          await dispatch("fetchCurrentUser", userId); // 현재 사용자 정보 추가
          commit("SET_BOOLEANVALUE");
          if (getters.getCurrentUser.resign) {
            await dispatch("logout");
            Toast.fire({
              icon: "error",
              title: "이미 탈퇴한 회원입니다!",
            });
          }
        })
        .catch((err) => {
          Toast.fire({
            icon: "warning",
            title: "잘못된 정보를 입력하셨습니다.",
          });
          console.error(err.response.data);
        });
    },

    async register({ commit, dispatch }, credentials) {
      await http
        .post("sign/signup/", credentials)
        .then(({ data }) => {
          // console.log(commit);
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

    async fetchCurrentUser({ commit, getters }, userId) {
      if (userId == getters.getLoginUserId) {
        await http
          .get("user/info/" + userId, {
            headers: getters.authHeader,
          })
          .then((res) => {
            console.log(res.data);
            commit("SET_CURRENT_USER", res.data);
          })
          .catch((err) => {
            console.log("현 사용자 정보 저장 중 에러 발생");
            console.error(err.response.data);
            if (err.response.status === 401) {
              commit("SET_TOKEN", "");
              commit("SET_LOGIN_USERID", "");
            }
          });
      } else {
        console.log("현재 이 유저가 아닌 거 같습니다..");
      }
    },

    async fetchAnothertUser({ commit, getters }, userId) {
      await http
        .get("user/info/" + userId, {
          headers: getters.authHeader,
        })
        .then((res) => {
          console.log("다른 유저 프로필");
          console.log(res.data);
          commit("SET_ANOTHER_USER", res.data);
        })
        .catch((err) => {
          console.log("다른 사용자 정보 저장 중 에러 발생");
          commit("SET_BOOLEANVALUE");
          console.error(err.response.data);
        });
    },

    // 비밀번호 검증
    async certifyPassword({ getters }, password) {
      await http
        .post(
          "verify/password",
          { password: password },
          {
            headers: getters.authHeader,
          }
        )
        .then(() => {
          console.log("비밀번호 검증 성공");
        })
        .catch((err) => {
          Toast.fire({
            icon: "warning",
            title: "비밀번호가 잘못되었습니다.",
          });
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
          Toast.fire({
            icon: "success",
            title: "임시 비밀번호를 발급했습니다, 로그인해 주세요!",
          });
          commit("SET_BOOLEANVALUE");
        })
        .catch((err) => {
          Toast.fire({
            icon: "error",
            title: "에러로 임시 비밀번호를 보내지 못했습니다!",
          });
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
            id: getters.getLoginUserId,
          },
          {
            headers: getters.authHeader,
          }
        )
        .then(() => {
          Toast.fire({
            icon: "success",
            title: "비밀번호를 변경했습니다!",
          });
        })
        .catch((err) => {
          Toast.fire({
            icon: "error",
            title: "비밀번호 변경 에러입니다.",
          });
          console.error(err.response.data);
        });
    },

    // 닉네임 중복 체크
    async checkNickName({ commit }, nickName) {
      await http
        .get("verify/nickname/" + nickName)
        .then(({ data }) => {
          if (!data) {
            Toast.fire({
              icon: "success",
              title: "사용가능한 닉네임입니다.",
            });
            commit("SET_BOOLEANVALUE");
          } else {
            Toast.fire({
              icon: "warning",
              title: "중복된 닉네임입니다.",
            });
          }
        })
        .catch((e) => {
          console.log("닉네임 중복 체크 에러: " + e);
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
            Toast.fire({
              icon: "warning",
              title: "이미 가입된 이메일입니다!",
            });
            console.log("중복된 이메일!");
          }
        })
        .catch((e) => {
          Toast.fire({
            icon: "warning",
            title: "이메일 체크에 실패하였습니다!",
          });
          console.log("이메일 중복 체크 에러: " + e);
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
          Toast.fire({
            icon: "warning",
            title: "인증 코드를 보내지 못했습니다!",
          });
          console.log("인증 번호 전송 에러: " + e);
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
          console.log("인증 번호 검증 에러: " + e);
        });
    },

    async logout({ commit, getters }) {
      await http
        .delete("sign/signout", {
          headers: getters.authHeader,
        })
        .then((res) => {
          if (res.data.isLogOut) {
            localStorage.removeItem("access_TOKEN");
            localStorage.removeItem("refresh_TOKEN");
            localStorage.removeItem("userId");
            Toast.fire({
              icon: "success",
              title: "로그아웃 하셨습니다.",
            });
            commit("SET_CURRENT_USER", {});
            commit("SET_TOKEN", "");
            commit("SET_LOGIN_USERID", "");
            commit("SET_BOOLEANVALUE");
          } else {
            Toast.fire({
              icon: "error",
              title: "로그아웃에 실패했습니다.",
            });
          }
        })
        .catch((err) => {
          Toast.fire({
            icon: "error",
            title: "로그아웃에 실패했습니다.",
          });
          console.error(err.response);
        });
    },

    async resign({ commit, getters, dispatch }) {
      await http
        .put("user/resign", {
          headers: getters.authHeader,
        })
        .then(async (res) => {
          console.log("res", res);
          if (res.data.status === 200) {
            Toast.fire({
              icon: "success",
              title: "회원 탈퇴되었습니다.",
            });
            localStorage.removeItem("access_TOKEN");
            localStorage.removeItem("refresh_TOKEN");
            localStorage.removeItem("userId");
            commit("SET_CURRENT_USER", {});
            commit("SET_TOKEN", "");
            commit("SET_LOGIN_USERID", "");
            commit("SET_BOOLEANVALUE");
            await dispatch("logout");
            Toast.fire({
              icon: "success",
              title: "회원 탈퇴했습니다!",
            });
          } else {
            console.log("200이 아닌 다른 값이 반환되었습니다");
          }
        })
        .catch((err) => {
          Toast.fire({
            icon: "error",
            title: "회원 탈퇴 에러입니다.",
          });
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
          Toast.fire({
            icon: "error",
            title: "회원 정보 수정 에러입니다.",
          });
          console.error(err.response.data);
        });
    },

    updateUserImage(context, formData) {
      http
        .post("user/info/profileimg/", formData, {
          headers: {
            ACCESS_TOKEN: localStorage.getItem("access_TOKEN"),
            "Content-Type": "multipart/form-data",
            // ...payload.getHeaders(),
          },
        })
        .then(() => {
          console.log("이미지 수정 성공");
        })
        .catch((err) => {
          console.log("회원 이미지 추가 에러입니다.");
          console.error(err.response.data);
        });
    },

    // 유저 검색
    // 닉네임
    findNickUser({ getters, commit }, keyword) {
      http
        .get("find/nickname/" + keyword, {
          headers: getters.authHeader,
        })
        .then((res) => {
          commit("SET_USER_LIST_NICK", res.data);
        })
        .catch((err) => console.error(err.response));
    },
    // 이메일
    findEmailUser({ getters, commit }, keyword) {
      http
        .get("find/email/" + keyword, {
          headers: getters.authHeader,
        })
        .then((res) => commit("SET_USER_LIST_EMAIL", res.data))
        .catch((err) => console.error(err.response));
    },

    // { REFRESH_TOKEN: localStorage.getItem("REFRESH_TOKEN") }
    // accessToken 재요청
    async refreshToken({ dispatch, getters }) {
      // headers: { REFRESH_TOKEN: localStorage.getItem("refresh_TOKEN") }
      //accessToken 만료로 재발급 후 재요청시 비동기처리로는 제대로 처리가 안되서 promise로 처리함
      let promise = new Promise((resolve, reject) => {
        http
          .post("sign/token" + getters.getLoginUserId, null, {
            // headers: getters.authHeader,
            headers: { REFRESH_TOKEN: localStorage.getItem("refresh_TOKEN") },
          })
          .then((res) => {
            console.log("res data!");
            console.log(
              "예전 access여 안녕!",
              localStorage.getItem("access_TOKEN")
            );
            console.log(
              "예전 refresh여 안녕!",
              localStorage.getItem("refresh_TOKEN")
            );

            console.log("res: ", res);
            dispatch("saveAccess", res.headers.access_token);
            localStorage.setItem("refresh_TOKEN", res.headers.refresh_token);
            console.log(
              "새로운 access여 안녕!",
              localStorage.getItem("access_TOKEN")
            );
            console.log(
              "새로운? refresh여 안녕!",
              localStorage.getItem("refresh_TOKEN")
            );
            resolve(res.data);
          })
          .catch((err) => {
            console.log("refreshToken error : ", err);
            reject(err.response);
          });
      });
      let result = await promise;

      return result;
    },
  },
};
