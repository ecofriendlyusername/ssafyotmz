import Vue from 'vue'
import VueRouter from 'vue-router'
import UtilOneView from '../views/UtilOneView.vue'
import UtilTwoView from '../views/AboutView.vue'
import UtilThreeView from '../views/AboutView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/util_one',
    name: 'util_one',
    component: UtilOneView
  },
  {
    path: '/util_two',
    name: 'util_two',
    component: UtilTwoView
  },
  {
    path: '/util_three',
    name: 'util_three',
    component: UtilThreeView
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
