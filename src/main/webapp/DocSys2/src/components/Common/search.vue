<template>
  <div class="query">
    <div class="left">
      <Input type="text" v-model="keyword" placeholder="请输入关键词" style="width:270px;" @on-enter="goQuery">
      <span slot="prepend">关键词</span>
      </Input>
    </div>
    <div class="right">
      <Button-group>
        <Button type="primary" @click="goQuery">
          <Icon type="md-search"></Icon>
          搜索
        </Button>
        <Button @click="goRefresh">
          <Icon type="md-refresh"></Icon>
          重置
        </Button>
      </Button-group>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      keyword: ''
    }
  },
  methods: {
    goQuery () {
      this.$emit('goQuery', this.keyword)
    },
    goRefresh () {
      this.keyword = ''
      this.$emit('goQuery', this.keyword)
    }
  }
}
</script>
<style>
  .query{
    float: right;
    margin: 3px 0px 0px 0px
  }
  .left{
    float: left
  }
  .right{
    float: right
  }
</style>
