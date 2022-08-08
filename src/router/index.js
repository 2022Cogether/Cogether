import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainView from "../views/MainView.vue";
// import SignView from "../views/SignView.vue";
import RankingView from "../views/RankingView.vue";
import ProfileMain from "../components/profile/ProfileMain.vue";

const routes = [
  {
    path: "/homeview",
    name: "home",
    component: HomeView,
  },
  {
    path: "/",
    name: "mainview",
    component: MainView,
  },
  {
    path: "/api/til",
    name: "TilView",
    component: () => import("@/views/TilView.vue"),
    children: [
      {
        path: "create",
        name: "TilCreate",
        component: () => import("@/components/til/TilCreate.vue"),
      },
      {
        path: ":tilPK",
        name: "TilDetail",
        component: () => import("@/components/til/TilDetail.vue"),
      },
      {
        path: ":tilPK/edit",
        name: "TilUpdate",
        component: () => import("@/components/til/TilUpdate.vue"),
      },
    ],
  },
  {
    path: "/sign",
    name: "signview",
    component: () => import("@/views/SignView.vue"),
    redirect: "/",
    children: [
      {
        path: "signin",
        name: "SignIn",
        component: () => import("@/components/sign/SignIn.vue"),
      },
      {
        path: "signup",
        name: "SignUp",
        component: () => import("@/components/sign/SignUp.vue"),
      },
      {
        path: "passwordseek",
        name: "PassWordSeek",
        component: () => import("@/components/sign/PassWordSeek.vue"),
      },
      {
        path: "passwordchange",
        name: "PassWordChange",
        component: () => import("@/components/sign/PassWordChange.vue"),
      },
      {
        path: "resign",
        name: "ReSign",
        component: () => import("@/components/sign/ReSign.vue"),
      },
    ],
  },
  {
    path: "/challenge",
    name: "challenge",
    component: () => import("@/views/ChallengeView.vue"),
    redirect: "/challenge/main",
    children: [
      {
        path: "main",
        name: "ChallengeMain",
        component: () => import("@/components/challenge/ChallengeMain.vue"),
      },
      {
        path: "list",
        name: "CoopList",
        component: () => import("@/components/challenge/CoopList.vue"),
      },
      {
        path: "create",
        name: "CoopCreate",
        component: () => import("@/components/challenge/CoopCreate.vue"),
      },
      {
        path: "room/:roomNo",
        name: "CoopRoom",
        component: () => import("@/components/challenge/CoopRoom.vue"),
      },
    ],
  },
  {
    path: "/recruit",
    name: "RecruitView",
    component: () => import("@/views/RecruitView.vue"),
    redirect: "/recruit/main",
    children: [
      {
        path: "main",
        name: "RecruitMain",
        component: () => import("@/components/recruit/RecruitMain.vue"),
      },
      {
        path: "study",
        name: "StudyList",
        component: () => import("@/components/recruit/study/StudyList.vue"),
      },
      {
        path: "create",
        name: "RecruitCreate",
        component: () =>
          import("@/components/recruit/create/RecruitCreate.vue"),
      },
      {
        path: "personcreate",
        name: "PersonCreate",
        component: () => import("@/components/recruit/create/PersonCreate.vue"),
      },
    ],
  },
  {
    path: "/ranking",
    name: "ranking",
    component: RankingView,
  },
  {
    path: "/profile",
    name: "profile",
    component: ProfileMain,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
