<template>
  <div>
    <Row>
      <Col span="6">
        <Breadcrumb :style="{margin: '24px 0'}">
          <BreadcrumbItem>档案管理</BreadcrumbItem>
          <BreadcrumbItem>流转列表</BreadcrumbItem>
        </Breadcrumb>
      </Col>
      <Col span="6" :style="{margin: '20px 0'}">
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
      <template slot-scope="{ row }" slot="type">
        {{row.type}}
      </template>
      <template slot-scope="{ row }" slot="time">
        {{row.time}}
      </template>
      <template slot-scope="{ row }" slot="department">
        {{row.department}}
      </template>
      <template slot-scope="{ row }" slot="user">
        {{row.user}}
      </template>
      <template slot-scope="{ row, index }" slot="action" >
        <Button type="primary" size="small" style="margin-right: 5px" @click="goPrintIn(index)" v-if="row.type.toString() === '存档 ' && row.department === department">打印存档</Button>
        <Button type="success" size="small" style="margin-right: 5px"  @click="goPrintOut(index)" v-if="row.type.toString() === '提档' && row.department === department">打印提档</Button>
        <Button type="success" size="small" style="margin-right: 5px"  @click="goPrintBorrow(index)" v-if="row.type.toString() === '借档' && row.department === department">打印借档</Button>
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
  name: 'flow',
  components: { Search, Bottom },
  data () {
    return {
      queryURL: API.queryFlow,
      totalURL: API.totalFlow,
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
          title: '流转类型',
          slot: 'type'
        },
        {
          title: '流转时间',
          slot: 'time',
          width: 150
        },
        {
          title: '所属部门',
          slot: 'department'
        },
        {
          title: '操作用户',
          slot: 'user'
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
        title: `流转信息---ID:${this.pageList[index].id}`,
        content: `档案编号：${this.pageList[index].code}<br>人员姓名：${this.pageList[index].name}<br>身份证号码：${this.pageList[index].number}<br>流转类型：${this.pageList[index].type}<br>所属部门：${this.pageList[index].department}<br>操作用户：${this.pageList[index].user}`
      })
    },
    goPrintIn (index) {
      window.location.href = API.printIn + '?id=' + this.pageList[index].id
    },
    goPrintOut (index) {
      window.location.href = API.printOut + '?id=' + this.pageList[index].id
    },
    goPrintBorrow (index) {
      window.location.href = API.printBorrow + '?id=' + this.pageList[index].id
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
