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
    prediction(context, formData) {
      axios.post('http://127.0.0.1:8000/ai/v1/style', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(response => {
                    console.log(response.data);
                    console.log("setImage", formData.get('image'));
                    // console.log(require('@/assets/img/MLC.png'))
                    watermark([formData.get('image'), 'http://localhost:3000/img/MLC.30de3069.png'])
                    .image()
                    .then(function (img) {
                      context.commit("setImage", img);  
                    });
                    // context.commit("setImage", formData.get('image'));
                    context.commit("setResult", response.data);
                })
                .catch(error => {
                    console.log(error);
                });
    }
  },

  modules: {

  }
})
