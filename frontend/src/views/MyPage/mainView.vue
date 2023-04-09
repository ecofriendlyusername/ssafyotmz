<template>
  <div id="MyPage">


    <div v-if="Auth['nickname']">
      <!-- 유저 정보 칸 -->
      <img :src="Auth['profileImagePath']" id="Profile" style="height: 100px; width: 100px;">
      <p id="Nickname">{{ Auth['nickname'] }}</p>
      

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
      <div style="display:flex; justify-content: center; margin: 20px; align-items: center;">
        <div style="width:55%;">
          <canvas id="myChart"></canvas>
        </div>
        <div style="width:100%">
          <b><p v-if="myData['topStyleList'].length > 2">TOP 3</p></b>
          <p v-for="look in myData['topStyleList']" style="margin-top:-10px;">
          {{ getStyleName(look['style']) }} : {{ look['count'] }}% 
           </p>
            <div v-if="myData['topStyleList'].length === 1" ><p>-</p><p>-</p></div>
          <div v-if="myData['topStyleList'].length === 2" ><p>-</p></div>
        </div>
      </div>
    </div>

    <br> <br>

    <div id="mydiv1">
        <div>
          내 옷장
        </div>
        <div>
          <router-link to='/MyPage/closet' style="text-decoration:none; color:black;">
            보러가기
          </router-link>
        </div>
    </div>

    <hr>

    <div style="padding-bottom:60px">
      <p id="mystyle">
        내가 가진 옷: {{ this.myData['totalItemCount'] }} 개
      </p>
      <!-- 아니면 3개 고정인 점을 이용해서 각자 집어넣어도 될 듯 -->
      <div style="display:flex; justify-content: center; margin: 20px; align-items: center;">
        <div style="width:55%;">
          <canvas id="myChart2"></canvas>
        </div>
        <div style="width:100%">
          
        <!-- 옷 종류 TOP3 출력 -->
        <b><p v-if="myData['itemStyleByCountResponse'].length > 2">TOP 3</p></b>
        <p v-for="look in myData['itemStyleByCountResponse']" style="margin-top:-10px;">
          {{ getStyleName(look['style']) }} : {{ look['count'] }}% 
           </p>
          <div v-if="myData['itemStyleByCountResponse'].length === 1" ><p>-</p><p>-</p></div>
          <div v-if="myData['itemStyleByCountResponse'].length === 2" ><p>-</p></div>
        </div>
      </div>

    </div>

      <!-- <p>내 옷장 {{ this.myData['totalItemCount'] }} 개</p> -->


          
      

  </div>
</template>

<script>
import axios from 'axios';
import Chart from 'chart.js/auto';
import { setBlockTracking } from 'vue';

export default {
  name: 'MyPageView',
  data() {
    return {
      //...
    }
  },
  methods: {
    getStyleName(style) {
      switch (style) {
        case 'CLASSIC':
          return '클래식';
        case 'PREPPY':
          return '프레피';
        case 'mannish':
          return '매니시';
          case 'TOMBOY':
          return '톰보이';
        case 'ROMANTIC':
          return '로맨틱';
        case 'SEXY':
          return '섹시';
          case 'HIPPIE':
          return '히피';
        case 'WESTERN':
          return '웨스턴';
        case 'ORIENTAL':
          return '오리엔탈';
          case 'MODERN':
          return '모던';
        case 'SOPHISTICATED':
          return '소피스트케이티드';
        case 'AVANTGARDE':
          return '아방가르드';
          case 'COUNTRY':
          return '컨트리';
        case 'RESORT':
          return '리조트';
        case 'GENDERLESS':
          return '젠더리스';
          case 'SPORTY':
          return '스포티';
        case 'RETRO':
          return '레트로';
        case 'HIPHOP':
          return '힙합';
          case 'KITSCH':
          return '키치';
        case 'PUNK':
          return '펑크';
        case 'STREET':
          return '스트릿';
          case 'MILITARY':
          return '밀리터리';
          case 'FEMININE':
          return '페미닌';
          case 'classic':
          return '클래식';
        case 'preppy':
          return '프레피';
        case 'mannish':
          return '매니시';
        case 'tomboy':
          return '톰보이';
        case 'romantic':
          return '로맨틱';
        case 'sexy':
          return '섹시';
        case 'hippie':
          return '히피';
        case 'western':
          return '웨스턴';
        case 'oriental':
          return '오리엔탈';
          case 'modern':
          return '모던';
        case 'sophisticated':
          return '소피스트케이티드';
        case 'avantgarde':
          return '아방가르드';
          case 'country':
          return '컨트리';
        case 'resort':
          return '리조트';
        case 'genderless':
          return '젠더리스';
          case 'sporty':
          return '스포티';
        case 'retro':
          return '레트로';
        case 'hiphop':
          return '힙합';
          case 'kitsch':
          return '키치';
        case 'punk':
          return '펑크';
        case 'street':
          return '스트릿';
          case 'military':
          return '밀리터리';
          case 'feminine':
          return '페미닌';
        default:
          return style;
      }
    },
  },
    name:'MyPageView',
    data() {
      return{
        Auth: this.$store.state.Auth,
        myData: {
          'nickname': null,
          topStyleList: [],
          itemStyleByCountResponse: []
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
        this.myData = response.data
      })
      .then(() => {
        const ctx = document.getElementById('myChart');
        const data = Object.values(this.myData['topStyleList'])
        new Chart(ctx, {
          type: 'bar',
          data: {
            labels: data.map(x => {
      switch (x.style) {
        case 'CLASSIC':
          return '클래식';
        case 'PREPPY':
          return '프레피';
        case 'mannish':
          return '매니시';
          case 'TOMBOY':
          return '톰보이';
        case 'ROMANTIC':
          return '로맨틱';
        case 'SEXY':
          return '섹시';
          case 'HIPPIE':
          return '히피';
        case 'WESTERN':
          return '웨스턴';
        case 'ORIENTAL':
          return '오리엔탈';
          case 'MODERN':
          return '모던';
        case 'SOPHISTICATED':
          return '소피스트케이티드';
        case 'AVANTGARDE':
          return '아방가르드';
          case 'COUNTRY':
          return '컨트리';
        case 'RESORT':
          return '리조트';
        case 'GENDERLESS':
          return '젠더리스';
          case 'SPORTY':
          return '스포티';
        case 'RETRO':
          return '레트로';
        case 'HIPHOP':
          return '힙합';
          case 'KITSCH':
          return '키치';
        case 'PUNK':
          return '펑크';
        case 'STREET':
          return '스트릿';
        case 'MILITARY':
          return '밀리터리';
        case 'FEMININE':
          return '페미닌';
        default:
          return x.style;
      }
    }),
            datasets: [{
              // label: '# of Votes',
              data: data.map(x => x['count']),
              borderWidth: 0.8,
              backgroundColor: [
                '#FFBFBA',
                '#FFD89D',
                '#C8E4FF',
              ],
              borderColor: '#000000',
              borderWidth: 2,
              borderRadius:8,
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
        const ctx2 = document.getElementById('myChart2');
        const data2 = Object.values(this.myData['itemStyleByCountResponse'])
        new Chart(ctx2, {
          type: 'bar',
          data: {
            labels: data2.map(x => {
        switch (x.style) {
        case 'classic':
          return '클래식';
        case 'preppy':
          return '프레피';
        case 'mannish':
          return '매니시';
        case 'tomboy':
          return '톰보이';
        case 'romantic':
          return '로맨틱';
        case 'sexy':
          return '섹시';
        case 'hippie':
          return '히피';
        case 'western':
          return '웨스턴';
        case 'oriental':
          return '오리엔탈';
          case 'modern':
          return '모던';
        case 'sophisticated':
          return '소피스트케이티드';
        case 'avantgarde':
          return '아방가르드';
          case 'country':
          return '컨트리';
        case 'resort':
          return '리조트';
        case 'genderless':
          return '젠더리스';
          case 'sporty':
          return '스포티';
        case 'retro':
          return '레트로';
        case 'hiphop':
          return '힙합';
          case 'kitsch':
          return '키치';
        case 'punk':
          return '펑크';
        case 'street':
          return '스트릿';
          case 'military':
          return '밀리터리';
          case 'feminine':
          return '페미닌';
        default:
          return x.style;
      }
    }),
            datasets: [{
              // label: '# of Votes',
              data: data2.map(x => x['count']),
              borderWidth: 0.8,
              borderRadius:8,
              backgroundColor: [
                '#FFBFBA',
                '#FFD89D',
                '#C8E4FF',
              ],
              borderColor: '#000000',
              borderWidth: 2,
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
  width: 50%;
  margin: 15px;
  border-radius: 100%;
  border: 4px solid black;
   object-fit: cover;
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