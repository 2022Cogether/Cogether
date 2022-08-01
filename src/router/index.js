import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainView from "../views/MainView.vue";
import SignView from "../views/SignView.vue";
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
    path: "/signview",
    name: "signview",
    component: SignView,
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
        path: "room",
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
        component: () => import("@/components/recruit/StudyList.vue"),
      },
      {
        path: "create",
        name: "RecruitCreate",
        component: () => import("@/components/recruit/RecruitCreate.vue"),
      },
      {
        path: "personcreate",
        name: "PersonCreate",
        component: () => import("@/components/recruit/PersonCreate.vue"),
      },
      {
        path: "studycreate",
        name: "StudyCreate",
        component: () => import("@/components/recruit/StudyCreate.vue"),
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