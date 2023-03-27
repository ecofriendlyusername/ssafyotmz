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

    <br>
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
      // 예외처리
      if (this.file == null) {
        return;
      }
      // 폼 데이터 만들기 - 위에서 form 양식 쓸 필요 없으니까 지우기
      const formData = new FormData();
      formData.append('image', this.file);

      // api요청으로 이미지 분석하기
      axios.post(process.env.VUE_APP_PREDICTION_API_URL, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      // 결과 받아서 저장
      .then(response => {
        // 검출 결과 저장
        this.result = response.data;
        formData.append('result', this.result);
      })
      // 워터마크 찍기
      .then(() => {
        const reader = new FileReader();
        reader.onload(evnet => {
          const tmp_img = this.file
          // 이미지 파일 읽어서 워터마크 찍기
          tmp_img.onload(() => {
            watermark([tmp_img, require('@/assets/img/watermark/logo.png')])
            .image(watermark.image.upperRight(0.5))
            .then((img) => {
              // 폼데이터 이미지 워터마크 찍힌걸로 변경
              formData.image = img
            });
          });
        });
      })
      // (워터마크 찍힌 이미지 + 결과값) 서버에 저장하기
      .then(() => {
        axios.post('URL', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then((response) => {
          // 파일 저장하는 api 리턴값으로 파일 경로 달라고 해야 함
          this.image = response.data
        })
        .catch(error =>{
          console.log(error)
          this.$router.push('/Find/Error')
        })
      })
      // 상태에 저장 경로 저장하고 라우트 이동
      .then(() => {
        this.$store.commit('SET_RESULT', {img_path: this.image, data: this.result});
        this.$router.push('/Find/result');
      })
      .catch(error => {
        console.log(error);
        this.$router.push('/Find/Error')
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
