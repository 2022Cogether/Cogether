import http from "@/api/http";

export const recruitStore = {
  state: {
    projectTeams: [
      {
        projectId: 1,
        userId: 1,
        userNickname: "홍철",
        userImgurl: null,
        start: "2022-08-01T15:17:22",
        total_mem: 6,
        cur_mem: 2,
        online: "온라인",
        title: "프로젝트 모집",
        content: "프로젝트를 함께할 팀원을 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: false,
      },
      {
        projectId: 2,
        userId: 1,
        userNickname: "진회",
        userImgurl: null,
        start: "2022-08-02T15:17:22",
        total_mem: 6,
        cur_mem: 2,
        online: "미정",
        title: "백엔드 모집",
        content: "프로젝트를 함께할 백엔드를 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: true,
      },
      {
        projectId: 3,
        userId: 1,
        userNickname: "지연",
        userImgurl: null,
        start: "2022-08-03T15:17:22",
        total_mem: 6,
        cur_mem: 2,
        online: "오프라인",
        title: "함 모집",
        content: "프로젝트를 함 팀원을 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: false,
      },
      {
        projectId: 4,
        userId: 1,
        userNickname: "나령",
        userImgurl: null,
        start: "2022-08-04T15:17:22",
        total_mem: 10,
        cur_mem: 9,
        online: "미정",
        title: "꺄 모집",
        content: "프로젝트를 꺄 백엔드를 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: true,
      },
      {
        projectId: 5,
        userId: 1,
        userNickname: "성은",
        userImgurl: null,
        start: "2022-08-05T15:17:22",
        total_mem: 5,
        cur_mem: 3,
        online: "미정",
        title: "ㅇㅇ 모집",
        content: "프로젝트를 ㅇㅇ 팀원을 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: false,
      },
      {
        projectId: 6,
        userId: 1,
        userNickname: "진회",
        userImgurl: null,
        start: "2022-08-6T15:17:22",
        total_mem: 6,
        cur_mem: 2,
        online: "미정",
        title: "66 모집",
        content: "프로젝트를 66 백엔드를 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: true,
      },
    ],
    projectPeople: [
      {
        huntingId: 1,
        userId: 1,
        userNickname: "홍철",
        userImgurl: null,
        title: "나 어때",
        content: "나 이런 사람이야",
        skillList: [
          {
            huntingSkillId: 1,
            huntingId: 1,
            skillName: "java",
          },
          {
            huntingSkillId: 2,
            huntingId: 1,
            skillName: "c",
          },
        ],
        scrap: false,
      },
      {
        huntingId: 2,
        userId: 2,
        userNickname: "성은",
        userImgurl: null,
        title: "나는 22",
        content: "나는 22 사람이야",
        skillList: [],
        scrap: true,
      },
      {
        huntingId: 1,
        userId: 1,
        userNickname: "홍철",
        userImgurl: null,
        title: "나 어때",
        content: "나 이런 사람이야",
        skillList: [
          {
            huntingSkillId: 1,
            huntingId: 1,
            skillName: "java",
          },
          {
            huntingSkillId: 2,
            huntingId: 1,
            skillName: "c",
          },
        ],
        scrap: false,
      },
      {
        huntingId: 2,
        userId: 2,
        userNickname: "성은",
        userImgurl: null,
        title: "나는 22",
        content: "나는 22 사람이야",
        skillList: [],
        scrap: true,
      },
      {
        huntingId: 1,
        userId: 1,
        userNickname: "홍철",
        userImgurl: null,
        title: "나 어때",
        content: "나 이런 사람이야",
        skillList: [
          {
            huntingSkillId: 1,
            huntingId: 1,
            skillName: "java",
          },
          {
            huntingSkillId: 2,
            huntingId: 1,
            skillName: "c",
          },
        ],
        scrap: true,
      },
      {
        huntingId: 2,
        userId: 2,
        userNickname: "성은",
        userImgurl: null,
        title: "나는 22",
        content: "나는 22 사람이야",
        skillList: [],
        scrap: true,
      },
    ],
    studyTeams: [
      {
        studyId: 1,
        userId: 1,
        userNickname: "홍철",
        userImgurl: null,
        start: "2022-08-01T15:17:22",
        total_mem: 6,
        cur_mem: 2,
        online: "온라인",
        title: "프로젝트 모집",
        content: "프로젝트를 함께할 팀원을 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: false,
      },
      {
        studyId: 2,
        userId: 1,
        userNickname: "진회",
        userImgurl: null,
        start: "2022-08-02T15:17:22",
        total_mem: 6,
        cur_mem: 2,
        online: "미정",
        title: "백엔드 모집",
        content: "프로젝트를 함께할 백엔드를 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: true,
      },
      {
        studyId: 3,
        userId: 1,
        userNickname: "지연",
        userImgurl: null,
        start: "2022-08-03T15:17:22",
        total_mem: 6,
        cur_mem: 2,
        online: "오프라인",
        title: "함 모집",
        content: "프로젝트를 함 팀원을 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: false,
      },
      {
        studyId: 4,
        userId: 1,
        userNickname: "나령",
        userImgurl: null,
        start: "2022-08-04T15:17:22",
        total_mem: 10,
        cur_mem: 9,
        online: "미정",
        title: "꺄 모집",
        content: "프로젝트를 꺄 백엔드를 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: true,
      },
      {
        studyId: 5,
        userId: 1,
        userNickname: "성은",
        userImgurl: null,
        start: "2022-08-05T15:17:22",
        total_mem: 5,
        cur_mem: 3,
        online: "미정",
        title: "ㅇㅇ 모집",
        content: "프로젝트를 ㅇㅇ 팀원을 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: true,
      },
      {
        studyId: 6,
        userId: 1,
        userNickname: "진회",
        userImgurl: null,
        start: "2022-08-6T15:17:22",
        total_mem: 6,
        cur_mem: 2,
        online: "미정",
        title: "66 모집",
        content: "프로젝트를 66 백엔드를 모집합니다.",
        skillList: [
          {
            projectSkillId: 1,
            projectId: 1,
            skillName: "java",
          },
          {
            projectSkillId: 2,
            projectId: 1,
            skillName: "c",
          },
        ],
        createdAt: null,
        scrap: true,
      },
    ],
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
          commit("SET_PROJECT_TEAMS", data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getProjectPeople({ commit }, userId) {
      http
        .get("hunting/list/" + userId)
        .then(({ data }) => {
          console.log("프로젝트개인리스트받기성공");
          console.log(data);
          commit("SET_PROJECT_People", data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
    getStudyTeams({ commit }, userId) {
      http
        .get("study/list/" + userId)
        .then(({ data }) => {
          console.log("스터디팀리스트받기성공");
          console.log(data);
          commit("SET_STUDY_TEAMS", data);
        })
        .catch((e) => {
          console.log("에러: " + e);
        });
    },
  },
  modules: {},
};
