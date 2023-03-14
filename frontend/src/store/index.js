import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    image: null,
    result: null,
  },
  getters: {
    getImage(state) {
      return state.image
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
    setImage(context, payload) {
      context.commit('setImage', payload)
    }
  },
  modules: {

  }
})
