<template>
  <div class="page">
    <Page :total="total" :current="current" :page-size="pageSize" @on-page-size-change="onPageSizeChange" @on-change="onChange" show-sizer show-elevator show-total></Page>
  </div>
</template>
<script>
export default {
  props: ['queryURL', 'totalURL', 'keyword'],
  data () {
    return {
      total: 0,
      current: 1,
      pageSize: 10,
      pageList: []
    }
  },
  // created: function () {
  //   this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
  // },
  methods: {
    getLists (queryURL, totalURL, keyword, pageCurrent, pageSize) {
      this.$api.query(queryURL, keyword, pageCurrent, pageSize).then(res => {
        if (res.rc === 0) {
          this.$api.total(totalURL, keyword).then(re => {
            if (re.rc === 0) {
              this.pageList = res.data.item
              this.total = re.data.item
              this.$emit('goList', this.pageList, this.total)
            }else {
              this.$Message.info(re.desc);
            }
          }).catch(erro => {
            this.$Message.info(erro);
          })
        }else {
          this.$Message.info(res.desc);
        }
      }).catch(error => {
        this.$Message.info(error);
      })
    },
    onPageSizeChange (value) {
      this.pageSize = value
      this.current = 1
      this.$emit('savePageCurrent', this.current)
      this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
    },
    onChange (value) {
      this.current = value
      this.$emit('savePageCurrent', this.current)
      this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
    },
    query (keyword) {
      this.keyword = keyword
      this.current = 1
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
