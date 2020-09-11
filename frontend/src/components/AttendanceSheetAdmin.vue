<template>
  <div class="container">
    <div class="pb-3">
      <label>Válassz dolgozót</label>
      <v-select :options="employees" v-model="selectedEmployee" label="name" />
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

export default {
  components: {
    AttendanceSheet,
  },
  data() {
    return {
      selectedEmployee: null,
    };
  },
  computed: {
    ...mapGetters(["employees"]),
  },
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