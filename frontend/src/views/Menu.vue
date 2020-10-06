<template>
  <div>
    <div v-if="isMobile">
      <Slide noOverlay :closeOnNavigation="true">
        <div
          v-for="(btn, idx) in isAdmin ? adminButtons : userButtons"
          :key="idx"
        >
          <router-link :to="btn.url" tag="div">
            <span>{{ btn.text }}</span>
          </router-link>
        </div>
      </Slide>
    </div>
    <div v-else class="pb-3 d-flex justify-content-center">
      <b-button-group variant="outline-secondary">
        <span
          v-for="(btn, idx) in isAdmin ? adminButtons : userButtons"
          :key="idx"
        >
          <b-button
            :to="btn.url"
            :pressed="btn.state"
            @click="btnPressed(idx)"
            squared
            variant="outline-dark"
          >
            <b-icon :icon="btn.icon"></b-icon> {{ btn.text }}</b-button
          >
        </span>
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
        {
          text: "Jelenléti ív",
          state: true,
          url: "/jelenlet",
          icon: "calendar3",
        },
        {
          text: "Beállítások",
          state: false,
          url: "/beallitasok",
          icon: "tools",
        },
      ],
      adminButtons: [
        {
          text: "Jelenléti ív",
          state: true,
          url: "/jelenletek",
          icon: "calendar3",
        },
        {
          text: "Helyszínek",
          state: false,
          url: "/helyszinek",
          icon: "building",
        },
        {
          text: "Dolgozók",
          state: false,
          url: "/dolgozok",
          icon: "person-lines-fill",
        },
        { text: "Report", state: false, url: "/report", icon: "newspaper" },
        {
          text: "Beállítások",
          state: false,
          url: "/beallitasok",
          icon: "tools",
        },
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