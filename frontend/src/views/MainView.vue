<template>
  <div>
    <div style="background-color:black">
      <img src="@/assets/img/main1.png" style="width:100%">
      <img src="@/assets/img/main2.png" style="width:100%">
      <img src="@/assets/img/main3.png" style="width:100%">
      <img src="@/assets/img/main4.png" style="width:100%">
      
      <div v-if="this.EndPoint = true"  v-on:click=loginWithKakao()>
      <div class="fade-in-box">
        <div style="display:flex; align-items: center; margin: 0px 30px;">
          <div>
            <img src="@/assets/img/카카오톡.png" style="width:25px; margin-top: 5px;">
          </div>
          &nbsp;&nbsp;
          <div>
            Login with Kakao
          </div>
        </div>
      </div>
    </div>
    </div>

    


    
  </div>
</template>

<script>
import axios from "axios";
export default {
  name:'MainView',
  data() {
    return {
      EndPoint: false,
    }
  },
  methods: {
    loginWithKakao: function () {
      Kakao.init("4a558f01722d37955f2c7bb1c18170d0")
      Kakao.Auth.authorize({
        redirectUri: process.env.VUE_APP_KAKAO_REDIRECT_API_URL
      })
    },
  }, 
  mounted () {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const state = urlParams.get('state');
    let auth = null;
    if (code) {
      console.log(code)
      axios.get(process.env.VUE_APP_KAKAO_CERTIFIED_API_URL +'?code=' + code + '&redirect_uri=http://localhost:3000')
      .then(response => {
        this.$store.commit('setAuth', response.data); // auth 값을 스토어에 저장
      })
      .then(() => {
        console.log(this.$store.state.Auth)
      })
      .catch(error => console.log(error))
    }


    window.addEventListener('scroll', () => {
    let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
    let windowHeight = window.innerHeight; // 스크린 창
    let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x

    if(scrollLocation + windowHeight >= fullHeight){
      console.log('끝')
      // alert('dd')
      this.EndPoint = true
      console.log(this.EndPoint)
    } else {
      this.EndPoint = false
      console.log(this.EndPoint)
    }
})
  }

}

</script>

<style>
.fade-in-box {
  display: inline-block;
  margin: 10px 0px 60px 0px;
  border-radius: 10px;
  background: #FAE100;
  color: #371C1D;
  font-weight: bold;
  padding: 10px;
  animation: fadein 4.5s;
  -moz-animation: fadein 4.5s; /* Firefox */
  -webkit-animation: fadein 4.5s; /* Safari and Chrome */
  -o-animation: fadein 4.5s; /* Opera */
}
@keyframes fadein {
    from {
        opacity:0;
    }
    to {
        opacity:1;
    }
}
@-moz-keyframes fadein { /* Firefox */
    from {
        opacity:0;
    }
    to {
        opacity:1;
    }
}
@-webkit-keyframes fadein { /* Safari and Chrome */
    from {
        opacity:0;
    }
    to {
        opacity:1;
    }
}
@-o-keyframes fadein { /* Opera */
    from {
        opacity:0;
    }
    to {
        opacity: 1;
    }
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
</style>