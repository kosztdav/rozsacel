<template>
  <div>
    <nav class="navbar navbar-dark bg-dark">
      <button
        v-if="user != null && isMobile"
        class="navbar-toggler"
        type="button"
        @click="openMenu"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="navbar-brand" v-if="user == null">Nincs bejelentkezve</div>
      <div class="navbar-brand" v-else>{{ user.name }}</div>
    </nav>

    <div class="container">
      <div v-if="isMobile" class="pt-3 pb-3 text-center mr-5">
        <img src="../assets/roseicon.png" width="70%" />
      </div>
      <div v-else class="pt-3 pb-3 text-center mr-5">
        <img class="btn" src="../assets/roseicon.png" width="40%" />
      </div>
    </div>

    <hr />
    <div v-if="user != null && isMobile">
      <Slide
        :isOpen="open"
        noOverlay
        :closeOnNavigation="true"
        :burgerIcon="false"
        @closeMenu="open = false"
      >
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
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import myMixin from "../mixins/myMixin";

export default {
  data() {
    return {
      open: false,
    };
  },
  methods: {
    openMenu(e) {
      e.preventDefault();
      e.stopPropagation();
      this.open = true;
    },
  },
  mixins: [myMixin],
  computed: {
    ...mapGetters(["user", "isAdmin"]),
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