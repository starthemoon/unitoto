import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userId: ''
  },
  mutations: {
    setUser (state, id) {
      state.userId = id
    },
    reset (state) {
      state.userId = ''
    }
  }
})
