<template>
  <div id="closetview">
    <div class="categories">
      <div v-for="(category,index) in categories" class="category" :class="{ 'selected': curCategory === category }" @click="changeCategory(category)">{{categoriesKorean[index]}}</div>
    </div>
    <ItemView :key="curCategory" :category="curCategory"></ItemView>
</div>
<router-link to='/AddClothes'>옷 추가하기</router-link>
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
  .categories {
    display: flex;
    margin: 10px;
  }

  .category {
    margin: 0 5px;
  }

  .selected {
    background-color: black;
    color: white;
  }
</style>


