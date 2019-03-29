<template>
  <div>
    <Row>
      <Col span="6">
        <Breadcrumb :style="{margin: '24px 0'}">
          <BreadcrumbItem>部门管理</BreadcrumbItem>
          <BreadcrumbItem>部门列表</BreadcrumbItem>
        </Breadcrumb>
      </Col>
      <Col span="6" :style="{margin: '20px 0'}">
        <Button type="info" @click="goAdd" long>新增</Button>
      </Col>
      <Col span="12"><Search @goQuery="getQuery"/></Col>
    </Row>
    <Table
      highlight-row
      @on-row-dblclick="onRowDblclick"
      :loading="loading"
      :height="height"
      :context="self"
      :border="border"
      :stripe="stripe"
      :size="size"
      :columns="columns"
      :data="pageList">
      <template slot-scope="{ row }" slot="name">
        {{ row.name }}
      </template>
      <template slot-scope="{ row }" slot="phone">
        {{row.phone}}
      </template>
      <template slot-scope="{ row }" slot="address">
        {{row.address}}
      </template>
      <template slot-scope="{ row, index }" slot="action">
        <Button type="primary" size="small" style="margin-right: 5px" @click="goEdit(index)">修改</Button>
        <Button type="error" size="small" @click="goDel(index)">删除</Button>
      </template>
    </Table>
    <Bottom
      ref="bottom"
      @changeData="getData"
      @changeList="getList"
      @changeBorder="getBorder"
      @changeStripe="getStripe"
      @changeSize="getSize"
      :queryURL="queryURL"
      :totalURL="totalURL"
    >
    </Bottom>
  </div>
</template>
<script>
import Search from '../../components/Common/search.vue'
import Bottom from '../../components/Common/bottom.vue'
import * as API from './API.js'
import axios from 'axios'
export default {
  name: 'list',
  components: { Search, Bottom },
  data () {
    return {
      queryURL: API.query,
      totalURL: API.total,
      loading: true,
      border: false,
      stripe: false,
      size: 'small',
      height: 450,
      self: this,
      pageList: [],
      columns: [
        {
          title: '部门名称',
          slot: 'name',
          width: 150
        },
        {
          title: '联系电话',
          slot: 'phone',
          width: 150
        },
        {
          title: '联系地址',
          slot: 'address'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ]
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
  },
  methods: {
    getQuery (keyword) {
      this.$refs.bottom.query(keyword)
    },
    getData () {
      this.loading = true
    },
    getList (pageList) {
      this.pageList = pageList
      this.loading = false
    },
    getBorder (border) {
      this.border = border
    },
    getStripe (stripe) {
      this.stripe = stripe
    },
    getSize (size) {
      if (size.toString() === 'true') {
        this.height = 665
        this.size = 'large'
      } else {
        this.height = 450
        this.size = 'small'
      }
    },
    onRowDblclick (row, index) {
      this.$Modal.info({
        title: `部门信息---ID:${this.pageList[index].id}`,
        content: `部门名称：${this.pageList[index].name}<br>联系电话：${this.pageList[index].phone}<br>联系地址：${this.pageList[index].address}`
      })
    },
    goAdd () {
      this.$router.push({ path: '/Department/Add/' })
    },
    goEdit (index) {
      this.$router.push({ path: '/Department/Edit/' + this.pageList[index].id })
    },
    goDel (index) {
      this.$Loading.start()
      axios.get(API.del, {
        params: {
          id: this.pageList[index].id
        }
      }).then(res => {
        if (res.data === 'OK') {
          this.$Loading.finish()
          this.$Message.success('删除成功!')
          this.getQuery(this.keyword)
        } else {
          this.$Loading.error()
          this.$Notice.error({
            title: res.data
          })
        }
      }).catch(res => {
        this.$Loading.error()
        this.$Notice.error({
          title: '服务器内部错误!'
        })
      })
    }
  }
}
</script>
<style scoped>
</style>
