import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/index'
import Search from '../components/search'
import User from '../components/user'
import Add from '../components/add'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/search',
      name: 'Search',
      component: Search
    },
    {
      path: '/user',
      name: 'User',
      component: User
    },
    {
      path: '/add',
      name: 'Add',
      component: Add
    }
  ]
})
