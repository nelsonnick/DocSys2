<template>
  <div>
    <Breadcrumb :style="{margin: '24px 0'}">
      <BreadcrumbItem>档案管理</BreadcrumbItem>
      <BreadcrumbItem>修改流转信息</BreadcrumbItem>
    </Breadcrumb>
    <Content :style="{padding: '24px', minHeight: '500px', background: '#fff'}">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
        <Row>
          <Col span="8">
            <FormItem label="档案编号" prop="code">
              <Input v-model="formValidate.code" placeholder="请输入档案编号" disabled></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="人员姓名" prop="name">
              <Input type="text" v-model="formValidate.name" placeholder="请输入姓名" :maxlength="20" disabled></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="证件号码" prop="number">
              <Input v-model="formValidate.number" placeholder="请输入18位身份证号码" :maxlength="18" disabled></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem label="联系电话" prop="phone">
              <Input v-model="formValidate.phone" placeholder="请输入11位手机号码" :maxlength="11" disabled></Input>
            </FormItem>
          </Col>
          <Col span="16">
            <FormItem label="联系地址" prop="address">
              <Input v-model="formValidate.address" placeholder="请输入联系地址" disabled></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem label="流转原因" prop="reason">
              <Input v-model="formValidate.reason" placeholder="请输入流转原因"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="档案方向" prop="source">
              <Input v-model="formValidate.source" placeholder="请输入档案方向"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="传递方式" prop="delivery">
              <Select v-model="formValidate.delivery" placeholder="请选择传递方式">
                <Option value="1">个人</Option>
                <Option value="2">邮寄</Option>
                <Option value="3">专人</Option>
                <Option value="4">其他</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem label="信息整理" prop="check">
              <Select v-model="formValidate.check" placeholder="请选择信息整理情况" disabled>
                <Option value="1">已完成</Option>
                <Option value="2">未完成</Option>
                <Option value="3">整理中</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="档案年龄" prop="age">
              <Select v-model="formValidate.age" placeholder="请选择档案年龄" disabled>
                <Option value="1">与身份证一致</Option>
                <Option value="2">早于身份证</Option>
                <Option value="3">晚于身份证</Option>
                <Option value="4">未知</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="退休年月" prop="retire">
              <Input v-model="formValidate.retire" placeholder="请输入6位退休年月" :maxlength="6" disabled></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="档案材料" prop="inside">
              <Input v-model="formValidate.inside" type="textarea" :autosize="{minRows: 2,maxRows: 2}" disabled
                     placeholder="如有必要，请输入档案所含材料"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="备注信息" prop="remark">
              <Input v-model="formValidate.remark" type="textarea" :autosize="{minRows: 2,maxRows: 2}" disabled
                     placeholder="如有必要，请输入备注信息"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            &nbsp;
          </Col>
          <Col span="8">
            <FormItem>
              <Button type="primary" @click="goSave('formValidate')" :disabled="dis">保存</Button>
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
import IdentityCodeValid from '../../plugins/checkId'
export default {
  data () {
    const validateName = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入人员姓名'))
      } else if (/^[\u4E00-\u9FA5]{2,8}$/.test(value)) {
        callback()
      } else {
        callback(new Error('人员姓名应为2-8个汉字'))
      }
    }
    const validateNumber = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入18位身份证号码'))
      } else if (/^\d{18}$|^\d{17}(\d|X)$/.test(value)) {
        var tip = IdentityCodeValid(value)
        if (tip === '' || value === '000000000000000000') {
          callback()
        } else {
          callback(new Error(tip))
        }
      } else {
        callback(new Error('身份证号码应为18位，如末尾的X需要大写'))
      }
    }
    const validatePhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入11位手机号码'))
      } else if (/^[1][0-9]{10}$/.test(value) || value === '00000000000') {
        callback()
      } else {
        callback(new Error('手机号码应为以1开头的11位数字'))
      }
    }
    const validateRetire = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入退休年月'))
      } else if (/[20]\d{4}$/.test(value) || value === '000000') {
        callback()
      } else {
        callback(new Error('退休年月应为以20开头的6位数字'))
      }
    }
    return {
      dis: false,
      formValidate: {
        code: '',
        name: '',
        number: '',
        phone: '',
        address: '',
        reason: '个人要求',
        source: '未知',
        delivery: '1',
        check: '1',
        age: '1',
        retire: '',
        inside: '',
        remark: ''
      },
      ruleValidate: {
        code: [
          { required: true, message: '请输入档案编号', trigger: 'blur' },
          { type: 'string', min: 4, message: '档案编号不得少于4个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        number: [
          { required: true, validator: validateNumber, trigger: 'blur' }
        ],
        phone: [
          { required: true, validator: validatePhone, trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入联系地址', trigger: 'blur' }
        ],
        reason: [
          { required: true, message: '还档原因为必填项', trigger: 'blur' }
        ],
        source: [
          { required: true, message: '档案来源为必填项', trigger: 'blur' }
        ],
        delivery: [
          { required: true, message: '传递方式为必填项' }
        ],
        check: [
          { required: true, message: '信息整理为必填项' }
        ],
        age: [
          { required: true, message: '档案年龄为必填项' }
        ],
        retire: [
          { required: true, validator: validateRetire, trigger: 'blur' }
        ]
      }
    }
  },
  created: function () {
    this.fetchData(this.$route.params.id)
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'fetchData'
  },
  methods: {
    goSave (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.dis = true
          this.$Loading.start()
          axios.get(API.editFlow, {
            params: {
              id: this.$route.params.id,
              reason: this.formValidate.reason,
              source: this.formValidate.source,
              delivery: this.formValidate.delivery
            }
          }).then(res => {
            if (res.data === 'OK') {
              this.$Loading.finish()
              this.$Message.success('重存成功!')
              this.$Notice.success({
                title: '操作完成!',
                desc: '档案：' + this.formValidate.code + '已修改流转信息！'
              })
              setTimeout(() => {
                this.$router.push({ path: '/File/Flow' })
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
              title: '服务器内部错误，无法保存档案信息!'
            })
          })
        } else {
          this.$Message.error('请核实填写的信息!')
        }
      })
    },
    goReset () {
      this.fetchData(this.$route.params.id)
    },
    goBack () {
      this.$router.push({ path: '/File/Flow' })
    },
    fetchData (id) {
      axios.get(API.getFlow,
        { params: { id: id } }
      ).then(res => {
        this.formValidate.code = res.data.code
        this.formValidate.name = res.data.name
        this.formValidate.number = res.data.number
        this.formValidate.phone = res.data.phone
        this.formValidate.address = res.data.address
        this.formValidate.check = res.data.check.toString()
        this.formValidate.age = res.data.age.toString()
        this.formValidate.retire = res.data.retire
        this.formValidate.inside = res.data.inside
        this.formValidate.remark = res.data.remark
        this.formValidate.reason = res.data.reason
        this.formValidate.source = res.data.source
        this.formValidate.delivery = res.data.delivery.toString()
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
