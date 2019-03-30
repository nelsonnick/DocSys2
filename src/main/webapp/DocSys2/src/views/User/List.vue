<template>
  <div>
    <Row>
      <Col span="6">
        <Breadcrumb :style="{margin: '24px 0'}">
          <BreadcrumbItem>用户管理</BreadcrumbItem>
          <BreadcrumbItem>用户列表</BreadcrumbItem>
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
      <template slot-scope="{ row }" slot="login">
        {{row.login}}
      </template>
      <template slot-scope="{ row }" slot="state">
        {{row.state}}
      </template>
      <template slot-scope="{ row }" slot="department">
        {{row.department}}
      </template>
      <template slot-scope="{ row, index }" slot="action" >
        <Button type="primary" size="small" style="margin-right: 5px" @click="goEdit(index)" v-if="row.state.toString() === '启用'">修改</Button>
        <Button type="success" size="small" style="margin-right: 5px"  @click="goActive(index)" v-if="row.state.toString() === '禁用'">激活</Button>
        <Button type="error" size="small" style="margin-right: 5px"  @click="goUnactive(index)" v-if="row.state.toString() === '启用'">注销</Button>
        <Button type="warning" size="small" style="margin-right: 5px"  @click="goReset(index)">重置密码</Button>
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
          title: '人员姓名',
          slot: 'name'
        },
        {
          title: '登录名称',
          slot: 'login'
        },
        {
          title: '用户状态',
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
        title: `用户信息---ID:${this.pageList[index].id}`,
        content: `用户姓名：${this.pageList[index].name}<br>登录名称：${this.pageList[index].login}<br>用户状态：${this.pageList[index].state}<br>所属部门：${this.pageList[index].department}`
      })
    },
    goAdd () {
      this.$router.push({ path: '/User/Add/' })
    },
    goEdit (index) {
      this.$router.push({ path: '/User/Edit/' + this.pageList[index].id })
    },
    goActive (index) {
      this.$Loading.start()
      axios.get(API.active, {
        params: {
          id: this.pageList[index].id
        }
      }).then(res => {
        if (res.data === 'OK') {
          this.$Loading.finish()
          this.$Message.success('激活成功!')
          this.$refs.bottom.query(this.$store.state.keyword)
        } else {
          this.$Loading.error()
          this.$Notice.error({
            title: res.data
          })
        }
      }).catch(res => {
        this.$Loading.error()
        this.$Notice.error({
          title: '服务器内部错误，无法激活!'
        })
      })
    },
    goUnactive (index) {
      this.$Loading.start()
      axios.get(API.unactive, {
        params: {
          id: this.pageList[index].id
        }
      }).then(res => {
        if (res.data === 'OK') {
          this.$Loading.finish()
          this.$Message.success('激活成功!')
          this.$refs.bottom.query(this.$store.state.keyword)
        } else {
          this.$Loading.error()
          this.$Notice.error({
            title: res.data
          })
        }
      }).catch(res => {
        this.$Loading.error()
        this.$Notice.error({
          title: '服务器内部错误，无法激活!'
        })
      })
    },
    goReset (index) {
      this.$Loading.start()
      axios.get(API.reset, {
        params: {
          id: this.pageList[index].id
        }
      }).then(res => {
        if (res.data === 'OK') {
          this.$Loading.finish()
          this.$Message.success('密码重置成功!')
          this.$refs.bottom.query(this.$store.state.keyword)
        } else {
          this.$Loading.error()
          this.$Notice.error({
            title: res.data
          })
        }
      }).catch(res => {
        this.$Loading.error()
        this.$Notice.error({
          title: '服务器内部错误，无法重置密码!'
        })
      })
    }
  }
}
</script>
<style scoped>
</style>
