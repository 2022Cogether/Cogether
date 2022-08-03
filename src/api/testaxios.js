//test 나중에 삭제
import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "https://de64a989-2614-4194-b6d1-445d399f5f08.mock.pstmn.io",
  headers: {
    "Content-type": "application/json",
  },
});
