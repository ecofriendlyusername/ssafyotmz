<template>
<div>
  <div>
    옷 추가하기
  </div>
  <hr>
    <label for="imagefile">옷 이미지를 추가하세요</label><input type="file" id="imagefile" name="imagefile" @change="fileUpload">
    <br>
    <label for="text">옷 이름을 입력하세요</label><input type="text" id="name" name="name">
    <br>
    <br>
    <input type="file"
       id="avatar" name="avatar"
       accept="image/png, image/jpeg">
       <br>
  <hr>
  <img src="@/assets/img/StartBtn.png" id="StartBtn" v-on:click=processImageAndCreateItem()>
  <!-- <img :src="imageSrc" v-if="imageSrc"/> -->
  <!-- <img id="qr" :src="qrImg" /> -->
  <!-- <img src="http://localhost:8080/api/v1/images/1" /> -->
  <!-- <router-link to='/'>메인페이지</router-link> -->
</div>
</template>

<script>
import axios from 'axios'
// import { Buffer } from 'buffer';
// import { mapGetters } from 'vuex'

export default {
  name:'AddClothesView',
  data () {
    return {
      file: null,
      image: null,
      imagefile: null,
      name: "",
      imageSrc: null,
      qrImg: null,
      result: null,
      style: null,
      Auth: this.$store.state.Auth,
      TOKEN : 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NzYyMDkyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk3ODM2OTIsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5NzYyMDkyfQ.ys-nl4gzDzXXAc_USiH6w7OZf5fI1ESj6iILLiJwY5s',
    }
  },
  computed: {
    // mix the getters into computed with object spread operator
    // ...mapGetters([
    //   'getAuth',
    // ])
  },
  methods: {
    fileUpload(event) {
      this.file = event.target.files[0];
      // this.image = URL.createObjectURL(this.file)
      this.name = document.getElementById('name').value
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
    deleteMultipleItemsWith() {
      this.deleteMultipleItems([10,11,12,13])
    },
    getStyle(formData) {
      console.log('sending request')
      axios.post(process.env.VUE_APP_AI_STYLE, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        this.style = response.data
        return response
      })
      .catch(e => {
        console.log(e)
        return e
      })
    },
    processImageAndCreateItem() {
      if (this.file == null) {
        return;
      }
      const formDataAI = new FormData();
      formDataAI.append('image', this.file);
      console.log('file before :' + this.file)

      this.removeBackground(formDataAI)
      .then((res) => {
        const tempFormData = new FormData();
        tempFormData.append('imageFile',this.processedImage)
        this.getStyle(tempFormData)
        .then((res) => {
          const formData = new FormData();
          formData.append('imagefile', this.imagefile)
          const itemJson = {
            "name" : "hey",
            "color":"color",
            "styleVector" : JSON.stringify(this.style),
          }
          const jsonString = JSON.stringify(itemJson);
          const itemBlob = new Blob([jsonString], {
          type: 'application/json'});
          formData.append('item',itemBlob);
          formData.append('category','outer')
          this.createItem(formData)
          .then((res) => {
            console.log(res)
          })
          .catch((e) => {
            console.log(e)
          })
          return res
        })
        .catch((e) => {
          return e
        })
        return res
      })
      .catch((e) => {
        console.log(e)
        return e
      })
    },
    async removeBackground(formData) {
      await axios.post(process.env.VUE_APP_AI_REMOVE, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        const str = 'data:image/png;base64,' + response.data
        this.processedImage = this.dataURLtoFile(str,'processedImage.jpeg')
        return response
      })
      .catch(e => {
        console.log(e)
        return e
      })
    },
    createItem(formData) {
      var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5OTcxMzU5LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk5OTI5NTksInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5OTcxMzU5fQ.FTn1UVY5bJL3d7S-t-hHkPpPD4piMG4ZJDcUxfmVmDE'
      axios.post(process.env.VUE_APP_ITEM,formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : TOKEN,
        }
      })
    },
    createItemMatchWith() {
      if (this.file == null) {
        return;
      }
      const formData = new FormData();
      formData.append('imagefile', this.file);
      const itemJson = {
        "name" : "hey",
        "comment" : "comment",
      }
      const jsonString = JSON.stringify(itemJson);
      const itemBlob = new Blob([jsonString], {
        type: 'application/json'
      });
      formData.append('itemMatch',itemBlob);
      this.createItemMatch(formData)
    },    
    viewItem(id) {
      var TOKEN = this.Auth.accessToken
      axios.get(process.env.VUE_APP_ITEM + '/' + id, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
        console.log(res)
        this.qrImg = "data:image/jpg;base64,"+res.data.image;
      }).catch((e) => {
        console.log(e)
      })
    },
    deleteItem() {
      var TOKEN = this.Auth.accessToken
      axios.delete(process.env.VUE_APP_ITEMS + '/' + id, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },
    deleteMultipleItems(array) {
      var TOKEN = this.Auth.accessToken
      console.log(this.TOKEN);
      axios.delete(process.env.VUE_APP_ITEMS + `?ids=${array.join(',')}`, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },
    createItemMatch(formData) {
      var TOKEN = this.Auth.accessToken
      axios.post(process.env.VUE_APP_CODYBOOK,formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : TOKEN,
        }
      })
    },
    viewItemMatch(id) {
      var TOKEN = this.Auth.accessToken
      axios.get(process.env.VUE_APP_CODYBOOK + '/' + id, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
          this.qrImg = "data:image/jpg;base64,"+res.data.image;
      }).catch((e) => {
        console.log(e)
      })
    },
    viewMultipleItemMatches(category,page,size) {
      var TOKEN = this.Auth.accessToken
      axios.get(process.env.VUE_APP_CODYBOOKS+`/${category}?page=${page}&size=${size}`, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },
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
    deleteMultipleItemMatches(array) {
      var TOKEN = this.Auth.accessToken
      console.log(this.TOKEN);
      axios.delete(process.env.VUE_APP_CODYBOOKS + `?ids=${array.join(',')}`, {
        headers: {
          'Authorization' : TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    }
  }
}
</script>

<style>

</style>