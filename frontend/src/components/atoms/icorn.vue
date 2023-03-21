<template>
  <div>
    <img v-bind:="icornSrc" alt="">
  </div>
</template>

<script>
export default {
    name:'icornComponent',
    props:{
      icornSrc: String
    }
}
</script>

<style>

</style>