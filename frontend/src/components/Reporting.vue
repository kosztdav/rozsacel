<template>
  <div class="container">
    <div class="pb-3">
      <vue-monthly-picker
          @input="getReport"
          :monthLabels="monthLabels"
          v-model="month"
      ></vue-monthly-picker>
    </div>
    <div class="mt-3" v-if="user && reportData.length != 0">
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
        <template v-slot:cell(weekendWage)="row">
          <div>
            {{
              (getWageOfPlace(row.index, true)) | currencyFormat
            }}
          </div>
        </template>

        <template v-slot:cell(normalWage)="row">
          <div>
            {{
              (getWageOfPlace(row.index)) | currencyFormat
            }}
          </div>
        </template>
        <template v-slot:cell(hours)="row">
          <span v-if="row.item.minutes != 0">
            {{ row.item.hours }}:{{ row.item.minutes }}
          </span>
          <span v-else>
            {{ row.item.hours }}:00
          </span>
        </template>
        <template v-slot:cell(weekendHours)="row">
          <span v-if="row.item.weekendMinutes != 0">
             {{ row.item.weekendHours }}:{{ row.item.weekendMinutes }}
          </span>
          <span v-else>
             {{ row.item.weekendHours }}:00
          </span>
        </template>
        <template v-slot:cell(wage)="row">
          <div>
            {{
              (getTotalWageOfPlace(row.index)) | currencyFormat
            }}
          </div>
        </template>
        <template v-slot:foot(workPlace)>
          <div class="text-left">
            <div>Alapbér: {{ user.baseWage | currencyFormat }}</div>
            <div>
              Órabér: {{ user.wagePerHour | currencyFormat }}
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
          <div>
            {{
              (user.baseWage + normalWage + weekendWage)
                  | currencyFormat
            }}
          </div>
          <div>
            Össz: {{ totalHours }}
          </div>
        </template>
      </b-table>
    </div>
    <div class="mt-3" v-else-if="user && reportData.length == 0">
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
import {mapGetters} from "vuex";
import {BTable} from "bootstrap-vue";
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
          variant: "success",
        },
        {
          key: "weekendHours",
          label: "Hétvégi órák",
        },
        {
          key: "weekendWage",
          label: "Hétvég bér",
          variant: "success",
        },
        {
          key: "wage",
          label: "Össz bér",
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
    ...mapGetters(["employees", "user"]),
    mNumber() {
      const date = new Date(this.month);
      return date.getMonth() + 1;
    },
    yNumber() {
      const date = new Date(this.month);
      return date.getFullYear();
    },
    days() {
      let days = 0;
      this.reportData.forEach((el) => {
        days += el.days;
      });
      return days;
    },
    hours() {
      let hours = 0;
      let minutes = 0;
      this.reportData.forEach((el) => {
        hours += el.hours;
        minutes += el.minutes;
      });
      while (minutes >= 60) {
        hours++;
        minutes -= 60;
      }
      return hours + ":" + (minutes != 0 ? minutes : '00');
    },
    totalHours() {
      let hours = 0;
      let minutes = 0;
      this.reportData.forEach((el) => {
        hours += el.hours;
        hours += el.weekendHours;
        minutes += el.minutes;
        minutes += el.weekendMinutes;
      });
      while (minutes >= 60) {
        hours++;
        minutes -= 60;
      }
      return hours + ":" + (minutes != 0 ? minutes : '00');
    },
    normalWage() {
      let hours = 0;
      let minutes = 0;
      this.reportData.forEach((el) => {
        hours += el.hours;
        minutes += el.minutes;
      });
      hours += minutes / 60;
      let wage = hours * this.user.wagePerHour;
      let fixedWage = parseInt(wage.toFixed());
      return fixedWage;
    },
    weekendHours() {
      let weekend = 0;
      let weekendMinutes = 0;
      this.reportData.forEach((el) => {
        weekend += el.weekendHours;
        weekendMinutes += el.weekendMinutes;
      });
      while (weekendMinutes >= 60) {
        weekend++;
        weekendMinutes -= 60;
      }
      return weekend + ":" + (weekendMinutes != 0 ? weekendMinutes : '00');
    },
    weekendWage() {
      let hours = 0;
      let minutes = 0;
      this.reportData.forEach((el) => {
        hours += el.weekendHours;
        minutes += el.weekendMinutes;
      });
      hours += minutes / 60;
      let wage = hours * this.user.wagePerHour * 1.5;
      let fixedWage = parseInt(wage.toFixed());
      return fixedWage;
    },
  },
  mounted() {
    this.getReport();
  },
  methods: {
    getReport() {
      api
          .getReport(this.user.id, this.yNumber, this.mNumber)
          .then((response) => {
            console.log("Data loaded");
            this.reportData = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
    },
    getWageOfPlace(idx, isWeekend = false) {
      let hours = 0;
      let minutes = 0;
      if (!isWeekend) {
        hours = this.reportData[idx].hours;
        minutes = this.reportData[idx].minutes;
        hours += minutes / 60;
        let wage = this.user.wagePerHour * hours;
        let fixedWage = parseInt(wage.toFixed());
        return fixedWage;
      }
      hours = this.reportData[idx].weekendHours;
      minutes = this.reportData[idx].weekendMinutes;
      hours += minutes / 60;
      let wage = this.user.wagePerHour * hours * 1.5;
      let fixedWage = parseInt(wage.toFixed());
      return fixedWage;
    },
    getTotalWageOfPlace(idx) {
      let normalWage = this.getWageOfPlace(idx);
      let weekendWage = this.getWageOfPlace(idx, true);
      return normalWage + weekendWage;
    }
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
      if (store.state.user != null) {
        next();
      } else {
        next("/");
      }
    });
  },
};
</script>

