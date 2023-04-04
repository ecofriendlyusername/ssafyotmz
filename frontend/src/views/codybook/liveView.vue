<template>
  <div>
    라이브 코디북 만들기 페이지
  </div>
  <hr>
  <div>
    <p @click="getItems('outer')">아우터</p>
    <p @click="getItems('upper')">상의</p>
    <p @click="getItems('lower')">하의</p>
    <p @click="getItems('dress')">원피스</p>
    <p @click="getItems('etc')">기타</p>
  </div>
  <div v-for="item in items" @click="choice(item)" :key="item.id">
    <div>{{item}}</div>
    <img :src='`${item.src}`' style="width:100px;hegiht:100px"/>
  </div>
  <hr>
  <div>
    현재 코디북에 등록된 옷
    <div v-if="dragItemId" @click="removeItem">삭제</div>
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
    name:'CodybookLiveView',

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
      this.backgroundColor = '#' +Math.floor(Math.random()*16777215).toString(16);
    },

    methods: {
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
        console.log('click')
        if (e.target === e.target.getStage()) {
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
        img.src = process.env.VUE_APP_API_URL + '/images/' + item.imageId;

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
        axios.get(process.env.VUE_APP_API_URL + '/items/' + category +'?page=0&size=10', { // outer, upper, lower, dress, etc
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
          this.items.forEach(item => item.src = process.env.VUE_APP_API_URL + '/images/' + item.imageId);
        })
        .catch(error =>{
          console.log(error)
        });
      },
    },
}
</script>