<template>
  <div>
    <Breadcrumb :style="{margin: '24px 0'}">
      <BreadcrumbItem>用户信息</BreadcrumbItem>
      <BreadcrumbItem>修改密码</BreadcrumbItem>
    </Breadcrumb>
    <Content :style="{padding: '24px', minHeight: '500px', background: '#fff'}">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
        <Row>
          <Col span="8">
            <FormItem label="用户姓名" prop="name">
              <Input v-model="formValidate.name" placeholder="请输入用户姓名" :maxlength="30" disabled></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="登录名称 " prop="login">
              <Input v-model="formValidate.login" placeholder="请输入登录名称" :maxlength="30" disabled>></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="所属部门" prop="departmentId">
              <Select v-model="formValidate.departmentId" placeholder="请选择所属部门" disabled>
                <Option v-for="item in departmentList" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem label="原始密码" prop="pass1">
              <Input v-model="formValidate.pass1" placeholder="请输入原始密码" :maxlength="30"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="新密码 " prop="pass2">
              <Input v-model="formValidate.pass2" placeholder="请输入新密码" :maxlength="30"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="再输一遍" prop="pass3">
              <Input v-model="formValidate.pass3" placeholder="请再次输入新密码" :maxlength="30"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            &nbsp;
          </Col>
          <Col span="8">
            <FormItem>
              <Button type="primary" @click="goSave('formValidate')" :disabled="dis">修改</Button>
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
  name: 'Main',
  data () {
    return {
      dis: false,
      formValidate: {
        name: '',
        login: '',
        departmentId: '',
        pass1: '',
        pass2: '',
        pass3: ''
      },
      departmentList: [],
      ruleValidate: {
        pass1: [
          { required: true, message: '请输入原始密码', trigger: 'blur' }
        ],
        pass2: [
          { required: true, message: '请输入新密码', trigger: 'change' }
        ],
        pass3: [
          { required: true, message: '请输入新密码', trigger: 'change' }
        ]
      }
    }
  },
  created: function () {
    this.getDepartment()
    this.fetchData()
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
          axios.get(API.alt, {
            params: {
              pass1: this.formValidate.pass1,
              pass2: this.formValidate.pass2,
              pass3: this.formValidate.pass3
            }
          }).then(res => {
            if (res.data === 'OK') {
              this.$Loading.finish()
              this.$Message.success('修改成功!')
              this.$Notice.success({
                title: '操作完成!',
                desc: '用户：' + this.formValidate.name + '已修改密码！'
              })
              setTimeout(() => {
                this.$router.push({ path: '/Main' })
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
              title: '服务器内部错误，无法保存新密码!'
            })
          })
        } else {
          this.$Message.error('请核实填写的信息!')
        }
      })
    },
    goReset (name) {
      this.getDepartment()
      this.fetchData(this.$route.params.id)
    },
    fetchData () {
      axios.get(API.getUser
      ).then(res => {
        this.formValidate.name = res.data.name
        this.formValidate.login = res.data.login
        this.formValidate.departmentId = res.data.department_id.toString()
      }).catch(res => {
        this.$Notice.error({
          title: '服务器内部错误，无法获取数据!'
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
