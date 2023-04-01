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
  
  
  