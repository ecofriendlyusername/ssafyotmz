<template>
  <header>
    <p v-on:click=displayToken()>aaa</p>
    <div>
      <router-link class="logo" to="/">
        <img src="@/assets/img/logo.png" style="width:65px;">
      </router-link>
    </div>
    <!-- <h1>제목</h1> -->
    <button class="hamburger" @click="toggleMenu">☰</button>
    <ul v-if="showMenu" class="menu">
      <li><a v-on:click="loginWithKakao">로그인</a></li>
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
    },
    loginWithKakao: function () {
      Kakao.init("4a558f01722d37955f2c7bb1c18170d0")
      Kakao.Auth.authorize({
        redirectUri: 'http://localhost:3000'
      })
    },
    displayToken() {
      var token = getCookie('authorize-access-token');
      if(token) {
        Kakao.Auth.setAccessToken(token);
        Kakao.Auth.getStatusInfo()
        .then(function(res) {
          if (res.status === 'connected') {
            document.getElementById('token-result').innerText
              = 'login success, token: ' + Kakao.Auth.getAccessToken();
          }
        })
        .catch(function(err) {
          Kakao.Auth.setAccessToken(null);
        });
      }
    },
    getCookie(name) {
      var parts = document.cookie.split(name + '=');
      if (parts.length === 2) { return parts[1].split(';')[0]; }
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
  background-color: black;
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
margin-top: 0px;
font-size: 30px;
background-color: transparent;
color: white;
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