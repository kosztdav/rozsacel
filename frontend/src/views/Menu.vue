<template>
  <div class="pb-3 d-flex justify-content-center">
    <div v-if="!isAdmin">
      <b-button-group variant="outline-secondary">
        <b-button
          v-for="(btn, idx) in userButtons"
          :key="idx"
          :to="btn.to"
          :pressed="btn.state"
          @click="btnPressed(idx)"
          squared
          variant="outline-dark"
        >{{ btn.caption }}</b-button>
      </b-button-group>
    </div>
    <div v-if="isAdmin">
      <b-button-group variant="outline-secondary">
        <b-button
          v-for="(btn, idx) in adminButtons"
          :key="idx"
          :to="btn.to"
          :pressed="btn.state"
          @click="adminBtnPressed(idx)"
          squared
          variant="outline-dark"
        >{{ btn.caption }}</b-button>
      </b-button-group>
    </div>
  </div>
</template>


<script>
import { BButtonGroup } from "bootstrap-vue";
import { mapGetters } from "vuex";
export default {
  components: { BButtonGroup },
  data() {
    return {
      userButtons: [
        { caption: "Jelenléti ív", state: true, to: "/jelenlet" },
        { caption: "Beállítások", state: false, to: "/beallitasok" },
      ],
      adminButtons: [
        { caption: "Jelenléti ív", state: true, to: "/dolgozok" },
        { caption: "Helyszínek", state: false, to: "/helyszinek" },
        { caption: "Bérek", state: false, to: "/berek" },
      ],
    };
  },
  computed: {
    ...mapGetters(["isAdmin"]),
  },
  methods: {
    btnPressed(idx) {
      for (let i = 0; i < this.userButtons.length; i++) {
        if (idx == i) {
          this.userButtons[i].state = true;
        } else {
          this.userButtons[i].state = false;
        }
      }
    },
    adminBtnPressed(idx) {
      for (let i = 0; i < this.adminButtons.length; i++) {
        if (idx == i) {
          this.adminButtons[i].state = true;
        } else {
          this.adminButtons[i].state = false;
        }
      }
    },
  },
};
</script>

<style>
</style>