import store from "@/store";
import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "http://i7a801.p.ssafy.io:8080/api",
  headers: {
    "Content-type": "application/json",
  },
});

// axios interceptors를 통해 access 토큰 검증 -> refresh 배출
let isRefreshing = false;
let subscribers = [];

axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (err) => {
    const {
      config,
      response: { status },
    } = err;

    const originalRequest = config;

    // Refresh Token 마저 완료된 경우
    // if (status === 403) {
    //   router.push({ name: "SignIn" });
    //   return Promise.reject(false);
    // }

    // if (originalRequest.url.include("login_check")) {
    //   return Promise.reject(err);
    // }

    if (status === 401) {
      if (!isRefreshing) {
        isRefreshing = true;
        store
          .dispatch("refreshToken")
          .then(({ status }) => {
            if (status === 200) {
              isRefreshing = false;
            }
            subscribers = [];
          })
          .catch((error) => {
            console.log(error);
          });
      }

      const requestSubscribers = new Promise((resolve) => {
        subscribeTokenRefresh(() => {
          resolve(axios(originalRequest));
        });
      });

      onRefreshed();

      return requestSubscribers;
    }
  }
);

function subscribeTokenRefresh(cb) {
  subscribers.push(cb);
}

function onRefreshed() {
  subscribers.map((cb) => cb());
}

subscribers = [];
