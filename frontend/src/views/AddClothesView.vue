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
  <img src="@/assets/img/StartBtn.png" id="StartBtn" v-on:click=createItemMatchWith()>
  <img :src="imageSrc" v-if="imageSrc"/>
  <!-- <img id="qr" :src="qrImg" /> -->
  <img src="http://localhost:8080/api/v1/images/1" />
  <!-- <router-link to='/'>메인페이지</router-link> -->
</div>
</template>

<script>
import axios from 'axios'
import { Buffer } from 'buffer';
export default {
  name:'AddClothesView',
  data () {
    return {
      file: null,
      image: null,
      name: "",
      imageSrc: null,
      qrImg: null,
      TOKEN : 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NzYyMDkyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk3ODM2OTIsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5NzYyMDkyfQ.ys-nl4gzDzXXAc_USiH6w7OZf5fI1ESj6iILLiJwY5s',
    }
  },
  methods: {
    fileUpload(event) {
      this.file = event.target.files[0];
      // this.image = URL.createObjectURL(this.file)
      this.name = document.getElementById('name').value
    },
    viewMultipleItemsWith() {
      this.viewMultipleItems('upper',2,3)
    },
    deleteMultipleItemsWith() {
      this.deleteMultipleItems([10,11,12,13])
    },
    createItemWith() {
      if (this.file == null) {
        return;
      }
      const formData = new FormData();
      formData.append('imagefile', this.file);
      const itemJson = {
        "name" : "hey",
        "category": "outer",
        "categoryVector" : "hey",
        "printVector" : "hey",
        "fabricVector" : "hey",
      }
      const jsonString = JSON.stringify(itemJson);
      const itemBlob = new Blob([jsonString], {
        type: 'application/json'
      });
      formData.append('item',itemBlob);
      formData.append('category','outer')
      this.createItem(formData)
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
    createItem(formData) {
      axios.post(process.env.VUE_APP_ITEM,formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : this.TOKEN,
        }
      })
    },
    
    viewItem(id) {
      axios.get(process.env.VUE_APP_ITEM + '/' + id, {
        headers: {
          'Authorization' : this.TOKEN
        }
      }).then((res) => {
        console.log(res)
        this.qrImg = "data:image/jpg;base64,"+res.data.image;
      }).catch((e) => {
        console.log(e)
      })
    },
    viewMultipleItems(category,page,size) {
      axios.get(process.env.VUE_APP_ITEMS+`/${category}?page=${page}&size=${size}`, {
        headers: {
          'Authorization' : this.TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },
    deleteItem() {
      axios.delete(process.env.VUE_APP_ITEMS + '/' + id, {
        headers: {
          'Authorization' : this.TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },
    deleteMultipleItems(array) {
      console.log(this.TOKEN);
      axios.delete(process.env.VUE_APP_ITEMS + `?ids=${array.join(',')}`, {
        headers: {
          'Authorization' : this.TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },



    createItemMatch(formData) {
      axios.post(process.env.VUE_APP_CODYBOOK,formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : this.TOKEN,
        }
      })
    },
    viewItemMatch(id) {
      axios.get(process.env.VUE_APP_CODYBOOK + '/' + id, {
        headers: {
          'Authorization' : this.TOKEN
        }
      }).then((res) => {
          this.qrImg = "data:image/jpg;base64,"+res.data.image;
      }).catch((e) => {
        console.log(e)
      })
    },
    viewMultipleItemMatches(category,page,size) {
      axios.get(process.env.VUE_APP_CODYBOOKS+`/${category}?page=${page}&size=${size}`, {
        headers: {
          'Authorization' : this.TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },
    deleteItemMatch() {
      axios.delete(process.env.VUE_APP_CODYBOOK + '/' + id, {
        headers: {
          'Authorization' : this.TOKEN
        }
      }).then((res) => {
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
    },
    deleteMultipleItemMatches(array) {
      console.log(this.TOKEN);
      axios.delete(process.env.VUE_APP_CODYBOOKS + `?ids=${array.join(',')}`, {
        headers: {
          'Authorization' : this.TOKEN
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