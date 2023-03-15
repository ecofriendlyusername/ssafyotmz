<template>
  <div>
    <p>asassfsd</p>
    <div v-if="image">
      <img :src=imageBlobUrl />
      <!-- <img :src=imageWatermark /> -->

    </div>
    <div>11{{ image }}22</div>
    <div>33{{ result }}44</div>

  </div>
</template>

<script src="https://unpkg.com/watermarkjs"></script>
<script>

export default {
  name: 'resultComponentVue',
  computed: {
    image(){
      return this.$store.state.image
    },
    result(){
      return this.$store.state.result
    },
    imageBlobUrl() { // Vuex state에서 파일 객체 불러오기
      const blobUrl = URL.createObjectURL(this.image); // 파일 객체를 Blob URL로 변환하기
      return blobUrl;
    },
  }
}
</script>

<style></style>