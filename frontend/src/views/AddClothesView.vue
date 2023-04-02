<template>
<div id="AC">
  <div>
    옷 추가하기
  </div>
  <hr>
  <img v-if="isLoading" src="../assets/img/loading.gif" class="loadingImg">

<div v-show="!isLoading">
  <img v-if="userUploadedImgExist" :src="userUploadedImg" class="uploadedImage" >
  <br>
    <label for="imagefile">옷 이미지 선택하기</label><input type="file" id="imagefile" name="imagefile" @change="fileUpload" >
    <br>
<!-- <img v-if="isLoading" :src="loadingImg"> -->
<div v-if="haveImage">
  <img :src="processedImageStr" class="uploadedImage">
  <div>이 이미지로 등록하시겠습니까?</div>
  <button class="buttons" @click="createItemWithProcessedImage()">등록하기</button>
  <button class="buttons" @click="no()">사진 다시 올리기</button>
</div>
<label for="text">이름</label><input type="text" id="name" name="name">
    <div class="cats">
      <div v-for="(category,index) in categories" class="itemCat" :class="{ 'uploadedItemCat': curCategory === category }" @click="changeCategory(category)">{{categoriesKorean[index]}}</div>
    </div> 
    <br>
    <br>
    <input type="file"
       id="avatar" name="avatar"
       accept="image/png, image/jpeg">
       <br>
  <hr>
  <!-- <img src="@/assets/img/StartBtn.png" id="StartBtn" v-on:click=processImageAndCreateItem() @touchstart="processImageAndCreateItem()" > -->
  <div @mousedown="processImageAndCreateItem()">이미지 업로드</div>
</div>
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
      userUploadedImg: null,
      loadingImg: "https://upload.wikimedia.org/wikipedia/commons/b/b9/Youtube_loading_symbol_1_(wobbly).gif",
      categories: ['outer','upper','lower','dress','etc'],
      categoriesKorean: ['아우터','상의','하의','원피스','ETC'],
      curCategory: 'outer',
      userUploadedImgExist: false,
      processedImageStr: "",
      haveImage: false,
      Auth: this.$store.state.Auth,
    }
  },
  methods: {
    fileUpload(event) {
      if (event.target.files.length == 0) return 
      this.file = event.target.files[0];
      this.userUploadedImg = URL.createObjectURL(this.file);
      this.userUploadedImgExist = true
    },
    no() {
      this.haveImage = false
      this.processedImageStr = ""    
    },
    changeCategory(category) {
      this.curCategory = category;
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
      console.log(this.isLoading + " <- this.isLoading")
      const formDataAI = new FormData();
      this.userUploadedImg = null
      this.userUploadedImgExist = false
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
      formData.append('category',this.curCategory)
      await this.createItem(formData)
      .then((res) => {
        this.haveImage = false
        this.processedImageStr = ""
        return res
      }).catch((e) => {
        return e
      })
    },
    async removeBackground(formData) {
      var a = this
      await axios.post(process.env.VUE_APP_AI_REMOVE, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        var len = response.data.image.body.length
        var str = response.data.image.body.substring(1,len-1)
        console.log(str)
        a.processedImageStr = 'data:image/png;base64,' + str
        const style = response.data.style
        this.processedImage = this.dataURLtoFile(a.processedImageStr,'processedImage.jpeg')
        this.style = style
        this.isLoading = false
        this.haveImage = true
        return response
      })
      .catch(e => {
        this.isLoading = false
        console.log('failed')
        alert('이미지 처리에 문제가 있었습니다 다시 등록해주세요')
        return e
      })
    },
    async createItem(formData) {
      axios.post(process.env.VUE_APP_ITEM,formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : this.Auth.accessToken,
        }
      }).then((res) => {
        return res
      }).catch((e) => {
        return e
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
.buttons {
  background-color: blanchedalmond;
  border: none;
}

input {
        border-top-style: hidden;
        border-right-style: hidden;
        border-left-style: hidden;
        border-bottom-style: groove;
        border-bottom: 1px solid;
      }

      select {
        border-top-style: hidden;
        border-right-style: hidden;
        border-left-style: hidden;
        border-bottom-style: groove;
        border-bottom: 1px solid;
      }

      select option {
  display:inline-block;
}

.uploadedItemCat {
    background-color: grey;
    color: white;
  }

  .itemCat {
    margin: 0 12px;
  }

  .cats {
    /* display: flex; */
    margin: 10px;
  }

  .uploadedImage {
    width: 100%;
    max-width: 280px;
  }

.loadingImg {
  width: 70%;
}
</style>