import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainView from "../views/MainView.vue";
import SignView from "../views/SignView.vue";
import RecruitView from "../views/RecruitView.vue";
import RankingView from "../views/RankingView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/mainview",
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
    name: "recruit",
    component: RecruitView,
  },
  {
    path: "/ranking",
    name: "ranking",
    component: RankingView,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
