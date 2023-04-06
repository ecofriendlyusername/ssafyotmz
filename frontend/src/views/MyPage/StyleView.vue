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
      <p>유저이름: {{ myData['nickname'] }}</p>
      <p>스타일 진단 횟수 {{ this.myData['totalStyleCount'] }} 회</p>
    </div>
    <div>
      <canvas id="myChart" ></canvas>
    </div>

    <hr>

    <div style="display:flex; justify-content:center">
      <div class="container" style="gap:5px">
        <div v-for="(style, index) in styleList" :key="index">
          <img :src= '`${ path }/images/${ style.imageId }`' style="width:100%;" id="picture" v-on:click="modal(style, index)">
        </div>
      </div>
    </div>
    <!-- 여기까지 -->
    <hr>
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
      },
      isModal: false,
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
      console.log(data)
      this.isModal = !this.isModal
      this.modalData = data
      this.modalData.key = idx
    },
    styleDelete(lookID) {
      axios.delete(process.env.VUE_APP_API_URL + '/looks/' + lookID, {
        headers: {
          'Authorization' : this.$store.state.Auth['accessToken']
        }
      }).then((res) => {
        this.isModal = false
        location.reload()
      }).catch((e) => {
        console.log(e)
      })
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
      console.log(response.data)
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
    // console.log(response.data)
    this.myData = response.data
  })
  .then(() => {
      const ctx = document.getElementById('myChart');
      const data = Object.values(this.myData.styleByPercentResponseList)
      new Chart(ctx, {
      type: 'bar',
      data: {
        labels: data.map(x => x['style'] + ' : ' + x['count'] + '%'),
        datasets: [{
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