import Vue from 'vue'
import VueRouter from 'vue-router'
import UtilOneVue from "@/components/utilOne.vue";
import roadingVue from "@/components/roading.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/util',
    name: 'UtilOneVue',
    component: UtilOneVue,
    children: [
      {
        path:'roading',
        component: roadingVue,
      },

    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
