<template>
  <div>
    <Breadcrumb :style="{margin: '24px 0'}">
      <BreadcrumbItem>档案管理</BreadcrumbItem>
      <BreadcrumbItem>档案列表</BreadcrumbItem>
    </Breadcrumb>
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
    <div class="right">
      <Page
        ref="pages"
        @goList="getList"
        @savePageCurrent="saveCurrent"
        @savePageCurrentAndKeyword="CurrentAndKeyword"
        :queryURL="query"
        :totalURL="total"
        :keyword="keyword"
      >
      </Page>
    </div>
  </div>
</template>
<script>
  import Search from '../../components/Common/search.vue'
  import Page from '../../components/Common/page.vue'
  export default {
    name: 'list',
    components: {Search, Page},
    data () {
      return {
        userName: '',
        LocationId: '',
        sys: false,
        active: 'person',
        name: '',
        keyword: '',
        pageList: [],
        pageTotal: '',
        index: '',
        border: false,
        stripe: false,
        size: 'small',
        height: 450,
        self: this,
        addPerson: true,
        columns: [
          {
            title: 'Name',
            key: 'name',
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    type: 'person'
                  }
                }),
                h('strong', params.row.name)
              ]);
            }
          },
          {
            title: 'Age',
            key: 'age'
          },
          {
            title: 'Address',
            key: 'address'
          },
          {
            title: 'Action',
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
                }, 'View'),
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
                }, 'Delete')
              ]);
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
      show (index) {
        this.$Modal.info({
          title: 'User Info',
          content: `Name：${this.pageList[index].name}<br>Age：${this.pageList[index].age}<br>Address：${this.pageList[index].address}`
        })
      },
      remove (index) {
        this.pageList.splice(index, 1);
      }
    }
  }
</script>
<style scoped>
  .right {
    margin: 15px;
    border-radius: 4px;
    float: right;
  }
</style>
