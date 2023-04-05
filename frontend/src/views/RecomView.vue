<template>
  <div id="modal" v-if="isModal">
    <p>{{ modalData.ownerName }} 님의 {{ modalData.style }} 스타일의 옷이에요</p>
    <hr>
    <img :src="`${ path }/images/${ modalData.imageId }`" alt="">
    <hr>
    <button v-on:click="isModal = false">닫기</button>
  </div>
  <!-- <div>
    스타일 추천 페이지
  </div> -->
  <br>

  <div style="display:flex; justify-content:space-between; margin: 10px;">
    <div style="font-weight:bold; font-size:120%">
      {{ this.labels[this.filter] }}
    </div>

    <div>
      <button v-on:click="btnToggle('isOMZ')" id="recomBtn">옷엠지 추천</button>
      <button v-on:click="btnToggle('isStyle')" id="recomBtn">스타일 필터</button>
    </div>
  </div>
  
  <div v-if="isOMZ" style="display:flex; justify-content:end;">
    <label for="similar" v-on:click="this.filter='similar'" class="category">나랑 비슷한거</label><input type="radio" name="OMZ" id="similar">
    <label for="issimilar" v-on:click="this.filter='issimilar'" class="category" style="margin-left:-17px; margin-right: 23px;">안비슷한거</label><input type="radio" name="OMZ" id="issimilar"> 
  </div>

  <div v-if="isStyle" style="display:grid; grid-template-columns: 1fr 1fr 1fr 1fr; margin-top: 10px;">
      <div v-for="(key, value) in labels">
        <div v-on:click="this.filter=value">
          <div class="category">{{ key }}</div>
        </div>
      </div>
    </div>
  <hr>

  <div style="display:grid; grid-gap: 10px 5px;">
    <div class="container">
      <div v-for="item in items">
        <img :src= '`${ path }/images/${ item.imageId }`' style="width:100%;" id="picture" v-on:click="modal(item)">
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";


export default {
  name:'RecomView',
  data() {
    return {
      path: process.env.VUE_APP_API_URL,
      isOMZ:false,
      isStyle: false,
      filter: null,
      labels: {
        'romantic': '로맨틱',
        'feminine':'페미닌',
        'street':'스트릿',
        'country':'컨트리',
        'resort':'리조트',
        'retro':'레트로',
        'classic':'클래식',
        'sexy':'섹시',
        'hippie':'히피',
        'modern':'모던',
        'sophisticated':'소피스티케이티드',
        'kitsch':'키치',
        'avantgarde':'아방가르드',
        'western':'웨스턴',
        'oriental':'오리엔탈',
        'preppy':'프레피',
        'sporty':'스포티',
        'hiphop':'힙합',
        'manish':'매니쉬',
        'tomboy':'톰보이',
        'genderless':'젠더리스',
        'punk':'펑크',
        'military':'밀리터리'
      },
      items: [],
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
  mounted() {
    axios.get(process.env.VUE_APP_API_URL + '/looks/recommended', {
          headers: {
            'Authorization': this.$store.state.Auth['accessToken']
          }
        })
        .then(response => {
          console.log(response.data)
          this.items = response.data
        })
        .catch(error => console.log(error))
  },
  watch: {
    filter(newValue, oldValue) {
      // 여기서 axios로 데이터 불러오기
      if (this.isOMZ) {
        // 우리 추천
        // console.log(this.isOMZ, this.filter)
        let reverse = ''
        if (this.filter === 'issimilar') {
          this.isOMZ = !this.isOMZ
          reverse = '?/reversed= true'
        } 
        axios.get(process.env.VUE_APP_API_URL + '/looks/recommended' + reverse, {
          headers: {
            'Authorization': this.$store.state.Auth['accessToken']
          }
        })
        .then(response => {
          console.log(response.data)
          this.items = response.data
        })
        .catch(error => console.log(error))
      
      }
      else {
        // 카테고리별 추천
        console.log(this.isStyle, this.filter)
        this.isStyle = !this.isStyle
        axios.get(process.env.VUE_APP_API_URL + `/looks?style=${ this.filter }`, {
          headers: {
            'Authorization': this.$store.state.Auth['accessToken']
          }
        })
        .then(response => {
          console.log(response.data)
          this.items = response.data
        })
        .catch(error => console.log(error))
      }
    }
  },
  methods:{
    btnToggle(key){
      if (key == 'isOMZ') {
        this.isOMZ = !this.isOMZ
        this.isStyle = false
      }else{
        this.isOMZ = false
        this.isStyle = !this.isStyle
      }
    },
    modal(data) {
      console.log(data)
      this.isModal = !this.isModal
      this.modalData = data
    }
  }
}
</script>

<style>

input[type="radio"] {
  opacity: 0;
  z-index: -1;
}

#recomBtn {
  background-color: white;
  border: 1px solid gray;
  border-radius: 15px;
  padding: 5px 15px;
  color: gray;
  font-weight: bold;
}
#recomBtn:hover {
  background-color: black;
  border: 1px solid black;
  color: rgb(255, 255, 255);
}

.category {
  border: 2px solid gray;
  /* border-radius: 15px; */
  color: black;
  font-weight: bold;
  padding: 2px 2px;
  margin: 3px;
  font-size: 72%;
} 
.category:hover {
  background-color: black;
  color: white;
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

</style>
