import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import axios from 'axios';
import watermark from 'watermarkjs';

export default new Vuex.Store({
  state: {
    image: null,
    result: null,
  },

  getters: {
    getImage(state) {
      return state.image
    },
    getResult(state) {
      return state.result
    }
  },

  mutations: {
    setImage(state, payload) {
      state.image = payload;
    },

    setResult(state, payload) {
      state.result = payload;
    }
  },

  actions: {
    setImage(state, payload) {
      state.image = payload;
    },

    setResult(state, payload) {
      state.result = payload;
    },

    prediction(context, formData) {
      axios.post('http://127.0.0.1:8000/ai/v1/style', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then(response => {
          console.log(response.data);
          context.commit("setImage", formData.get('image'));
          context.commit("setResult", response.data); 
        })
        .then(() => {

        })
        .catch(error => {
          console.log(error);
        });
    },
    marking() {
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
      // reader.readAsDataURL(file);
    }
  },

  modules: {

  }
})
