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
    setKeyword (state, payload) {
      state.keyword = payload.keyword
    },
    setPageCurrent (state, payload) {
      state.pageCurrent = payload.pageCurrent
    },
    setPageSize (state, payload) {
      state.pageSize = payload.pageSize
    }
  },
  actions: {

  }
})
