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
  <hr>
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
  <hr>
  <router-link to='/AddClothes'>옷 추가하기</router-link> | 
  <router-link to='/Codybook'>코디북 만들기</router-link> |
  <router-link to='/MyPage'>마이페이지</router-link>
</template>

<script>
export default {
  name:'MyPageClosetView',
  methods:{
    getData() {
      console.log(this.filter)
      axios.get('https://j8c105.p.ssafy.io/api/v1/', this.filter, {
        headers: {
          'Authorization': this.$store.Auth['accessToken']
        }
      })
      .then(response => {
        console.log(response.data)
      })
      .catch(error => console.log(error))
    },
  }
}
</script>

<style>

</style>