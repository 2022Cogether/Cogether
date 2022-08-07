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
    createProjectTeam({ commit }, param) {
      console.log(param);
      console.log(commit);
      const post = {
        userId: 1,
        start: param.startDate + "T00:00:00",
        total_mem: param.maxPeople,
        cur_mem: param.curPeople,
        online: param.process,
        title: param.title,
        content: param.content,
        skillList: param.useStack,
      };
      http
        .post("project", post)
        .then(({ data }) => {
          console.log("프로젝트팀작성성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    createStudyTeam({ commit }, param) {
      console.log(param);
      console.log(commit);
      http
        .post("study", param)
        .then(({ data }) => {
          console.log("스터디팀작성성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    createProjectPerson({ commit }, param) {
      console.log(param);
      console.log(commit);
      http
        .post("hunting", param)
        .then(({ data }) => {
          console.log("스터디개인작성성공");
          console.log(data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getProjectTeams({ commit }, userId) {
      http
        .get("project/list/" + userId)
        .then(({ data }) => {
          console.log("프로젝트팀리스트받기성공");
          console.log(data);
          commit("SET_PROJECT_TEAMS", data.whatwhat);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
  },
  modules: {},
};
