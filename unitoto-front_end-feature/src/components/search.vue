<template>
  <div v-if="true">
    <div trigger="custom" :visible="visible" @on-click="confirmSelect" class="search-main-content">
      <Input v-model="value" placeholder="请输入想要搜索的用户名字" @on-keyup.enter="confirmSearch" @on-blur="hide_dropdown_list" @on-focus="show_dropdown_list_and_get_follow">
        <Button @click="confirmSearch" slot="append" icon="ios-search"></Button>
      </Input>
      <!-- <DropdownMenu slot="list" class="search-menu">
        <div class="search-title">
          <span>please input username to search</span>
        </div>
      </DropdownMenu> -->
    </div>
    <div class="recommend" v-show="if_show_recommend">
      <Row v-for="(row, index_row) in recommendRows" :key="index_row" type="flex" justify="space-between" align="middle" class="code-row-bg" :gutter="16">
        <Col v-for="(col, index_col) in row" :key="(index_row - 1) * 3 + index_col - 1" class="item-img" span="10">
          <p class="user-font">{{col.username}}</p>
          <div class="button-font">
            <Button v-if="col.canFollow" class="follow" type="error" shape="circle" @click="follow(col.userid)">尚未关注，点击可关注</Button>
            <Button v-else class="follow" type="primary" shape="circle" @click="unfollow(col.userid)">已关注，点击可取消关注</Button>
          </div>
        </Col>
      </Row>
    </div>
    <router-view></router-view>
  </div>
</template>

<style>
  .user-font{
    font-family:Helvetica;
    font-size: 30px;
    text-align: center;
  }
  .button-font{
    text-align: center;
  }
  @media screen and (max-width: 700px) {
    .search-main-content {
      width: 80%;
      text-align: left;
      margin: auto;
      display: block !important;
    }
    .recommend {
      width: 95%;
      margin: 20px auto 0 auto;
    }
  }
  @media screen and (min-width: 700px) {
    .search-main-content {
      width: 60%;
      text-align: left;
      margin: auto;
    }
    .recommend {
      width: 60%;
      margin: 40px auto 0 auto;
    }
  }
  .recommend img {
    width: 100%;
  }
  .search-title {
    font-size: 12px;
    padding: 4px 6px;
  }
  .search-title span {
    color: #666;
    font-weight: 700;
  }
  .search-title .link {
    float: right;
  }
  .code-row-bg {
    margin-top: 5px;
  }
  .follow {
    min-width: 50px;
    min-height: 50px;
    font: outline;
    font-family: 'Times New Roman', Times, serif;
    font-size: 150%;
  }
</style>

<script>
  import axios from 'axios'
  export default {
    data () {
      return {
        visible: false,
        users: [],
        recommendRows: [],
        following: [],
        value: '',
        if_show_recommend: true,
        isLogin: true
      }
    },
    methods: {
      confirmSearch: function (event) {
        // send the value to get the result from server
        var that = this
        axios({
          url: '/service/getUserByUserName.do',
          method: 'get',
          params: {
            username: that.value
          }
        }).then(function (res) {
          if (res.data.length === 0) {
            that.$Message.error('该用户名不存在')
          }
          var usersPerRow = []
          that.users = []
          that.recommendRows = []
          for (var i = 0; i < res.data.length; i++) {
            var user = res.data[i]
            user.canFollow = true
            for (var j = 0; j < that.following.length; j++) {
              if (that.following[j].userid === user.userid) {
                user.canFollow = false
                break
              }
            }
            that.users.push(user)
            usersPerRow.push(user)
            if (i !== 0 && i % 3 === 0) {
              that.recommendRows.push(usersPerRow)
              usersPerRow = []
            }
          }
          if (usersPerRow.length !== 0) that.recommendRows.push(usersPerRow)
        }).catch(function (err) {
          console.log(err)
          that.$Message.error('搜索错误，请稍后重试')
        })
      },
      follow: function (targetid) {
        var that = this
        axios({
          url: '/service/addFollow.do',
          method: 'post',
          params: {
            userid: that.$store.state.userId,
            targetid: targetid
          }
        }).then(function (res) {
          if (res.data === false) {
            that.$Message.error('关注失败')
          } else {
            for (var i = 0; i < that.users.length; i++) {
              if (that.users[i].userid === targetid) {
                var user = that.users[i]
                that.following.push(user)
                that.users[i].canFollow = false
                break
              }
            }
          }
        }).catch(function (err) {
          console.log(err)
          that.$Message.error('发生错误，请稍后重试')
        })
      },
      unfollow: function (targetid) {
        var that = this
        axios({
          url: '/service/deleteFollow.do',
          method: 'post',
          params: {
            userid: that.$store.state.userId,
            targetid: targetid
          }
        }).then(function (res) {
          if (res.data === false) {
            that.$Message.error('取消关注失败')
          } else {
            for (var i = 0; i < that.users.length; i++) {
              if (that.users[i].userid === targetid) {
                that.users[i].canFollow = true
                break
              }
            }
            var newFollow = []
            for (var j = 0; j < that.following.length; j++) {
              if (that.following[j].userid !== targetid) {
                newFollow.push(that.following[j])
              }
            }
            that.following = newFollow
          }
        }).catch(function (err) {
          console.log(err)
          that.$Message.error('发生错误，请稍后重试')
        })
      },
      confirmSelect: function (name) {
        this.value = name
        this.visible = false
      },
      hide_dropdown_list: function () {
        this.visible = false
        this.if_show_recommend = true
      },
      show_dropdown_list_and_get_follow: function () {
        var that = this
        this.visible = true
        this.if_show_recommend = false
        axios({
          url: '/service/getFollowings.do',
          method: 'get',
          params: {
            userid: that.$store.state.userId
          }
        }).then(function (res) {
          that.following = []
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].canFollow = false
            that.following.push(res.data[i])
          }
        }).catch(function (err) {
          console.log('获取用户关注人列表失败')
          that.$Message.error(err)
        })
      }
    },
    created: function () {
      var that = this
      if (this.$store.state.userId === '') {
        this.isLogin = false
        this.$Message.error('您尚未登录，3s后回到主页')
        var c = setInterval(function () {
          that.$router.push('/')
          clearInterval(c)
        }, 3000)
      } else {
        this.isLogin = true
      }
    },
    watch: {
      value: function (curVal, oldVal) {
        // this.visible = true
      }
    }
  }
</script>

<style>
  .search-input {
    position: absolute;
    left: 0;
    right: 0;
    width: 50%;
    margin: auto;
  }
  .demo-auto-complete-item{
    padding: 4px 0;
    border-bottom: 1px solid #F6F6F6;
  }
  .demo-auto-complete-group{
    font-size: 12px;
    padding: 4px 6px;
  }
  .demo-auto-complete-group span{
    color: #666;
    font-weight: bold;
  }
  .demo-auto-complete-group a{
    float: right;
  }
  .demo-auto-complete-count{
    float: right;
    color: #999;
  }
  .demo-auto-complete-more{
    display: block;
    margin: 0 auto;
    padding: 4px;
    text-align: center;
    font-size: 12px;
  }
</style>