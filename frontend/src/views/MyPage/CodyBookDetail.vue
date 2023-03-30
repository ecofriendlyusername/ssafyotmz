<template>
<div>
    <div>
      내 코디북 페이지
    </div>
    <hr>    
    <div id="item">
        <img :src="env+selected.imageId" width="100"/>
        <div>{{ this.selected.name }}</div>
        <div>{{ this.selected.comment }}</div>
    </div>
    <button @click="deleteItemMatchWith()">삭제</button>
    <router-link to='/MyPage'>마이페이지</router-link>
</div>
</template>
  
<script>
import axios from 'axios'
export default {
name:'CodybookDetail',
props: ['selected'],
data () {
    return {
      env: process.env.VUE_APP_IMG,
      image: "",
      name: "",
      comment: "",
      Auth: this.$store.state.Auth
    }
},
methods:{
    async deleteItemMatch(id) {
      await axios.delete(process.env.VUE_APP_CODYBOOK + '/' + this.selected.id, {
        headers: {
          'Authorization' : this.Auth.accessToken
        }
        }).then((res) => {
          return res
        }).catch((e) => {
          console.log(e)
        })
      }
    },
    deleteItemMatch() {
        axios.delete(process.env.VUE_APP_CODYBOOK + '/' + this.selected.id, {
          headers: {
            'Authorization' : this.Auth.accessToken
          }
        }).then((res) => {
          this.$emit('deleted')
          return res
        }).catch((e) => {
          console.log(e)
        })
        
      },

//   async beforeMount() {
//     this.viewItemMatch(this.selected)
//   },
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