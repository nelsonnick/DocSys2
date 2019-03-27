<template>
  <div class="page">
    <Page :total="pageTotal" :current="pageCurrent" :page-size="pageSize" @on-page-size-change="sizeChange" @on-change="pageChange" show-sizer show-elevator show-total></Page>
  </div>
</template>
<script>
  import axios from 'axios'
  import * as BASE from './Base.js'

  export default {
    props: ['queryURL', 'totalURL', 'keyword'],
    data () {
      return {
        current: 1,
        pageSize: 10,
        total: 0,
        pageList: []
      }
    },
    created: function () {
      this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
    },
    methods: {
      getLists (queryURL, totalURL, keyword, pageCurrent, pageSize) {
        axios.get(queryURL, {
          params: {
            keyword: keyword,
            current: pageCurrent,
            pageSize: pageSize
          }
        }).then(res => {
          if (res.data.toString() === 'illegal' || res.data.toString() === 'overdue') {
          } else {
            axios.get(totalURL, {
              params: {
                keyword: keyword
              }
            }).then(response => {
              if (response.data.toString() === 'illegal' || response.data.toString() === 'overdue') {
                this.$Notice.error({
                  title: '登录过期或非法操作!'
                })
                window.location.href = BASE.base
              } else {
                this.pageList = res.data
                this.pageTotal = response.data
                this.$emit('goList', this.pageList, this.total)
              }
            }).catch(response => {
              this.$Loading.error()
              this.$Notice.error({
                title: '服务器内部错误!'
              })
            })
          }
        }).catch(res => {
          this.$Loading.error()
          this.$Notice.error({
            title: '服务器内部错误!'
          })
        })
      },
      onPageSizeChange (value) {
        this.pageSize = value
        this.pageCurrent = 1
        this.$emit('savePageCurrent', this.current)
        this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
      },
      onChange (value) {
        this.pageCurrent = value
        this.$emit('savePageCurrent', this.current)
        this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
      },
      query (keyword) {
        this.keyword = keyword
        this.pageCurrent = 1
        this.$emit('savePageCurrentAndKeyword', this.keyword, this.current)
        this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
      },
      queryNoChange (keyword) {
        this.keyword = keyword
        this.$emit('savePageCurrentAndKeyword', this.keyword, this.current)
        this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
      }
    }
  }
</script>
<style>
  .page{
    float:right ;
    margin: 15px;
  }
</style>
