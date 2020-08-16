<template>
  <div class="container">
    <Header />
    <Menu v-if="user!=null" />
    <router-view />
  </div>
</template>

<script>
import Header from "./views/Header.vue";
import Menu from "./views/Menu.vue";
import { mapGetters } from "vuex";

export default {
  components: {
    Header,
    Menu,
  },
  computed: {
    ...mapGetters(["user"]),
  },
};
</script>
