<template>
  <Menu mode="horizontal" theme="dark" :active-name="menuTop" @on-select="onSelect">
    <div class="layout-logo"></div>
    <div class="layout-nav">
      <MenuItem name="Main" to="/Main">
        <Icon type="ios-navigate"></Icon>
        当前用户：{{name}}
      </MenuItem>
      <MenuItem name="File" to="/File" v-cloak v-if="notAdmin">
        <Icon type="ios-navigate"></Icon>
        档案管理
      </MenuItem>
      <MenuItem name="User" to="/User" v-cloak v-if="admin">
        <Icon type="ios-analytics"></Icon>
        用户管理
      </MenuItem>
      <MenuItem name="Department" to="/Department" v-cloak v-if="admin">
        <Icon type="ios-analytics"></Icon>
        部门管理
      </MenuItem>
      <MenuItem name="Info" to="/Info" v-cloak v-if="notAdmin">
        <Icon type="ios-analytics"></Icon>
        个人信息
      </MenuItem>
      <MenuItem name="Exit">
        <Icon type="ios-paper"></Icon>
        退出系统
      </MenuItem>
    </div>
  </Menu>
</template>
<script>
import * as API from './API.js'
import axios from 'axios'
export default {
  name: 'MenuTop',
  data () {
    return {
      name: '',
      menuTop: 'Main',
      admin: false,
      notAdmin: false
    }
  },
  created: function () {
    axios.get(API.isAdmin).then(res => {
      if (res.data.toString() === '1') {
        this.admin = true
        this.notAdmin = false
      } else {
        this.admin = false
        this.notAdmin = true
      }
    }).catch(res => {
      this.$Loading.error()
      this.$Notice.error({
        title: '服务器内部错误，无法获取用户!'
      })
    })
    axios.get(API.name).then(res => {
      this.name = res.data.toString()
    }).catch(res => {
      this.$Loading.error()
      this.$Notice.error({
        title: '服务器内部错误，无法获取用户姓名!'
      })
    })
  },
  methods: {
    onSelect (name) {
      this.menuTop = name
      if (name.toString() === 'Exit') {
        window.location.href = '/logout'
      }
    }
  }
}
</script>

<style scoped>
  .layout-logo{
    width: 1px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
  }
  .layout-nav{
    width: 1200px;
    margin: 0 auto;
    margin-right: 20px;
  }
</style>
