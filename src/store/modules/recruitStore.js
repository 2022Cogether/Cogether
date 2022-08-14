import http from "@/api/http";

export const recruitStore = {
  state: {
    projectTeams: [],
    projectPeople: [],
    studyTeams: [],
  },
  getters: {
    getProjectTeams(state) {
      return state.projectTeams;
    },
    getProjectPeople(state) {
      return state.projectPeople;
    },
    getStudyTeams(state) {
      return state.studyTeams;
    },
  },
  mutations: {
    SET_PROJECT_TEAMS(state, projectTeams) {
      state.projectTeams = projectTeams;
    },
    SET_PROJECT_PEOPLE(state, projectPeople) {
      state.projectPeople = projectPeople;
    },
    SET_STUDY_TEAMS(state, studyTeams) {
      state.studyTeams = studyTeams;
    },
  },
  actions: {
    //모달
    //프로젝트팀
    getProjectTeams({ commit, getters }, userId) {
      http
        .get("project/list/" + userId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("프로젝트팀리스트받기성공");
          console.log(data);
          commit("SET_PROJECT_TEAMS", data.projectList);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getMyProjectTeams({ commit, getters }, userId) {
      http
        .get("project/list/my/" + userId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("나의프로젝트팀리스트받기성공");
          console.log(data);
          commit("SET_PROJECT_TEAMS", data.projectList);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async createProjectTeam({ commit, getters }, param) {
      console.log(commit);
      await http
        .post("project", param, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("프로젝트팀작성성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("dd");
          console.log(getters.authHeader);
          console.log("에러: " + e);
        });
    },
    async deleteProjectTeam({ commit, getters }, projectId) {
      console.log(commit);
      await http
        .delete("project/" + projectId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("프로젝트팀삭제성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    setProjectTeamScrap({ commit, getters }, param) {
      console.log(commit);
      const sendData = {
        projectId: param,
        userId: getters.getLoginUserId,
      };
      http
        .post("project/scrap", sendData, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("프로젝트팀스크랩성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    deleteProjectTeamScrap({ commit, getters }, param) {
      console.log(commit);
      http
        .delete("project/scrap", param, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("프로젝트팀스크랩삭제성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    //프로젝트사람
    getProjectPeople({ commit, getters }, userId) {
      http
        .get("hunting/list/" + userId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("프로젝트개인리스트받기성공");
          commit("SET_PROJECT_PEOPLE", data.huntings);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getMyProjectPeople({ commit, getters }, userId) {
      http
        .get("hunting/list/my/" + userId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("내프로젝트개인리스트받기성공");
          commit("SET_PROJECT_PEOPLE", data.huntings);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async createProjectPerson({ commit, getters }, param) {
      console.log(commit);
      await http
        .post("hunting", param, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("스터디개인작성성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async deleteProjectPerson({ commit, getters }, huntingId) {
      console.log(commit);
      await http
        .delete("hunting/" + huntingId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("스터디개인삭제성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    setProjectPersonScrap({ commit, getters }, param) {
      console.log(commit);
      const sendData = {
        huntingId: param,
        userId: getters.getLoginUserId,
      };
      http
        .post("hunting/scrap", sendData, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("프로젝트개인스크랩성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    deleteProjectPersonScrap({ commit, getters }, param) {
      console.log(commit);
      http
        .delete("hunting/scrap", param, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("프로젝트개인스크랩삭제성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    //스터디팀
    getStudyTeams({ commit, getters }, userId) {
      http
        .get("study/list/" + userId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("스터디팀리스트받기성공");
          console.log(data);
          commit("SET_STUDY_TEAMS", data.studyList);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getMyStudyTeams({ commit, getters }, userId) {
      http
        .get("study/list/my/" + userId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("내스터디팀리스트받기성공");
          console.log(data);
          commit("SET_STUDY_TEAMS", data.studyList);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    createStudyTeam({ commit, getters }, param) {
      console.log(param);
      console.log(commit);
      http
        .post("study", param, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("스터디팀작성성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    async deleteStudyTeam({ commit, getters }, studyId) {
      console.log(commit);
      await http
        .delete("study/" + studyId, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("스터디팀삭제성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    setStudyTeamScrap({ commit, getters }, param) {
      console.log(commit);
      const sendData = {
        studyId: param,
        userId: getters.getLoginUserId,
      };
      http
        .post("study/scrap", sendData, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("스터디팀스크랩성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    deleteStudyTeamScrap({ commit, getters }, param) {
      console.log(commit);
      http
        .delete("study/scrap", param, {
          headers: getters.authHeader,
        })
        .then(({ data }) => {
          console.log("스터디팀스크랩삭제성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
  },
  modules: {},
};
