<template>
  <div class="container">
    <div class="pb-3">
      <label>Válassz dolgozót</label>
      <v-select
        :options="employees"
        @input="getReport"
        v-model="selectedEmployee"
        label="name"
      />
    </div>
    <div v-if="selectedEmployee">
      <vue-monthly-picker
        @input="getReport"
        :monthLabels="monthLabels"
        v-model="month"
      ></vue-monthly-picker>
    </div>
    <div class="mt-3" v-if="selectedEmployee && reportData.length != 0">
      <b-table
        foot-variant="light"
        foot-clone
        responsive
        :items="reportData"
        :fields="fields"
        class="text-center"
      >
        <template v-slot:head(workPlace)="row">
          <div class="text-left">{{ row.label }}</div>
        </template>
        <template v-slot:cell(workPlace)="row">
          <div class="text-left">{{ row.item.workPlace }}</div>
        </template>
        <template v-slot:head(days)="row">
          <div>
            <div>{{ row.label }}</div>
            <div>
              <small class="text-muted">(Összesen)</small>
            </div>
          </div>
        </template>

        <template v-slot:cell(weekendWage)="row">
          <div>
            {{
              (row.item.weekendHours * (selectedEmployee.wagePerHour * 1.5))
                | currencyFormat
            }}
          </div>
        </template>

        <template v-slot:cell(normalWage)="row">
          <div>
            {{
              (row.item.hours * selectedEmployee.wagePerHour) | currencyFormat
            }}
          </div>
        </template>

        <template v-slot:cell(wage)="row">
          <div>
            {{
              (row.item.hours * selectedEmployee.wagePerHour +
                row.item.weekendHours * (selectedEmployee.wagePerHour * 1.5))
                | currencyFormat
            }}
          </div>
        </template>
        <template v-slot:foot(workPlace)>
          <div class="text-left">
            <div>Alapbér: {{ selectedEmployee.baseWage | currencyFormat }}</div>
            <div>
              Órabér: {{ selectedEmployee.wagePerHour | currencyFormat }}
            </div>
          </div>
        </template>
        <template v-slot:foot(days)>
          {{ days }}
        </template>
        <template v-slot:foot(hours)>
          {{ hours }}
        </template>
        <template v-slot:foot(normalWage)>
          {{ normalWage | currencyFormat }}
        </template>
        <template v-slot:foot(weekendHours)>
          {{ weekendHours }}
        </template>
        <template v-slot:foot(weekendWage)>
          {{ weekendWage | currencyFormat }}
        </template>
        <template v-slot:foot(wage)>
          {{
            (selectedEmployee.baseWage + normalWage + weekendWage)
              | currencyFormat
          }}
        </template>
      </b-table>
    </div>
    <div class="mt-3" v-else-if="selectedEmployee && reportData.length == 0">
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
          key: "normalWage",
          label: "Bér",
          variant: "danger",
        },
        {
          key: "weekendHours",
          label: "Hétvégi órák",
        },
        {
          key: "weekendWage",
          label: "Hétvég bér",
          variant: "danger",
        },
        {
          key: "wage",
          label: "Össz bér",
          variant: "secondary",
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
    days() {
      var days = 0;
      this.reportData.forEach((el) => {
        days += el.days;
      });
      return days;
    },
    hours() {
      var hours = 0;
      this.reportData.forEach((el) => {
        hours += el.hours;
      });
      return hours;
    },
    normalWage() {
      var wage = 0;
      this.reportData.forEach((el) => {
        wage += el.hours;
      });
      return wage * this.selectedEmployee.wagePerHour;
    },
    weekendHours() {
      var weekend = 0;
      this.reportData.forEach((el) => {
        weekend += el.weekendHours;
      });
      return weekend;
    },
    weekendWage() {
      var weekend = 0;
      this.reportData.forEach((el) => {
        weekend += el.weekendHours;
      });
      return weekend * this.selectedEmployee.wagePerHour * 1.5;
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
  filters: {
    currencyFormat(number) {
      return new Intl.NumberFormat("hu-HU", {
        style: "currency",
        currency: "HUF",
        minimumFractionDigits: 0,
      }).format(number);
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

