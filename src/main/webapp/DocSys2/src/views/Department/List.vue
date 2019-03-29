<template>
  <div>
    <Row>
      <Col span="8">
        <Breadcrumb :style="{margin: '24px 0'}">
          <BreadcrumbItem>部门管理</BreadcrumbItem>
          <BreadcrumbItem>部门列表</BreadcrumbItem>
        </Breadcrumb>
      </Col>
      <Col span="16"><Search @goQuery="getQuery"/></Col>
    </Row>
    <Table
      highlight-row
      :height="height"
      :context="self"
      :border="border"
      :stripe="stripe"
      :size="size"
      :columns="columns"
      :data="pageList">
    </Table>
    <Bottom
      ref="bottom"
      @changeList="getList"
      @changeBorder="getBorder"
      @changeStripe="getStripe"
      @changeSize="getSize"
    >
    </Bottom>
  </div>
</template>
<script>
import Search from '../../components/Common/search.vue'
import Bottom from '../../components/Common/bottom.vue'
export default {
  name: 'list',
  components: { Search, Bottom },
  data () {
    return {
      active: 'person',
      name: '',
      keyword: '',
      total: '',
      index: '',
      border: false,
      stripe: false,
      size: 'small',
      height: 450,
      self: this,
      columns: [
        {
          title: '部门名称',
          key: 'name',
          width: 150
        },
        {
          title: '联系电话',
          key: 'phone',
          width: 150
        },
        {
          title: '联系地址',
          key: 'address'
        },
        {
          title: '操作',
          key: 'action',
          width: 150,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.show(params.index)
                  }
                }
              }, '修改'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.remove(params.index)
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      pageList: [
        {
          name: 'John Brown',
          age: 18,
          address: 'New York No. 1 Lake Park'
        },
        {
          name: 'Jim Green',
          age: 24,
          address: 'London No. 1 Lake Park'
        },
        {
          name: 'Joe Black',
          age: 30,
          address: 'Sydney No. 1 Lake Park'
        },
        {
          name: 'Jon Snow',
          age: 26,
          address: 'Ottawa No. 2 Lake Park'
        }
      ]
    }
  },
  methods: {
    getQuery (keyword) {
      this.keyword = keyword
      this.$store.commit('saveKeyword', {
        keyword: keyword
      })
      this.$refs.bottom.query(keyword)
    },
    getList (pageList) {
      this.pageList = pageList
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
    show (index) {
      this.$Modal.info({
        title: 'User Info',
        content: `Name：${this.pageList[index].name}<br>Age：${this.pageList[index].age}<br>Address：${this.pageList[index].address}`
      })
    },
    remove (index) {
      this.pageList.splice(index, 1)
    }
  }
}
</script>
<style scoped>
</style>
