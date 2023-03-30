<template>
</template>
  
  
<script>
import { register } from 'swiper/element/bundle';
import SwipeBox from '@shopid/vue3-swipe-box';
register();
import 'swiper/css';
export default {
  name:'ItemMatchView',
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
  methods: {
      
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
  
  
  