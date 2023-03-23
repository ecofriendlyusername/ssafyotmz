<template>
<div>
  <div>
    옷 추가하기
  </div>
  <hr>
  <form id="itemForm" @submit.prevent="createItemWith" action="#">
    <label for="imagefile">옷 이미지를 추가하세요</label><input type="file" id="imagefile" name="imagefile">
    <br>
    <label for="">옷 이름을 입력하세요</label><input type="text" id="name" name="name">
    <br>
    <br>
    <input type="file"
       id="avatar" name="avatar"
       accept="image/png, image/jpeg">
       <br>
    <input type="submit" value="추가하기">
  </form>
  <hr>
  <!-- <router-link to='/'>메인페이지</router-link> -->
</div>
</template>

<script>
export default {
  name:'AddClothesView',
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