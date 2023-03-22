<template>
  <div>
    <div style="background-color:black">
      <img src="@/assets/img/main1.png" style="width:100%">
      <img src="@/assets/img/main2.png" style="width:100%">
      <img src="@/assets/img/main3.png" style="width:100%">
      <img src="@/assets/img/main4.png" style="width:100%">
      
      <div v-if="this.EndPoint = true">
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
export default {
  data() {
    return {
      EndPoint: false,
    }
  },
  mounted () {
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