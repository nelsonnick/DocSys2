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
      path: '/File',
      component: () => import('./views/Main.vue'),
      children: [
        { path: '',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Index.vue')
        },
        {
          path: 'Add',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Add.vue')
        },
        {
          path: 'Query',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Query.vue')
        }
      ]
    },
    {
      path: '/Person',
      component: () => import('./views/Main.vue'),
      children: [
        {
          path: 'Add',
          component: () => import(/* webpackChunkName: "group-person" */ './views/File/Add.vue')
        },
        {
          path: 'Query',
          component: () => import(/* webpackChunkName: "group-person" */ './views/File/Query.vue')
        }
      ]
    }
  ]
})
