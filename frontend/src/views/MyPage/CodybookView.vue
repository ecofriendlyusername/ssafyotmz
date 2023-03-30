<template>
<div>
  <div>
    내 코디북 페이지
  </div>
  <hr>
  <!-- 스타일 별 코디북 -->
  <div id="temp">
    <SwipeBox ref="myswipe" @onChange="mySwipeChanged" speed="150">
      <div style="width: 350px; height: 250px; border: 1px solid black">
        <div v-for="page in pages">
          <div class="row">
            <div v-if="page[0]" class="column">
              <img :src="env+page[0].imageId" @click="clicked(page[0])" width="50" height="60" />
              <figcaption>{{ page[0].name }}</figcaption>
            </div>
            <div v-if="page[1]" class="column">
              <img :src="env+page[1].imageId" @click="clicked(page[1])" width="50" height="60" />
              <figcaption>{{ page[1].name }}</figcaption>
            </div>
          </div>
          <div class="row">
            <div v-if="page[2]" class="column">
              <img :src="env+page[3].imageId" @click="clicked(page[2])" width="50" height="60" />
              <figcaption>{{ page[2].name }}</figcaption>
            </div>
            <div v-if="page[3]" class="column">
              <img :src="env+page[3].imageId" @click="clicked(page[3])" width="50" height="60" />
              <figcaption>{{ page[3].name }}</figcaption>
            </div>
          </div>
        </div>
      </div>
    </SwipeBox>
    <Teleport to="body">
  <div v-if="open" class="modal">
    <CodyBookDetail :selected="selected" @close="closeModal">your content...</CodyBookDetail>
    <button @click="open = false">Close</button>
  </div>
</Teleport>
  </div>
  <hr>
  <!-- <router-link to='/Codybook'>코디북 만들기</router-link> | -->
  <router-link to='/MyPage'>마이페이지</router-link>
  <button @click="select()">select</button>
</div>
</template>

<script>
import { register } from 'swiper/element/bundle';
import CodyBookDetail from './CodyBookDetail.vue'
import SwipeBox from '@shopid/vue3-swipe-box';
import axios from 'axios'
register();
export default {
  name:'MyPageCodybookView',
  components: {
    SwipeBox,
    CodyBookDetail,
  },
  data () {
    return {
      pages : [],
      selected: null,
      imagesToDelete: [],
      open: false,
      env: process.env.VUE_APP_IMG,
      TOKEN : 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NzYyMDkyLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk3ODM2OTIsInN1YiI6IjEiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjc5NzYyMDkyfQ.ys-nl4gzDzXXAc_USiH6w7OZf5fI1ESj6iILLiJwY5s',
    }
  },
  methods:{
    viewMultipleItemMatches(page,size) {
        var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgwMDY1NTkzLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAwODcxOTMsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgwMDY1NTkzfQ.qM3dCn1QO8dNV2fC05Q0hT-a7GExr_5kI3oY9UkBs8g'
        axios.get(process.env.VUE_APP_CODYBOOKS+`?page=${page}&size=${size}`, {
          headers: {
            'Authorization' : TOKEN
          }
        }).then((res) => {
          if (res.data.content.length !== 0) {
            this.pages.push(res.data.content)
          }
          return res
        }).catch((e) => {
          console.log(e)
          return e
        })
      },
      imagesToDelete() {

      },
      mySwipeChanged (index) {
        if (index === this.pages.length - 1) {                
          this.viewMultipleItemMatches(index,4)
        }
        console.log('index' + index);
      },
      clicked(id) {
        this.selected = id
        this.open = true
      },
      closeModal() {
        this.isShow = false
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
      createItemMatch(formData) {
        // var TOKEN = this.Auth.accessToken
        var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgwMDQ5NTI2LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAwNzExMjYsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgwMDQ5NTI2fQ.TD-0mZDiVziNMyuKsGC6tDwln7kVy9wLOiqKt4M2cIY'
        axios.post(process.env.VUE_APP_CODYBOOK,formData, {
          headers: {
            'Content-Type' : 'multipart/form-data',
            'Authorization' : TOKEN,
          }
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
    createItemMatch(formData) {
      // var TOKEN = this.Auth.accessToken
      var TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgwMDQ5NTI2LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAwNzExMjYsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgwMDQ5NTI2fQ.TD-0mZDiVziNMyuKsGC6tDwln7kVy9wLOiqKt4M2cIY'
      axios.post(process.env.VUE_APP_CODYBOOK,formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : TOKEN,
        }
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
  },
  async beforeMount() {
    this.viewMultipleItemMatches(0,4)
    this.viewMultipleItemMatches(1,4)
  },
}
</script>
<style>
.modal {
  position: fixed;
  z-index: 999;
  top: 20%;
  left: 50%;
  width: 300px;
  margin-left: -150px;
  background-color: bisque;
}
</style>