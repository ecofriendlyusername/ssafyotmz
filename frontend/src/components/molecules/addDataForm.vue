<template>
  <div>
    <div id="BackgroundCard">
      <div id="InnerCard">
        <img id="ImgCard" style="width:100%; border-radius: 20px; max-height: 325px; " :src="image" />
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
import axios from 'axios';
import watermark from 'watermarkjs';

export default {
  name: 'HelloWorld',
  data() {
    return {
      file: null,
      image: null,
      resized: null,
      result: null
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
      var reader = new FileReader();
      reader.onload = (e) => {
        console.log(e.target)
        var img = new Image();
        img.onload = () => {
          var thumbFile = (_IMG) => {
            var canvas = document.createElement("canvas");
            // var newWidthAndHeight = this.calculateAspectRatioFit(_IMG.width,_IMG.height, 360, 480)
            // var width = newWidthAndHeight.width;
            // var height = newWidthAndHeight.height;
            var width = 360;
            var height = 480;
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
          this.image = URL.createObjectURL(file);
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

    uploadImage() {
      // 예외처리
      const ImgCard = document.getElementById('ImgCard')
      if (ImgCard.src == null) {
        return;
      }

      const formData = new FormData();
      formData.append('imageFile', this.file);

      axios.post(process.env.VUE_APP_AI_URL + '/style', formData, {
        headers: {
          'Content-Type': 'multipart/form-data' 
        }
      })
      // 결과 받아서 저장
      .then(response => {
        console.log(response.data)
        this.result = response.data;     
      })
      // 워터마크 찍기
      .then(() => {
        watermark([this.image, require('@/assets/img/watermark/logo_left.png')])
          .image(watermark.image.upperLeft(0.5))
          .load([require('@/assets/img/watermark/logo_right.png')])
          .image(watermark.image.upperRight(0.5))
          .load([require('@/assets/img/watermark/' + this.result['data']['1']['style'] + '.png')])
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
        // this.$router.push('/Find')
      });
    },
  }
}
</script>

<style>
#BackgroundCard {
  display: inline-block;
  /* display: flex; */
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
