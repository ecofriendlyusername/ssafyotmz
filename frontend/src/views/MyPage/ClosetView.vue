<template>
  <!-- 내 옷장 -->
  <!-- 목차 -->
  <hr>
  <!-- 옷 목록 -->
  <!-- 그리드, 가로 2~3 정도로 -->
  <ul style="list-style-type:none;">
    <li v-for="(category,index) in categories" @click="changeCategory(category)">{{ categoriesKorean[index] }}</li>
    <ItemView :key="curCategory" :category="curCategory"></ItemView>
  </ul>
  <hr>
  <router-link to='/AddClothes'>옷 추가하기</router-link> | 
  <!-- <router-link to='/Codybook'>코디북 만들기</router-link> | -->
  <router-link to='/MyPage'>마이페이지</router-link>
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


