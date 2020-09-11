
<template>
  <div>
    <b-form @submit.stop.prevent>
      <b-form-group id="name" label="Név" label-for="name">
        <b-form-input :state="validation" v-model="userName" @input="checkUser"></b-form-input>
        <b-form-invalid-feedback>Nincs ilyen felhasználó</b-form-invalid-feedback>
      </b-form-group>
      <b-form-group id="pass" label="Jelszó" label-for="pass">
        <b-form-input type="password" v-model="password"></b-form-input>
      </b-form-group>
      <button class="btn btn-dark" @click="login" :disabled="!validation || password==''">Belépés</button>
    </b-form>
  </div>
</template>

<script>
import { BFormGroup } from "bootstrap-vue";
import { BFormInput } from "bootstrap-vue";
import api from "../api/api";
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
import store from "../store/index";
import myMixin from "../mixins/myMixin";

export default {
  components: {
    BFormGroup,
    BFormInput,
  },
  mixins: [myMixin],
  data() {
    return {
      userName: "",
      password: "",
      validation: null,
    };
  },
  computed: {
    ...mapGetters(["user", "isAdmin"]),
  },
  mounted() {
    this.authUser({ user: null });
  },
  methods: {
    ...mapActions(["authUser", "setEmployees"]),
    checkUser() {
      api
        .doesUserExist(this.userName)
        .then((response) => {
          this.validation = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    login() {
      api
        .getUserData(this.userName, this.password)
        .then((response) => {
          this.authUser({ user: response.data });
          this.getWorkPlaces();
          if (this.isAdmin) {
            this.getEmployees();
            this.$router.push({ name: "AttendanceAdmin" });
          } else {
            this.$router.push({ name: "Attendance" });
          }
        })
        .catch((error) => {
          console.log(error);
          alert("Hibás adatok");
        });
    },
    getEmployees() {
      api
        .getAllUsers()
        .then((response) => {
          var employees = new Array();
          response.data.forEach((employee) => {
            if (!employee.role) {
              employees.push(employee);
            }
          });
          this.setEmployees({ employees });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
