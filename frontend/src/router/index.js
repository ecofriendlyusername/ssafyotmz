import Vue from 'vue'
import VueRouter from 'vue-router'
import UtilOneComponentVue from "@/components/utilOne.vue";
import roadingComponentVue from "@/components/roadingComponent.vue";
import resultComponentVue from "@/components/resultComponent.vue";
import startComponentVue from "@/components/startComponent.vue"
import watermarkComponent from "@/components/watermarkComponent.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/util',
    name: 'UtilOneVue',
    component: UtilOneComponentVue,
    children: [
      {
        path:'start',
        component: startComponentVue
      },
      {
        path:'roading',
        component: roadingComponentVue,
      },
      {
        path:'result',
        component: resultComponentVue,
      }
    ]
  },
  {
    path: '/watermark',
    name:'water',
    component: watermarkComponent
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
