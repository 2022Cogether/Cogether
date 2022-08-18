import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainView from "../views/MainView.vue";
// import SignView from "../views/SignView.vue";
import RankingView from "../views/RankingView.vue";
import ProfileView from "../views/ProfileView.vue";

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
    path: "/til",
    name: "TilView",
    component: () => import("@/views/TilView.vue"),
    children: [
      {
        path: "create",
        name: "TilCreate",
        component: () => import("@/components/til/TilCreate.vue"),
      },
      // {
      //   path: ":tilPk",
      //   name: "TilDetail",
      //   component: () => import("@/components/til/TilDetail.vue"),
      // },
      {
        path: ":tilPk/edit",
        name: "TilUpdate",
        component: () => import("@/components/til/TilUpdate.vue"),
      },
      {
        path: "list/my/:userId",
        name: "TilList",
        component: () => import("@/components/til/TilListLayout.vue"),
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
    name: "profileView",
    component: ProfileView,
    children: [
      {
        path: ":userId",
        name: "profile",
        component: () => import("@/components/profile/ProfileMain.vue"),
      },
      {
        path: ":userId/edit",
        name: "profileEdit",
        component: () => import("@/components/profile/ProfileEdit.vue"),
      },
      {
        path: "pro",
        name: "pro",
        compoonent: () => import("@/components/profile/ProPro.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

// 네비게이션 가드
import store from "@/store";

let profileBool = false;
// let profilePath = "";

router.beforeEach((to, from, next) => {
  console.log("#to", to);
  console.log("#from", from);
  console.log("#from FullPath", from.fullPath, "bvbvbvvv  ");
  console.log("#to FullPath", to.fullPath, "toooo   ");
  if (
    to.name != "SignIn" &&
    to.name != "SignUp" &&
    to.name != "PassWordSeek" &&
    !store.getters.isLoggedIn
  )
    next({ name: "SignIn" }); // 아직 TOKEN 없어서 ! 붙여놓았음
  else if (
    from.name == "profile" &&
    to.name == "profile" &&
    from.fullPath != to.fullPath &&
    !profileBool
  ) {
    // profileBool = true;
    // profilePath = to.fullPath;
    // next({ name: "mainview" });
    console.log("라우터 변경 감지");
    next();
    // next();
  } else next();
});

export default router;
