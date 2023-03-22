<template>
  <div>
    <div>
      <!-- 유저 정보 칸 -->
      <img src="@/assets/img/logo.png" alt="">
      <p>{{ 'username' }}</p>
      <!-- 프로필이미지 -->
      <p>{{ 'sytle' }} 스타일 진단</p>
      <p>{{ 'clothes' }} 내 옷장</p>
    </div>
    <hr>
    <div>
      <span>
        <h4>내 스타일 요약</h4>
        <router-link to='/MyPage/style'>내 스타일 보기</router-link>
      </span>
      <ul>
        <li>{{ 'styleName' }} <br> {{ 'stylePercent' }}%</li>
        <li>{{ 'styleName' }} <br> {{ 'stylePercent' }}%</li>
        <li>{{ 'styleName' }} <br> {{ 'stylePercent' }}%</li>
      </ul>
    </div>
    <hr>
    <div>
      <router-link to='/MyPage/closet'>
        <img src="@/assets/img/logo.png" alt="">
        <p>내 옷장 보기</p>
      </router-link>
      <router-link to='/MyPage/codybook'>
        <img src="@/assets/img/logo.png" alt="">
        <p>내 코디북 보기</p>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
    name:'MyPageView',
}
</script>

<style>

</style>