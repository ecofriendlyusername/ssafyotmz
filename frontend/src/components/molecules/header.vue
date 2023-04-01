<template>
  <header>
    <div>
        <p class="logo">O't MZ</p>
        <!-- <img src="@/assets/img/logo.png" style="width:65px;"> -->
    </div>

    
    <div v-if="login['memberId']" id="Logout" v-on:click="logOut()">
      LOGOUT
    </div>

  </header>


</template>

<script>

export default {
  name:'headerComponent',
  computed: {
    login() {
      return this.$store.state.Auth
    }
  },
  methods: {
    logOut() {
      this.$store.commit('setAuth', {
        accessToken: null,
        refreshToken: null,
        memberId: null,
        nickname: null,
        profileImagePath: null,
      });
      sessionStorage.removeItem('vuex')
    },
  },
  watch: {
    login() {
      this.$router.push('/')
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
  background-color: rgb(255, 255, 255);
  border-bottom: rgb(220, 220, 220) solid 0.1px;
}

.logo {
  font-weight: bold;
  font-size: 19px;
  display: flex; 
  align-items: center; 
  justify-content: center;
  color: rgb(0, 0, 0);
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

#Logout {
  color: white;
  font-size: 12px;
  font-weight: bold;
  color: black;
}



</style>