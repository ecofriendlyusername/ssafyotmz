<template>
    <div>
        <div>
          내 옷
        </div>
        <hr>    
        <div id="item">
            <img :src="env+selected.imageId" width="100"/>
            <div>{{ this.selected.name }}</div>
            <div>{{ this.selected.comment }}</div>
        </div>
        <button @click="deleteItem()" @touchstart="deleteItem">삭제</button>
        <router-link to='/MyPage'>마이페이지</router-link>
    </div>
    </template>
      
    <script>
    import axios from 'axios'
    export default {
    name:'ItemDetail',
    props: ['selected'],
    data () {
        return {
          env: process.env.VUE_APP_IMG,
          image: "",
          name: "",
          category: "",
          TOKEN : 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NzYyMDkyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk3ODM2OTIsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5NzYyMDkyfQ.ys-nl4gzDzXXAc_USiH6w7OZf5fI1ESj6iILLiJwY5s',
        }
    },
    methods:{
      deleteItem() {
        var a = this
        var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgwMTUxMzM1LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAxNzI5MzUsInN1YiI6IjMiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgwMTUxMzM1fQ.chZHsP_gx-ZubEkPLtT3kvjDAXOEh-63DxSE_JZ2Id4'
        axios.delete(process.env.VUE_APP_ITEM + '/' + this.selected.id, {
          headers: {
            'Authorization' : TOKEN
          }
        }).then((res) => {
          a.$emit('deleted')
          return res
        }).catch((e) => {
          console.log(e)
        })
      },
    }
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