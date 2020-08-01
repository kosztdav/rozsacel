<template>
  <div>
    <div>
      <div class="header">
        <div v-if="user==null" class="text-right pr-2">Nincs bejelentkezve</div>
        <div class="text-right pr-2" v-else>{{user.name}}</div>
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
export default {
  computed: {
    isMobile() {
      if (
        /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
          navigator.userAgent
        )
      ) {
        return true;
      } else {
        return false;
      }
    },
    ...mapGetters(["user"]),
  },
};
</script>

<style scoped>
.header {
  background-color: gray;
  display: block;
  color: white;
  height: 50px;
}
</style>