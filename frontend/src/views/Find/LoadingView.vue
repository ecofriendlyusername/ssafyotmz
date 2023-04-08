<template>
  <div style="background-color:black; min-height: 780px;">

      <div id="LoadingText">
          <br> <br> 
          <div id="Loading">
              ㆍㆍㆍ
          </div>

          <div style="margin-top:35px; margin-bottom: 15px;">
              FIND YOUR STYLE
          </div>
      </div>

      <div id="LoadingBoard">
        <!-- 영상1 -->
        <div style="width:50%;">
            <video autoplay muted loop playsinline id="LoadingVideo1" src="@/assets/video/video1.mp4">
            </video>
        </div>
    
        <!-- 영상2 -->
        <div style="width:50%;">
            <video autoplay muted loop playsinline id="LoadingVideo2" src="@/assets/video/video2.mp4"></video>
        </div>
      </div>

      <br>

      <!-- 버튼 -->
      <button class="btn-3" v-on:click=saveData()>
        MY STYLE <span>FIND</span>
      </button>




  </div>
</template>

<script>
import axios from 'axios';

export default {

  methods: {
    // 서버에 저장하기
    saveData(){
      if (!this.$store.state.result['check']){
        return this.$router.push('/Find')
      }
      const formData = new FormData()
      formData.append('styleVector', JSON.stringify(this.$store.state.result['data']))
      formData.append('imageFile', this.$store.state.result['img_path'])
      formData.append('style', this.$store.state.result['data']['2']['style'])
      axios.post(process.env.VUE_APP_API_URL + '/looks', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': this.$store.state.Auth['accessToken']
        }
      })
      .then((response) => {
        // 파일 저장하는 api 리턴값으로 파일 경로 달라고 해야 함
        // this.image = response.data
        this.$store.state.result['imageId'] = response.data['imageId']
        this.$router.push('/Find/result')
      })
      .catch(error =>{
        console.log(error)
        this.$router.push('/Find/Error')
      })
    }
  }
}

</script>

<style>
@font-face {
  font-family: 'BookkMyungjo-Bd';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302@1.0/BookkMyungjo-Bd.woff2') format('woff2');
  font-weight: 700;
  font-style: normal;
}

#Loading {
font-size: 30px;
min-width:11px;
white-space: nowrap;
margin: 0;
position: absolute;
display: inline-block;
color: transparent;
transform: translate(-50%, -50%);
}

#Loading::before {
  content: "ㆍㆍㆍ";
position: absolute;

color: white;
overflow: hidden;

animation: typing 3s steps(31) ;
}
@keyframes typing{
0% {
  width: 0%;
}
100% {
  width: 100%;
}

}


#LoadingText {
  font-size: 25px;
  font-family: 'BookkMyungjo-Bd';
  color: white;
}

#LoadingBoard {
  display: flex;
  padding: 5px;
  margin-top: 20px;
  margin-bottom: 20px;
}

#LoadingVideo1 {
  width: 95%;
  height: 95%;
}

#LoadingVideo2 {
  width: 95%;
}


.btn-3 {
  font-family: 'BookkMyungjo-Bd';
padding: 10px 24px;
background: #dadada;
color: rgb(0, 0, 0);
font-size: 16px;
font-weight: bold;
border: none;
outline: none;
border-radius: 100px 0px 100px 100px;
transition: all 0.3s;
animation: fadein 9s;
}

.btn-3 span {
opacity: 0;
margin-left: -20px;
transition: all 0.5s;
color: #A71A0E;
font-weight: bold;
}
.btn-3:hover span {
opacity: 1;
margin-left: 2px;
}

@keyframes fadein {
  0% {
      opacity: 0;
  }
  30% {
      opacity: 0;
  }
  100% {
      opacity: 1;
  }
}

</style>
