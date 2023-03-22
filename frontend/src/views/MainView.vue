<template>
  <div id="MainPage">
    <!-- 1div -->
    <div style="display:flex; justify-content:space-around; color: #EAE1D3;">
      <div style="margin-top: 10px;">
        <h1 style="font-size:100px;">1</h1>
      </div>
      <div>
        <h2>WHAT'S MY STYLE</h2>
        <p>본인의 사진을 올리고 <br> 스타일을 판별 받아보세요 </p>
      </div>
    </div>

    <!-- 2div -->
    <div style="display:flex; justify-content:space-around; color: #EAE1D3;">
      <div style="margin-top: 10px;">
        <h1 style="font-size:100px;">2</h1>
      </div>
      <div>
        <h2>디지털 옷장</h2>
        <p>내 옷장 속 옷들을 등록하고<br>간편하게 코디를 해보세요</p>
      </div>
    </div>

    <!-- 3div -->
    <div style="display:flex; justify-content:space-around; color: #EAE1D3;">
      <div style="margin-top: 10px;">
        <h1 style="font-size:100px;">3</h1>
      </div>
      <div>
        <h2>가상착장</h2>
        <p>“이 옷 어울릴까?” <br>O’tMZ의 가상착장 서비스를 경험하고
          <br>나에게 어울리는 옷을 찾아보세요</p>
      </div>
    </div>

    <!-- 4div -->
    <div style="display:flex; margin-top: 60px; justify-content:space-around; color: white;">
      로그인 하고 O’t MZ의 서비스를 경험해보세요
    </div>


    <div id="LoginBtn" v-on:click=loginWithKakao()>
      <div style="padding:65px; font-size: 20px; ">
        LOGIN
      </div>
    </div>

  </div>


  <!-- 임의로 if 넣어놓음 -->
  <div v-if="user==true" id="MainPage2">
    <div id="WeatherBox">
      <p style="font-weight:bold;">
        오늘의 날씨에 어울리는 옷
      </p>
      
      <p>
        현재 온도
        <br>
        체감 온도 
      </p>
      
      <div style="display:flex; justify-content: center; padding: 5px;">
        <div id="ItemBox">
          1
        </div>
        <div id="ItemBox">
          2
        </div>
        <div id="ItemBox">
          3
        </div>
      </div>
      


    </div>
  </div>

  <!-- 메인페이지
    <br>
    <router-link to='/Find'>내 스타일 찾기</router-link> -->
</template>

<script>
import axios from "axios";
export default {
  name:'MainView',
  methods: {
    loginWithKakao: function () {
      Kakao.init("4a558f01722d37955f2c7bb1c18170d0")
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
      axios.get(process.env.VUE_APP_KAKAO_CERTIFIED_API_URL +'?code=' + code + '&redirect_uri=http://localhost:3000')
      .then(response => {
        console.log(response.data)
        this.$store.commit('setAuth', response.data); // auth 값을 스토어에 저장
      })
      .then(() => {
        console.log(this.$store.state.Auth)
      })
      .catch(error => console.log(error))
    }
  }
}
</script>

<style>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}

#MainPage {
  background: linear-gradient(#C92D1F, #870E09);
  height: 100%;
  font-family: 'S-CoreDream-3Light';
}

#MainPage2 {
  height: 100%;
  font-family: 'S-CoreDream-3Light';
}

#WeatherBox {
  border: 5px solid #A71A0E;
  margin: 20px;
  border-radius: 25px;
}

#ItemBox {
  background-color: black;
  border-radius: 10px;
  width: 100px;
  height: 30px;
  margin: 0px 0px 15px 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-weight: bold;
}

#LoginBtn {
  display: inline-block;
  margin-top: 60px;
  width: 180px;
  height: 100px;
  border-radius: 100px 100px 0px 0px;
  border: 1.8px solid white;
  color: white;
  /* position: absolute;
  bottom: 40px; */
}
</style>