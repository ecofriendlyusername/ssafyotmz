<template>
  <div>
    내 스타일 페이지
  </div>
  <div>
    <img src="@/assets/img/logo.png" alt="">
    <!-- user profileIMG -->
    <p>유저이름</p>
    <p>스타일 진단 횟수 {{ 'total' }} 회</p>
  </div>
  <!-- 스타일 구분별 스타일 사진 -->
  <!-- 얘도 가로 2~3 정도로  -->
  <ul>
    <li>
      <img src="@/assets/img/logo.png" alt="">
      <p>스타일 구분</p>
    </li>

    <li>
      <img src="@/assets/img/logo.png" alt="">
      <p>스타일 구분</p>
    </li>

    <li>
      <img src="@/assets/img/logo.png" alt="">
      <p>스타일 구분</p>
    </li>
  </ul>
  <router-link to='/MyPage'>마이페이지</router-link>
</template>

<script>
export default {
    name:'MyPageStyleView',
}
</script>

<style>

</style>