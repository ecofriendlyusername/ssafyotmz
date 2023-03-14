import Vue from 'vue'
import VueRouter from 'vue-router'
import UtilOneVue from "@/components/utilOne.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'UtilOneVue',
    component: UtilOneVue,
    children: [
      {
        path:'/',
        // component: ,
      },
      {}
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
