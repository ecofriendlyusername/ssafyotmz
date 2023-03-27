<template>
  <div>
    <div v-if="Auth['nickname']">
      <!-- 유저 정보 칸 -->
      <img :src="Auth['profileImagePath']" alt="">
      <p>{{ myData['nickname'] }}</p>
      <!-- 프로필이미지 -->
      <p>스타일 진단 {{ this.myData['totalStyleCount'] }} 회</p>
      <p>내 옷장 {{ this.myData['totalItemCount'] }} 개</p>
    </div>
    <hr>
    <div>
      <span>
        <h4>내 스타일 요약</h4>
        <router-link to='/MyPage/style'>내 스타일 보기</router-link>
      </span>
      <!-- 아니면 3개 고정인 점을 이용해서 각자 집어넣어도 될 듯 -->
      <div>
        <p v-for="look in myData['lookCountDtoList']">{{ look['style'] }} : {{ look['count'] }}</p>
      </div>
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
import axios from 'axios';

export default {
    name:'MyPageView',
    data() {
      return{
        Auth: this.$store.state.Auth,
        myData: {
          'nickname': null
        },
      }
    },
    mounted(){
      this.style
      this.closet
      axios.get('http://localhost:8080/api/v1/member/mypage', {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': this.$store.state.Auth['accessToken']
        }
      })
      .then(response => {
        console.log(response.data)
        this.myData = response.data
        this.myData = {
          lookCountDtoList: [
            {
              'style': '스트릿',
              'count':5
            },
            {
              'style': '캐주얼',
              'count':3
            },
            {
              'style': '러블리',
              'count':1
            },
          ], 
          nickname: "최선호", 
          totalItemCount: 10, 
          totalStyleCount: 10
        }
      })
      .catch(error => console.log(error))
    },
}
</script>

<style>

</style>