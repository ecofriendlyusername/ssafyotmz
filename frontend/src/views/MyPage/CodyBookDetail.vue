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
      TOKEN : 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NzYyMDkyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk3ODM2OTIsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5NzYyMDkyfQ.ys-nl4gzDzXXAc_USiH6w7OZf5fI1ESj6iILLiJwY5s',
    }
},
methods:{
    // viewItemMatch(id) {
    //   var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgwMDU3ODQyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAwNzk0NDIsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgwMDU3ODQyfQ.OuEmVBgDQi48U465MTEMZVwpN_mY8F-cjEQpxWe-Xn4'
    //   axios.get(process.env.VUE_APP_CODYBOOK + '/' + id, {
    //     headers: {
    //       'Authorization' : TOKEN
    //     }
    //   }).then((res) => {
    //       this.image = "data:image/jpg;base64,"+res.data.image
    //       this.name = res.data.name
    //       this.comment = res.data.comment
    //       return res
    //   }).catch((e) => {
    //     console.log(e)
    //     return e
    //   })
    // },
    deleteItemMatch() {
      var TOKEN = this.Auth.accessToken
        axios.delete(process.env.VUE_APP_CODYBOOK + '/' + id, {
          headers: {
            'Authorization' : TOKEN
          }
        }).then((res) => {
          console.log(res)
        }).catch((e) => {
          console.log(e)
        })
      },
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