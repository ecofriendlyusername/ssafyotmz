import { createStore } from 'vuex'
import axios from 'axios';

export default createStore({
  state: {
    result: {
      img_path: null,
      data: null,
      imageId: null,
    },
    Auth: {
      accessToken: null,
      refreshToken: null,
      memberId: null,
      nickname: null,
      profileImagePath: null,
    },
    Board: {
      title: null
    },
    Fillter: {
      kind: null,
      category: null,
    },
    CardData: {
      cardId: null,
      cardTitle: null,
      cardContent: null,
    },
    CardDataList: {
      cardListId: null,
      cardListTitle: null,
      cardListItems: null,
    },
  },
  getters: {
    getResult(state){
      return state.result
    },
    getAuth(state){
      return state.Auth
    },
    getBoard(state){
      return state.Board
    },
    getFillter(state){
      return state.Fillter
    },
    getCardData(state){
      return state.CardData
    },
    getCardDataList(state){
      return state.CardDataList
    },
  },
  mutations: {
    SET_RESULT(state, result){
      state.result = result
    },
    setAuth(state, Auth){
      state.Auth = Auth
    },
    setBoard(state, Board){
      state.Board = Board
    },
    setFillter(state, Fillter){
      state.Fillter = Fillter
    },
    setCardData(state, CardData){
      state.CardData = CardData
    },
    setCardDataList(state, CardDataList){
      state.CardDataList = CardDataList
    },
  },
  actions: {
    SET_RESULT(state, payload){
      state.result = payload
    },
    setAuth(state, Auth){
      state.Auth = Auth
    },
    AuthLogin(context, formData){
      axios.post(process.env.VUE_APP_AUTH_LOGIN_API_URL, formData)
      .then(response => {
        context.commit('setAuth', response.data)
        })
    },
    AuthLogout(context){
      context.commit('setAuth', {
        token: null, 
        userId: null
      })
    },
    getStyle(context){
      axios.post(process.env.VUE_APP_GET_STYLE_API_URL)
      .then(response => {
        context.commit('setStyle', response.data)
      })
    },
    getLookbook(context){
      axios.post(process.env.VUE_APP_GET_LOOKBOOK_API_URL)
      .then(response => {
        context.commit('setLookbook', response.data)
      })
    },
    // getClothes(context){
    //   axios.post(process.env.VUE_APP_GET_CLOTHES_API_URL)
    //   .then(response => {
    //     context.commit('setClothes', response.data)
    //   })
    // },
    updateFillter(context, formData){
      axios.post(process.env.VUE_APP_UPDATE_FILLTER_API_URL, formData)
      .then(response => {
        context.commit('setFillter', response.data)
      })
    },
    getDetail(context){
      axios.post(process.env.VUE_APP_GET_DETAIL_API_URL)
      .then(response => {
        context.commit('setDetail', response.data)
      })
    },
  },
  modules: {
  }
})
