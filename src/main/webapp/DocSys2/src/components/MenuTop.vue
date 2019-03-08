<template>
  <Menu mode="horizontal" theme="dark" active-name="1">
    <div class="layout-logo"></div>
    <div class="layout-nav">
      <MenuItem name="0" to="/">
        <Icon type="ios-navigate"></Icon>
        首页
      </MenuItem>
      <MenuItem name="1" to="/First">
        <Icon type="ios-navigate"></Icon>
        综合管理
      </MenuItem>
      <MenuItem name="2">
        <Icon type="ios-keypad"></Icon>
        系统管理
      </MenuItem>
      <MenuItem name="3">
        <Icon type="ios-analytics"></Icon>
        个人中心
      </MenuItem>
      <MenuItem name="4">
        <Icon type="ios-paper"></Icon>
        退出
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
    width: 600px;
    margin: 0 auto;
    margin-right: 20px;
  }
</style>
