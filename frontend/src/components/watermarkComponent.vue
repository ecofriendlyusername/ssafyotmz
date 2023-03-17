<template>
    <div>
        <label for="test">aaaa</label>
        <input type="file" @change="handleImageUpload" id="test">
        <div v-if="watermarkedImgUrl">
            <img :src="watermarkedImgUrl" alt="Watermarked Image">
        </div>
    </div>
</template>
  
<script>
import watermark from 'watermarkjs';

export default {
    data() {
        return {
            watermarkedImgUrl: null
        }
    },
    methods: {
        handleImageUpload(event) {
            const file = event.target.files[0];
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
                            this.watermarkedImgUrl = img.src;
                        });
                }
            }

            reader.readAsDataURL(file);
        }
    }
}
</script>

<style></style>