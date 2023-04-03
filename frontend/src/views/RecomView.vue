<template>
  <div>
    스타일 추천 페이지
  </div>
  <hr>
  <p>현재 필터: {{ this.filter }}</p>
  <div>
    <button v-on:click="btnToggle('isOMZ')">옷엠지 추천</button>
    <button v-on:click="btnToggle('isStyle')">스타일 필터</button>
  </div>
  
  <div v-if="isOMZ">
    <label for="similar" v-on:click="this.filter='similar'">나랑 비슷한거</label><input type="radio" name="OMZ" id="similar">
    <label for="issimilar" v-on:click="this.filter='issimilar'">안비슷한거</label><input type="radio" name="OMZ" id="issimilar"> 
  </div>
  <div v-if="isStyle">
    <div v-for="category in labels">
      <label :for="category" v-on:click="this.filter=category">{{ category }}</label><input type="radio" name="OMZ" :id="category">
    </div>
  </div>
  <hr>
  <ul>
    <!-- <li v-for=""></li> -->
  </ul>
</template>

<script>
import axios from "axios";

export default {
  name:'RecomView',
  data() {
    return {
      isOMZ:false,
      isStyle: false,
      filter: null,
      labels: [
        'romantic',
        'feminine',
        'street',
        'country',
        'resort',
        'retro',
        'classic',
        'sexy',
        'hippie',
        'modern',
        'sophisticated',
        'kitsch',
        'avantgarde',
        'western',
        'oriental',
        'preppy',
        'sporty',
        'hiphop',
        'manish',
        'tomboy',
        'genderless',
        'punk',
        'military'
      ]
    }
  },
  watch: {
    filter(newValue, oldValue) {
      // 여기서 axios로 데이터 불러오기
      if (this.isOMZ) {
        // 우리 추천
        // console.log(this.isOMZ, this.filter)
        const reverse = ''
        if (!isOMZ) {
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
        })
        .catch(error => console.log(error))
      
      }
      else {
        // 카테고리별 추천
        console.log(this.isStyle, this.filter)
        axios.get(process.env.VUE_APP_API_URL + `/looks?style=${ this.filter }`, {
          headers: {
            'Authorization': this.$store.state.Auth['accessToken']
          }
        })
        .then(response => {
          console.log(response.data)
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
  }
}
</script>

<style>

input[type="radio"] {
  opacity: 0;
  z-index: -1;
}

</style>
