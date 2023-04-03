<template>
  <div id="closetview">
    <div class="categories">
      <div v-for="(category,index) in categories" class="category" :class="{ 'selected': curCategory === category }" @click="changeCategory(category)">{{categoriesKorean[index]}}</div>
    </div>
    <ItemView :key="curCategory" :category="curCategory" id="CategoryDiv"></ItemView>
</div>

<router-link to='/AddClothes'>
  <div id="AddBtn">
    옷 추가하기
  </div>
</router-link>
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
      this.selec
      this.curCategory = category;
    }
  },
}
</script>

<style>
  .categories {
    display: flex;
    margin: 10px;
    background-color: #F5F5F7;
    padding: 10px;
  }

  .category {
    margin: 0 5px;
    border: 2px solid black;
    border-radius: 8px;
    padding: 5px;
    font-size: 90%;
  }

  .selected {
    background-color: black;
    color: white;
  }

  #CategoryDiv {
    display: inline-block;
    width: 94%;
    background-color: #F5F5F7;
    min-height: 120px;
    margin-bottom: 20px;
  }

  #AddBtn {
    background-color: black;
    color: white;
    display: inline-block;
    width: 30%;
    padding: 10px;
    border-radius: 10px;
    font-size:100%;
    margin-bottom: 20px;
  }
</style>


