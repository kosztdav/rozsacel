<template>
  <div class="container">
    <div>
      <label>Válassz dolgozót</label>
      <v-select :options="employees" v-model="selectedEmployee" label="name" />
    </div>
  </div>
</template>

<script>
import store from "../store/index";
import { mapGetters } from "vuex";

export default {
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