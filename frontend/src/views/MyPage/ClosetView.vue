<template>
  <!-- 내 옷장 -->
  <!-- 목차 -->
  <ul>
    <li>상의</li>
    <li>하의</li>
    <li>아우터</li>
    <li>원피스</li>
    <li>ETC</li>
  </ul>
  <hr>
  <!-- 옷 목록 -->
  <!-- 그리드, 가로 2~3 정도로 -->
  <ul>
    <li>
      <img src="@/assets/img/logo.png" alt="">
    </li>
    <li>
      <img src="@/assets/img/logo.png" alt="">
    </li>
    <li>
      <img src="@/assets/img/logo.png" alt="">
    </li>
    <div v-for="category in categories" @click="changeCategory(category)">{{ category }}</div>
    <ItemView :key="curCategory" :category="curCategory"></ItemView>
    <img src="@/assets/img/StartBtn.png" id="StartBtn" v-on:click=viewItemsWith()>
  </ul>
  <hr>
  <router-link to='/AddClothes'>옷 추가하기</router-link> | 
  <router-link to='/Codybook'>코디북 만들기</router-link> |
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
      categories: ['outer','upper','lower','etc'],
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


