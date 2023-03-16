<template>
    <div>
        <canvas ref="canvas" :width="imageWidth" :height="imageHeight"></canvas>
        <label for="test">aaaaaa</label>
        <input type="file" ref="fileInput" @change="handleImageChange" id="test">
    </div>
</template>
<script>
import watermarkImage from '@/assets/img/MLC.png'

export default {
    data() {
        return {
            imageWidth: 400,
            imageHeight: 400,
            imageSrc: null
        }
    },
    methods: {
        loadImage(src) {
            const canvas = this.$refs.canvas
            const ctx = canvas.getContext('2d')
            const img = new Image()
            img.src = src
            img.onload = () => {
                // Draw the image onto the canvas
                ctx.drawImage(img, 0, 0, this.imageWidth, this.imageHeight)

                // Draw the watermark image onto the canvas
                const watermarkImg = new Image()
                watermarkImg.src = watermarkImage
                watermarkImg.onload = () => {
                    const width = 50
                    const height = 50
                    const x = this.imageWidth - width - 10
                    const y = this.imageHeight - height - 10
                    ctx.drawImage(watermarkImg, x, y, width, height)
                }
            }
        },
        handleImageChange() {
            const file = this.$refs.fileInput.files[0]
            if (file && file.type.match(/^image\//)) {
                const reader = new FileReader()
                reader.onload = e => {
                    this.imageSrc = e.target.result
                    this.loadImage(this.imageSrc)
                }
                reader.readAsDataURL(file)
            }
        }
    }
}
</script>