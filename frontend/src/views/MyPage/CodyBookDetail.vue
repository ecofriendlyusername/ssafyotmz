<template>
<div>
    <div style="margin:10px; font-weight:bold">
      <div>{{ this.selected.name }}</div>
    </div>
    <div id="item" style="margin:10px">
        <img :src="env+selected.imageId" width="200"/>
        <div style="margin:10px;">{{ this.selected.comment }}</div>
    </div>
    <div class="selectionBox" style="display: flex; justify-content: end;">
        <div class="selection" @mousedown="deleteItemMatch()">삭제</div>
        <div class="selection" @mousedown="$emit('close')">Close</div>
      </div>
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
      Auth: this.$store.state.Auth
    }
},
methods:{
    async deleteItemMatch() {
      await axios.delete(process.env.VUE_APP_CODYBOOK + '/' + this.selected.id, {
        headers: {
          'Authorization' : this.Auth.accessToken
        }
        }).then((res) => {
          this.$emit('deleted')
          return res
        }).catch((e) => {
          console.log(e)
        })
      }
    },
    // deleteItemMatch() {
    //     axios.delete(process.env.VUE_APP_CODYBOOK + '/' + this.selected.id, {
    //       headers: {
    //         'Authorization' : this.Auth.accessToken
    //       }
    //     }).then((res) => {
    //       this.$emit('deleted')
    //       return res
    //     }).catch((e) => {
    //       console.log(e)
    //     })
        
    // },
}
</script>
  
<style>
</style>