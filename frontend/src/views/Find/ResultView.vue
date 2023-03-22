<template>
  <div>
    <img src="@/assets/img/T.png" alt="">
    <!-- 워터마크 찍은 이미지 -->
    <h3>당신은 @@@ 스타일 입니다.</h3>
    <p>@@@ 스타일은 어쩌고 저쩌고</p>
    
    <router-link to=''>스타일 저장하기</router-link> |
    <router-link to=''>스타일 공유하기</router-link> |
    <router-link to='/Recom'>다른 스타일 보기</router-link>
  </div>
</template>

<script>
export default {
    name:'FindResultView',
    props: {
      result: {
        type: Object,
        required: true
      }
    },
    mounted() {
    console.log('result:', this.result);
  }
}
</script>

<style>

</style>