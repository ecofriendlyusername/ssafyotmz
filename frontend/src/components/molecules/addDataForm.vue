<template>
  <div>
    <div id="BackgroundCard">
      <div id="InnerCard">
        <img style="width:100%; border-radius: 20px; max-height: 325px; " :src="image" />
      </div>

      <p id="CardText">전신이 나온 사진을 선택해주세요</p>
      
      <img src="@/assets/img/UploadBtn.png" id="UploadBtn">
    </div>

    <img src="@/assets/img/StartBtn.png" id="StartBtn">



    <form @submit.prevent="uploadImage">
      <label for="cameraInput" class="file-label">파일 선택</label>
      <input id="cameraInput" type="file" name="image" @change="fileUpload"> |
      <label for="fileInput" class="file-label">파일 선택</label>
      <input id="fileInput" type="file" name="image" @change="fileUpload">



      <button type="submit">스타일 판별하기</button>
    </form>
    <router-link to='Find/result'>결과 보기</router-link>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'HelloWorld',
  data() {
    return {
      file: null,
      image: null,
    };
  },

  methods: {
    fileUpload(event) {
      this.file = event.target.files[0];
      this.image = URL.createObjectURL(this.file);
    },

    uploadImage() {
      if (this.file == null) {
        return;
      }
      const formData = new FormData();
      formData.append('image', this.file);
      axios.post('http://127.0.0.1:8000/ai/v1/style', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        console.log(response.data);
        console.log(this.file);
        this.$store.commit('SET_RESULT', {img_path: this.file, data: response.data});
        this.$router.push('/Find/result')
      })
      .catch(error => {
        console.log(error);
        this.$router.push('/Find/Error')
      });
    },
  },
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
  display: inline-block;
  object-fit: cover;
  margin-top: 17%;
  margin-left: 4%;
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
