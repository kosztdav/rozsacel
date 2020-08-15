import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null,
    employees: []
  },
  mutations: {
    storeUser(state, user) {
      state.user = user;
    },
    storeEmployees(state, employees) {
      state.employees = employees
    }
  },
  actions: {
    async authUser({ commit }, { user }) {
      commit("storeUser", user);
    },
    async setEmployees({ commit }, { employees }) {
      commit("storeEmployees", employees);
    }
  },
  getters: {
    user(state) {
      return state.user;
    },
    isAdmin(state) {
      return state.user.role;
    },
    employees(state) {
      return state.employees;
    }
  }
});
