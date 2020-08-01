<template>
  <div class="container">
    <div>
      <div class="d-flex justify-content-center">
        <div style="width:80%">
          <div class="row">
            <div class="col">
              <label for>Válassz hónapot</label>
            </div>
          </div>
          <div class="row">
            <div class="col">
              <vue-monthly-picker :monthLabels="monthLabels" v-model="month"></vue-monthly-picker>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from "../store/index";
import VueMonthlyPicker from "vue-monthly-picker";

export default {
  components: {
    VueMonthlyPicker,
  },
  data() {
    return {
      month: null,
      monthLabels: [
        "Jan",
        "Feb",
        "Már",
        "Ápr",
        "Máj",
        "Jún",
        "Júl",
        "Aug",
        "Szep",
        "Okt",
        "Nov",
        "Dec",
      ],
    };
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

