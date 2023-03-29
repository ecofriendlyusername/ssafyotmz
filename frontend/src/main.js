import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vue3TouchEvents from "vue3-touch-events";
import VueKonva from 'vue-konva';

createApp(App)
    .use(store)
    .use(Vue3TouchEvents)
    .use(router)
    .use(VueKonva)
    .mount('#app')
