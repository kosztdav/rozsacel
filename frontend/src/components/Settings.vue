<template>
  <div class="container">
    <b-form @submit.stop.prevent>
      <b-form-group id="old" label="Régi jelszó" label-for="old">
        <b-form-input type="password" v-model="oldPass"></b-form-input>
        <b-form-invalid-feedback></b-form-invalid-feedback>
      </b-form-group>
      <b-form-group id="new" label="Új jelszó" label-for="new">
        <b-form-input type="password" v-model="newPass"></b-form-input>
      </b-form-group>
      <button class="btn btn-dark" @click="changePassword">Módosít</button>
    </b-form>
  </div>
</template>

<script>
import store from "../store/index";
import { BFormGroup } from "bootstrap-vue";
import { BFormInput } from "bootstrap-vue";
import api from "../api/api";
import { mapGetters } from "vuex";

export default {
  components: {
    BFormGroup,
    BFormInput,
  },
  data() {
    return {
      oldPass: "",
      newPass: "",
    };
  },
  computed: {
    ...mapGetters(["user"]),
  },
  methods: {
    changePassword() {
      api
        .changePassword(this.user.id, this.oldPass, this.newPass)
        .then((response) => {
          if (response.data) {
            alert("Jelszó módosítva");
            this.oldPass = "";
            this.newPass = "";
          } else {
            throw new Error();
          }
        })
        .catch((error) => {
          alert("Hibás adatok");
        });
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

<style  scoped>
</style>