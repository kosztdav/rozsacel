<template>
  <div>
    <Header />
    <div class="container">
      <Menu v-if="user != null && !isMobile" />
      <router-view />
    </div>
  </div>
</template>

<script>
import Header from "./views/Header.vue";
import Menu from "./views/Menu.vue";
import { mapGetters } from "vuex";
import myMixin from "./mixins/myMixin";

export default {
  components: {
    Header,
    Menu,
  },
  mixins: [myMixin],
  computed: {
    ...mapGetters(["user"]),
  },
};
</script>

<style>
.page-item.active .page-link {
  background-color: #292b2c !important;
  border-color: #292b2c !important;
}
.page-item {
  color: black;
}
.modal-backdrop {
  opacity: 0.5 !important;
}
</style>