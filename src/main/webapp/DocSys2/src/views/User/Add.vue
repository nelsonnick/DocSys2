<template>
  <div>
    <Breadcrumb :style="{margin: '24px 0'}">
      <BreadcrumbItem>用户管理</BreadcrumbItem>
      <BreadcrumbItem>新增用户</BreadcrumbItem>
    </Breadcrumb>
    <Content :style="{padding: '24px', minHeight: '500px', background: '#fff'}">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
        <Row>
          <Col span="8">
            <FormItem label="用户姓名" prop="name">
              <Input v-model="formValidate.name" placeholder="请输入用户姓名" :maxlength="30"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="登录名称 " prop="login">
              <Input v-model="formValidate.login" placeholder="请输入登录名称" :maxlength="30"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="所属部门" prop="departmentId">
              <Select v-model="formValidate.departmentId" placeholder="请选择所属部门">
                <Option v-for="item in departmentList" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            &nbsp;
          </Col>
          <Col span="8">
            <FormItem>
              <Button type="primary" @click="goSave('formValidate')" :disabled="dis">新增</Button>
              <Button @click="goReset('formValidate')" style="margin-left: 8px" :disabled="dis">重置</Button>
              <Button type="dashed" style="margin-left: 8px" @click="goBack" :disabled="dis">返回</Button>
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
        callback(new Error('请输入人员姓名'))
      } else if (/^[\u4E00-\u9FA5]{2,30}$/.test(value)) {
        callback()
      } else {
        callback(new Error('人员姓名应为2-30个汉字'))
      }
    }
    return {
      dis: false,
      formValidate: {
        name: '',
        login: '',
        departmentId: ''
      },
      departmentList: [],
      ruleValidate: {
        name: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        login: [
          { required: true, message: '请输入登录名称', trigger: 'blur' }
        ],
        departmentId: [
          { required: true, message: '请选择所属部门', trigger: 'change' }
        ]
      }
    }
  },
  created: function () {
    this.getDepartment()
  },
  methods: {
    // 一个变量指向Function，防止有些前端编译工具报错
    evil (fn) {
      let Fn = Function
      return new Fn('return ' + fn)()
    },
    getDepartment () {
      axios.get(API.getDepartment).then(res => {
        this.departmentList = this.evil('(' + res.data + ')')
      }).catch(res => {
        this.$Loading.error()
        this.$Notice.error({
          title: '服务器内部错误，无法获取部门列表!'
        })
      })
    },
    goSave (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.dis = true
          this.$Loading.start()
          axios.get(API.add, {
            params: {
              name: this.formValidate.name,
              login: this.formValidate.login,
              departmentId: this.formValidate.departmentId
            }
          }).then(res => {
            if (res.data === 'OK') {
              this.$Loading.finish()
              this.$Message.success('新增成功!')
              this.$Notice.success({
                title: '操作完成!',
                desc: '用户：' + this.formValidate.name + '已保存！'
              })
              setTimeout(() => {
                this.$router.push({ path: '/User/List' })
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
              title: '服务器内部错误，无法保存用户信息!'
            })
          })
        } else {
          this.$Message.error('请核实填写的信息!')
        }
      })
    },
    goReset (name) {
      this.$refs[name].resetFields()
    },
    goBack () {
      this.$router.push({ path: '/User/List' })
    }
  }
}
</script>
<style scoped>
</style>
