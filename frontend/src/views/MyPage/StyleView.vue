<template>
  <div id="MyPage">
    <div class="SubNav">
        <div class="SubNavText">
    내 스타일 페이지
  </div>
  </div>
  <hr>
  <div>
    <!-- user profileIMG -->
    <p>유저이름: {{ myData['nickname'] }}</p>
    <p>스타일 진단 횟수 {{ this.myData['totalStyleCount'] }} 회</p>
  </div>
  <div>
    <canvas id="myChart" ></canvas>
  </div>
  <!-- 스타일 구분별 스타일 사진 -->
  <!-- 얘도 가로 2~3 정도로  -->
  <ul>
    <!-- 라우트 링크 달아서 카테고리 검색 페이지로 이동? 카테고리 props 하고?  -->
    <li v-for="style in myData.styleByPercentResponseList">
      {{ style['style'] }}: {{ style['count'] }}
    </li>
  </ul>
  <hr>
    <ul>
      <li v-for="style in styleList">
        <img :src='`${path}/images/${style.imageId}`' id="picture">
        <hr>
      </li>
    </ul>
  <hr>
  <!-- <img :src='`${path}/images/${unit.imageId}`' id="picture"> -->
</div>
</template>

<script>
import axios from 'axios';
import Chart from 'chart.js/auto';

export default {
  name:'MyPageStyleView',
  data(){
    return {
      path: process.env.VUE_APP_API_URL,
      styleList: null,
      myData: {
          nickname: "홍길동", 
        }
    }
  },
  mounted() {
    axios.get(process.env.VUE_APP_API_URL + '/member/looks', {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': this.$store.state.Auth['accessToken']
      }
    })
    .then(response => {
      // console.log(response.data)
      this.styleList = response.data
    })
    .catch(error => console.log(error))

    axios.get(process.env.VUE_APP_API_URL + '/member/mypage', {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': this.$store.state.Auth['accessToken']
      }
    })
  .then(response => {
    console.log(response.data)
    this.myData = response.data
  })
  .then(() => {
      const ctx = document.getElementById('myChart');
      const data = Object.values(this.myData.styleByPercentResponseList)
      new Chart(ctx, {
      type: 'bar',
      data: {
        labels: data.map(x => x['style']),
        datasets: [{
        // label: '# of Votes',
          data: data.map(x => x['count']),
          borderWidth: 1,
              backgroundColor: '#000000',
              pointBorderColor: '#000000',
        }]
      },
        options: {
          scales: {
            y: {
              beginAtZero: true,
              display: false
            },
            x: {
              grid: {
                offset: true,
                display: false
              }
            },
          },
          plugins: {
            legend: {
              display: false
            }
          }
        }
      })
    })
    .catch(error => console.log(error))
  },
}
</script>

<style>

</style>