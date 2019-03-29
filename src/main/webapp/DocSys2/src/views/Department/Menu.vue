<template>
  <Menu :active-name="menuLeft" theme="light" width="auto" @on-select="onSelect">
<!--    <MenuItem name="Add" to="/Department/Add">新增部门</MenuItem>-->
    <MenuItem name="List" to="/Department/List">部门列表</MenuItem>
  </Menu>
</template>
<script>
export default {
  name: 'menuLeft',
  data () {
    return {
      menuLeft: ''
    }
  },
  methods: {
    onSelect (name) {
      this.menuLeft = name
    }
  }
}
</script>

<style scoped>
</style>
