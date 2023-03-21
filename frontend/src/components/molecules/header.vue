<template>
  <header>
    <router-link class="logo" to="/">
      logo
    </router-link>
    <h1>제목</h1>
    <button class="hamburger" @click="toggleMenu">☰</button>
    <ul v-if="showMenu" class="menu">
      <li><router-link to='/login'>로그인</router-link></li>
      <li><router-link to='/MyPage'>마이 페이지</router-link></li>
      <li><router-link to='/Find'>내 스타일 찾기</router-link></li>
      <li><router-link to='/Codybook'>코디북 만들기</router-link></li>
      <li><router-link to='/Recom'>스타일 추천</router-link></li>
    </ul>
  </header>


</template>

<script>

export default {
    name:'headerComponent',
    data() {
    return {
      showMenu: false
    };
  },
  methods: {
    toggleMenu() {
      this.showMenu = !this.showMenu;
    }
  }
}
</script>

<style>
header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 60px;
  padding: 0 20px;
  background-color: #f0f0f0;
}

.logo {
  font-weight: bold;
  font-size: 24px;
  display: flex; 
  align-items: center; 
  justify-content: center;
}

h1 {
  font-size: 24px;
  margin: 0;
}

.hamburger {
font-size: 24px;
background-color: transparent;
border: none;
cursor: pointer;
}

.menu {
  position: absolute;
  top: 60px;
  right: 20px;
  background-color: #fff;
  width: 200px; /* 너비를 200px로 설정 */
  padding: 20px; /* 패딩을 20px로 설정 */
  border: 1px solid #ccc;
  list-style: none;
  text-align: right;
}

.menu li {
margin-bottom: 10px;
text-align: center;
}

.menu a {
color: #333;
text-decoration: none;
font-size: 18px;
padding: 10px;
display: block;
}

.menu a:hover {
background-color: #f0f0f0;
}



</style>