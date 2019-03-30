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
      component: () => import('./views/File/Main.vue'),
      children: [
        { path: '',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Index.vue')
        },
        {
          path: 'Add',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Add.vue')
        },
        {
          path: 'List',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/List.vue')
        },
        {
          path: 'Flow',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Flow.vue')
        },
        {
          path: 'Edit/:id',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Edit.vue')
        },
        {
          path: 'Out/:id',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Out.vue')
        },
        {
          path: 'In/:id',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/In.vue')
        },
        {
          path: 'Borrow/:id',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Borrow.vue')
        },
        {
          path: 'Back/:id',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Back.vue')
        }
      ]
    },

    {
      path: '/Department',
      component: () => import('./views/Department/Main.vue'),
      children: [
        { path: '',
          component: () => import(/* webpackChunkName: "group-department" */ './views/Department/Index.vue')
        },
        {
          path: 'Add',
          component: () => import(/* webpackChunkName: "group-department" */ './views/Department/Add.vue')
        },
        {
          path: 'List',
          component: () => import(/* webpackChunkName: "group-department" */ './views/Department/List.vue')
        },
        {
          path: 'Edit/:id',
          component: () => import(/* webpackChunkName: "group-department" */ './views/Department/Edit.vue')
        }
      ]
    },
    {
      path: '/User',
      component: () => import('./views/User/Main.vue'),
      children: [
        { path: '',
          component: () => import(/* webpackChunkName: "group-department" */ './views/User/Index.vue')
        },
        {
          path: 'Add',
          component: () => import(/* webpackChunkName: "group-department" */ './views/User/Add.vue')
        },
        {
          path: 'List',
          component: () => import(/* webpackChunkName: "group-department" */ './views/User/List.vue')
        },
        {
          path: 'Edit/:id',
          component: () => import(/* webpackChunkName: "group-department" */ './views/User/Edit.vue')
        }
      ]
    }
  ]
})
