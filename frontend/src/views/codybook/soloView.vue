<template>
  <div>
    코디북 만들기 페이지
  </div>
  <hr>
  <div>
    내 옷 보여주는 창
  </div>
  <div>
    <p @click="met('outer')">아우터</p>
    <p @click="met('upper')">상의</p>
    <p @click="met('lower')">하의</p>
    <p @click="met('dress')">원피스</p>
    <p @click="met('etc')">기타</p>
  </div>
  <div v-for="item in items">
    <div>{{item}}</div>
    <img :src='`http://localhost:8080/api/v1/images/${item.imageId}`' style="width:100px;hegiht:100px"/>
  </div>
  <hr>
  <div>
    현재 코디북에 등록된 옷
  </div>
  <hr>
  <router-link to='/Codybook/live'>라이브 하기</router-link> |
  <router-link to='/'>메인페이지</router-link>
</template>

<script>
import axios from 'axios'

export default {
    name:'CodybookView',

    data() { 
      return {
        items: [],
        count: 0
      }
    },

    methods: {
      met(category) {
        axios.get(process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/items/' + category +'?page=0&size=10', { // outer, upper, lower, dress, etc
          headers: {
            'Content-Type': 'application/json',
            'Authorization': this.$store.state.Auth['accessToken']
          }
        })
        .then((response) => {
          // 파일 저장하는 api 리턴값으로 파일 경로 달라고 해야 함
          // this.image = response.data
          console.log(response.data)
          this.items = response.data.content;
        })
        .catch(error =>{
          console.log(error)
        });
      }
    },
}
</script>
