import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null
  },
  mutations: {
    storeUser(state, user) {
      state.user = user;
    }
  },
  actions: {
    async authUser({ commit }, { user }) {
      commit("storeUser", user);
    }
  },
  modules: {},
  getters: {
    user(state) {
      return state.user;
    }
  }
});
