<template>
  <div class="pb-3">
    <div class="d-flex justify-content-center">
      <b-button-group style="width:30%">
        <b-button :pressed="true">Jelenléti ív</b-button>
        <b-button>Beállítások</b-button>
      </b-button-group>
    </div>
  </div>
</template>


<script>
import { BButtonGroup } from "bootstrap-vue";
export default {
  components: { BButtonGroup },
  data() {
    return {
      change: true,
    };
  },
};
</script>

<style>
</style>