import { RouteConfig } from 'vue-router'

const routes: RouteConfig[] = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/SignIn.vue') },
      { path: 'signup', component: () => import('pages/SignUp.vue') },
      { path: 'history', component: () => import('pages/History.vue') },
      { path: 'list', component: () => import('pages/ProductList.vue') },
      { path: 'basket', component: () => import('pages/Basket.vue') }
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
