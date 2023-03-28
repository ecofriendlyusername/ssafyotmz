<template>
  <div id="temp">
    <SwipeBox ref="myswipe" @onChange="mySwipeChanged" speed="150">
        <div style="width: 350px; height: 250px; border: 1px solid black">
          <div v-for="page in pages">
            <div class="row">
              <div class="column">
                <img v-if="page[0]" :src="env+page[0].imageId" @click="clicked(page[0].id)" width="50" height="60" />
              </div>
              <div class="column">
                <img v-if="page[1]" :src="env+page[1].imageId" @click="clicked(page[1].id)" width="50" height="60" />
              </div>
              <div class="column">
                <img v-if="page[2]" :src="env+page[2].imageId" @click="clicked(page[2].id)" width="50" height="60" />
              </div>
            </div>

            <div class="row">
              <div class="column">
                <img v-if="page[3]" :src="env+page[3].imageId" @click="clicked(page[3].id)" width="50" height="60" />
              </div>
              <div class="column">
                <img v-if="page[4]" :src="env+page[4].imageId" @click="clicked(page[4].id)" width="50" height="60" />
              </div>
              <div class="column">
                <img v-if="page[5]" :src="env+page[5].imageId" @click="clicked(page[5].id)" width="50" height="60" />
              </div>
            </div>

            <div class="row">
              <div class="column">
                <img v-if="page[6]" :src="env+page[6].imageId" @click="clicked(page[6].id)" width="50" height="60" />
              </div>
              <div class="column">
                <img v-if="page[7]" :src="env+page[7].imageId" @click="clicked(page[7].id)" width="50" height="60" />
              </div>
              <div class="column">
                <img v-if="page[8]" :src="env+page[8].imageId" @click="clicked(page[8].id)" width="50" height="60" />
              </div>
            </div>
          </div>
          </div>
      </SwipeBox>
  </div>
</template>


<script>
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
      currentIdx: 0,
      lastPage: false,
      env: process.env.VUE_APP_IMG,
      TOKEN : 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NzYyMDkyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk3ODM2OTIsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5NzYyMDkyfQ.ys-nl4gzDzXXAc_USiH6w7OZf5fI1ESj6iILLiJwY5s',
    }
  },
  props: ['category'],
  methods: {
    viewMultipleItems(category,page,size) {
      var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5OTYzODYwLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk5ODU0NjAsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5OTYzODYwfQ.xOIScULrsWYRp_a3aTkZqbhfY1sYpLO2Q7qXfaIMJJ0'
      axios.get(process.env.VUE_APP_ITEMS+`/${category}?page=${page}&size=${size}`, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
        if (res.data.content.length !== 0) {
          this.pages.push(res.data.content)
        }
      }).catch((e) => {
        console.log(e)
      })
    },
    mySwipeChanged (index) {
      if (index === this.pages.length - 1) {
        this.viewMultipleItems(this.category,index,9)
      }
      console.log('index' + index);
    },
    clicked() {
    }
  },
  async beforeMount() {
   this.viewMultipleItems(this.category,0,9)
   this.viewMultipleItems(this.category,1,9)
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
</style>


