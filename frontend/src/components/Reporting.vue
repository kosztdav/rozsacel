<template>
  <div class="container">
    <div class="pb-3">
      <label>Válassz dolgozót</label>
      <v-select :options="employees" @input="getReport" v-model="selectedEmployee" label="name" />
    </div>
    <div v-if="selectedEmployee">
      <vue-monthly-picker @input="getReport" :monthLabels="monthLabels" v-model="month"></vue-monthly-picker>
    </div>
    <div class="mt-3" v-if="selectedEmployee && reportData.length != 0">
      <b-table responsive :items="reportData" :fields="fields" class="text-center">
        <template v-slot:head(workPlace)="row">
          <div class="text-left">{{ row.label }}</div>
        </template>
        <template v-slot:cell(workPlace)="row">
          <div class="text-left">{{row.item.workPlace}}</div>
        </template>
        <template v-slot:head(days)="row">
          <div>
            <div>{{row.label}}</div>
            <div>
              <small class="text-muted">(Összesen)</small>
            </div>
          </div>
        </template>
        <template v-slot:cell(hours)="row">
          <div v-if="row.item.minutes == 0">{{row.item.hours + ":00"}}</div>
          <div v-else>{{row.item.hours + ":"+row.item.minutes}}</div>
        </template>
        <template v-slot:cell(wage)="row">
          <div>{{row.item.hours *1300 + row.item.weekendDays * 5000 + row.item.overtime*500}}</div>
        </template>
      </b-table>
    </div>
    <div class="mt-3" v-else-if="selectedEmployee && reportData.length ==0">
      <div class="alert alert-dark text-center" role="alert">
        <i>
          <b>Nincs adat a választott hónapról</b>
        </i>
      </div>
    </div>
  </div>
</template>

<script>
import store from "../store/index";
import { mapGetters } from "vuex";
import { BTable } from "bootstrap-vue";
import VueMonthlyPicker from "vue-monthly-picker";
import api from "../api/api";

export default {
  components: {
    VueMonthlyPicker,
    BTable,
  },
  data() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, "0");
    var mm = String(today.getMonth() + 1).padStart(2, "0");
    var yyyy = today.getFullYear();
    today = mm + "/" + dd + "/" + yyyy;
    return {
      selectedEmployee: null,
      reportData: [],
      month: today,
      fields: [
        {
          key: "workPlace",
          label: "Helyszín",
        },
        {
          key: "days",
          label: "Napok",
        },
        {
          key: "hours",
          label: "Órák",
        },
        {
          key: "halfDays",
          label: "Félnapok",
        },
        {
          key: "weekendDays",
          label: "Hétvége",
        },
        {
          key: "overtime",
          label: "Túlóra",
        },
        {
          key: "wage",
          label: "Bér",
          variant: "danger",
        },
      ],
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
  computed: {
    ...mapGetters(["employees"]),
    mNumber() {
      var date = new Date(this.month);
      return date.getMonth() + 1;
    },
    yNumber() {
      var date = new Date(this.month);
      return date.getFullYear();
    },
  },
  methods: {
    getReport() {
      api
        .getReport(this.selectedEmployee.id, this.yNumber, this.mNumber)
        .then((response) => {
          console.log("Data loaded");
          this.reportData = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
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

