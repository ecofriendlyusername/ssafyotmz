<template>
  <div>
  <swiper v-if="pages.length!==0" class="items"
    @activeIndexChange="mySwipeChanged" 
    :space-between="1"
    :loop="false"
    :slidesPerView="1"
  >
    <swiper-slide v-for="i in Math.ceil(pages.length/9)">
      <div class="wrapperI">
        <div v-for="(page,index) in pages.slice((i-1)*9,i*9)" class="grid-item">
          <img v-if="page" :src="env+page.imageId" @click="selectItem(i,index)" @mousedown="selectItem(i,index)" :id="page.id" class="imgI" />
        </div>
      </div>
    </swiper-slide>
  </swiper>

  <br> <br>
  <div class="selectionBox">
    <div class="selection" id="selectB" v-if="pages.length!==0" @mousedown="selectItems()">선택</div>
    <div class="selection" id="deleteB" v-if="selectMode" @mousedown="deleteSelectedItems" >선택항목 삭제</div>
  </div>
    <div v-if="modalOpen" class="modal">
      <ItemDetail :selected="selected" @deleted="deleteItem()" @close="closeModal()">your content...</ItemDetail>
    </div>
  </div>
</template>
<script>
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from 'swiper/vue';

// Import Swiper styles
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';

import { register } from 'swiper/element/bundle';
import ItemDetail from './ItemDetail.vue'
import axios from 'axios'
register();

export default {
  name:'ItemView',
  components: {
    Swiper,
    SwiperSlide,
    ItemDetail,
  },
  data () {
    return {
      pages : [],
      env: process.env.VUE_APP_IMG,
      itemsToRemove: [],
      selectedIdx: -1,
      selected: null,
      Auth: this.$store.state.Auth,
      selectMode : false,
      modalOpen: false,
      selectedIndices : [],
    }
  },
  props: ['category'],
  methods: {
    printHi() {
      alert('hei')
    },
    closeModal() {
      this.modalOpen = false
    },
    selectItem(i,j) {
      var idx = (i-1)*9+j
      if (!this.selectMode) {
        this.selectedIdx = idx
        this.selected = this.pages[idx]
        this.modalOpen = true
        this.selectedIndices = []
        document.querySelector('.imgI').style.filter = 'saturate(1)'
      } else {
        if (this.selectedIndices.includes(idx)) {
          document.getElementById(this.pages[idx].id).style.filter = 'saturate(1)'
          document.getElementById(this.pages[idx].id).style.backgroundColor = '#F5F5F7'
          
          this.selectedIndices.splice(this.selectedIndices.indexOf(idx))
        } else {
          document.getElementById(this.pages[idx].id).style.filter = 'saturate(10%)'
          document.getElementById(this.pages[idx].id).style.backgroundColor = '#e7e7e7'
          this.selectedIndices.push(idx)
        }
      }
    },
    async deleteSelectedItems() {
      var a = this
      await this.deleteMultipleItems(this.selectedIndices.map(x => a.pages[x].id))
      .then(() => {
        var itemsToRemove = []
        a.selectedIndices.sort()
        a.selectedIndices.reverse()
        for (var idx of a.selectedIndices) {
          itemsToRemove.push(a.pages[idx].id)
          document.getElementById(a.pages[idx].id).style.filter = 'saturate(1)'
          a.pages.splice(idx,1)
        }
        a.selectMode = false
        document.getElementById('selectB').style.backgroundColor = 'white'
      })
      .catch((e) => {
        return e
      })
    },
    async viewMultipleItems(category,page,size) {
      var a = this
      var member_id = this.Auth.memberId;
      await axios.get(process.env.VUE_APP_API_URL+`/items/${member_id}/${category}?page=${page}&size=${size}&sort=id,DESC`).then((res) => {
        if (res.data.content.length !== 0) {
          for (var item of res.data.content) {
            a.pages.push(item)
          }
        }
      }).catch((e) => {
        console.log(e)
      })
    },
    mySwipeChanged (swiper) {
      var index = swiper.realIndex
      if (index === Math.ceil(this.pages.length/9)-1) {
        this.viewMultipleItems(this.category,index+1,9)
      }
    },
    selectItems() {
      if (this.selectMode) {
        this.selectedIndices.sort()
        this.selectedIndices.reverse()
        for (var idx of this.selectedIndices) {
          document.getElementById(this.pages[idx].id).style.filter = 'saturate(1)'
          document.getElementById(this.pages[idx].id).style.backgroundColor = '#F5F5F7'
        }
        this.selectedIndices = []
        document.getElementById('selectB').style.backgroundColor = '#a4a4a4'
      } else {
        document.getElementById('selectB').style.backgroundColor = 'black'
      }  
      this.selectMode = !this.selectMode;
    },
    async deleteMultipleItems(array) {
      axios.delete(process.env.VUE_APP_API_URL + `/items?ids=${array.join(',')}`, {
        headers: {
          'Authorization' : this.Auth.accessToken
        }
      }).then((res) => {
        console.log(res)
        return res
      }).catch((e) => {
        console.log(e)
        return e
      })
    },
    async deleteItem() {
      this.modalOpen = false
      this.pages.splice(this.selectedIdx,1)
    },
    onSlideChange () {
      console.log('slide change');
    },
    onSwiper (swiper) {
      console.log(swiper)
    },
    slideChange() {
      console.log('fuck you')
    },
  },

  async beforeMount() {
   await this.viewMultipleItems(this.category,0,9)
   await this.viewMultipleItems(this.category,1,9)
   await this.viewMultipleItems(this.category,2,9)
  },
}
</script>

<style>
.imgI {
  margin-top: 10px;
  max-height: 100px;
  max-width: 100px;
}

.selection {
  margin: 0 10px;
}

.selectionBox {
  display: flex;
  margin: 10px;
}

.wrapperI {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 5px;
  /* grid-auto-rows: 150px; */
}

#selectB {
  background-color: #a4a4a4;
  color: white;
  padding: 6px 15px;
  /* border: 1px solid black; */
  border-radius: 10px;
}

#deleteB {
  padding: 6px 0px;
  margin-left: -2px;
}
</style>


