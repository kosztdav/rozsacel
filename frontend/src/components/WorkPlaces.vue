<template>
  <div>
    <div class="mt-3">
      <b-table
        responsive
        outlined
        :items="allWorkPlaces"
        :fields="fields"
        :per-page="perPage"
        :current-page="currentPage"
      >
        <template v-slot:head(status)="row">
          <div class="text-center">{{ row.label }}</div>
        </template>
        <template v-slot:table-colgroup="scope">
          <col v-for="field in scope.fields" :key="field.key" :style="{ width: '33%' }" />
        </template>
        <template v-slot:cell(name)="row">
          <div v-if="editedRow!=row.item">{{row.item.name}}</div>
          <div v-else-if="editedRow==row.item">
            <input v-model="editedRow.name" class="form-control" />
          </div>
        </template>
        <template v-slot:cell(status)="row">
          <div class="text-center">
            <b-form-checkbox :disabled="editedRow != row.item" v-model="row.item.active"></b-form-checkbox>
          </div>
        </template>
        <template v-slot:cell(action)="row">
          <div class="text-center">
            <div v-if="editedRow == null">
              <button class="btn btn-dark btn-sm" @click="editedRow = row.item">Szerkesztés</button>
            </div>
            <div v-else-if="editedRow==row.item">
              <button class="btn btn-dark btn-sm" @click="saveWorkPlace">Mentés</button>
            </div>
          </div>
        </template>
      </b-table>
    </div>
    <div>
      <b-pagination
        class="mb-4"
        align="center"
        size="sm"
        v-model="currentPage"
        :total-rows="allWorkPlaces.length"
        :per-page="perPage"
      ></b-pagination>
    </div>
  </div>
</template>

<script>
import store from "../store/index";
import api from "../api/api";
import { mapGetters } from "vuex";
import { BTable } from "bootstrap-vue";
import { BPagination } from "bootstrap-vue";
import myMixin from "../mixins/myMixin";

export default {
  components: {
    BTable,
    BPagination,
  },
  mixins: [myMixin],
  data() {
    return {
      editedRow: null,
      currentPage: 1,
      perPage: 3,
      fields: [
        {
          key: "name",
          label: "Név",
        },
        {
          key: "status",
          label: "Státusz",
        },
        {
          key: "action",
          label: "",
        },
      ],
    };
  },
  computed: {
    ...mapGetters(["allWorkPlaces"]),
  },
  methods: {
    saveWorkPlace() {
      api
        .saveWorkPlace(this.editedRow)
        .then((response) => {
          console.log("Data saved: " + response.data);
          this.getWorkPlaces();
        })
        .catch((error) => {
          console.log(error);
        });
      this.editedRow = null;
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

<style >
</style>