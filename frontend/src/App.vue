<template>
  <div id="otmz">
  <headerComponent/>

  <router-view/>
  
  <footerComponent/>
</div>

  
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
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: black;
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

body {
  padding-bottom: 73px;
}

footer{
  width: 95.8%;
  padding-top: 7px;
}

#otmz {
  position: relative;
  width: 100%;
  height: 100%;
  overflow-x: hidden
}

</style>
