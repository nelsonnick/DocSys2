<template>
  <div>
    <Breadcrumb :style="{margin: '24px 0'}">
      <BreadcrumbItem>部门管理</BreadcrumbItem>
      <BreadcrumbItem>新增部门</BreadcrumbItem>
    </Breadcrumb>
    <Content :style="{padding: '24px', minHeight: '500px', background: '#fff'}">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
        <Row>
          <Col span="12">
            <FormItem label="部门名称" prop="name">
              <Input v-model="formValidate.name" placeholder="请输入部门名称"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="联系电话" prop="phone">
              <Input v-model="formValidate.phone" placeholder="请输入8位固定电话" :maxlength="8"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="联系地址" prop="address">
              <Input v-model="formValidate.address" placeholder="请输入联系地址"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            &nbsp;
          </Col>
          <Col span="8">
            <FormItem>
              <Button type="primary" @click="handleSubmit('formValidate')" :disabled="dis">保存</Button>
              <Button @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>
            </FormItem>
          </Col>
          <Col span="8">
            &nbsp;
          </Col>
        </Row>
      </Form>
    </Content>
  </div>
</template>

<script>
import * as API from './API.js'
import axios from 'axios'
export default {
  data () {
    const validateName = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入部门名称'))
      } else if (/^[\u4E00-\u9FA5]{2,30}$/.test(value)) {
        callback()
      } else {
        callback(new Error('部门名称应为2-30个汉字'))
      }
    }
    const validatePhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入8位固定号码'))
      } else if (/^\d{8}$/.test(value) || value === '00000000') {
        callback()
      } else {
        callback(new Error('联系电话应为8位数字'))
      }
    }
    return {
      dis: false,
      formValidate: {
        name: '',
        phone: '',
        address: ''
      },
      ruleValidate: {
        name: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        phone: [
          { required: true, validator: validatePhone, trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入联系地址', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.dis = true
          this.$Loading.start()
          axios.get(API.add, {
            params: {
              name: this.formValidate.name,
              phone: this.formValidate.phone,
              address: this.formValidate.address
            }
          }).then(res => {
            if (res.data === 'OK') {
              this.$Loading.finish()
              this.$Message.success('新增成功!')
              this.$Notice.success({
                title: '操作完成!',
                desc: '部门：' + this.formValidate.name + '已保存！'
              })
              setTimeout(() => {
                this.$router.push({ path: '/Department/Add' })
                this.dis = false
                this.$refs[name].resetFields()
              }, 1000)
            } else {
              this.dis = false
              this.$Loading.error()
              this.$Notice.error({
                title: res.data
              })
            }
          }).catch(res => {
            this.dis = false
            this.$Loading.error()
            this.$Notice.error({
              title: '服务器内部错误，无法保存部门信息!'
            })
          })
        } else {
          this.$Message.error('请核实填写的信息!')
        }
      })
    },
    handleReset (name) {
      this.$refs[name].resetFields()
    }
  }
}
</script>
<style scoped>
</style>
