import { createRouter, createWebHistory } from 'vue-router'

import MainView from "@/views/MainView.vue";
import loginView from "@/views/loginView.vue";

import MyPageView from "@/views/MyPage/mainView.vue";
import MyPageClosetView from "@/views/MyPage/ClosetView.vue";
import MyPageCodybookView from "@/views/MyPage/CodybookView.vue";
import MyPageStyleView from "@/views/MyPage/StyleView.vue";
import AddClothesView from "@/views/AddClothesView.vue";

import FindIndexView from "@/views/Find/IndexView.vue";
import FindMainView from "@/views/Find/MainView.vue";
import FindLoadingView from "@/views/Find/LoadingView.vue";
import FindResultView from "@/views/Find/ResultView.vue";
import FindErrorView from "@/views/Find/ErrorView.vue";

import CodybookView from "@/views/CodybookView.vue";

import RecomView from "@/views/RecomView.vue";

import showStoreView from "@/views/showStoreView.vue"

const routes = [
  {
    path: '/',
    name: 'Main',
    component: MainView
  },
  {
    path: '/login',
    name: 'login',
    component:loginView 
  },
  {
    path: '/MyPage',
    children:[
      {
        path: '',
        name: 'MyPageView',
        component:MyPageView,
      },
      {
        path: 'style',
        name: 'MyPageStyleView',
        component: MyPageStyleView
      },
      {
        path: 'closet',
        name: 'MyPageClosetView',
        component: MyPageClosetView
      },
      {
        path: 'codybook',
        name: 'MyPageCodybookView',
        component: MyPageCodybookView
      }
    ]
  },
  {
    path: '/AddClothes',
    name: 'AddClothesView',
    component: AddClothesView
  },
  {
    path: '/Find',
    children:[
      {
        path:'index',
        name: 'FindIndexView',
        component: FindIndexView
      },
      {
        path:'',
        name: 'FindMainView',
        component: FindMainView
      },
      {
        path:'loading',
        name: 'FindLoadingView',
        component: FindLoadingView
      },
      {
        path: 'Result',
        name: 'FindResultView',
        component: FindResultView,
      },
      {
        path: 'Error',
        name: 'FindErrorView',
        component: FindErrorView
      }
    ]
  },
  {
    path: '/Codybook',
    name: 'CodybookView',
    component: CodybookView
  },
  {
    path: '/Recom',
    name: 'RecomView',
    component: RecomView
  },
  {
    path: '/store',
    name: 'showStoreView',
    component: showStoreView
  },
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
