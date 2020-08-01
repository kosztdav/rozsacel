import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Attendance from "../components/AttendanceSheet.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login
  },
  {
    path: "/jelenlet",
    name: "Attendance",
    component: Attendance
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
