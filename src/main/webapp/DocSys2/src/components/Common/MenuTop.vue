<template>
  <Menu mode="horizontal" theme="dark" :active-name="menuTop" @on-select="onSelect">
    <div class="layout-logo"></div>
    <div class="layout-nav">
      <MenuItem name="Main" to="/Main">
        <Icon type="ios-navigate"></Icon>
        首页
      </MenuItem>
      <MenuItem name="File" to="/File">
        <Icon type="ios-navigate"></Icon>
        档案管理
      </MenuItem>
      <MenuItem name="User" to="/User">
        <Icon type="ios-analytics"></Icon>
        用户管理
      </MenuItem>
      <MenuItem name="Department" to="/Department">
        <Icon type="ios-analytics"></Icon>
        部门管理
      </MenuItem>
      <MenuItem name="Info" to="/Info">
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
export default {
  name: 'MenuTop',
  data () {
    return {
      menuTop: 'Main'
    }
  },
  methods: {
    onSelect (name) {
      this.menuTop = name
    }
  }
}
</script>

<style scoped>
  .layout-logo{
    width: 100px;
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
