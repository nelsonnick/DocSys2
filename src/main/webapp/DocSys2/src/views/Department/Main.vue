<template>
  <Layout>
    <Sider hide-trigger :style="{background: '#fff'}">
      <Menu/>
    </Sider>
    <Layout :style="{padding: '0 24px 24px'}">
      <router-view/>
    </Layout>
  </Layout>
</template>

<script>
import Menu from './Menu'
export default {
  name: 'Main',
  components: {
    Menu
  }
}
</script>

<style scoped>

</style>
