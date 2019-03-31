import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
let baseUrl = ''
export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { path: baseUrl + '/',
      component: () => import('./views/Welcome.vue')
    },
    { path: baseUrl + '/Info',
      component: () => import(/* webpackChunkName: "group-info" */'./views/Info/Main.vue')
    },
    { path: baseUrl + '/File',
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
          path: 'Resave/:id',
          component: () => import(/* webpackChunkName: "group-file" */ './views/File/Resave.vue')
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
    { path: baseUrl + '/Department',
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
    { path: baseUrl + '/User',
      component: () => import('./views/User/Main.vue'),
      children: [
        { path: '',
          component: () => import(/* webpackChunkName: "group-user" */ './views/User/Index.vue')
        },
        {
          path: 'Add',
          component: () => import(/* webpackChunkName: "group-user" */ './views/User/Add.vue')
        },
        {
          path: 'List',
          component: () => import(/* webpackChunkName: "group-user" */ './views/User/List.vue')
        },
        {
          path: 'Edit/:id',
          component: () => import(/* webpackChunkName: "group-user" */ './views/User/Edit.vue')
        }
      ]
    }
  ]
})
