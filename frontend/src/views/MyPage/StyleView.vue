<template>
  <div>
    내 스타일 페이지
  </div>
  <hr>
  <div>
    <!-- user profileIMG -->
    <p>유저이름: {{ myData['nickname'] }}</p>
    <p>스타일 진단 횟수 {{ this.myData['totalStyleCount'] }} 회</p>
  </div>
  <!-- 스타일 구분별 스타일 사진 -->
  <!-- 얘도 가로 2~3 정도로  -->
  <ul>
    <!-- 라우트 링크 달아서 카테고리 검색 페이지로 이동? 카테고리 props 하고?  -->
    <li v-for="style in styleList">
      {{ style['style'] }}: {{ style['count'] }}
    </li>
  </ul>
  <hr>
    <ul>
      <li v-for="style in styleList">
        <div style="height: 150px; width: 100px; background-color: black; margin: 0 auto;"></div>
        <router-link to="">{{ style['style'] }}</router-link>
        <hr>
      </li>
    </ul>
  <hr>
  <router-link to='/MyPage'>마이페이지</router-link>
</template>

<script>
import axios from 'axios';

export default {
  name:'MyPageStyleView',
  data(){
    return {
      styleList: [],
      myData: {
        'nickname': null
      }
    }
  },
  mounted() {
    axios.get('http://localhost:8080/api/v1/member/lookdetail', {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': this.$store.state.Auth['accessToken']
      }
    })
    .then(response => {
      console.log(response.data)
      this.styleList = response.data
      this.styleList = [
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
          {
            'style': '페미닌',
            'count':1
          }
      ]
    })
    .catch(error => console.log(error))

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
          totalItemCount: 0, 
          totalStyleCount: 10
        }
      })
      .catch(error => console.log(error))
  },
  methods:{
    styleShow(category){
      axios.get('http://localhost:8080/api/v1/looks', {
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
          totalItemCount: 0, 
          totalStyleCount: 10
        }
      })
      .catch(error => console.log(error))
    }
  }
}
</script>

<style>

</style>