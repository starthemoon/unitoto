import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/index'
import Search from '../components/search'
import User from '../components/user'
import Add from '../components/add'
import Community from '../components/community'
import Map from '../components/map'
import Group from '../components/group'

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
    },
    {
      path: '/community',
      name: 'Community',
      component: Community
    },
    {
      path: '/map',
      name: 'Map',
      component: Map
    },
    {
      path: '/group',
      name: 'Group',
      component: Group
    }
  ]
})