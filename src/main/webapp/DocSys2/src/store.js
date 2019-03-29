import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    keyword: '',
    pageCurrent: 1,
    pageSize: 10
  },
  mutations: {
    saveKeyword (state, payload) {
      state.keyword = payload.keyword
    },
    savePageCurrent (state, payload) {
      state.pageCurrent = payload.pageCurrent
    },
    savePageSize (state, payload) {
      state.pageSize = payload.pageSize
    }
  },
  actions: {

  }
})
