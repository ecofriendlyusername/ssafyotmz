<template>
  <div id="MyPage">
    <div class="SubNav">
        <div class="SubNavText">
            MY PAGE
        </div>
    </div>

    <div v-if="Auth['nickname']">
      <!-- 유저 정보 칸 -->
      <img :src="Auth['profileImagePath']" id="Profile">
      <p id="Nickname">{{ myData['nickname'] }}</p>
      

      <div id="mydiv1">
        <div>
          내 스타일 요약
        </div>
        <div>
          <router-link to='/MyPage/style' style="text-decoration:none; color:black;">
            분석 현황
          </router-link>
        </div>
      </div>
    </div>

    <hr>

    <!-- 프로필이미지
    <p>스타일 진단 {{ this.myData['totalStyleCount'] }} 회</p> -->
    <div>
      <p id="mystyle">
        스타일 진단 횟수: {{ this.myData['totalStyleCount'] }}회
      </p>
      <!-- 아니면 3개 고정인 점을 이용해서 각자 집어넣어도 될 듯 -->
      <div style="display:flex; justify-content: center; margin: 20px; align-items: center;">
        <div style="width:55%;">
          <canvas id="myChart"></canvas>
        </div>
        <div style="width:100%">
          
          
          <p>TOP 3</p>
          <p v-for="look in myData['lookCountDtoList']" style="margin-top:-10px;">
          {{ look['style'] }} : {{ look['count']/this.myData['totalStyleCount']*100 }}%</p>
        </div>
      </div>

    </div>


    <div id="mydiv1">
      내 코디를 부탁해
    </div>
    <hr>


      <!-- <p>내 옷장 {{ this.myData['totalItemCount'] }} 개</p> -->


    <div style="display:flex; justify-content: space-around ">
      <router-link to='/MyPage/closet'>
        <div>
          <img src="@/assets/img/logo.png" alt="">
          <p>내 옷장 보기</p>
        </div>
      </router-link>

      <router-link to='/MyPage/codybook'>
        <div>
          <img src="@/assets/img/logo.png" alt="">
          <p>내 코디북 보기</p>
        </div>
      </router-link>
    </div>



          
      

  </div>
</template>

<script>
import axios from 'axios';
import Chart from 'chart.js/auto';

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
      axios.get(process.env.VUE_APP_API_URL + '/member/mypage', {
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
      .then(() => {
        const ctx = document.getElementById('myChart');
        const data = Object.values(this.myData['lookCountDtoList'])
        new Chart(ctx, {
          type: 'bar',
          data: {
            labels: data.map(x => x['style']),
            datasets: [{
              // label: '# of Votes',
              data: data.map(x => x['count']),
              borderWidth: 1
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
              },
            }
          },
          
        });
      })
      .catch(error => console.log(error))
    },
}
</script>


<style>


#MyPage {
  font-family: 'NanumSquareNeo-Variable';
}

#Profile {
  width: 28%;
  margin: 15px;
}

#Nickname {
  font-size: 120%;
  font-weight:bold;
  margin-top: -10px;
}

#mydiv1 {
  display:flex;
  justify-content: space-between;
  margin-top: 20px;
  padding: 0px 20px;
  font-weight: bold;
  font-size: 115%;
}

#mystyle {
  font-weight: bold;
}
</style>