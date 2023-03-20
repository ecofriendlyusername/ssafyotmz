<template>
  <div>
    <div v-if="image">
      <img :src="image" />
    </div>
    <p>전신이 나온 사진을 선택해주세요</p>

    <label for="" class="">사진 촬영</label> |

    <label for="fileInput" class="file-label">파일 선택</label>
    <input id="fileInput" type="file" ref="fileInput" @change="fileUpload">

    <br>|<br>|<br>|<br><br>  

    <!-- 모달창 열기 -->
    <v-btn @click="uploadImage">스타일 판별하기</v-btn>
    
    <v-btn @click="dialog = true">모달 열기</v-btn>

    <v-dialog v-model="dialog" :scrim="false" persistent width="auto">
        <v-card color="primary">
          <v-card-text>
            스타일 판별중...
            <v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
          </v-card-text>
        </v-card>
      </v-dialog>

    <v-dialog v-model="dialog2">
      <v-card v-if="result">
        <v-card-title>결과창</v-card-title>
        <v-card-text>당신의 스타일은 {{ result['1']['style'] }}입니다!</v-card-text>
        <v-card-text>일치도는 {{ result[1].score }}점 입니다.    </v-card-text>
        <v-img width="400" :src="imageSrc"></v-img>
        <br>
        <v-card-actions>
          <v-btn color="primary" @click="dialog2 = false">공유하기</v-btn>
          <v-btn color="primary" @click="dialog2 = false">저장하기</v-btn>
          <v-btn color="primary" @click="dialog2 = false">닫기</v-btn>
        </v-card-actions>
      </v-card>

      <v-card v-else>
        <v-card-title>결과가 나오지 않았습니다!</v-card-title>
        <v-card-text>스타일 판별하기를 먼저 진행해주세요!</v-card-text>
        
        <v-card-actions>
          <v-btn color="primary" @click="dialog2 = false">닫기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import watermark from 'watermarkjs';
import axios from 'axios';

export default {
  name: 'HelloWorld',
  data() {
    return {
      file: null,
      image: null,
      formData: null,
      dialog: false,
      dialog2: false,
      result: null,
      imageSrc: null,
    };
  },
  watch: {
    dialog (val) {
      if (!val) return

      setTimeout(() => (this.dialog = false, this.dialog2 = true), 4000)

      const file = this.file;
      const reader = new FileReader();
      reader.onload = (e) => {
        const img = new Image();
        img.src = e.target.result;
        img.onload = () => {
          watermark([img, require('@/assets/img/logo.png')])
          .image(watermark.image.upperRight(0.5))
          .load([require('@/assets/img/MyStyleStreet.png')])
          .image(watermark.image.lowerLeft(0.5))
          .then((img) => {
          this.imageSrc = img.src;
          });
        }
      }
      reader.readAsDataURL(file);
    },
  },
  methods: {
    fileUpload() {
      this.file = this.$refs.fileInput.files[0];
      this.image = URL.createObjectURL(this.file);
      this.formData = new FormData()
      this.formData.append('image', this.file)
    },

    uploadImage() {
      if (this.file == null) {
        return;
      }
      axios.post('http://127.0.0.1:8000/ai/v1/style', this.formData, {
        headers: {
        'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        console.log(response.data);
        this.result = response.data
      })
      .catch(error => {
        console.log(error);
      });
    },
  },
}

</script>

<style>
input[type="file"] {
    opacity: 0;
    position: absolute;
    z-index: -1;
}

button {
    margin-top: -25px;
    margin-left: 10px;
}
</style>