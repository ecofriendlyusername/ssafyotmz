<template>
  <div>
    <div v-if="!image" id="PhotoBox">
      <img src="@/assets/img/T.png" style="width:250px; margin-top:25px;">
    </div>

    <div v-if="image" id="PhotoBox">
      <img style="width:250px; border-radius: 40px;" :src="image" />
    </div>
    <p v-else style="color:#C92D1F; font-size: 18px; margin-top: -20px;">전신이 나온 사진을 선택해주세요</p>

    <form @submit.prevent="uploadImage">
      <label for="cameraInput" class="file-label">파일 선택</label>
      <input id="cameraInput" type="file" name="image" @change="fileUpload"> |
      <label for="fileInput" class="file-label">파일 선택</label>
      <input id="fileInput" type="file" name="image" @change="fileUpload">

      <br>|<br>|<br>|<br><br>  

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
input[type="file"] {
  opacity: 0;
  position: absolute;
  z-index: -1;
}

button {
  margin-top: -25px;
  margin-left: 10px;
}

#PhotoBox {
  margin: 30px;
  display: inline-block;
  width: 280px;
  height: 400px;
  border: 1px solid #C92D1F;
  border-radius: 40px;
}
</style>
