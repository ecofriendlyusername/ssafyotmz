<template>
  <!-- 내 옷장 -->
  
  <!-- 목차 -->
  <ul>
    <li>상의</li>
    <li>하의</li>
    <li>아우터</li>
    <li>원피스</li>
    <li>ETC</li>
  </ul>
  <!-- 옷 목록 -->
  <!-- 그리드, 가로 2~3 정도로 -->
  <ul>
    <li>
      <img src="@/assets/img/logo.png" alt="">
    </li>

    <li>
      <img src="@/assets/img/logo.png" alt="">
    </li>

    <li>
      <img src="@/assets/img/logo.png" alt="">
    </li>
  </ul>
  <router-link to='/MyPage'>마이페이지</router-link>
</template>

<script>
export default {
    name:'MyPageClosetView',
}
</script>

<style>

</style>