<template>
  <div class="container">
    <div>
      <label>Válassz dolgozót</label>
      <v-select :options="employees" v-model="selectedEmployee" label="name" />
      <hr />
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
      if (store.state.user != null) {
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