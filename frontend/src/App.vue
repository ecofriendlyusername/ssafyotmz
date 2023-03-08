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

    <router-view />
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  height: 50%;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
