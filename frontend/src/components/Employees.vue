<template>
  <div class="mt-3">
    <b-table responsive outlined :items="employees" :fields="fields">
      <template v-slot:table-colgroup="scope">
        <col
          v-for="field in scope.fields"
          :key="field.key"
          :style="field.key != 'action' ? { width: '22%' } : { width: '12%' }"
        />
      </template>
      <template v-slot:cell(baseWage)="row">
        {{ row.item.baseWage | currencyFormat }}
      </template>
      <template v-slot:cell(wagePerHour)="row">
        {{ row.item.wagePerHour | currencyFormat }}
      </template>
      <template v-slot:cell(action)="row">
        <div class="text-center">
          <div v-if="editedRow == null">
            <div
              class="btn"
              @click="
                (editedRow = JSON.parse(JSON.stringify(row.item))),
                  (editedRow.index = row.index);
                $bvModal.show('modal');
              "
            >
              <img src="../assets/modify.png" width="25" alt="" />
            </div>
          </div>
          <div v-else-if="editedRow != null && editedRow.index == row.index">
            <div class="text-center my-2">
              <b-spinner small class="align-middle" variant="dark"></b-spinner>
            </div>
          </div>
        </div>
      </template>
    </b-table>

    <!-- MODAL -->
    <b-modal id="modal" title="Adatmódosítás" @hide="hide">
      <div v-if="editedRow">
        <div class="row">
          <div class="col">
            <div>
              <label>Név</label>
              <input class="form-control" v-model="editedRow.name" />
            </div>
          </div>
          <div class="col">
            <div>
              <label>Felhasználó</label>
              <input class="form-control" v-model="editedRow.userName" />
            </div>
          </div>
        </div>
        <div class="row mt-3">
          <div class="col">
            <div>
              <label>Alapbér</label>
              <input class="form-control" v-model="editedRow.baseWage" />
            </div>
          </div>
          <div class="col">
            <div>
              <label>Órabér</label>
              <input class="form-control" v-model="editedRow.wagePerHour" />
            </div>
          </div>
        </div>
      </div>
      <template v-slot:modal-footer="{ cancel }">
        <b-button v-if="editedRow" size="sm" variant="dark" @click="save"
          >Mentés</b-button
        >
        <b-button size="sm" variant="outline-dark" @click="cancel()"
          >Mégsem</b-button
        >
      </template>
    </b-modal>
  </div>
</template>

<script>
import api from "../api/api";
import store from "../store/index";
import { BTable } from "bootstrap-vue";
import { mapGetters } from "vuex";
import myMixin from "../mixins/myMixin";

export default {
  data() {
    return {
      editedRow: null,
      fields: [
        {
          key: "name",
          label: "Név",
        },
        {
          key: "baseWage",
          label: "Alapbér",
        },
        {
          key: "wagePerHour",
          label: "Órabér",
        },
        {
          key: "action",
          label: "",
        },
      ],
    };
  },
  components: {
    BTable,
  },
  mixins: [myMixin],
  computed: {
    ...mapGetters(["employees"]),
  },
  methods: {
    hide() {
      this.$bvModal.hide("modal");
      this.editedRow = null;
    },
    save() {
      api
        .saveEmployee(this.editedRow)
        .then((response) => {
          console.log("Data saved: " + response.data);
          this.getEmployees();
          this.$bvModal.hide("modal");
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
      if (store.state.user != null) {
        next();
      } else {
        next("/");
      }
    });
  },
};
</script>