import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { FormPlugin } from "bootstrap-vue";
import { ButtonPlugin } from 'bootstrap-vue'
import { TablePlugin } from 'bootstrap-vue'
import vSelect from "vue-select";
import 'vue-select/dist/vue-select.css';
import VueTimepicker from 'vue2-timepicker/src/vue-timepicker.vue'
import { FormCheckboxPlugin } from 'bootstrap-vue'
import { Slide } from 'vue-burger-menu'
import { ModalPlugin } from 'bootstrap-vue'
import { SpinnerPlugin } from 'bootstrap-vue'

Vue.component("v-select", vSelect);
Vue.component("vue-timepicker", VueTimepicker)
Vue.component("Slide", Slide);


Vue.use(SpinnerPlugin)
Vue.use(Slide);
Vue.use(FormCheckboxPlugin)
Vue.use(VueTimepicker)
Vue.use(TablePlugin)
Vue.use(ButtonPlugin)
Vue.use(FormPlugin);
Vue.use(ModalPlugin)

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
