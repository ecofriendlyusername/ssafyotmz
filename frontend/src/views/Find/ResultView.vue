<template>
  <div id="BackgroundCard">
    <div id="InnerCard"> 
      <!-- 워터마크 찍은 이미지 사이즈 맞춰서 넣기 -->
        <img style="width:100%; border-radius: 20px; max-height: 325px; " :src="imgsrc" />
      </div>
    
      <!-- 스타일 출력 -->
      <!-- 스타일 별 설명 값 넣기 -->
      <div id="ResultText">
        <h3>#{{ result['data']['1'].style}} #{{ result['data']['2'].style}} #{{ result['data']['3'].style}}</h3>
        <p style="font-size:90%">편리하고 활동성을 중요시하면서도 <br>
          스타일리쉬하게 입는 것을 즐기시네요.</p>
      </div>
    </div>


      <div style="display:flex; justify-content: center; margin-top: -55px;">
        <!-- 카카오 링크 공유 연결 -->
            <div v-on:click=shareKakao() id="kakaotalk-sharing-btn" class="ResultBtn">
              <div>
                <img src="@/assets/img/link.png" style="width:20px; margin-top: 4px;">
              </div>
              &nbsp;&nbsp;
              <div>
                스타일 공유하기
              </div>
            </div>

            <div class="ResultBtn" v-on:click="$router.push('/Find')">
              <div>
                <img src="@/assets/img/restart.png" style="width:20px; margin-top: 4px;">
              </div>
              &nbsp;&nbsp;
              <div>
                다시하기
              </div>
            </div>
      </div>


      
      <router-link to='/Recom' style="text-decoration:none; color:black">
        <div style="display:flex; justify-content:center; margin-top: 12px;">
          <div id="GoStyle">
            <p style="font-size:95%">
              더욱 다양한 스타일을 즐겨보세요
            </p>
            <p style="font-size:90%">
              Go to another style
            </p>
          </div>
        </div>
      </router-link>
      
    
        <!-- <div v-on:click=shareKakao() id="kakaotalk-sharing-btn">스타일 공유하기</div> | -->
        <!-- <router-lin to='/Find'>다시하기</router-link> -->

</template>

<script>
export default {
  name:'FindResultView',
  data(){
    return {
      initkakao:false,
      imgsrc: null,
    }
  },
  methods:{
    async shareKakao(){
      await Kakao.init('4a558f01722d37955f2c7bb1c18170d0');

      await Kakao.Share.createCustomButton({
        container: '#kakaotalk-sharing-btn',
        templateId: 91677,
        templateArgs: {
          img_path: process.env.VUE_APP_API_URL + '/image' + this.result['imageId'],
          username: this.$store.state.Auth.nickname,
          style: this.result['data']['1']['style'],
        },
      })
    }
  },
  computed: {
    result() {
      return this.$store.state.result
    }
  },
  mounted() {
    console.log(this.result)
    this.imgsrc = URL.createObjectURL(this.result['img_path']);
    // 여기 아래에 워터마크 찍는 코드 붙여야 함

  }

}
</script>

<style>
#ResultText {
  font-family: 'NanumSquareNeo-Variable';
  color: black;
}

.ResultBtn {
  font-family: 'NanumSquareNeo-Variable';
  align-items: center;
  background-color: #F5F5F7;
  border-radius: 14px;
  width: 32%;
  font-weight: bold;
  font-size: 80%;
  padding: 6px;
  margin: 5px;
  box-shadow: 5px 5px 5px rgb(192, 192, 192);
  color: black;
  display:flex; justify-content: center;
}

#GoStyle {
  font-family: 'NanumSquareNeo-Variable';
  background-color: #F5F5F7;
  border-radius: 18px;
  width: 70%;
  font-weight: bold;
  padding: 5px;
  box-shadow: 5px 5px 5px rgb(192, 192, 192);
  line-height: 7px;
  
}

</style>