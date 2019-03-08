<template>
  <Layout>
    <Sider hide-trigger :style="{background: '#fff'}">
      <MenuLeft/>
    </Sider>
    <Layout :style="{padding: '0 24px 24px'}">
      <router-view/>
    </Layout>
  </Layout>
</template>

<script>
import MenuLeft from '../components/MenuLeft.vue'
export default {
  name: 'Main',
  components: {
    MenuLeft
  }
}
</script>

<style scoped>

</style>
