<template>
  <div class="container">
    <Header />
    <Menu v-if="$store.state.user!=null" />
    <router-view />
  </div>
</template>

<script>
import Header from "./views/Header.vue";
import Menu from "./views/Menu.vue";

export default {
  components: {
    Header,
    Menu,
  },
};
</script>
