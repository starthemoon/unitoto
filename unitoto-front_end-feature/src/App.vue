<template>
  <div id="app">
    <Menu ref='menu' mode="horizontal" class='main-header' v-on:on-select='jump'>
      <div class="main-wrap-header">
        <a v-on:click='jump("main")' class="main-logo"><img src="./assets/logo.png" /></a>
        <Button type="ghost" icon="ios-search" class="main-search" v-on:click='jump("search")'>Search</Button>
        <div class="main-menu">
          <div class="main-userIcon" v-on:click='jump("user")'>
            <Avatar icon="person" />
          </div>
        </div>
      </div>
    </Menu>
    <router-view class='main-content'/>

    <Modal v-model='isLogin' :closable=false>
      <Tabs value='signin'>
        <TabPane label='登录' name='signin'>
          <div class='app-login'>
            <Form ref='loginData' :model='loginData' :rules='ruleValidate'>
              <FormItem prop='loginUserid'>
                <Input type='text' v-model='loginData.loginUserid' placeholder='请输入用户ID'></Input>
              </FormItem>
              <FormItem prop='loginPassword'>
                <Input type='password' v-model='loginData.loginPassword' placeholder='请输入密码'></Input>
              </FormItem>
              <FormItem>
                <Button type='primary' @click='login' long>登录</Button>
              </FormItem>
            </Form>
          </div>
        </TabPane>
        <TabPane label='注册' name='signup'>
          <Form ref='signupData' :model='signupData' :rules='ruleValidate'>
            <FormItem prop='signupUserid'>
              <Input type='text' v-model='signupData.signupUserid' placeholder='请输入用户ID，日后不可修改'></Input>
            </FormItem>
            <FormItem prop='signupUsername'>
              <Input type='text' v-model='signupData.signupUsername' placeholder='请输入用户名'></Input>
            </FormItem>
            <FormItem prop='signupPassword'>
              <Input type='password' v-model='signupData.signupPassword' placeholder='请输入密码'></Input>
            </FormItem>
            <FormItem prop='signupCpassword'>
              <Input type='password' v-model='signupData.signupCpassword' placeholder='请再次输入密码'></Input>
            </FormItem>
            <FormItem>
              <Button type='primary' @click='signup' long>注册</Button>
            </FormItem>
          </Form>
        </TabPane>
      </Tabs>
      <div slot='footer'></div>
    </Modal>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
export default {
  data: function () {
    var that = this
    var signinPasswordValidate = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      }
      callback()
    }
    var signupPasswordValidate = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      }
      callback()
    }
    var signupCpasswordValidate = function (rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== that.signupData.signupPassword) {
        callback(new Error('两次输入的密码不匹配'))
      } else {
        callback()
      }
    }
    return {
      isSearch: true,
      isLogin: false,
      loginData: {
        loginUserid: '',
        loginPassword: ''
      },
      signupData: {
        signupUserid: '',
        signupPassword: '',
        signupUsername: '',
        signupCpassword: ''
      },
      ruleValidate: {
        loginUserid: [
          { required: true, message: '请输入用户唯一标识', trigger: 'blur' }
        ],
        signupUserid: [
          { required: true, message: '请输入用户唯一标识', trigger: 'blur' }
        ],
        signupUsername: [
          { required: true, message: '请输入用户唯一标识', trigger: 'blur' }
        ],
        signupPassword: [
          { validator: signupPasswordValidate, trigger: 'blur' }
        ],
        signupCpassword: [
          { validator: signupCpasswordValidate, trigger: 'blur' }
        ],
        loginPassword: [
          { validator: signinPasswordValidate, trigger: 'blur' }
        ]
      }
    }
  },
  name: 'app',
  methods: {
    jump: function (name) {
      switch (name) {
        case 'main':
          name = '/'
          break
        case 'map':
          name = '/map'
          break
        case 'community':
          name = '/community'
          break
        case 'search':
          name = '/search'
          break
        case 'user':
          name = '/user'
          if (this.$store.state.userId === '') {
            this.isLogin = true
            return
          }
          break
        case 'group':
          name = '/group'
          break
        default:
          break
      }
      this.$router.push(name)
      if (name !== '/community' && name !== '/map') {
        this.clearMenuActiveState()
      }
    },
    login: function () {
      var that = this
      this.$refs.loginData.validate((valid) => {
        if (valid) {
          axios({
            method: 'post',
            url: '/service/login.do',
            data: qs.stringify({
              userid: this.loginData.loginUserid,
              userpassword: this.loginData.loginPassword
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
          })
            .then(function (res) {
              if (res.data) {
                that.$store.commit('setUser', that.loginData.loginUserid)
                that.loginData = {}
                that.signupData = {}
                that.isLogin = false
                that.$Message.success('登录成功')
                that.$router.push('/user')
              } else {
                that.$Message.error('登录失败，请稍后重试')
              }
            })
            .catch(function (err) {
              console.log(err)
              that.$Message.error('登录失败，请稍后重试')
            })
        } else {
          this.$Message.error('请先完成表单')
        }
      })
    },
    signup: function () {
      var that = this
      this.$refs.signupData.validate((valid) => {
        if (valid) {
          axios({
            method: 'post',
            url: '/service/register.do',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: qs.stringify({
              userid: that.signupData.signupUserid,
              userpassword: that.signupData.signupPassword,
              username: that.signupData.signupUsername
            })
          })
            .then(function (res) {
              if (res.data) {
                that.loginData = {}
                that.signupData = {}
                that.isLogin = false
                that.$Message.success('注册成功')
              } else {
                that.$Message.error('注册失败，请稍后重试')
              }
            })
            .catch(function (err) {
              console.log(err)
              that.$Message.error('注册失败，请稍后重试')
            })
        } else {
          this.$Message.error('请先完成表单')
        }
      })
    },
    clearMenuActiveState: function () {
      this.$nextTick(function () {
        this.$refs['menu'].currentActiveName = ''
      })
    }
  }
}
</script>

<style>
body {
  background-color: #EEEEEE !important;
}
.search {
  display: none;
}
.main-logo {
  float: left;
}
.main-menu {
  float: right;
}
.main-logo img {
  height: 35px;
  margin-top: 10px;
}
.main-wrap-header {
  margin: 0 auto;
  width: 80%;
}
.main-userIcon {
  float: right;
  margin-left: 8px;
}
.main-search {
  float: left;
  margin-top: 14px;
  margin-left: 3%;
  width: 25%;
}
.main-header {
  position: fixed !important;
  width: 100%;
  top: 0;
}
.main-content {
  margin-top: 60px;
}
.main-userIcon {
  margin-top: 12px;
}
.ivu-tabs-nav .ivu-tabs-tab {
  height: 40px;
}
.app-login {
  margin: 20% auto;
}
@media screen and (max-width: 700px){
  .main-wrap-header {
    margin: 0 auto;
    width: 96%;
  }
  .main-search {
    float: left;
    margin-top: 14px;
    margin-left: 2%;
  }
}
</style>