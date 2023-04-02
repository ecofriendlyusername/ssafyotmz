<template>
<div>
  <div>
    옷 추가하기
  </div>
  <hr>
    <label for="imagefile">옷 이미지를 추가하세요</label><input type="file" id="imagefile" name="imagefile" @change="fileUpload">
    <br>
    <label for="text">옷 이름을 입력하세요</label><input type="text" id="name" name="name">
    <select id="categories">
  <option value="outer" selected>아우터</option>
  <option value="upper" selected="selected">상의</option>
  <option value="lower">하의</option>
  <option value="dress">원피스</option>
  <option value="etc">기타</option>
</select>
<img v-if="isLoading" :src="loadingImage">
<div v-if="haveImage">
  <img :src="processedImageStr">
  <div>이 이미지로 등록하시겠습니까?</div>
  <button @click="createItemWithProcessedImage()"></button>
  <button @click="no()"></button>
</div>
    <br>
    <br>
    <input type="file"
       id="avatar" name="avatar"
       accept="image/png, image/jpeg">
       <br>
  <hr>
  <img src="@/assets/img/StartBtn.png" id="StartBtn" v-on:click=processImageAndCreateItem() @touchstart="processImageAndCreateItem()" >
</div>
</template>

<script>
import axios from 'axios'

export default {
  name:'AddClothesView',
  data () {
    return {
      file: null,
      imagefile: null,
      result: null,
      style: null,
      cropped: null,
      isLoading: false,
      processedImageStr: "",
      loadingImage: "https://upload.wikimedia.org/wikipedia/commons/b/b9/Youtube_loading_symbol_1_(wobbly).gif",
      haveImage: false,
      Auth: this.$store.state.Auth,
    }
  },
  methods: {
    fileUpload(event) {
      this.file = event.target.files[0];
    },
    no() {
      this.haveImage = false
      this.processedImageStr = ""    
    },
    dataURLtoFile(dataurl, filename) {
      var arr = dataurl.split(','),
      mime = arr[0].match(/:(.*?);/)[1],
      bstr = window.atob(arr[1]),
      n = bstr.length,
      u8arr = new Uint8Array(n);
      while(n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, {type:mime})
    },
    viewMultipleItemsWith() {
      this.viewMultipleItems('upper',2,3)
    },
    async getStyle(formData) {
      console.log('sending request')
      await axios.post(process.env.VUE_APP_AI_STYLE, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then((response) => {
        this.style = response.data
        console.log('thisstyle ' + this.style)
        console.log('response data' + response.data)
        return response
      })
      .catch((e) => {
        console.log(e)
        return e
      })
    },
    async processImageAndCreateItem() {
      var a = this
      if (this.file == null) {
        alert('사진을 올려주세요!')
        return;
      }
      this.isLoading = true
      const formDataAI = new FormData();
      formDataAI.append('image', this.file);
      await this.removeBackground(formDataAI)
    },
    async createItemWithProcessedImage() {
      const formData = new FormData();
      formData.append('imagefile',this.processedImage)
      const itemJson = {
        "name" : document.getElementById('name').value,
        "color":"color",
        "styleVector" : JSON.stringify(this.style),
      }
      const jsonString = JSON.stringify(itemJson);
      const itemBlob = new Blob([jsonString], {
      type: 'application/json'});
      formData.append('item',itemBlob);
      formData.append('category',document.getElementById("categories").value)
      this.createItem(formData)
    },
    async removeBackground(formData) {
      var a = this
      await axios.post(process.env.VUE_APP_AI_REMOVE, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        a.processedImageStr = 'data:image/png;base64,' + response.data.image
        const style = response.data.style
        this.processedImage = this.dataURLtoFile(a.processedImageStr,'processedImage.jpeg')
        this.style = style
        this.isLoading = false
        this.haveImage = true
        return response
      })
      .catch(e => {
        this.isLoading = false
        alert('이미지 처리에 문제가 있었습니다 다시 등록해주세요')
        return e
      })
    },
    createItem(formData) {
      axios.post(process.env.VUE_APP_ITEM,formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : this.Auth.accessToken,
        }
      })
    },
    deleteItem() {
      axios.delete(process.env.VUE_APP_ITEMS + '/' + id, {
        headers: {
          'Authorization' : this.Auth.accessToken
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },
  }
}
</script>

<style>

</style>