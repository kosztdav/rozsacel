import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null,
    employees: [],
    workPlaces: [],
    allWorkPlaces: []
  },
  mutations: {
    storeUser(state, user) {
      state.user = user;
    },
    storeEmployees(state, employees) {
      state.employees = employees
    },
    storeWorkPlaces(state, places) {
      state.workPlaces = places
    },
    storeAllWorkPlaces(state, places) {
      state.allWorkPlaces = places
    }
  },
  actions: {
    authUser({ commit }, { user }) {
      commit("storeUser", user);
    },
    setEmployees({ commit }, { employees }) {
      commit("storeEmployees", employees);
    },
    setWorkPlaces({ commit }, { places }) {
      commit("storeWorkPlaces", places);
    },
    setAllWorkPlaces({ commit }, { places }) {
      commit("storeAllWorkPlaces", places);
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
    },
    workPlaces(state) {
      return state.workPlaces;
    },
    allWorkPlaces(state) {
      return state.allWorkPlaces;
    }
  }
});
