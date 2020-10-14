<template>
  <div class="container">
    <div class="pb-3">
      <div class="mt-3">
        <b-nav tabs :small="isMobile">
          <b-nav-item
            :active="chosen == index"
            v-for="(employee, index) in employees"
            @click="chosen = index"
            :key="index"
          >
            {{ employee.name }}
          </b-nav-item>
        </b-nav>
      </div>
      <!--   <label>Válassz dolgozót</label>
      <v-select :options="employees" v-model="selectedEmployee" label="name" /> -->
    </div>
    <div v-if="selectedEmployee">
      <AttendanceSheet :id="selectedEmployee.id" :key="selectedEmployee.id" />
    </div>
  </div>
</template>

<script>
import store from "../store/index";
import { mapGetters } from "vuex";
import AttendanceSheet from "./AttendanceSheet";
import myMixin from "../mixins/myMixin";

export default {
  components: {
    AttendanceSheet,
  },
  data() {
    return {
      chosen: 0,
    };
  },
  computed: {
    ...mapGetters(["employees"]),
    selectedEmployee() {
      return this.employees[this.chosen];
    },
  },
  mixins: [myMixin],
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      if (store.state.user != null && store.state.user.role) {
        next();
      } else {
        next("/");
      }
    });
  },
};
</script>

<style scoped>
</style>