<template>
  <Menu mode="horizontal" theme="dark" :active-name="active" @on-select="MenuClick">
    <div class="layout-nav">
      <MenuItem name="main">
        <Icon type="user"></Icon>
        欢迎：{{ userName }}
      </MenuItem>
      <MenuItem name="IntegratedManagement">
        <Icon type="android-person"></Icon>
        综合管理
      </MenuItem>
      <MenuItem name="SystemManagement">
        <Icon type="android-people"></Icon>
        系统管理
      </MenuItem>
      <MenuItem name="UserManagement">
        <Icon type="ios-people-outline"></Icon>
        用户管理
      </MenuItem>
      <MenuItem name="logout">
        <Icon type="power"></Icon>
        退出登录
      </MenuItem>
    </div>
  </Menu>
</template>
<script>
  import * as BASE from './Base.js'
  export default {
    name: 'menubar',
    props: [ 'active', 'userName'],
    methods: {
      MenuClick (name) {
        if (name.toString() === 'personList') {
          window.location.href = BASE.base + 'person'
        } else {
          window.location.href = BASE.base
        }
      }
    }
  }
</script>
<style scoped>
  .layout-nav {
    width: 1200px;
    margin: 0 auto;
    margin-right: 20px;
  }
</style>
