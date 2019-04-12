<template>
  <div>
    <Row>
      <Col span="6">
        <Breadcrumb :style="{margin: '24px 0'}">
          <BreadcrumbItem>档案管理</BreadcrumbItem>
          <BreadcrumbItem>档案列表</BreadcrumbItem>
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
      <template slot-scope="{ row }" slot="code">
        {{ row.code }}
      </template>
      <template slot-scope="{ row }" slot="name">
        {{row.name}}
      </template>
      <template slot-scope="{ row }" slot="number">
        {{row.number}}
      </template>
      <template slot-scope="{ row }" slot="state">
        {{row.state}}
      </template>
      <template slot-scope="{ row }" slot="department">
        {{row.department}}
      </template>
      <template slot-scope="{ row, index }" slot="action" >
        <Button type="primary" size="small" style="margin-right: 5px" @click="goEdit(index)" v-if="row.state.toString() === '在档' && row.department === department">修改</Button>
        <Button type="error" size="small" style="margin-right: 5px" @click="goOut(index)" v-if="row.state.toString() === '在档' && row.department === department">提档</Button>
        <Button type="warning" size="small" style="margin-right: 5px" @click="goBorrow(index)" v-if="row.state.toString() === '在档' && row.department === department">借档</Button>
        <Button type="info" size="small" style="margin-right: 5px" @click="goBack(index)" v-if="row.state.toString() === '借档' && row.department === department">还档</Button>
        <Button type="dash" size="small" style="margin-right: 5px" @click="goResave(index)" v-if="row.state.toString() === '提档' && row.department === department">重存</Button>
        <Button type="dash" size="small" style="margin-right: 5px" @click="goResaveOther(index)" v-if="row.state.toString() === '提档' && row.department !== department">存档</Button>
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
      department: '',
      loading: true,
      border: false,
      stripe: false,
      size: 'small',
      height: 450,
      self: this,
      pageList: [],
      columns: [
        {
          title: '档案编号',
          slot: 'code'
        },
        {
          title: '人员姓名',
          slot: 'name'
        },
        {
          title: '身份证号码',
          slot: 'number'
        },
        {
          title: '档案状态',
          slot: 'state',
          width: 150
        },
        {
          title: '所属部门',
          slot: 'department'
        },
        {
          title: '操作',
          slot: 'action',
          align: 'center'
        }
      ]
    }
  },
  created: function () {
    this.getUser()
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
        title: `档案信息---ID:${this.pageList[index].id}---PID:${this.pageList[index].person}`,
        content: `档案编号：${this.pageList[index].code}<br>用户姓名：${this.pageList[index].name}<br>身份证号码：${this.pageList[index].number}<br>档案状态：${this.pageList[index].state}<br>所属部门：${this.pageList[index].department}`
      })
    },
    goAdd () {
      this.$router.push({ path: '/File/Add/' })
    },
    goEdit (index) {
      this.$router.push({ path: '/File/Edit/' + this.pageList[index].id })
    },
    goBack (index) {
      this.$router.push({ path: '/File/Back/' + this.pageList[index].id })
    },
    goBorrow (index) {
      this.$router.push({ path: '/File/Borrow/' + this.pageList[index].id })
    },
    goOut (index) {
      this.$router.push({ path: '/File/Out/' + this.pageList[index].id })
    },
    goResave (index) {
      this.$router.push({ path: '/File/Resave/' + this.pageList[index].id })
    },
    goResaveOther (index) {
      this.$router.push({ path: '/File/ResaveOther/' + this.pageList[index].id })
    },
    getUser () {
      axios.get(API.getUser
      ).then(res => {
        this.department = res.data
      }).catch(res => {
        this.$Notice.error({
          title: '服务器内部错误，无法获取数据!'
        })
      })
    }
  }
}
</script>
<style scoped>
</style>
