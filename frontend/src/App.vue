<template>
  <div id="app">
    <router-link to="/util/start">Home</router-link>
    <router-link to="/util/result">result</router-link>
    <router-link to="/watermark">watermark</router-link>
    
    <router-view/>
  </div>
</template>

<script>

  export default {
    name: 'App',
    components: {

    },
  }
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
