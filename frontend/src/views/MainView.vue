<template>

<div v-if="isModal" class="modal__background" style="z-index:100">
  <div id="modal" v-if="isModal">
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

  <div v-if="Auth.first == true" style="margin-top: -16px; padding: 13px; background-color:black; color: white;;">
    <br><br>

    <p style="display:flex; font-weight: bold;">스타일 진단 설문을 진행해주세요</p>
    
    <div style="display:flex; color: gray;margin-top: 20px;">
      O't MZ가 취향을 분석할 수 있도록
    </div>
    <div style="display:flex; color: gray; margin-top: 5px;">
      아래 버튼을 클릭하여 설문을 진행해주세요.
    </div>

    <div>
      <img src="@/assets/img/survey.png" style="width:85%; margin-top:25px;">
    </div>


      <button v-on:click="$router.push('/survey')" id="surveyBtn">
        SURVEY
      </button>


  </div>

  <!-- 로그인 시 메인화면 -->
  <div v-else-if="Auth.memberId" style="font-family: 'NanumSquareNeo-Variable';">
    <swiper
      :modules="modules"
      :space-between="20"
      :loop="true"
      :pagination="{clickable: true}"  
      :autoplay="{
        delay: 4000,
        disableOnInteraction: false,
        pauseOnMouseEnter: true
      }"
    >

    <swiper-slide v-for="text in swiperTextBase" :key="text.name">
      
      <img :src="text.imageUrl" alt="image" style="width:100%; height: 280px; opacity:1 ; ">
      <div style="position: absolute; top: 0px; background-color:black; opacity:0.5; height:280px; width:100%">
      </div>

      <div style="position:absolute; top:200px;">
        <p style="color: white; display: flex; font-weight: bold; font-size: 15px; padding: 5px 7px;">{{ text.name }}</p>
        <p style="font-size: 13px; color: white; display: flex; padding-left: 7px; margin-top: -16px;">
          {{ text.seller }}
        </p>
      </div>

      <div style="position:absolute; top:10px;font-size: 95%; color:white; margin-left: 77%;">
        <a :href= text.url style="text-decoration:none; color:white;">
          → 구매하기
        </a>
      </div>

    </swiper-slide>
    </swiper>

    <div>
      <div id="LoginDiv0" v-on:click="$router.push('/Recom')">
        <div>
          모아보기
        </div>
        <div style="color:red;">
          ❤
        </div>

      </div>
    </div>

    <div>
      <div id="LoginDiv1">
        {{ Auth['nickname'] }} 님의 취향이 담긴 코디
      </div>
      <div v-for="(items, key) in dressDatas">
        <div id="LoginDiv2">
          # {{ key.toUpperCase() }}
        </div>
        <div style="display:flex; justify-content:center; margin-top:7px;">
          <div class="container">
            <div class="item" v-for="unit in items">
              <img :src='`${path}/images/${unit.imageId}`' id="picture" v-on:click="modal(unit)">
            </div>
          </div>
        </div>
      </div>   
    </div>
  </div>

  <!-- 비로그인 시 메인화면 -->
  <div v-else style="background-color:black" class="main">
    <div id="start-div" style="height:820px; background-color:#FBFBFD">
      <img data-aos="zoom-in" data-aos-duration="2000" src="@/assets/img/main1.png" style="width:100.3%">
    </div>
    
    <div id="first-div"></div>
    <img src="@/assets/img/main2.png"
      data-aos="fade-up"
      data-aos-anchor="#first-div"
      data-aos-duration="380"
      style="width:100%">

    <div id="second-div" style="height:820px; background-color:#FBFBFD">
      <img src="@/assets/img/main3.png" 
      data-aos="fade-up"
      data-aos-anchor="#second-div"
      data-aos-duration="380"
      style="width:100%; ">
    </div>  

    <div id="third-div"></div>
    <img src="@/assets/img/main4.png"
      data-aos="zoom-in"
      data-aos-anchor="#third-div"
      data-aos-duration="380"
      style="width:100%">


    <div style="height:0px" id="last-div"></div>

    <div
      data-aos="fade-up"
      data-aos-duration="500"
      data-aos-anchor="#last-div"
      v-on:click=loginWithKakao()>
    <div class="fade-in-box">
        <div style="display:flex; justify-content: center; align-items: center;  margin: 0px 10px;">
            <img src="@/assets/img/카카오톡.png" style="width:15%;">
              &nbsp;&nbsp;&nbsp;&nbsp;
            <span style="font-size:90%;">
              Login with Kakao
            </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import AOS from 'aos';
import 'aos/dist/aos.css'; // You can also use <link> for styles
AOS.init();


import {Swiper, SwiperSlide} from 'swiper/vue'
import {Autoplay, Pagination} from 'swiper'
import 'swiper/css'
import 'swiper/css/pagination'
import {onMounted, ref} from 'vue'


export default {
  name: 'MainView',
  components: {Swiper, SwiperSlide},
  setup() {
    const swiperTextBase = ref([
      {seller:'러브앤드팝',
      name: '[UNISEX] 미니멀 오버핏셔츠',
      imageUrl: 'https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20210225_1614235708677593m.jpg'
      },
      {seller:'아더먼데이',
      name: '미니쉘 기본 오버핏셔츠',
      imageUrl: 'https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20230126_1674699972522098m.jpg'
      },
      {seller:'바이영',
      name: '봄가을 소매트임 베이직 자켓',
      imageUrl: 'https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20200916_1600266495690951m.JPG'
      },
    ]);

    return {modules:[Pagination, Autoplay], swiperTextBase};
  },
  data() {
    return {
      EndPoint: false,
      Auth: this.$store.state.Auth,
      dressDatas: null,
      path: process.env.VUE_APP_API_URL,
      isModal: false,
      modalData: {
        id: null, 
        imageId: null,
        memberId: null, 
        ownerName: null, 
        style: null
      }
    }
  },
  created(){
    AOS.init({
    duration: 2400,
    })
  },
  methods: {
    loginWithKakao: function () {
      Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT_API_URL)
      Kakao.Auth.authorize({
        redirectUri: process.env.VUE_APP_KAKAO_REDIRECT_API_URL
      })
    },
    modal(data) {
      console.log(data)
      this.isModal = !this.isModal
      this.modalData = data
    }
  },
  computed:{
    Auth(){
      return this.$store.state.Auth
    }
  },
  mounted() {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const state = urlParams.get('state');
    if (code) {
      console.log(code)
      axios.get(process.env.VUE_APP_API_URL + '/oauth/kakao?code=' + code + process.env.VUE_APP_LOCAL_USE_API_URL)
        .then(response => {
          console.log(response.data)
          this.$store.commit('setAuth', response.data); // auth 값을 스토어에 저장
        })
        .then(() => {
          console.log(this.$store.state.Auth)
          this.Auth = this.$store.state.Auth  
        })
        .then(()=>{
          axios.get(process.env.VUE_APP_API_URL + '/member/styles', {
        headers: {
          'Authorization': this.$store.state.Auth['accessToken']
        }
      })
      .then(response => {
        console.log(response.data)
        this.dressDatas = response.data
      })
      .catch(error => console.log(error))
        })
        .catch(error => console.log(error))
    }

    if (this.Auth) {
      axios.get(process.env.VUE_APP_API_URL + '/member/styles', {
        headers: {
          'Authorization': this.$store.state.Auth['accessToken']
        }
      })
      .then(response => {
        console.log(response.data)
        this.dressDatas = response.data
      })
      .catch(error => console.log(error));

       axios.get(process.env.VUE_APP_API_URL + '/items/brandi', {
        headers: {
          'Authorization': this.$store.state.Auth['accessToken']
        }
      })
      .then(response => {
        console.log(response.data)
        if (response.data.length !== 0) {
          this.swiperTextBase = response.data;
        }
      })
      .catch(error => console.log(error));
    }
  }
}

</script>

<style>

.fade-in-box {
  display: inline-block;
  margin: 10px 0px 60px 0px;
  border-radius: 10px;
  width: 55%;
  background: #fae100f8;
  color: #371C1D;
  font-weight: bold;
  padding: 10px;
}


.fade-enter-from {
  opacity: 0;
  transform: translateY(150px);
}

.fade-enter-active {
  transition: all 1s ease;
}

.fade-enter-to {
  opacity: 1;
  transform: translateY(0px);
}

#LoginDiv0 {
  display: flex;
  justify-content: space-between;
  font-size: 125%;
  font-weight: bold;
  background-color: rgba(0, 0, 0, 0.84);
  color: white;
  padding: 10px 15px;
  border-radius: 5px;
  margin: 15px 15px;
  box-shadow: 2px 2px 2px 2px rgb(174, 174, 174);
}
#LoginDiv0:hover {
  background-color: black;
  color: white;
}

#LoginDiv1 {
  display: flex;
  margin-top: 16px;
  margin-bottom: -28px;
  padding: 10px 15px;
  font-size: 125%;
  font-weight: bold;
}

#LoginDiv2 {
  display: flex;
  margin-top: 25px;
  padding: 0px 15px;
  font-size: 118%;
  font-weight: bold;
}

#picture {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.item {
  margin: 3px;
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  position: relative;
  font-size: 100%;
  color: black;
  /* border: 1px solid gray; */
  font-weight: 900;
}

.item::after {
  padding-bottom: 100%;
}

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



.item:nth-child(1) {
  grid-column: 1 / 3;
  /* max-height: 300px; */
}

#surveyBtn {
  background-color:#e63525;
  border: none;
  color:white;
  font-weight: bold;
  font-size: 85%;
  /* width: 60%; */
  padding: 15px 32px;
  border-radius: 15px;
  position: fixed;
  bottom:70px;
  left: 50%;
  transform: translate(-53%, 0);
  letter-spacing: 7px;
}
#surveyBtn:hover {
  background-color:#ff1500;
  border: 4px solid rgb(0, 0, 0);
}


#modal {
  z-index: 999;
  border: #000 solid 2px;
  background-color: #fff;
  border-radius: 15px;
  position: fixed;
  left:50%; top:50%; transform: translate(-50%, -50%)
}
.modal__background{
  position: fixed;
  top:0; left: 0; bottom: 0; right: 0;
  background: rgba(0, 0, 0, 0.8);
}
.ModalBtn {
  background-color: white;
  padding:5px 15px;
  font-size: 100%;
  font-weight: bold;
  margin-top: 25px;
  margin-bottom: 10px;
  border: 2px solid black;
  border-radius: 8px;
}
.ModalBtn:hover {
  background-color: black;
  color: white;
}

</style>