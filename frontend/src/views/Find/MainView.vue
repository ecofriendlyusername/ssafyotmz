<template>
  <div>
    <addDataFormComponent/>
    
  </div>
  <router-link to='/'>메인페이지</router-link>
</template>

<script>
import addDataFormComponent from "@/components/molecules/addDataForm.vue";
export default {
    name:'FindMainView',
    components:{
      addDataFormComponent,
    }
}
</script>

<style>

</style>