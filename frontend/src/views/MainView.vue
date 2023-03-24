<template>
  <div v-if="Auth.memberId">{{ Auth }}</div>
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



export default {
  name: 'MainView',
  data() {
    return {
      EndPoint: false,
      Auth: this.$store.state.Auth
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
  mounted() {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const state = urlParams.get('state');
    let auth = null;
    if (code) {
      console.log(code)
      axios.get(process.env.VUE_APP_KAKAO_CERTIFIED_API_URL + '?code=' + code + '&redirect_uri=http://localhost:3000')
        .then(response => {
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
}</style>