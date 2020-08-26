<template>
  <div>
    <div v-if="isMobile">
      <Slide noOverlay :closeOnNavigation="true">
        <div v-for="(btn, idx) in isAdmin? adminButtons : userButtons" :key="idx">
          <router-link :to="btn.url" tag="div">
            <span>{{btn.text}}</span>
          </router-link>
        </div>
      </Slide>
    </div>
    <div v-else class="pb-3 d-flex justify-content-center">
      <b-button-group variant="outline-secondary">
        <b-button
          v-for="(btn, idx) in isAdmin? adminButtons : userButtons"
          :key="idx"
          :to="btn.url"
          :pressed="btn.state"
          @click="btnPressed(idx)"
          squared
          variant="outline-dark"
        >{{ btn.text }}</b-button>
      </b-button-group>
    </div>
  </div>
</template>


<script>
import { BButtonGroup } from "bootstrap-vue";
import { mapGetters } from "vuex";
import myMixin from "../mixins/myMixin";

export default {
  components: { BButtonGroup },
  mixins: [myMixin],
  data() {
    return {
      userButtons: [
        { text: "Jelenléti ív", state: true, url: "/jelenlet" },
        { text: "Beállítások", state: false, url: "/beallitasok" },
      ],
      adminButtons: [
        { text: "Jelenléti ív", state: true, url: "/dolgozok" },
        { text: "Helyszínek", state: false, url: "/helyszinek" },
        { text: "Bérek", state: false, url: "/berek" },
        { text: "Report", state: false, url: "/report" },
        { text: "Beállítások", state: false, url: "/beallitasok" },
      ],
    };
  },
  computed: {
    ...mapGetters(["isAdmin"]),
  },
  methods: {
    btnPressed(idx) {
      var btnArray = new Array();
      if (this.isAdmin) {
        btnArray = this.adminButtons;
      } else {
        btnArray = this.userButtons;
      }
      for (let i = 0; i < btnArray.length; i++) {
        if (idx == i) {
          btnArray[i].state = true;
        } else {
          btnArray[i].state = false;
        }
      }
    },
  },
};
</script>

<style>
.bm-burger-button {
  margin-left: 7% !important;
  width: 30px !important;
  height: 25px !important;
  left: 0px !important;
  top: 12px !important;
}
.bm-burger-bars {
  background-color: white !important;
}
.bm-item-list {
  color: white !important;
}
</style>