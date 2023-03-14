<template>
    <div>
        <div v-if="image">
            <img :src="image" />
        </div>

        <p>전신이 나온 사진을 선택해주세요</p>

        <label for="" class="">사진 촬영</label> |

        <label for="fileInput" class="file-label">파일 선택</label>
        <input id="fileInput" type="file" ref="fileInput" @change="fileUpload" hidden>

        <br>|<br>|<br>|<br>

        <router-link to="util/roading">
            <button @click="uploadImage">Upload</button>
        </router-link>

    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'UtilOneVue',
    data() {
        return {
            file: null,
            image: null,
            formData: null
        };
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

            this.uploadForm(this.formData)

        },

        uploadForm(formData) {
            return axios.post('http://127.0.0.1:8000/ai/v1/style', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(response => {
                    console.log(response.data);

                })
                .catch(error => {
                    console.log(error);
                });
        }
    },
    computed: {
        // uploadImage() {
        //     this.$store.dispatch('setImage', this.image)
        // }
    }
};
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