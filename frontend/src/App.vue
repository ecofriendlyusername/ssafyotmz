<template>
  <headerComponent/>
  <footerComponent/>
  <router-view/>
</template>

<script>
import headerComponent from "@/components/molecules/header.vue";
import footerComponent from "@/components/molecules/footer.vue";

export default {
  components: {
    headerComponent,
    footerComponent
  },
  mounted() {
    window.addEventListener('beforeunload', this.confirmExit);
  },
  beforeUnmount() {
    window.removeEventListener('beforeunload', this.confirmExit);
  },
  methods: {
    confirmExit(event) {
      // 페이지를 닫으려는 경우
      localStorage.removeItem('vuex')
    }
  }
}
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: black;
  font-family: 'NanumSquareNeo-Variable';
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

footer{
  width: 100%;
  padding-top: 7px;
}

</style>
