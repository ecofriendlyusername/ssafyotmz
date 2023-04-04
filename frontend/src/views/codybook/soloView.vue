<template>


  <div class="categories">
    <div class="category" :class="{ 'selected': selected === 'outer' }" @click="getItems('outer')">아우터</div>
    <div class="category" :class="{ 'selected': selected === 'upper' }" @click="getItems('upper')">상의</div>
    <div class="category" :class="{ 'selected': selected === 'lower' }" @click="getItems('lower')">하의</div>
    <div class="category" :class="{ 'selected': selected === 'dress' }" @click="getItems('dress')">원피스</div>
    <div class="category" :class="{ 'selected': selected === 'etc' }" @click="getItems('etc')">기타</div>
  </div>
  <swiper class="items"
    @activeIndexChange="loadItems" 
    :modules="modules"
    :space-between="1"
    :loop="false"
    :slidesPerView="5"
  >
    <swiper-slide v-for="item in items" @click="choice(item)" :key="item.id" >
      <img :src='`${item.src}`' style="width:90px;hegiht:80px"/>
    </swiper-slide>
  </swiper>

  <br>

  <div style="display:flex; justify-content:center;">
    <div style="display:flex; width: 95%;">
      <div style="width: 30%;">
        <label for="text" style="font-weight:bold">코디 이름</label>
      </div>
      <div style="width: 70%;">
        <input type="text" id="name" name="name" v-model="name" style="border: 3px solid black; border-left-width:0; border-right-width:0; border-top-width:0; width: 80%;">
      </div>
    </div>
  </div>
  <br>

  <div style="display:flex; justify-content:center;">
    <div style="display:flex; width: 95%;">
      <div style="width: 30%;">
        <label for="text" style="font-weight:bold">설명</label>
      </div>
      <div style="width: 70%;">
        <input type="text" id="comment" name="comment" v-model="comment" style="border: 3px solid black; border-left-width:0; border-right-width:0; border-top-width:0; width: 80%;">
      </div>
    </div>
  </div>

  <br>

  <!-- <label for="text" style="font-weight:bold">코디 이름</label>&nbsp;&nbsp;&nbsp;<input type="text" id="name" name="name" v-model="name">
  <br>
  <label for="text">설명</label><input type="text" id="comment" name="comment" v-model="comment">
   -->
  <div>
    <div class="settings">
      <div class="box">
        <span class="editor-btn icon" title="Color Picker">
          <input title="Color Picker" type="color" id="color" v-model="backgroundColor">
        </span>
      </div>

<!-- 
        <div @click="clear">전체 삭제</div>
        <div v-if="dragItemId" @click="removeItem">선택항목 삭제</div> -->

    </div>

    <div :style="{margin: '10px', backgroundColor: backgroundColor}">
      <v-stage
        ref="stage"
        :config="configKonva"
        @dragstart="handleDragstart"
        @dragend="handleDragend"
        @mousedown="handleMouseDown"
        @touchstart="handleMouseDown"
      >
        <v-layer ref="layer"
        :config="configKonva">
          <v-rect 
            id="background"
            :config="{
              x: 0,
              y: 0,
              width: 400,
              height: 400,
              fill: backgroundColor
            }"
          />
          <v-image :config="{
            x: 330,
            y: 330,
            width: 50,
            height: 50,
            image: logo
          }" />
          <v-image v-for="item in list" :key="item.id"
            :config="{
              name: item.name,
              image: item.image,
              x: item.x,
              y: item.y,
              width: item.width,
              height: item.height,
              rotation: item.rotation,
              id: item.id,
              numPoints: 5,
              innerRadius: 30,
              outerRadius: 50,
              draggable: item.dragable,
              shadowColor: 'black',
              shadowBlur: 10,
              shadowOffsetX: dragItemId === item.id ? 10 : 3,
              shadowOffsetY: dragItemId === item.id ? 10 : 3,
              shadowOpacity: 0.2,
            }"
            @transformend="handleTransformEnd"
          ></v-image>
          <v-transformer ref="transformer"/>
        </v-layer>
      </v-stage>
    </div>
  </div>

  <div style="display:flex; justify-content:space-between;">
    <router-link to='/Codybook/live' id="goTogether">← 함께 코디하러 가기</router-link>
    <div v-if="dragItemId" @click="removeItem" id="DelBtn" style="margin-right:-50px">선택항목 삭제</div>
    <div @click="clear" id="DelBtn">전체 삭제</div>
  </div>

  <br> <br>



  <div style="display:flex; justify-content:center; align-items:center;">
    <div @click="captureCodiBoard" style="border:3px solid black; border-radius: 10px; font-weight: bold;">
      <div style="padding:10px 40px;">
        <div style="height:30px;">
          <img src="@/assets/img/camera.png" style="width:30px;">
        </div>
        <div>
          캡처
        </div>
      </div>
    </div>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

    <div @click="saveCodiBoard" style="border:3px solid black; border-radius: 10px; font-weight: bold;">
      <div style="padding:10px 40px;">
        <div style="height:30px;">
          <img src="@/assets/img/comp.png" style="width:23px; margin-top:3px">
        </div>
        <div>
          저장
        </div>
      </div>
    </div>

  </div>

  <br><br><br>



</template>

<script>
import axios from 'axios'

import {Swiper, SwiperSlide} from 'swiper/vue'
import 'swiper/css'
import 'swiper/css/pagination'

const width = 400;
const height = 400;

export default {
    name:'CodybookView',

    components: {Swiper, SwiperSlide},

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
        logo: null,
        backgroundColor: '#FFDAB9',
        selected: 'outer',

        name: '',
        comment: '',
      }
    },

    created() {
      const logoImage = new Image();
      logoImage.src = '/logo.png'
      logoImage.onload = () => {
        this.logo = logoImage;
      }
    },

    mounted() {
      this.backgroundColor = '#' +Math.floor(Math.random()*16777215).toString(16);
    },

    methods: {
      dataURLtoFile(dataurl, filename) {
        const arr = dataurl.split(',')
        const mime = arr[0].match(/:(.*?);/)[1]
        const bstr = atob(arr[1])
        let n = bstr.length
        const u8arr = new Uint8Array(n)
        while (n) {
          u8arr[n - 1] = bstr.charCodeAt(n - 1)
          n -= 1 // to make eslint happy
        }
        return new File([u8arr], filename, { type: mime })
      },
      saveCodiBoard() {
        const formData = new FormData();

        const stage = this.$refs.stage.getNode();

        formData.append('imagefile', this.dataURLtoFile(stage.toDataURL({ pixelRatio: 3 }), 'codiboard.png'));
        formData.append('itemMatch', new Blob([JSON.stringify({'name': this.name, 'comment': this.comment})], {type: 'application/json'}));

        axios.post(process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/itemmatch', formData, { // outer, upper, lower, dress, etc
          headers: {
            'Content-Type' : 'multipart/form-data',
            'Authorization': this.$store.state.Auth['accessToken']
          }
        })
        .then((response) => {
          // 파일 저장하는 api 리턴값으로 파일 경로 달라고 해야 함
          // this.image = response.data
          console.log(response.data);
          alert('코디가 저장되었습니다!');
          history.go(-1);
        })
        .catch(error =>{
          console.log(error)
        });
      },  
      loadItems(swiper) {
        const index = swiper.realIndex;
        if (index % 10 === 5 && index + 5 === this.items.length) {
          axios.get(process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/items/' + this.$store.state.Auth.memberId + '/' + this.selected +'?page=' + (parseInt(index / 10) + 1) + '&size=10', { // outer, upper, lower, dress, etc
            headers: {
              'Content-Type': 'application/json',
              'Authorization': this.$store.state.Auth['accessToken']
            }
          })
          .then((response) => {
            // 파일 저장하는 api 리턴값으로 파일 경로 달라고 해야 함
            // this.image = response.data
            console.log(response.data)
            response.data.content.forEach(item => {
              item.src = process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/images/' + item.imageId;
              this.items.push(item);
            });
          })
          .catch(error =>{
            console.log(error)
          });
        }
      },
      captureCodiBoard() {
        const stage = this.$refs.stage.getNode();
        const dataURL = stage.toDataURL({ pixelRatio: 3 });
        var link = document.createElement('a');
        link.download = 'capture.png';
        link.href = dataURL;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      },
      removeItem() {
        if (this.dragItemId === null) {
          return;
        }
          const index = this.list.findIndex(i => i.name === this.dragItemId);
          this.list.splice(index, 1);
          this.dragItemId = null;
          this.updateTransformer();
      },
      handleTransformEnd(e) {
        const item = this.list.find(i => i.name === this.dragItemId);
        item.x = e.target.x();
        item.y = e.target.y();
        item.rotation = e.target.rotation();
        item.scaleX = e.target.scaleX();
        item.scaleY = e.target.scaleY();
        item.width = e.target.width();
        item.height = e.target.height();
      },
      handleMouseDown(e) {
        console.log('click', e.target)
        if (e.target.id() === 'background') {
          this.dragItemId = null;
          this.updateTransformer();
          return;
        }

        const clickedOnTransformer = e.target.getParent().className === 'Transformer';
        if (clickedOnTransformer) {
          return;
        }

        const name = e.target.name();
        const item = this.list.find(i => i.name === name);
        if (item) {
          this.dragItemId = name;
          this.updateTransformer();
          const index = this.list.indexOf(item);
          this.list.splice(index, 1);
          this.list.push(item);
        } else {
          this.dragItemId = null;
        }
      },
      clear() {
        this.list = []
        this.dragItemId = null;
        this.updateTransformer();
      },
      handleDragstart(e) {
        // save drag element:
        this.dragItemId = e.target.id();
        // move current element to the top:
        const item = this.list.find(i => i.id === this.dragItemId);
        const index = this.list.indexOf(item);
        this.list.splice(index, 1);
        this.list.push(item);

        this.updateTransformer();
      },
      handleDragend(e) {
        const item = this.list.find(i => i.name === this.dragItemId);

        console.log(this.list.find(i => i.name === this.dragItemId));

        item.x = e.target.x();
        item.y = e.target.y();
        item.rotation = e.target.rotation();

      },
      updateTransformer() {
        const transformerNode = this.$refs.transformer.getNode();
        const stage = transformerNode.getStage();
        const { dragItemId } = this;

        const selectedNode = stage.findOne('.' + dragItemId);
        if (selectedNode === transformerNode.node()) {
          return;
        }

        if (selectedNode) {
          transformerNode.nodes([selectedNode]);
        } else {
          transformerNode.nodes([]);
        }
      },
      choice(item) {
        const img = new Image();
        img.src = process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/images/' + item.imageId;
        img.crossOrigin = 'Anonymous';

        const targetIndex = this.list.findIndex(x => x.name === String(item.id));
        this.dragItemId = null;
        this.updateTransformer();

        if (targetIndex !== -1) {
          const target = this.list[targetIndex];
          this.list.splice(targetIndex, 1);
          this.list.push(target);
          return;
        }
      
        this.list.push({
          name: String(item.id),
          image: img,
          id: String(item.id),
          x: item.x ? item.x : 10,
          y: item.y ? item.y : 10,
          rotation: 0,
          width: item.width ? item.width : 200,
          height: item.height ? item.height : 200,
          scale: 1,
          dragable: true
        });
        console.log(this.list)
      },
      getItems(category) {
        this.selected = category;
        axios.get(process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/items/' + this.$store.state.Auth.memberId + '/' + category +'?page=0&size=10', { // outer, upper, lower, dress, etc
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
          this.items.forEach(item => item.src = process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/images/' + item.imageId);
        })
        .catch(error =>{
          console.log(error)
        });
      },
    },
}
</script>

<style scoped>
  .categories {
    display: flex;
    margin: 10px;
    
  }

  .category {
    margin: 0 5px;
    
  }

  .selected {
    background-color: black;
    color: white;
  }

  .settings {
    display: flex;
    flex-direction: row-reverse;
    margin: 10px;
  }

  .items {
    min-height: 150px;
    background-color: #F5F5F7;
    width: 95%;
    margin-top: -10px;
  }

  #DelBtn {
    background-color: #F5F5F7;
    padding: 5px;
    margin: 0px 3%;
    border: 2px solid black;
    border-radius: 5px;
  }

  #goTogether {
    margin-left: 10px;
    margin-top: 7px;
    text-decoration: none;
    font-weight: bold;
  }
</style>