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
        path: "create",
        name: "RecruitCreate",
        component: () => import("@/components/recruit/RecruitCreate.vue"),
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
