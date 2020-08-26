import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Attendance from "../components/AttendanceSheet.vue";
import Settings from "../components/Settings.vue";
import Employees from "../components/Employees.vue";
import WorkPlaces from "../components/WorkPlaces.vue";

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
  },
  {
    path: "/beallitasok",
    name: "Settings",
    component: Settings
  },
  {
    path: "/dolgozok",
    name: "Employees",
    component: Employees
  },
  {
    path: "/helyszinek",
    name: "WorkPlaces",
    component: WorkPlaces
  },
  {
    path: '*', redirect: '/',

  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
