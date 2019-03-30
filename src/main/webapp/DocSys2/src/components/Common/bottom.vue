<template>
  <div>
    <div class="left">
      <div>
        尺寸：
        <i-switch v-model="size" style="margin-right: 5px">
          <span slot="open">大</span>
          <span slot="close">小</span>
        </i-switch>
        边框：
        <i-switch size="large" v-model="border" style="margin-right: 5px">
          <span slot="open">有</span>
          <span slot="close">无</span>
        </i-switch>
        斑马纹：
        <i-switch v-model="stripe" style="margin-right: 5px">
          <span slot="open">有</span>
          <span slot="close">无</span>
        </i-switch>
      </div>
    </div>
    <div class="right">
      <Page
        :total="pageTotal"
        :current="pageCurrent"
        :page-size="pageSize"
        @on-page-size-change="onPageSizeChange"
        @on-change="onChange"
        show-sizer
        show-elevator
        show-total>
      </Page>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  props: ['queryURL', 'totalURL'],
  data () {
    return {
      border: false,
      stripe: false,
      size: false,
      pageTotal: 0,
      pageCurrent: 1,
      pageSize: 10,
      pageList: []
    }
  },
  created: function () {
    this.$store.commit('setKeyword', {
      keyword: ''
    })
    this.$store.commit('setPageCurrent', {
      pageCurrent: 1
    })
    this.$store.commit('setPageSize', {
      pageSize: 10
    })
    this.getLists(this.queryURL, this.totalURL, this.$store.state.keyword, this.$store.state.pageCurrent, this.$store.state.pageSize)
  },
  watch: {
    border: function (val) {
      this.$emit('changeBorder', val)
    },
    stripe: function (val) {
      this.$emit('changeStripe', val)
    },
    size: function (val) {
      this.$emit('changeSize', val)
    }
  },
  methods: {
    getLists (queryURL, totalURL, keyword, pageCurrent, pageSize) {
      this.$emit('changeData')
      axios.get(queryURL, {
        params: {
          keyword: keyword,
          pageCurrent: pageCurrent,
          pageSize: pageSize
        }
      }).then(res => {
        axios.get(totalURL, {
          params: {
            keyword: keyword
          }
        }).then(response => {
          this.pageList = res.data
          this.pageTotal = response.data
          this.$emit('changeList', this.pageList, this.total)
        }).catch(response => {
          this.$Loading.error()
          this.$Notice.error({
            title: '服务器内部错误,无法获取数量!'
          })
        })
      }).catch(res => {
        this.$Loading.error()
        this.$Notice.error({
          title: '服务器内部错误，无法获取列表数据!'
        })
      })
    },
    // 切换每页条数
    onPageSizeChange (value) {
      this.pageSize = value
      this.pageCurrent = 1
      this.$store.commit('setPageSize', {
        pageSize: value
      })
      this.$store.commit('setPageCurrent', {
        pageCurrent: 1
      })
      this.getLists(this.queryURL, this.totalURL, this.$store.state.keyword, this.$store.state.pageCurrent, this.$store.state.pageSize)
    },
    // 切换页码
    onChange (value) {
      this.pageCurrent = value
      this.$store.commit('setPageCurrent', {
        pageCurrent: value
      })
      this.getLists(this.queryURL, this.totalURL, this.$store.state.keyword, this.$store.state.pageCurrent, this.$store.state.pageSize)
    },
    // 查询关键词
    query (keyword) {
      this.keyword = keyword
      this.pageCurrent = 1
      this.$store.commit('setKeyword', {
        keyword: keyword
      })
      this.$store.commit('setPageCurrent', {
        pageCurrent: 1
      })
      console.log(this.$store.state.keyword)
      console.log(this.$store.state.pageCurrent)
      console.log(this.$store.state.pageSize)
      this.getLists(this.queryURL, this.totalURL, this.$store.state.keyword, this.$store.state.pageCurrent, this.$store.state.pageSize)
    }
  }
}
</script>
<style>
  .left {
    float: left;
    margin: 15px;
  }
  .right{
    float: right;
    margin: 15px;
  }
</style>
