<template>
<div>
  <div style="list-style-type:none;">
    <li v-for="(category,index) in categories" @click="changeCategory(category)">{{ categoriesKorean[index] }}</li>
    <!-- <div style="width: 350px; height: 250px; border: 1px solid black"> -->
      <ItemView :key="curCategory" :category="curCategory"></ItemView>
    <!-- </div> -->
  </div>
  <hr>
  <router-link to='/AddClothes'>옷 추가하기</router-link>
  <router-link to='/MyPage'>마이페이지</router-link>
</div>
</template>

<script>
import ItemView from './tempasset/ItemView.vue'
export default {
  name:'MyPageClosetView',
  components: {
    ItemView
  },
  data () {
    return {
      categories: ['outer','upper','lower','dress','etc'],
      categoriesKorean: ['아우터','상의','하의','원피스','ETC'],
      curCategory: 'outer',
    }
  },
  methods: {
    changeCategory(category) {
      this.curCategory = category;
    }
  },
}
</script>

<style>
</style>


