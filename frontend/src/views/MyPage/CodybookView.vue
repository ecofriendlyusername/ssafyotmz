<template>
<div>
  <div>
    내 코디북 페이지
  </div>
  <hr>
  <!-- 스타일 별 코디북 -->
  <div id="temp">
    <!-- <SwipeBox v-if="pages.length !== 0" ref="myswipe" @onChange="mySwipeChanged" speed="150">
      <div style="width: 350px; height: 250px; border: 1px solid black">
        <div v-for="i in Math.ceil(pages.length/4)">
          <div class="wrapperIT">
            <div v-for="(page,index) in pages.slice((i-1)*4,i*4)" class="grid-item">
              <img v-if="page" :src="env+page.imageId" @click="selectItemMatch(i,index)" @touchstart="viewItem(page.id)" :id="page.id" class="imgIT" />
            </div>
          </div>
        </div>
      </div>
    </SwipeBox> -->
    <swiper v-if="pages.length!==0" class="items"
    @activeIndexChange="mySwipeChanged" 
    :space-between="1"
    :loop="false"
    :slidesPerView="1"
  >
    <swiper-slide v-for="i in Math.ceil(pages.length/4)">
      <div class="wrapperIT">
        <div v-for="(page,index) in pages.slice((i-1)*4,i*4)" class="grid-item">
          <img v-if="page" :src="env+page.imageId" @click="selectItemMatch(i,index)" @mousedown="selectItemMatch(i,index)" :id="page.id" class="imgIT" />
        </div>
      </div>
    </swiper-slide>
  </swiper>
    <div v-if="pages.length===0">
      아직 코디북이 없습니다!
    </div>
    <Teleport to="body">
  <div v-if="modalOpen" class="modal">
    <CodyBookDetail :selected="selected" @close="closeModal" @deleted="deleteItemMatch()">your content...</CodyBookDetail>
    <button @click="modalOpen = false" @mousedown="modalOpen = false">Close</button>
  </div>
</Teleport>
  </div>
  <hr>
  <!-- <router-link to='/Codybook'>코디북 만들기</router-link> | -->
  <router-link to='/Codybook/solo'>코디북 만들러가기</router-link>
  <button v-if="pages.length!==0" @mousedown="selectItemMatches()">선택</button>
  <button v-if="selectMode" @mousedown="deleteSelectedItemMatches()">선택된 코디북 삭제</button>
</div>
</template>

<script>
import { register } from 'swiper/element/bundle';
import CodyBookDetail from './CodyBookDetail.vue'
import { Swiper, SwiperSlide } from 'swiper/vue';

// Import Swiper styles
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';

import axios from 'axios'
register();
export default {
  name:'MyPageCodybookView',
  components: {
    Swiper,
    SwiperSlide,
    CodyBookDetail,
  },
  data () {
    return {
      pages : [],
      selectedIdx: -1,
      selected: null,
      selectedIndices: [],
      Auth: this.$store.state.Auth,
      modalOpen: false,
      selectMode: false,
      env: process.env.VUE_APP_IMG,
    }
  },
  methods:{
    viewMultipleItemMatches(page,size) {
      console.log('going? : ' + this.Auth.accessToken)
        axios.get(process.env.VUE_APP_CODYBOOKS+`?page=${page}&size=${size}`, {
          headers: {
            'Authorization' : this.Auth.accessToken
          }
        }).then((res) => {
          if (res.data.content.length !== 0) {
            for (var item of res.data.content) {
              this.pages.push(item)
            }
          }
          return res
        }).catch((e) => {
          console.log(e)
          return e
        })
      },
      selectItemMatches() {
        document.querySelector('.imgIT').style.filter = 'saturate(1)'
        this.selectedIndices = []
        this.selectMode = !this.selectMode;
      },
      mySwipeChanged (index) {
        if (index === Math.ceil(this.pages.length/9)-1) {
          this.viewMultipleItemMatches(this.category,index+1,9)
        }
        console.log('hh')
      },
      clicked(itemMatch) {
        this.selected = itemMatch
        this.modalOpen = true
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
        axios.post(process.env.VUE_APP_CODYBOOK,formData, {
          headers: {
            'Content-Type' : 'multipart/form-data',
            'Authorization' : this.Auth.accessToken
          }
        })
      },
      deleteItemMatch() {
        this.modalOpen = false
        this.pages.splice(selectedIdx,1)
      },
      selectItemMatch(i,j) {
        var idx = (i-1)*4+j
        if (!this.selectMode) {
          this.selectedIdx = idx
          this.selected = this.pages[idx]
          this.modalOpen = true
          this.selectedIndices = []
          document.querySelector('.imgIT').style.filter = 'saturate(1)'
        } else {
          if (this.selectedIndices.includes(idx)) {
            document.getElementById(this.pages[idx].id).style.filter = 'saturate(1)'
            this.selectedIndices.splice(this.selectedIndices.indexOf(idx))
          } else {
            document.getElementById(this.pages[idx].id).style.filter = 'saturate(50%)'
            this.selectedIndices.push(idx)
          }
        }
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
      axios.post(process.env.VUE_APP_CODYBOOK,formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : this.Auth.accessToken
        }
      })
    },
    async deleteSelectedItemMatches() {
      var a = this
      await this.deleteMultipleItemMatches(this.selectedIndices.map(x => a.pages[x].id))
      .then(() => {
        var itemMatchesToRemove = []
        a.selectedIndices.sort()
        a.selectedIndices.reverse()
        for (var idx of a.selectedIndices) {
          itemMatchesToRemove.push(a.pages[idx].id)
          document.getElementById(a.pages[idx].id).style.filter = 'saturate(1)'
          a.pages.splice(idx,1)
        }
        this.selectMode = false
      })
      .catch((e) => {
        return e
      })
    },
    async deleteMultipleItemMatches(array) {
      await axios.delete(process.env.VUE_APP_CODYBOOKS + `?ids=${array.join(',')}`, {
        headers: {
          'Authorization' : this.Auth.accessToken
        }
      }).then((res) => {
        return res
      }).catch((e) => {
        return res
      })
    }
  },
  async beforeMount() {
    this.viewMultipleItemMatches(0,4)
    this.viewMultipleItemMatches(1,4)
    this.viewMultipleItemMatches(2,4)
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

.imgIT {
  width: 30%
}

.wrapperIT {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  grid-auto-rows: 100px;
}
</style>