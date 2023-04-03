<template>
  <!-- 로그인 시 메인화면 -->
  <div v-if="Auth.memberId" style="font-family: 'NanumSquareNeo-Variable';">
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

    <swiper-slide v-for="text in swiperTextBase" :key="text.description">
      
      <img :src="text.img" alt="image" style="width:100%; height: 280px; opacity:1 ; ">
      <div style="position: absolute; top: 0px; background-color:black; opacity:0.5; height:280px; width:100%">
      </div>

      <div style="position:absolute; top:200px;">
        <p style="color: white; display: flex; font-weight: bold; font-size: 18px; padding: 5px 7px;">{{ text.description }}</p>
        <p style="font-size: 13px; color: white; display: flex; padding-left: 7px; margin-top: -16px;">
          {{ text.author }}
        </p>
      </div>

      <div style="position:absolute; top:10px;font-size: 100%; color:white; margin-left: 79%;">
          → 구매하기
      </div>

    </swiper-slide>
    </swiper>

    <div>
      <div id="LoginDiv1" v-on:click="$router.push('/Recom')">
        랭킹 보기
      </div>
    </div>

    <div>
      <div id="LoginDiv1">
        {{ Auth['nickname'] }} 님의 취향이 담긴 코디
      </div>
      <div id="LoginDiv2">
        # 러블리
      </div>

      <!-- 그리드 -->
      <div style="display:flex; justify-content:center; margin-top:7px;">
        <div class="container">
          <div class="item">
            <img src="https://d20s70j9gw443i.cloudfront.net/t_GOODS_INFORMATION/https://imgb.a-bly.com/data/editor/20220828_1661640468492713l.jpg" id="picture">
          </div>
          <div class="item">
            <img src="https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20230302_1677698198733017m.jpg" id="picture">
          </div>
          <div class="item">
            <img src="https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20230308_1678255054352384m.jpg" id="picture">
          </div>
          <div class="item">
            <img src="https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/064e6fd8f60339b7e2ea452c06bcf244.jpg" id="picture">
          </div>
          <div class="item">
            <img src="https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20230206_1675654028387077m.jpg" id="picture">
          </div>
        </div>
      </div>

      <div id="LoginDiv2" style="margin-top:26px;">
        # 페미닌
      </div>

      <!-- 그리드 -->
      <div style="display:flex; justify-content:center; margin-top:7px;">
        <div class="container">
          <div class="item">1</div>
          <div class="item">2</div>
          <div class="item">3</div>
          <div class="item">4</div>
          <div class="item">5</div>
        </div>
      </div>
      

    </div>
    
    <!-- {{ Auth }} -->
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
            <span style="font-size:95%;">
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
import {ref} from 'vue'


export default {
  name: 'MainView',
  components: {Swiper, SwiperSlide},
  setup() {
    const swiperTextBase = ref([
      {author:'러브앤드팝',
      description: '[UNISEX] 미니멀 오버핏셔츠',
      img: 'https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20210225_1614235708677593m.jpg'
      },
      {author:'아더먼데이',
      description: '미니쉘 기본 오버핏셔츠',
      img: 'https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20230126_1674699972522098m.jpg'
      },
      {author:'바이영',
      description: '봄가을 소매트임 베이직 자켓',
      img: 'https://d20s70j9gw443i.cloudfront.net/t_GOODS_DETAIL/https://imgb.a-bly.com/data/goods/20200916_1600266495690951m.JPG'
      },
    ]);
    return {modules:[Pagination, Autoplay], swiperTextBase}
  },
  data() {
    return {
      EndPoint: false,
      Auth: this.$store.state.Auth,
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
    let auth = null;
    if (code) {
      console.log(code)
      axios.get(process.env.VUE_APP_KAKAO_CERTIFIED_API_URL + '?code=' + code + process.env.VUE_APP_LOCAL_USE_API_URL)
        .then(response => {
          console.log(response.data)
          this.$store.commit('setAuth', response.data); // auth 값을 스토어에 저장
        })
        .then(() => {
          console.log(this.$store.state.Auth)
          this.Auth = this.$store.state.Auth  
        })
        .catch(error => console.log(error))
    }

 


    // window.addEventListener('scroll', () => {
    //   let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
    //   let windowHeight = window.innerHeight; // 스크린 창
    //   let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x

    //   if (scrollLocation + windowHeight >= fullHeight) {
    //     console.log('끝')
    //     alert('dd')
    //     this.EndPoint = true
    //     console.log(this.EndPoint)
    //   } else {
    //     this.EndPoint = false
    //     console.log(this.EndPoint)
    //   }
    // })
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

#LoginDiv1 {
  display: flex;
  margin-top: 5px;
  padding: 10px 15px;
  font-size: 125%;
  font-weight: bold;
}

#LoginDiv2 {
  display: flex;
  margin-top: -3px;
  padding: 0px 15px;
  font-size: 118%;
  font-weight: bold;
}

#picture {
  width: 100%;
  height: 100%;
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
}

</style>