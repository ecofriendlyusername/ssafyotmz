<template>
<div id="x">
  <img v-if="isLoading" src="../assets/img/loading.gif" class="loadingImg">
  <div v-if="isLoading">
    배경을 제거 중입니다
  </div>

  
  <div v-show="!isLoading">
    <div v-if="!haveImage">
      <div style="font-size:95%; font-weight: bold; margin-top: 5px;">
        ① → ② 순서대로 작업을 수행하세요.
      </div>
      <div id="selecBtn">
        <label for="imagefile">① 옷 이미지 선택하기</label><input type="file" id="imagefile" name="imagefile" @change="fileUpload" >
      </div>
      &nbsp;&nbsp;
      <div id="selecBtn" @mousedown="processImageAndCreateItem()">② 옷 추출하기</div>
      <br>
    </div>
    
    <div v-if="!haveImage" id="closetbox">
      <p v-if="!userUploadedImgExist" id="notImg">선택된 옷 미리보기</p>
      <img v-if="userUploadedImgExist" :src="userUploadedImg" class="uploadedImage" >
    </div>

    <div v-if="haveImage">
      <div style="font-size:95%; padding:2px; font-weight: bold; margin-top: 5px;">
        <p style="margin-top:0px;">
          배경이 제거되었습니다!
        </p>
        <p style="margin-top:-13px;">
          정보 입력을 완료하고 옷장에 등록해 보세요.
        </p>
      </div>
      <img :src="processedImageStr" class="uploadedImage">
      <br>
    </div>
    <br>
    

    <div style="width:90%; display:inline-block; margin: 15px 0px;">
      <div style="display:flex;">
        <div style="width:40%; font-weight:bold; font-size: 100%;">
          <label for="text">ㆍ 옷 이름</label>
        </div>
        <div style="width:60%">
          <input type="text" id="name" name="name">
        </div>
      </div>
    </div>
    

    <div style="width:90%; display:inline-block;">
      <div style="display:flex;">
        <div style="width:40%; font-weight:bold; font-size: 100%;">
          ㆍ 종류 선택
        </div>
        <div style="width:60%">
          <div v-for="(category,index) in categories" class="itemCat" :class="{ 'uploadedItemCat': curCategory === category }" @click="changeCategory(category)">{{categoriesKorean[index]}}</div>
        </div>
      </div>
    </div>

    <br><br>
      <div v-if="haveImage">
        <button id="selecBtn" @click="createItemWithProcessedImage()">등록하기</button> &nbsp;&nbsp;
        <button id="selecBtn" @click="no()">사진 다시 올리기</button>
      </div>
    </div>
  </div>
  <br> <br>
</template>

<script>
import axios from 'axios'
import headerComponent from "@/components/molecules/header.vue";
import footerComponent from "@/components/molecules/footer.vue";

export default {
  name:'AddClothesView',
  components: {
    headerComponent,
    footerComponent
  },
  data () {
    return {
      file: null,
      imagefile: null,
      result: null,
      style: null,
      cropped: null,
      isLoading: false,
      processedImageStr: "",
      userUploadedImg: null,
      categories: ['outer','upper','lower','dress','etc'],
      categoriesKorean: ['아우터','상의','하의','원피스','ETC'],
      curCategory: 'outer',
      userUploadedImgExist: false,
      haveImage: false,
      Auth: this.$store.state.Auth,

    }
  },
  methods: {
    fileUpload(e) {
      var reader = new FileReader();
      reader.onload = (e) => {
        var img = new Image();
        img.onload = () => {
          var thumbFile = (_IMG) => {
            var canvas = document.createElement("canvas");
            var newWidthAndHeight = this.calculateAspectRatioFit(_IMG.width,_IMG.height, 360, 480)
            var width = newWidthAndHeight.width;
            var height = newWidthAndHeight.height;
            canvas.width = width;
            canvas.height = height;
            canvas.getContext("2d").drawImage(_IMG, 0, 0, width, height);

            var dataURL = canvas.toDataURL("image/jpg");
            var byteString = atob(dataURL.split(',')[1]);
            var mimeString = dataURL.split(',')[0].split(':')[1].split(';')[0];
            var ab = new ArrayBuffer(byteString.length);
            var ia = new Uint8Array(ab);
            for (var i = 0; i < byteString.length; i++) {
              ia[i] = byteString.charCodeAt(i);
            }
            var tmpThumbFile = new Blob([ab], {type: mimeString});
            return tmpThumbFile;
          };
          var file = thumbFile(img);
          this.file = file;
          this.userUploadedImg = URL.createObjectURL(file);
          this.userUploadedImgExist = true
        };
        img.onerror = () => {
        };
        img.src = reader.result;
      };
      reader.readAsDataURL(event.target.files[0]);
    },
    calculateAspectRatioFit (srcWidth, srcHeight, maxWidth, maxHeight) {
      var ratio = [maxWidth / srcWidth, maxHeight / srcHeight ];
      ratio = Math.min(ratio[0], ratio[1]);
      return { width:srcWidth*ratio, height:srcHeight*ratio };
    },
    changeCategory(category) {
      this.curCategory = category;
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
      await axios.post(process.env.VUE_APP_AI_URL + '/style', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then((response) => {
        this.style = response.data
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
      this.userUploadedImg = null
      this.userUploadedImgExist = false
      formDataAI.append('image', this.file);
      await this.removeBackground(formDataAI)
    },
    async createItemWithProcessedImage() {
      const formData = new FormData();
      formData.append('imagefile',this.processedImage)
      var styleJSON = JSON.stringify(this.style)
      const itemJson = {
        "name" : document.getElementById('name').value,
        "color":"color",
        "styleVector" : styleJSON,
      }
      const jsonString = JSON.stringify(itemJson);
      const itemBlob = new Blob([jsonString], {
      type: 'application/json'});
      formData.append('item',itemBlob);
      formData.append('category',this.curCategory)
      formData.append('style',this.style["1"]["style"])
      document.getElementById('name').value = ''
      this.createItem(formData)
      .then((res) => {
        return res
      }).catch((e) => {
        return e
      })

    },
    async removeBackground(formData) {
      var a = this
      await axios.post(process.env.VUE_APP_AI_URL + '/remove', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        var len = response.data.image.body.length
        console.log(response.data)
        var str = response.data.image.body.substring(1,len-1)
        a.processedImageStr = 'data:image/png;base64,' + str
        const style = response.data.style
        this.processedImage = this.dataURLtoFile(a.processedImageStr,'processedImage.jpeg')
        this.style = style
        this.isLoading = false
        this.haveImage = true
        const gate = response.data.category
        // ['outer','upper','lower','dress','etc']
        if (gate == 'bag' || gate ==  'shoes' || gate ==  'hat') {
          this.curCategory = 'etc'
        } else {
          this.curCategory = gate
        }
        return response
      })
      .catch(e => {
        this.isLoading = false
        alert('이미지 처리에 문제가 있었습니다 다시 등록해주세요')
        return e
      })
    },
    async createItem(formData) {
      axios.post(process.env.VUE_APP_API_URL + '/item', formData, {
        headers: {
          'Content-Type' : 'multipart/form-data',
          'Authorization' : this.Auth.accessToken,
        }
      }).then((res) => {
        alert('옷이 등록되었습니다')
        this.haveImage = false
        this.processedImageStr = ""
        return res
      }).catch((e) => {
        return e
      })
      
    },
    deleteItem() {
      axios.delete(process.env.VUE_APP_API_URL + '/items/' + id, {
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
    display: inline-block;
    background-color: #F5F5F7;
    width: 90%;
    margin: 10px;
    font-size: 100%;
  }

  .uploadedImage {
    max-width: 280px;
    max-height: 250px;
  }

.loadingImg {
  width: 70%;
}

#x {
  margin-top: 10px;
  width: 100%;
  height: 100%;
  overflow-y: scroll;
  overflow-x: hidden; 
  position: relative;
}

#closetbox {
  display: inline-block;
  width: 90%;
  height: 250px;
  background-color: #F5F5F7;
}

#selecBtn {
  display: inline-block;
  width: 35%;
  font-size: 80%;
  padding: 6px;
  font-weight: bold;
  border-radius: 10px;
  border: 1px solid black;
  background-color: white;
  box-shadow: 1px 1px 1px 1px gray;
  margin: 10px 0px;
}

#notImg {
  height: 220px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: gray;
}

#name {
  margin-top: -20px;
  margin-bottom: 5px;
}


</style>