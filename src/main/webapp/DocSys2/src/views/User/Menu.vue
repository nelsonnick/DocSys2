<template>
  <Menu :active-name="menuLeft" theme="light" width="auto" @on-select="onSelect">
    <MenuItem name="List" to="/User/List">用户列表</MenuItem>
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
