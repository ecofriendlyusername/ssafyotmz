<template>
  <div>
    스타일 추천 페이지
  </div>
  <hr>
  <p>현재 필터: {{ this.filter }}</p>
  <button v-on:click="getData()">검색하기</button>
  <div>
    <button v-on:click="btnToggle('isOMZ')">옷엠지 추천</button>
    <button v-on:click="btnToggle('isStyle')">스타일 필터</button>
  </div>
  
  <div v-if="isOMZ">
    <label for="similar" v-on:click="this.filter = 'similar'">나랑 비슷한거</label><input type="radio" name="OMZ" id="similar">
    <label for="issimilar" v-on:click="this.filter = 'issimilar'">안비슷한거</label><input type="radio" name="OMZ" id="issimilar"> 
  </div>
  <div v-if="isStyle" v-on:click="filtering()">
      <label for="필터1">필터1</label><input type="checkbox" name="Style" id="필터1">
      <label for="필터2">필터2</label><input type="checkbox" name="Style" id="필터2">
      <label for="필터3">필터3</label><input type="checkbox" name="Style" id="필터3">
      <label for="필터4">필터4</label><input type="checkbox" name="Style" id="필터4">
      <label for="필터5">필터5</label><input type="checkbox" name="Style" id="필터5">
  </div>
  <hr>
  <ul>
    <li>현재 필터에 맞는 스타일</li>
    <li>현재 필터에 맞는 스타일</li>
    <li>현재 필터에 맞는 스타일</li>
    <li>현재 필터에 맞는 스타일</li>
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
      filter: null
    }
  },
  methods:{
    getData() {
      console.log(this.filter)
      axios.get('https://j8c105.p.ssafy.io/api/v1/looks/recommeded', this.filter, {
        headers: {
          'Authorization': this.$store.Auth['accessToken']
        }
      })
      .then(response => {
        console.log(response.data)
      })
      .catch(error => console.log(error))
    },
    btnToggle(key){
      if (key == 'isOMZ') {
        this.isOMZ = !this.isOMZ
        this.isStyle = false
      }else{
        this.isOMZ = false
        this.isStyle = !this.isStyle
      }
    },
    filtering(){
      const query = 'input[name="Style"]:checked';
      const selectedEls = document.querySelectorAll(query);
      this.filter = ''
      // 선택된 목록에서 value 찾기
      selectedEls.forEach((el) => {
        this.filter += el.id + ' ';
      });
    },

  }
}
</script>

<style>

/* input[type="checkbox"] {
  opacity: 0;
  z-index: -1;
} */

</style>