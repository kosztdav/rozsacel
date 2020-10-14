<template>
  <div>
    <div class="pb-3 d-flex justify-content-center">
      <b-button-group variant="outline-secondary">
        <span
          v-for="(btn, idx) in isAdmin ? adminButtons : userButtons"
          :key="idx"
        >
          <b-button
            :to="btn.url"
            :pressed="$route.path == btn.url"
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

  computed: {
    ...mapGetters(["isAdmin"]),
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