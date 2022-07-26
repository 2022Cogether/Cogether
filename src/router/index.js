import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainView from "../views/MainView.vue";
import SignView from "../views/SignView.vue";

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
    redirect: "/challenge/list",
    children: [
      {
        path: "list",
        name: "CoopList",
        component: () => import("@/components/challenge/CoopList.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
