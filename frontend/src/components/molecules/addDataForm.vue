<template>
  <div>
    <div id="BackgroundCard">
      <div id="InnerCard">
        <img style="width:100%; border-radius: 20px; max-height: 325px; " :src="image" />
      </div>

      <p id="CardText">전신이 나온 사진을 선택해주세요</p>
      <label for="fileInput" class="file-label">
        <img src="@/assets/img/UploadBtn.png" id="UploadBtn">
        <input id="fileInput" type="file" name="image" @change="fileUpload">
      </label>
    </div>

    <img src="@/assets/img/StartBtn.png" id="StartBtn" v-on:click=uploadImage()>

  </div>
</template>

<script>
import { templateElement } from '@babel/types';
import axios from 'axios';
import watermark from 'watermarkjs';

export default {
  name: 'HelloWorld',
  data() {
    return {
      file: null,
      image: null,
    };
  },

  methods: {
    dataURLtoFile(dataurl, filename) {
      const arr = dataurl.split(',');
      const mime = arr[0].match(/:(.*?);/)[1];
      const bstr = atob(arr[1]);
      let n = bstr.length;
      const u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, {type: mime});
    },

    fileUpload(event) {
      this.file = event.target.files[0];
      this.image = URL.createObjectURL(this.file);
    },

    uploadImage() {
      // 예외처리

      if (this.file == null) {
        return;
      }
      // 폼 데이터 만들기
      const formData = new FormData();
      formData.append('imageFile', this.file);
      // api요청으로 이미지 분석하기

      axios.post(process.env.VUE_APP_AI_URL + '/style', formData, {
        headers: {
          'Content-Type': 'multipart/form-data' 
        }
      })
      // 결과 받아서 저장
      .then(response => {
        console.log(response.data.keys)
        if (!response.data.keys) {
          console.log('break!')
          this.$router.push('/Find')
        }
        else {
          // 검출 결과 저장
          this.result = response.data;
          formData.append('styleVector', this.result);
          formData.append('style', this.result['1']['style']);
        }
      })
      // 워터마크 찍기
      .then(() => {
        watermark([this.image, require('@/assets/img/watermark/logo.png')])
          .image(watermark.image.upperRight(0.5))
          .load([require('@/assets/img/watermark/MyStyleStreet.png')])
          .image(watermark.image.lowerLeft(0.5))
          .then((img) => {
            const watermark = this.dataURLtoFile(img.src, 'temp.jpg')
            this.file = watermark
          })      
          .then(() => {
            this.$store.commit('SET_RESULT', {img_path: this.file, data: this.result['data'], imageId: null, check: this.result['check']})
          });
      })
      // 상태에 저장하고 라우트 이동

      .then(() => {
        this.$router.push('/Find/loading');
      })
      .catch(error => {
        console.log(error);
        this.$router.push('/Find')
      });
    },
  }
}
</script>

<style>
#BackgroundCard {
  display: inline-block;
  margin-top: 20px;
  width: 85%;
  height: 580px;
  /* background-color: red; */
  background-image: url("@/assets/img/UploadCard.png");
  background-size: 98% 88%;
  background-repeat: no-repeat;
}



@font-face {
    font-family: 'NanumSquareNeo-Variable';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/NanumSquareNeo-Variable.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}

#CardText {
  display:inline-block;
  margin-top: 5%;
  margin-left: 4%;
  font-size: 87%;
  font-family: 'NanumSquareNeo-Variable';
  font-weight: bold;
}

input[type="file"] {
  opacity: 0;
  position: absolute;
  z-index: -1;
}

button {
  margin-top: -25px;
  margin-left: 10px;
}

#InnerCard {
  display: flex;
  /* display: inline-block; */
  /* align-content: center; */
  object-fit: cover;
  margin-top: 17%;
  margin-left: 12%;
  width:80%;
  height: 325px;
  /* height: 50%; */

  background-color: white;
  border: 1px solid #a7a7a7;
  border-radius: 20px;
}

#UploadBtn {
  display:inline-block;
  margin-top: -5px;
  margin-left: 4%;
  width: 58%; 
}

#StartBtn {
  width: 45%;
  margin-left: 4%;
  margin-top: -15%;
}



</style>
