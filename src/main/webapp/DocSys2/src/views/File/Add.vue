<template>
  <div>
    <Breadcrumb :style="{margin: '24px 0'}">
      <BreadcrumbItem>档案管理</BreadcrumbItem>
      <BreadcrumbItem>新增档案</BreadcrumbItem>
    </Breadcrumb>
    <Content :style="{padding: '24px', minHeight: '500px', background: '#fff'}">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
        <Row>
          <Col span="8">
            <FormItem label="档案编号" prop="code">
              <Input v-model="formValidate.code" placeholder="请输入档案编号"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="人员姓名" prop="name">
              <Input v-model="formValidate.name" placeholder="请输入姓名" maxlength="20"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="证件号码" prop="number">
              <Input v-model="formValidate.number" placeholder="请输入18位身份证号码" maxlength="18"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem label="联系电话" prop="phone">
              <Input v-model="formValidate.phone" placeholder="请输入11位手机号码" maxlength="11"></Input>
            </FormItem>
          </Col>
          <Col span="16">
            <FormItem label="联系地址" prop="address">
              <Input v-model="formValidate.address" placeholder="请输入联系地址"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem label="存档原因" prop="reason">
              <Input v-model="formValidate.reason" placeholder="请输入存档原因"></Input>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="档案来源" prop="source" >
              <Input v-model="formValidate.source" placeholder="请输入档案来源"></Input>
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
              <Select v-model="formValidate.check" placeholder="请选择信息整理情况">
                <Option value="1">已完成</Option>
                <Option value="2">未完成</Option>
                <Option value="3">整理中</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="档案年龄" prop="age">
              <Select v-model="formValidate.age" placeholder="请选择档案年龄">
                <Option value="1">与身份证一致</Option>
                <Option value="2">早于身份证</Option>
                <Option value="3">晚于身份证</Option>
                <Option value="4">未知</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="退休年月" prop="retire">
              <Input v-model="formValidate.retire" placeholder="请输入6位退休年月" maxlength="6"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="档案材料" prop="inside">
              <Input v-model="formValidate.inside" type="textarea" :autosize="{minRows: 2,maxRows: 2}" placeholder="如有必要，请输入档案所含材料"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="备注信息" prop="desc">
              <Input v-model="formValidate.desc" type="textarea" :autosize="{minRows: 2,maxRows: 2}" placeholder="如有必要，请输入备注信息"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            &nbsp;
          </Col>
          <Col span="8">
            <FormItem>
              <Button type="primary" @click="handleSubmit('formValidate')">保存</Button>
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
        if (IdentityCodeValid(value) || value === '000000000000000000') {
          callback()
        } else {
          callback(new Error('身份证号码错误，请进行核实'))
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
        desc: ''
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
          { required: true, message: '存档原因为必填项', trigger: 'blur' }
        ],
        source: [
          { required: true, message: '档案来源为必填项', trigger: 'blur' }
        ],
        delivery: [
          { required: true }
        ],
        check: [
          { required: true }
        ],
        age: [
          { required: true }
        ],
        retire: [
          { required: true, validator: validateRetire, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.$Message.success('Success!')
        } else {
          this.$Message.error('Fail!')
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
