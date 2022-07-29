//test 나중에 삭제
import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "http://names.drycodes.com",
  headers: {
    "Content-type": "application/json",
  },
});
