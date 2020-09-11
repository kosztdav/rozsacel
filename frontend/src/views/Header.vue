<template>
  <div>
    <div>
      <div class="header row">
        <div v-if="user==null" class="text-right pr-2">Nincs bejelentkezve</div>
        <div v-else>
          <div v-if="isMobile">
            <div class="ml-3">
              <Menu />
            </div>
          </div>
          <div class="text-right pr-2">{{user.name}}</div>
        </div>
      </div>
    </div>
    <div v-if="isMobile" class="pt-3 pb-3 text-center mr-5">
      <img src="../assets/rose.png" width="50%" />
    </div>
    <div v-else class="pt-3 pb-3 text-center mr-5">
      <img class="btn" src="../assets/rose.png" width="20%" />
    </div>
    <hr />
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import myMixin from "../mixins/myMixin";
import Menu from "./Menu";

export default {
  components: {
    Menu,
  },
  mixins: [myMixin],
  computed: {
    ...mapGetters(["user"]),
    menuStyle() {
      if (window.innerWidth > 550) {
        return "Elfordítva";
      }
      return "Állítva";
    },
  },
};
</script>

<style scoped>
.header {
  background-color: #292b2c;
  display: block;
  color: white;
  height: 50px;
}
</style>