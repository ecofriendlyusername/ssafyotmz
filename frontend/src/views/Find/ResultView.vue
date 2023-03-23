<template>
  <div v-if="result['data']">
    <img :src=imgsrc alt="">
    <!-- 워터마크 찍은 이미지 -->
    <h3>당신은 {{ result['data'][1].style }} 스타일 입니다.</h3>
    <p>{{ result['data'][1].style }} 스타일은 어쩌고 저쩌고</p>
    
    <hr>
    <router-link to=''>스타일 저장하기</router-link> |
    <div v-on:click=shareKakao() id="kakaotalk-sharing-btn">스타일 공유하기</div> |
    <router-link to='/Recom'>다른 스타일 보기</router-link>
  </div>
</template>

<script>
export default {
  name:'FindResultView',
  data(){
    return {
      initkakao:false,
      imgsrc: null
    }
  },
  methods:{
    async shareKakao(){
      await Kakao.init('4a558f01722d37955f2c7bb1c18170d0');

      await Kakao.Share.createCustomButton({
        container: '#kakaotalk-sharing-btn',
        templateId: 91677,
        templateArgs: {
          img_path: 'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg',
          username: this.$store.state.Auth.nickname,
          style: this.result['data']['1'].style,
        },
      })
      

      // Kakao.Share.createCustomButton({
      //   container: '#kakaotalk-sharing-btn',
      //   templateId: 91677,
      //   templateArgs: {
      //     title: '라이언이 즐겨먹던 바로 그 틴케이스 치즈볼',
      //     description: '바라만 봐도 즐거워지는 힐링 패키지에는 시크릿 스토리가 숨어있어요.',
      //     },
      //   })
      // .catch(error=>console.log(error));
    }
  },
  computed: {
    result() {
      return this.$store.state.result
    }
  },
  mounted() {
    this.imgsrc = URL.createObjectURL(this.result['img_path']);
    // 여기 아래에 워터마크 찍는 코드 붙여야 함

  }

}
</script>

<style>

</style>