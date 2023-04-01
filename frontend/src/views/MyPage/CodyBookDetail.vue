<template>
<div>
    <div>
      <div>{{ this.selected.name }}</div>
    </div>
    <div id="item">
        <img :src="env+selected.imageId" width="100"/>
        <div>{{ this.selected.comment }}</div>
    </div>
    <div class="selectionBox">
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