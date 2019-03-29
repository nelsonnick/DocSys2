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
export default {
  props: ['queryURL', 'totalURL', 'keyword'],
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
  // created: function () {
  //   this.getLists(this.queryURL, this.totalURL, this.keyword, this.current, this.pageSize)
  // },
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

    },
    // 切换每页条数
    onPageSizeChange (value) {
      this.pageSize = value
      this.pageCurrent = 1
      this.$store.commit('savePageSize', {
        pageSize: value
      })
      this.$store.commit('savePageCurrent', {
        pageCurrent: 1
      })
      console.log(this.$store.state.pageSize)
      console.log(this.$store.state.pageCurrent)
    },
    // 切换页码
    onChange (value) {
      this.pageCurrent = value
      this.$store.commit('savePageCurrent', {
        pageCurrent: 1
      })
    },
    // 查询关键词
    query (keyword) {
      this.keyword = keyword
      this.pageCurrent = 1
      this.$store.commit('saveKeyword', {
        keyword: keyword
      })
      this.$store.commit('savePageCurrent', {
        pageCurrent: 1
      })
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
