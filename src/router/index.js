import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainView from "../views/MainView.vue";
import SignView from "../views/SignView.vue";
import RecruitView from "../views/RecruitView.vue";

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
    redirect: "/challenge/list",
    children: [
      {
        path: "list",
        name: "CoopList",
        component: () => import("@/components/challenge/CoopList.vue"),
      },
    ],
  },
  {
    path: "/recruit",
    name: "recruit",
    component: RecruitView,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
