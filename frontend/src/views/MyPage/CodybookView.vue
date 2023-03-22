<template>
  <div>
    내 코디북 페이지
  </div>
  <hr>
  <!-- 스타일 별 코디북 -->
  <ul>
    <li>
      <img src="@/assets/img/logo.png" alt="">
      <p>코디북 이름</p>
    </li>

    <li>
      <img src="@/assets/img/logo.png" alt="">
      <p>코디북 이름</p>
    </li>

    <li>
      <img src="@/assets/img/logo.png" alt="">
      <p>코디북 이름</p>
    </li>
  </ul>
  <hr>
  <router-link to='/Codybook'>코디북 만들기</router-link> |
  <router-link to='/MyPage'>마이페이지</router-link>
</template>

<script>
export default {
    name:'MyPageCodybookView',
}
</script>

<style>

</style>