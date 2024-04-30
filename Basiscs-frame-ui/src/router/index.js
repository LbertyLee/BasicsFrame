import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import { title } from '@/settings'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: '首页',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system',
    name: '系统管理',
    meta: {
      title: '系统管理',
      icon: 'el-icon-message-solid'
    },
    children: [
      {
        path: 'user',
        component: () => import('@/views/system/user/index'), 
        name: '用户管理',
        meta: { title: '用户管理',icon: 'user' },
      },
      {
        path: '文件管理',
        component: () => import('@/views/system/file/index'),
        name: '文件管理',
        meta: { title: '文件管理',icon:'el-icon-tickets' }
      }
    ]
  },
  {
    path: '/club',
    component: Layout,
    redirect: '/club',
    name: '刷题管理',
    meta: {
      title: '刷题管理',
      icon: 'el-icon-message-solid'
    },
    children: [
      {
        path: 'subject',
        component: () => import('@/views/club/subject/index'), 
        name: '题目管理',
        meta: { title: '题目管理',icon: 'user' },
      },
      {
        path: 'category',
        component: () => import('@/views/club/category/index'),
        name: '分类管理',
        meta: { title: '分类管理',icon:'el-icon-tickets' }
      }
    ]
  },

  {
    path: '/generator',
    component: Layout,
    redirect: '/generator',
    name: '代码生成器',
    meta: {
      title: '代码生成器',
      icon: 'el-icon-message-solid'
    },
    children: [
      {
        path: 'back',
        component: () => import('@/views/generator/back/index'), 
        name: '后端代码',
        meta: { title: '后端代码',icon: '' },
      },
      {
        path: 'back',
        component: () => import('@/views/generator/back/index'), 
        name: '前端代码',
        meta: { title: '前端代码',icon: '' },
      }
    ]
  },

  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },


  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
