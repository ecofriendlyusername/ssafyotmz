<template>
<div id="codybookview">

  <br>
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
      <img src="@/assets/img/noresult.png" style="width:38%">
      <div>
        아직 생성된 코디북이 없습니다!
      </div>
    </div>
  
    <div class="selectionBox">
    <div class="selection" id="selectB" v-if="pages.length!==0" @mousedown="selectItemMatches()">선택</div>
    <div class="selection" id="deleteB" v-if="selectMode" @mousedown="deleteSelectedItemMatches">선택항목 삭제</div>
  </div>
  <div v-if="modalOpen" class="modal">
    <CodyBookDetail :selected="selected" @close="closeModal" @deleted="deleteItemMatch()">your content...</CodyBookDetail>
  </div>
  <!-- <router-link to='/Codybook'>코디북 만들기</router-link> | -->
  <router-link to='/Codybook/solo' style="text-decoration:none; color: black;">
    <div id="selecBtn">
      코디북 만들러가기
    </div>
  </router-link>
  <br>
  <router-link to='/Codybook/live' style="text-decoration:none; color: black;">
    <div id="selecBtn">
      함께 코디하기
    </div>
  </router-link>
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
    async viewMultipleItemMatches(page, size) {
        await axios.get(process.env.VUE_APP_API_URL+`/itemmatches?page=${page}&size=${size}&sort=id,DESC`, {
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
        if (this.selectMode) {
          this.selectedIndices.sort()
        this.selectedIndices.reverse()
        for (var idx of this.selectedIndices) {
          document.getElementById(this.pages[idx].id).style.filter = 'saturate(1)'
        }
          this.selectedIndices = []
          document.getElementById('selectB').style.backgroundColor = '#a4a4a4'
        } else {
          document.getElementById('selectB').style.backgroundColor = 'black'
        }
        this.selectMode = !this.selectMode
      },
      mySwipeChanged (swiper) {
        const index = swiper.realIndex;
        console.log('인덱스', index, this.pages);
        if (index === Math.ceil(this.pages.length/4)-1) {
          this.viewMultipleItemMatches(index+1, 4)
        }
      },
      clicked(itemMatch) {
        this.selected = itemMatch
        this.modalOpen = true
      },
      closeModal() {
        this.modalOpen = false
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
        axios.post(process.env.VUE_APP_API_URL + '/itemmatch',formData, {
          headers: {
            'Content-Type' : 'multipart/form-data',
            'Authorization' : this.Auth.accessToken
          }
        })
      },
      deleteItemMatch() {
        this.modalOpen = false
        this.pages.splice(this.selectedIdx,1)
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
      axios.post(process.env.VUE_APP_API_URL + '/itemmatch',formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : this.Auth.accessToken
        }
      })
    },
    async deleteSelectedItemMatches() {
      var a = this
      this.selectedIndices.sort()
      this.selectedIndices.reverse()
      await this.deleteMultipleItemMatches(this.selectedIndices.map(x => a.pages[x].id))
      .then(() => {
        // var itemMatchesToRemove = []
        for (var idx of a.selectedIndices) {
          // itemMatchesToRemove.push(a.pages[idx].id)
          document.getElementById(a.pages[idx].id).style.filter = 'saturate(1)'
          a.pages.splice(idx,1)
        }
        document.getElementById('selectB').style.backgroundColor = '#a4a4a4;'
        this.selectMode = false
      })
      .catch((e) => {
        return e
      })
    },
    async deleteMultipleItemMatches(array) {
      await axios.delete(process.env.VUE_APP_API_URL + `/itemmatches?ids=${array.join(',')}`, {
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
    await this.viewMultipleItemMatches(0,4)
    await this.viewMultipleItemMatches(1,4)
    await this.viewMultipleItemMatches(2,4)
  },
}
</script>
<style>
.codybookview {
  font-family: 'NanumSquareNeo-Variable';
}
.modal {
  position: fixed;
  z-index: 999;
  top: 20%;
  left: 50%;
  width: 300px;
  min-height: 250px;
  margin-left: -150px;
  background-color: white;
  border-radius: 10px;
  border-style: solid;
  border-color: #000000;
}

.imgIT {
  width: 80%
}

.wrapperIT {
  display: grid;
  grid-template-columns: repeat(2, 1fr);

  grid-auto-rows: 180px;
}

.selection {
  margin: 0 10px;
}

.selectionBox {
  display: flex;
  margin: 10px;
}

</style>