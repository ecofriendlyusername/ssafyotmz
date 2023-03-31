<template>
  <div>
    <SwipeBox v-if="pages.length !== 0" ref="myswipe" @onChange="mySwipeChanged" speed="150">
      <div style="width: 350px; height: 250px; border: 1px solid black">
        <div v-for="i in Math.ceil(pages.length/9)">
          <div class="wrapperI">
            <div v-for="(page,index) in pages.slice((i-1)*9,i*9)" class="grid-item">
              <img v-if="page" :src="env+page.imageId" @click="selectItem(i,index)" @touchend="selectItem(i,index)" :id="page.id" class="imgI" />
            </div>
          </div>
        </div>
      </div>
    </SwipeBox>
    <div v-if="pages.length===0" style="width: 350px; height: 250px; border: 1px solid black"></div>
    <button v-if="pages.length!==0" @click="selectItems()" @touchstart="selectItems()">select</button>
    <button v-if="selectMode" @click="deleteSelectedItems" @touchstart="deleteSelectedItems">delete</button>
    <div v-if="modalOpen" class="modal">
      <ItemDetail :selected="selected" @close="closeModal" @deleted="deleteItem()">your content...</ItemDetail>
      <button @click="modalOpen = false" @touchstart="modalOpen = false">Close</button>
    </div>
  </div>
</template>


<script>
// import { getCurrentInstance } from 'vue';
import { register } from 'swiper/element/bundle';
import SwipeBox from '@shopid/vue3-swipe-box';
import ItemDetail from './ItemDetail.vue'
import axios from 'axios'
register();
import 'swiper/css';

export default {
  name:'ItemView',
  components: {
    SwipeBox,
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
          console.log('contains')
          document.getElementById(this.pages[idx].id).style.filter = 'saturate(1)'
          this.selectedIndices.splice(this.selectedIndices.indexOf(idx))
        } else {
          console.log('contains not')
          document.getElementById(this.pages[idx].id).style.filter = 'saturate(50%)'
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
      })
      .catch((e) => {
        return e
      })
    },
    viewMultipleItems(category,page,size) {
      var a = this
      var member_id = 2;
      axios.get(process.env.VUE_APP_ITEMS+`/${member_id}/${category}?page=${page}&size=${size}&sort=id,DESC`).then((res) => {
        if (res.data.content.length !== 0) {
          for (var item of res.data.content) {
            a.pages.push(item)
          }
        }
      }).catch((e) => {
        console.log(e)
      })
    },
    mySwipeChanged (index) {
      if (index === Math.ceil(this.pages.length/9)-1) {
        this.viewMultipleItems(this.category,index+1,9)
      }
    },
    selectItems() {
      document.querySelector('.imgI').style.filter = 'saturate(1)'
      this.selectedIndices = []
      this.selectMode = !this.selectMode;
    },
    async deleteMultipleItems(array) {
      axios.delete(process.env.VUE_APP_ITEMS + `?ids=${array.join(',')}`, {
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
      console.log(this.modalOpen)
      this.pages.splice(this.selectedIdx,1)
    },
  },
  async beforeMount() {
   this.viewMultipleItems(this.category,0,9)
   this.viewMultipleItems(this.category,1,9)
   this.viewMultipleItems(this.category,2,9)
  },
}
</script>

<style>
.imgI {
  width: 40%
}

.wrapperI {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  grid-auto-rows: 100px;
}
</style>


