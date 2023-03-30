<template>
  <div :id="some">
    <SwipeBox ref="myswipe" @onChange="mySwipeChanged" speed="150">
      <div style="width: 350px; height: 250px; border: 1px solid black">
        <div v-for="i in Math.ceil(pages.length/9)">
          <div class="wrapper">
            <div v-for="(page,index) in pages.slice((i-1)*9,i*9)" class="grid-item">
              <img v-if="page" :src="env+page.imageId" @click="selectItem(i,index)" @touchstart="viewItem(page.id)" :id="page.id" class="imgs" />
            </div>
          </div>
        </div>
      </div>
    </SwipeBox>
    <button @click="selectItems()">select</button>
    <button v-if="selectMode" @click="deleteMultipleImagesWith">delete</button>
  </div>
</template>


<script>
// import { getCurrentInstance } from 'vue';
import { register } from 'swiper/element/bundle';
import SwipeBox from '@shopid/vue3-swipe-box';
import axios from 'axios'
register();
import 'swiper/css';

export default {
  name:'ItemView',
  components: {
    SwipeBox,
  },
  data () {
    return {
      pages : [],
      env: process.env.VUE_APP_IMG,
      itemsToRemove: [],
      selectMode : false,
      selectedIndices : [],
      TOKEN : 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NzYyMDkyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk3ODM2OTIsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5NzYyMDkyfQ.ys-nl4gzDzXXAc_USiH6w7OZf5fI1ESj6iILLiJwY5s',
    }
  },
  props: ['category'],
  methods: {
    selectItem(i,j) {
      var idx = (i-1)*9+j
      if (this.selectedIndices.includes(idx)) {
        document.getElementById(this.pages[idx].id).style.filter = 'saturate(1)'
        this.selectedIndices.remove(this.selectedIndices.indexOf(idx))
      } else {
        document.getElementById(this.pages[idx].id).style.filter = 'saturate(50%)'
        this.selectedIndices.push(idx)
      }
    },
    async deleteMultipleImagesWith() {
      await this.deleteMultipleItems(this.selectedIndices.map(x => this.pages[x].id))
      .then(() => {
        var itemsToRemove = []
        this.selectedIndices.sort()
        this.selectedIndices.reverse()
        for (var idx of this.selectedIndices) {
          itemsToRemove.push(this.pages[idx].id)
          document.getElementById(this.pages[idx].id).style.filter = 'saturate(1)'
          this.pages.splice(idx,1)
        }
      })
      .catch((e) => {
        return e
      })
      // const instance = getCurrentInstance();
      // instance.proxy.forceUpdate();
    },
    viewMultipleItems(category,page,size) {
      var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgwMTMzNjYxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAxNTUyNjEsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgwMTMzNjYxfQ.wQtYO7x5ZBFDNHYH875WbMXzAb8HcGH9zaR7gtIfWiI'
      axios.get(process.env.VUE_APP_ITEMS+`/${category}?page=${page}&size=${size}&sort=id,DESC`, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
        if (res.data.content.length !== 0) {
          for (var item of res.data.content) {
            this.pages.push(item)
          }
          // this.pages.push(res.data.content)
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
    viewItem() {
      console.log('do nothing yet')
    },
    selectItems() {
      this.selectedIndices = []
      this.selectMode = !this.selectMode;
    },
    async deleteMultipleItems(array) {
      var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgwMTMzNjYxLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAxNTUyNjEsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgwMTMzNjYxfQ.wQtYO7x5ZBFDNHYH875WbMXzAb8HcGH9zaR7gtIfWiI'
      console.log(this.TOKEN);
      axios.delete(process.env.VUE_APP_ITEMS + `?ids=${array.join(',')}`, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
        console.log(res)
        return res
      }).catch((e) => {
        console.log(e)
        return e
      })
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
.row {
  display: flex;
}

.column {
  flex: 33.33%;
  padding: 5px;
}

.imgs {
  width: 30%
}

.wrapper {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  grid-auto-rows: 100px;
}
</style>


