import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { path: '/',
      component: () => import('./views/Welcome.vue')
    },
    {
      path: '/First',
      component: () => import('./views/Main.vue'),
      children: [
        {
          path: 'DocList',
          component: () => import(/* webpackChunkName: "group-first" */ './views/DocList.vue')
        },
        {
          path: 'Option1',
          component: () => import(/* webpackChunkName: "group-first" */ './views/Option1.vue')
        }
      ]
    }
  ]
})
