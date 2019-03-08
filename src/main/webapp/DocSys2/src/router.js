import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/Option1',
      name: 'Option1',
      component: () => import('./views/Option1.vue')
    },
    {
      path: '/DocList',
      name: 'DocList',
      component: () => import('./views/DocList.vue')
    }
  ]
})
