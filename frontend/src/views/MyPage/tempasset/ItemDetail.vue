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
          Auth: this.$store.state.Auth
        }
    },
    methods:{
      deleteItem() {
        var a = this
        axios.delete(process.env.VUE_APP_ITEM + '/' + this.selected.id, {
          headers: {
            'Authorization' : this.Auth.accessToken
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