<template>
  <div>
    옷 추가하기
  </div>
  <hr>
  <form action="#">
    <label for="">옷 이미지를 추가하세요</label><input type="file" name="" id="" >
    <br>
    <label for="">옷 이름을 입력하세요</label><input type="text">
    <br>
    <input type="submit" value="추가하기">
  </form>
  <hr>
  <router-link to='/'>메인페이지</router-link>
</template>

<script>
export default {
    name:'AddClothesView',
}
</script>

<style>

</style>