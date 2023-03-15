<template>
  <div>
    <div v-if="imageBlobUrl">
      <img :src=imageBlobUrl id="image" />
    </div>
    
    <h2>당신의 스타일은</h2>
    <div>
      <div>{{ result[1].style }} 이며,</div>
      <div>일치도는 {{ result[1].score }}점 입니다.</div>
    </div>
    
    <!-- 판별 결과의 2순위가 30점 이상일 경우에만 보여주기 -->
    <div v-if="result[2].score >= 30">
      <div>style: {{ result[2].style }} </div>
      <div>score: {{ result[2].score }}</div>
    </div>
    <div>
      <button>공유하기</button>
      <router-link to="start">
        <button>다시하기</button>
      </router-link>
    </div>

  </div>
</template>


<script>
// import watermark from 'watermarkjs';

export default {
  name: 'resultComponentVue',
  methods:{
    test(){

    }
  },
  computed: {
    image() {
      return this.$store.state.image
    },
    result() {
      var res = this.$store.state.result
      console.log(res['1'])
      return res
    },
    imageBlobUrl() { // Vuex state에서 파일 객체 불러오기
      if (this.image) {
        console.log(this.image)
        return URL.createObjectURL(this.image)
      }
      return null
    },
  }
}
</script>

<style></style>