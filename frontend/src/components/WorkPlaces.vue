<template>
  <div>
    <div>
      <div>
        <label>
          <i>Keresés</i>
        </label>
      </div>
      <div class="row">
        <div class="col text-left" v-if="isMobile">
          <input class="form-control" v-model="filter" />
        </div>
        <div class="col-3 text-left" v-else>
          <input class="form-control" v-model="filter" />
        </div>
        <div class="col text-right">
          <b-button class="btn btn-dark btn-sm" v-b-modal.modal>Új helyszín</b-button>
        </div>
      </div>
    </div>
    <div class="mt-3">
      <b-table
        responsive
        outlined
        :items="allWorkPlaces"
        :current-page="currentPage"
        :filter="filter"
        :fields="fields"
        :per-page="perPage"
        :filterIncludedFields="['name']"
        @filtered="onFiltered"
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
        :total-rows="totalRows"
        :per-page="perPage"
      ></b-pagination>
    </div>

    <b-modal id="modal" title="Új helyszín">
      <div class="row">
        <div class="col-9">
          <div>
            <label>Név</label>
            <input v-model="workPlace.name" class="form-control" />
          </div>
        </div>
        <div class="col">
          <div class="text-center">
            <label>Státusz</label>
            <b-form-checkbox v-model="workPlace.isActive"></b-form-checkbox>
          </div>
        </div>
      </div>
      <template v-slot:modal-footer="{ ok, cancel}">
        <b-button size="sm" variant="dark" @click="newWorkPlace(), ok()">Mentés</b-button>
        <b-button size="sm" variant="outline-dark" @click="cancel()">Mégsem</b-button>
      </template>
    </b-modal>
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
      workPlace: {
        name: "",
        isActive: true,
      },
      filter: "",
      totalRows: 1,
      currentPage: 1,
      perPage: 5,
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
    newWorkPlace() {
      api
        .newWorkPlace(this.workPlace)
        .then((response) => {
          console.log("Data saved: " + response.data);
          this.getWorkPlaces();
          this.workPlace.name = "";
          this.workPlace.isActive = true;
          this.totalRows++;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    onFiltered(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
  },
  mounted() {
    this.totalRows = this.allWorkPlaces.length;
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