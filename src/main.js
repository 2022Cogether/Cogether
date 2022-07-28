import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faTowerBroadcast,
  faBell,
  faHouse,
  faGamepad,
  faPeopleRoof,
  faTrophy,
  faClipboardQuestion,
  faUser,
  faComments,
  faMagnifyingGlass,
  faHandshake,
  faPenClip,
  faPlus,
  faEllipsisVertical,
  faPeopleGroup,
  faEllipsis,
  faHeart,
  faBookmark,
  faBookmark as faBookmark_solid,
  faAddressBook,
  faXmark,
  faCompress,
  faExpand,
  faPaperPlane,
  faArrowLeft,
} from "@fortawesome/free-solid-svg-icons";
import { faBookmark as faBookmark_reg } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(
  faTowerBroadcast,
  faBell,
  faHouse,
  faGamepad,
  faPeopleRoof,
  faTrophy,
  faClipboardQuestion,
  faUser,
  faComments,
  faMagnifyingGlass,
  faHandshake,
  faPenClip,
  faPlus,
  faEllipsisVertical,
  faPeopleGroup,
  faEllipsis,
  faHeart,
  faBookmark_reg,
  faBookmark_solid,
  faAddressBook,
  faXmark,
  faCompress,
  faExpand,
  faPaperPlane,
  faArrowLeft
);

createApp(App)
  .use(store)
  .use(router)
  .component("font-awesome-icon", FontAwesomeIcon)
  .mount("#app");
