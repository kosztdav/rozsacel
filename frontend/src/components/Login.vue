
<template>
  <div>
    <b-form @submit.stop.prevent>
      <b-form-group id="name" label="Név" label-for="name">
        <b-form-input :state="validation" v-model="userName" @input="checkUser"></b-form-input>
        <b-form-invalid-feedback>Nincs ilyen felhasználó</b-form-invalid-feedback>
      </b-form-group>
      <b-form-group id="name" label="Jelszó" label-for="name">
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

export default {
  components: {
    BFormGroup,
    BFormInput,
  },
  data() {
    return {
      userName: "",
      password: "",
      validation: null,
    };
  },
  computed: {
    ...mapGetters(["user"]),
  },
  mounted() {
    this.authUser({ user: null });
  },
  methods: {
    ...mapActions(["authUser"]),
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
          this.$router.push({ name: "Attendance" });
        })
        .catch((error) => {
          console.log(error);
          alert("Hibás adatok");
        });
    },
  },
};
</script>
