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
        canvasItems: [],
        offset: {x: 0, y: 0},
        start: {x: 0, y: 0},
        mouseDown: false
      }
    },

    mounted() {
        this.canvas = document.getElementById('myCanvas');
        // this.offset = {x: this.canvas.offsetLeft, y: this.canvas.offsetTop};
        this.start = {x: 0, y: 0};

        this.canvas.addEventListener("mousedown", (e) => {
          e.preventDefault();
          e.stopPropagation();

          const winScrollTop = window.scrollY;
          this.start.x = parseInt(e.clientX - this.offset.x);
          this.start.y = parseInt(e.clientY - this.offset.y + winScrollTop);

          this.mouseDown = true;
        });

        this.canvas.addEventListener("mouseup", (e) => {
          console.log('mouse clicked!!');
          this.mouseDown = false;
        });

        this.canvas.addEventListener("mousemove", (e) => {
          if (this.canvasItems === undefined || this.canvasItems.length === 0) {
            return;
          }
          e.preventDefault();
          	if(this.mouseDown){
              const winScrollTop = window.scrollY,
                  mouseX = parseInt(e.clientX - this.offset.x),
                  mouseY = parseInt(e.clientY - this.offset.y + winScrollTop);
              const dx = mouseX - this.start.x, dy = mouseY - this.start.y;
                
              this.start.x = mouseX;
              this.start.y = mouseY;
                  
              this.canvasItems[0].x += Number(dx.toFixed(0));
              this.canvasItems[0].y += Number(dy.toFixed(0));
              
              this.drawItem();
            }
        });

        this.canvas.addEventListener("touchstart", (e) => {
          console.log('mouse click!!');

          e.preventDefault();
          e.stopPropagation();

          const winScrollTop = window.scrollY;
          this.start.x = parseInt(e.clientX - this.offset.x);
          this.start.y = parseInt(e.clientY - this.offset.y + winScrollTop);

          this.mouseDown = true;
        });

        this.canvas.addEventListener("touchend", (e) => {
          console.log('mouse clicked!!');
          this.mouseDown = false;
        });

        this.canvas.addEventListener("touchmove", (e) => {
          if (this.canvasItems === undefined || this.canvasItems.length === 0) {
            return;
          }
          e.preventDefault();
          	if(this.mouseDown){
              const winScrollTop = window.scrollY,
                  mouseX = parseInt(e.clientX - this.offset.x),
                  mouseY = parseInt(e.clientY - this.offset.y + winScrollTop);
              const dx = mouseX - this.start.x, dy = mouseY - this.start.y;
                
              this.start.x = mouseX;
              this.start.y = mouseY;
                  
              this.canvasItems[0].x += Number(dx.toFixed(0));
              this.canvasItems[0].y += Number(dy.toFixed(0));
              
              this.drawItem();
            }
        });
    },

    methods: {
      choice(item) {
        const selectedItem = {}
        selectedItem.item = item;
        selectedItem.x = 0;
        selectedItem.y = 0;
        selectedItem.width = 100;
        selectedItem.height = 100;
        
        const image = new Image();
        image.src = 'http://localhost:8080/api/v1/images/' + item.imageId;
        selectedItem.image = image;
        
        const canvas = this.canvas;

        image.onload = function(){
            const ctx = canvas.getContext("2d");
            ctx.drawImage(image, selectedItem.x, selectedItem.y, selectedItem.width, selectedItem.height);
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
      },
      itemSelection(x, y, canvasItem){
        const tx = canvasItem.x, ty = canvasItem.y, tWidth = canvasItem.width, tHeight = canvasItem.height;
        return (x >= tx - tWidth/2 && x <= tx + tWidth/2 && y >= ty - tHeight && y <= ty);
      },
      drawItem() {
        const ctx = this.canvas.getContext("2d");
        ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.canvasItems.forEach(item => {
          item.ctx.drawImage(item.image, item.x, item.y, item.width, item.height);
        });
      },
      clearItem(canvasItem) {
        canvasItem.ctx.drawImage(canvasItem.image, 0, 0);
      }
    },
}
</script>
