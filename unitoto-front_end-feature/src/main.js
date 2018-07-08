// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import vuex from './store'
import VueMap from 'vue-amap'
var VueCookie = require('vue-cookie')

Vue.config.productionTip = false

Vue.use(iView)
Vue.use(VueMap)
Vue.use(VueCookie)

VueMap.initAMapApiLoader({
  key: '32d6e54108af91c1574e85a78dec060f',
  plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType']
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store: vuex,
  template: '<App/>',
  components: { App }
})
