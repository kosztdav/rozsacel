<template>
  <div class="pb-3 d-flex justify-content-center">
    <div v-if="!isAdmin">
      <router-link to="/jelenlet" class="pr-1">
        <b-button :pressed="pressed" @click="pressed = !pressed">Jelenléti ív</b-button>
      </router-link>
      <router-link to="/beallitasok" class="pl-1">
        <b-button :pressed="!pressed" @click="pressed = !pressed">Beállítások</b-button>
      </router-link>
    </div>
    <div v-if="isAdmin">
      <b-button :pressed="true">Dolgozók</b-button>
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
      pressed: true,
    };
  },
  computed: {
    ...mapGetters(["isAdmin"]),
  },
};
</script>

<style>
</style>