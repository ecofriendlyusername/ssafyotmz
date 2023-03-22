<template>
  <div>
    코디북 만들기 페이지
  </div>
  <hr>
  <div>
    내 옷 보여주는 창
  </div>
  <hr>
  <div>
    현재 코디북에 등록된 옷
  </div>
  <hr>
  <router-link to='/'>메인페이지</router-link>
</template>

<script>
export default {
    name:'CodybookView',
    data () {
      return {
        count: 0
      }
    },
    methods: {
    }
}
</script>

<style>

</style>