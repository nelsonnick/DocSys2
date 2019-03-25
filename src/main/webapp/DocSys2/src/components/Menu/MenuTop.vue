<template>
  <Menu mode="horizontal" theme="dark" active-name="0">
    <div class="layout-logo"></div>
    <div class="layout-nav">
      <MenuItem name="0" to="/Main">
        <Icon type="ios-navigate"></Icon>
        首页
      </MenuItem>
      <MenuItem name="1" to="/File">
        <Icon type="ios-navigate"></Icon>
        档案管理
      </MenuItem>
      <MenuItem name="2" to="/Person">
        <Icon type="ios-keypad"></Icon>
        人员管理
      </MenuItem>
      <MenuItem name="3" to="/User">
        <Icon type="ios-analytics"></Icon>
        用户管理
      </MenuItem>
      <MenuItem name="4" to="/Department">
        <Icon type="ios-analytics"></Icon>
        部门管理
      </MenuItem>
      <MenuItem name="5" to="/Power">
        <Icon type="ios-analytics"></Icon>
        权限管理
      </MenuItem>
      <MenuItem name="6" to="/Info">
        <Icon type="ios-analytics"></Icon>
        个人信息
      </MenuItem>
      <MenuItem name="7">
        <Icon type="ios-paper"></Icon>
        退出系统
      </MenuItem>
    </div>
  </Menu>
</template>
<script>
export default {
  name: 'MenuTop'
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
