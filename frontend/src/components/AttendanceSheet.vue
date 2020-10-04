<template>
  <div>
    <div>
      <div>
        <div>
          <div class="row">
            <div class="col">
              <label for>Válassz hónapot</label>
            </div>
          </div>
          <div class="row">
            <div class="col">
              <vue-monthly-picker
                @input="refreshData(), (isBusy = true), (busyText = 'Betöltés')"
                :monthLabels="monthLabels"
                v-model="month"
              ></vue-monthly-picker>
            </div>
          </div>
        </div>
      </div>
      <div class="mt-3">
        <b-table
          class="tbl"
          responsive
          outlined
          :items="tableData"
          :fields="fields"
          :key="tblKey"
          :busy="isBusy"
        >
          <template v-slot:table-busy>
            <div class="text-center my-2">
              <b-spinner class="align-middle" variant="dark"></b-spinner>
              <strong> {{ busyText }}...</strong>
            </div>
          </template>
          <template v-slot:table-colgroup="scope">
            <col
              v-for="field in scope.fields"
              :key="field.key"
              :style="
                field.key != 'action' ? { width: '22%' } : { width: '12%' }
              "
            />
          </template>
          <template v-slot:cell(day)="row">
            <div v-if="typeof month === 'object'">
              {{
                monthLabels[month._d.getMonth()] + " " + row.item.index + "."
              }}
            </div>
            <div v-else>
              {{ monthLabels[mNumber] + " " + row.item.index + "." }}
            </div>
          </template>
          <template v-slot:cell(from)="row">
            <div>{{ row.item.from }}</div>
          </template>
          <template v-slot:cell(to)="row">
            <div>{{ row.item.to }}</div>
          </template>
          <template v-slot:cell(place)="row">
            <div>{{ row.item.place.name }}</div>
          </template>
          <template v-slot:cell(action)="row">
            <div class="text-center">
              <div v-if="editedRow == null && isEditable(row.item.index)">
                <div
                  class="btn"
                  @click="
                    (editedRow = JSON.parse(JSON.stringify(row.item))),
                      $bvModal.show('modal')
                  "
                >
                  <img src="../assets/modify.png" width="25" alt="" />
                </div>
              </div>
              <div v-else-if="compare(editedRow, row.item)">
                <div class="text-center my-2">
                  <b-spinner
                    small
                    class="align-middle"
                    variant="dark"
                  ></b-spinner>
                </div>
              </div>
            </div>
          </template>
          <template v-slot:foot()>
            <span></span>
          </template>
        </b-table>
        <div class="alert alert-dark" role="alert" v-if="yNumber != 1970">
          <i>
            <b>Összesen: {{ hours }}</b>
          </i>
        </div>
      </div>
    </div>
    <div>
      <!-- MODAL -->
      <b-modal id="modal" title="Jelenlét rögzítés" @hide="hide">
        <div v-if="editedRow">
          <div class="row">
            <div class="col">
              <label>Mettől</label>
              <div>
                <vue-timepicker
                  :minute-interval="10"
                  v-model="editedRow.from"
                ></vue-timepicker>
              </div>
            </div>
            <div class="col">
              <label>Meddig</label>
              <div>
                <vue-timepicker
                  :minute-interval="10"
                  v-model="editedRow.to"
                ></vue-timepicker>
              </div>
            </div>
          </div>
          <div class="row mt-3">
            <div class="col">
              <label>Helyszín</label>
              <v-select
                :options="workPlaces"
                v-model="editedRow.place"
                label="name"
              />
            </div>
          </div>
        </div>
        <template v-slot:modal-footer="{ cancel }">
          <b-button size="sm" variant="dark" @click="save">Mentés</b-button>
          <b-button size="sm" variant="outline-dark" @click="cancel()"
            >Mégsem</b-button
          >
        </template>
      </b-modal>
    </div>
  </div>
</template>

<script>
import store from "../store/index";
import VueMonthlyPicker from "vue-monthly-picker";
import { BTable } from "bootstrap-vue";
import api from "../api/api";
import { mapGetters } from "vuex";

export default {
  props: {
    id: Number,
  },
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
      tableData: [],
      month: today,
      tblKey: 0,
      editedRow: null,
      dataToSave: {
        from: "",
        to: "",
        place: {},
      },
      userId: null,
      isBusy: true,
      timeSheet: [],
      busyText: "",
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
      fields: [
        {
          key: "day",
          label: "Nap",
        },
        {
          key: "from",
          label: "Mettől",
        },
        {
          key: "to",
          label: "Meddig",
        },
        { key: "place", label: "Helyszín" },
        { key: "action", label: "" },
      ],
    };
  },
  computed: {
    ...mapGetters(["isAdmin", "user", "workPlaces"]),
    daysOfMonth() {
      var date = new Date(this.month);
      var mm = String(date.getMonth() + 1).padStart(2, "0");
      var yyyy = date.getFullYear();
      var numberOfDays = new Date(yyyy, mm, 0).getDate();

      return numberOfDays;
    },
    mNumber() {
      var date = new Date(this.month);
      return date.getMonth();
    },
    yNumber() {
      var date = new Date(this.month);
      return date.getFullYear();
    },
    hours() {
      var hours = 0;
      var minutes = 0;
      this.timeSheet.forEach((row) => {
        var from = new Date();
        var to = new Date();
        from.setHours(row.from.substring(0, 2));
        from.setMinutes(row.from.substring(3, 5));
        to.setHours(row.to.substring(0, 2));
        to.setMinutes(row.to.substring(3, 5));
        hours += to.getHours() - from.getHours();
        minutes += to.getMinutes() - from.getMinutes();
      });
      if (minutes >= 60) {
        while (minutes >= 60) {
          minutes -= 60;
          hours++;
        }
      } else if (minutes < 0) {
        while (minutes < 0) {
          minutes += 60;
          hours--;
        }
      }
      if (minutes == 0) {
        return hours + " óra";
      }
      return hours + " óra " + minutes + " perc";
    },
  },
  mounted() {
    if (this.id == null) {
      this.userId = this.user.id;
    } else {
      this.userId = this.id;
    }
    this.busyText = "Betöltés";
    this.getData();
    this.getTimeSheet();
  },
  methods: {
    isEditable(idx) {
      if (this.user.role) {
        return true;
      }
      var today = new Date();
      return (
        today.getMonth() == this.mNumber &&
        today.getFullYear() == this.yNumber &&
        (today.getDate() == idx || today.getDate() == idx + 1)
      );
    },
    compare(a, b) {
      return JSON.stringify(a) === JSON.stringify(b);
    },
    getData() {
      this.tableData = new Array();
      //console.log("getData");
      for (let i = 0; i < this.daysOfMonth; i++) {
        this.tableData[i] = {
          index: i + 1,
          from: "",
          to: "",
          place: { name: "" },
        };
      }
      this.tblKey++;
      this.editedRow = null;
    },
    getTimeSheet() {
      //console.log("getTimeSheet");
      api
        .getTimeSheet(this.userId, this.yNumber, this.mNumber + 1)
        .then((response) => {
          this.timeSheet = response.data;
          this.timeSheet.forEach((time) => {
            this.tableData[time.index] = time;
            this.tableData[time.index].place = {
              name: time.place,
              id: time.placeId,
            };
            this.tableData[time.index].index = time.index + 1;
          });
          this.tblKey++;
          this.isBusy = false;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    refreshData() {
      this.getData();
      this.getTimeSheet();
    },
    hide() {
      this.$bvModal.hide("modal");
      this.editedRow = null;
    },
    save() {
      var r = this.editedRow;
      this.$bvModal.hide("modal");
      this.busyText = "Mentés";
      this.isBusy = true;
      if (r.place == null || r.from == "" || r.to == "") {
        api
          .deleteTimeSheet(this.userId, this.yNumber, this.mNumber, r.index)
          .then((response) => {
            console.log("Data deleted: " + response.data);
            this.refreshData();
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        api
          .postTimeSheet(
            this.userId,
            r.from,
            r.to,
            r.place.id,
            this.yNumber,
            this.mNumber,
            r.index
          )
          .then((response) => {
            console.log("Data saved: " + response.data);
            this.refreshData();
          })
          .catch((error) => {
            console.log(error);
          });
      }
      this.editedRow = null;
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
<style>
.tbl {
  overflow: inherit;
}
</style>
