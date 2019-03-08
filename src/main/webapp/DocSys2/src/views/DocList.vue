<template>
  <div>
    <Breadcrumb :style="{margin: '24px 0'}">
      <BreadcrumbItem>Home</BreadcrumbItem>
      <BreadcrumbItem>Components</BreadcrumbItem>
      <BreadcrumbItem>Layout</BreadcrumbItem>
    </Breadcrumb>
    <Content :style="{padding: '24px', minHeight: '800px', background: '#fff'}">
      Option1
    </Content>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style scoped>

</style>
