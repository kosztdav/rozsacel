import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { FormPlugin } from "bootstrap-vue";
import { ButtonPlugin } from 'bootstrap-vue'

Vue.use(ButtonPlugin)
Vue.use(FormPlugin);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
