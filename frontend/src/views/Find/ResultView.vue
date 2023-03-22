<template>
  <div>
    <div>
      result: {{ result }}
    </div>
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