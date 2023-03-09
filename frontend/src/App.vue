<template>
  <div id="app">

    <ul class="nav justify-content-center">
      <li class="nav-item">
        <router-link to="/util_one" class="nav-link">기능1</router-link>
      </li>
      <li class="nav-item">
        <router-link to="/util_two" class="nav-link">기능2</router-link>
      </li>
      <li class="nav-item">
        <router-link class="nav-link" to="/util_three">기능3</router-link>
      </li>
    </ul>

    <div id="main">
      <div class="inner">
        <!-- <HeaderComponentVue /> -->
        <router-view />
      </div>
    </div>
    

    <SidebarComponentVue/>


    <router-view />
  </div>
</template>

<script>
  import SidebarComponentVue from './components/SidebarComponent.vue';

  export default {
    name: 'App',
    components: {
      SidebarComponentVue,
    },
  }
</script>
<script src="./assets/js/main.js"></script>

<style src="./assets/css/main.css"></style>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
