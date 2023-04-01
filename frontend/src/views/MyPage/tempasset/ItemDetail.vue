<template>
    <div>
        <div>
          <div>{{ this.selected.name }}</div>
        </div>
        <div id="item">
            <img :src="env+selected.imageId" width="100"/>
        </div>
      <div class="selectionBox">
        <div class="selection" @mousedown="deleteItem()">삭제</div>
        <div class="selection" @mousedown="$emit('close')">Close</div>
      </div>
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
    </style>