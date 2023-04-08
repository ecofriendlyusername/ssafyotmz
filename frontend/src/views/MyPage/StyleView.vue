<template>

<div v-if="isModal" class="modal__background" style="z-index:100">
  <div id="modal" v-if="isModal">

      <img v-on:click="styleDelete(modalData.id)" src="@/assets/img/trash.png" id="trashBtn">
    <!-- <button v-on:click="styleDelete(modalData.id)" class="ModalBtn">삭제</button> -->
    <img :src="`${ path }/images/${ modalData.imageId }`" style="padding:10px; border-radius: 25px;">
    <div style="display:flex">
      <div style="padding:0px 15px">
        <span style="font-weight:bold;">
          {{ modalData.ownerName }}
        </span> <span>님의</span>
      </div>
      <div style="margin-left:-10px">
        <span style="font-weight:bold;">
          #{{ modalData.style.toUpperCase() }}
        </span>
        <span>
          스타일
        </span>
      </div>
    </div>

    
    <button v-on:click="isModal = false" class="ModalBtn">
      닫기
    </button>
  </div>
</div>

  <div id="MyPage">
    <div class="SubNav">
      <div class="SubNavText">
        내 스타일 페이지
      </div>
    </div>
    <hr>
    <div>
      <!-- user profileIMG -->
      <b><p>유저이름: {{ myData['nickname'] }}</p></b>
     <b><p>스타일 진단 횟수 {{ this.myData['totalStyleCount'] }} 회</p></b>
    </div>
    <div>
      <canvas id="myChart" ></canvas>
    </div>

    <hr>

    <div style="display:flex; justify-content:center">
      <div class="container" style="gap:5px">

      <div v-for="(style, index) in data" :key="index">
          <img :src= '`${ path }/images/${ style.imageId }`' style="width:100%;" id="picture" v-on:click="modal(style, index)">
      </div>
      <infinite-loading v-if="hasMore" :identifier="infiniteId" @infinite="onScroll"></infinite-loading>
      </div>
    </div>
    <!-- 여기까지 -->
    <hr>
  </div>
</template>

<script>
import axios from 'axios';
import Chart from 'chart.js/auto';
import InfiniteLoading from "v3-infinite-loading";
export default {
  name:'MyPageStyleView',
  components: {
    InfiniteLoading,
  },
  data(){
    return {
      path: process.env.VUE_APP_API_URL,
      // styleList: null,
      data: [],
      hasMore : true,
      infiniteId: +new Date(),
      myData: {
        nickname: null, 
      },
      isModal: false,
      curPage: 1,
      busy: false,
      modalData: {
        key: null,
        id: null, 
        imageId: null,
        memberId: null, 
        ownerName: null, 
        style: null
      }
    }
  },
  methods:{
    modal(data, idx) {
      this.isModal = !this.isModal
      this.modalData = data
      this.modalData.key = idx
    },
    async onScroll() {
    var athis = this

    await axios.get(process.env.VUE_APP_API_URL + '/member/looks' + `?page=${athis.curPage}&size=10&sort=id,DESC`, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': athis.$store.state.Auth['accessToken']
      }
    })
    .then(response => {
      athis.curPage++
      for (var item of response.data) {
        athis.data.push(item)
      }
      if (response.data.length < 10) {
        this.hasMore = false
      } else {
        this.hasMore = true
      }
      this.hasMore = response.data.length === 10
      return response
    })
    .catch(error => console.log(error))
  }
},
  mounted() {
    axios.get(process.env.VUE_APP_API_URL + '/member/looks' + `?page=${this.curPage}&size=15&sort=id,DESC`, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': this.$store.state.Auth['accessToken']
      }
    })
    .then(response => {
      this.data = response.data
    })
    .catch(error => console.log(error))

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
      const data = Object.values(this.myData.styleByPercentResponseList)
      const chartData = []
      let tmp = 0

      data.forEach(e=>{
        if (chartData.length < 4){
          chartData.push(e)
        } else {
          tmp += e.count
        }
      })

      if (tmp) {
        chartData.push(
          {style: 'etc', count: tmp}
        )
      }

      new Chart(ctx, {
      type: 'bar',
      data: {
        labels: chartData.map(x => {
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
          data: chartData.map(x => x['count']),
          borderWidth: 0.8,
          backgroundColor: [
                '#FFBFBA',
                '#FFD89D',
                '#C8E4FF',
                '#C8CDFF',
                '#C8FFD7',
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

.container {
  width: 97%;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 0fr;
  /* padding: 7px; */
  /* grid-template-rows: repeat(2, 100px);
  grid-template-columns: repeat(3, 1fr); */
  /* grid-auto-rows: 100px; */
  grid-auto-rows:minmax(100px, auto)
}

#picture {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

#modal {
  z-index: 999;
  border: #000 solid 2px;
  background-color: #fff;
  position: fixed;
  /* width: 100%;
  height: 100%; */
  left:50%; top:50%; transform: translate(-50%, -50%)
}

#trashBtn {
  width:20px;
  position: absolute;
  margin: 5%;
  background-color: white;
  border-radius: 10px;
  padding: 7px;
}
</style>