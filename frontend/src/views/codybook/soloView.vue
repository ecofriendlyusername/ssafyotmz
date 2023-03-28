<template>
  <div>
    코디북 만들기 페이지
  </div>
  <hr>
  <div>
    <p @click="getItems('outer')">아우터</p>
    <p @click="getItems('upper')">상의</p>
    <p @click="getItems('lower')">하의</p>
    <p @click="getItems('dress')">원피스</p>
    <p @click="getItems('etc')">기타</p>
  </div>
  <div v-for="item in items" @click="choice(item)">
    <div>{{item}}</div>
    <img :id='`${item.imageId}`' :src='`http://localhost:8080/api/v1/images/${item.imageId}`' style="width:100px;hegiht:100px"/>
  </div>
  <hr>
  <div>
    현재 코디북에 등록된 옷
    <canvas id="myCanvas" width="400" height="400" style="background-color: #325890"></canvas>
  </div>
  <div>{{canvasItems}}</div>
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
        count: 0,
        canvas: undefined,
        canvasItems: []
      }
    },

    mounted() {
        this.canvas = document.getElementById('myCanvas');

        // const ctx1 = this.canvas.getContext("2d");
        // ctx1.fillStyle = 'blue';
        // ctx1.fillRect(0, 0, 150, 75);

        // const ctx2 = this.canvas.getContext("2d");
        // ctx2.fillStyle = 'white';
        // ctx2.fillRect(100, 100, 150, 75);
    },

    methods: {
      choice(item) {
        const selectedItem = {}
        selectedItem.item = item;
        
        const image = new Image();
        image.src = 'http://localhost:8080/api/v1/images/' + item.imageId;
        
        const canvas = this.canvas;

        image.onload = function(){
            const ctx = canvas.getContext("2d");
            ctx.drawImage(image, 0, 0, 100, 100);
            selectedItem.ctx = ctx;
        }
      
        this.canvasItems.push(selectedItem);
      },
      getItems(category) {
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
