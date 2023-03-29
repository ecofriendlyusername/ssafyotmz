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
    <div :style="{margin: '10px', backgroundColor: backgroundColor}">
      <v-stage
        ref="stage"
        :config="configKonva"
        @dragstart="handleDragstart"
        @dragend="handleDragend"
      >
        <v-layer ref="layer">
          <v-image v-for="item in list" :key="item.id"
            :config="{
              image: item.image,
              x: item.x,
              y: item.y,
              width: 100,
              height: 100,
              rotation: item.rotation,
              id: item.id,
              numPoints: 5,
              innerRadius: 30,
              outerRadius: 50, fill: '#89b717',
              opacity: 0.8,
              draggable: true,
              scaleX: dragItemId === item.id ? item.scale * 1.2 : item.scale,
              scaleY: dragItemId === item.id ? item.scale * 1.2 : item.scale,
              shadowColor: 'black',
              shadowBlur: 10,
              shadowOffsetX: dragItemId === item.id ? 10 : 3,
              shadowOffsetY: dragItemId === item.id ? 10 : 3,
              shadowOpacity: 0.2,
            }"
          ></v-image>
        </v-layer>
      </v-stage>
    </div>
    <div>{{list}}</div>
    <div @click="clear">clear</div>
    <div class="box">
      <span class="editor-btn icon" title="Color Picker">
        <input title="Color Picker" type="color" id="color" v-model="backgroundColor">
      </span>
    </div>
  </div>
  <hr>
  <router-link to='/Codybook/live'>라이브 하기</router-link> |
  <router-link to='/'>메인페이지</router-link>
</template>

<script>
import axios from 'axios'

const width = 400;
const height = 400;

export default {
    name:'CodybookView',

    data() { 
      return {
        items: [],
        count: 0,
        list: [],
        dragItemId: null,
        configKonva: {
          width: width,
          height: height
        },
        backgroundColor: '#FFDAB9'
      }
    },

    mounted() {
    },

    methods: {
      clear() {
        this.list = []
      },
      handleDragstart(e) {
        // save drag element:
        this.dragItemId = e.target.id();
        // move current element to the top:
        const item = this.list.find(i => i.id === this.dragItemId);
        const index = this.list.indexOf(item);
        this.list.splice(index, 1);
        this.list.push(item);
      },
      handleDragend(e) {
        this.dragItemId = null;
      },

      choice(item) {
        console.log(item)
        const img = new Image();
        img.src = 'http://localhost:8080/api/v1/images/' + item.imageId;

        const targetIndex = this.list.findIndex(x => x.id === item.id);

        if (targetIndex !== -1) {
          console.log(targetIndex)
          console.log(this.list)
          this.list.splice(targetIndex, 1)
          console.log(this.list)
        }

        this.list.push({
          image: img,
          id: item.id,
          x: 0,
          y: 0,
          scale: 1
        });
        console.log(this.list)
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
    },
}
</script>