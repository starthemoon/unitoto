<template>
  <div v-if='isLogin'>
    <div class="message-font">
      <p class="user_font">{{name}}</p>
      <p class="Tel-font">
        <span v-if='tel && tel.length > 0'>Tel：{{tel?tel:''}}</span>
        <span v-if='Email && Email.length > 0'>E-mail: {{Email}}</span>
      </p>
      <p class="show" v-if='show && show.length > 0'>个人简介： {{show}}</p>
    </div>
    <div class = "tab-div">
      <Tabs value="name1" type='card'>
        <TabPane label="相 册" icon="images">
          <div class="user-recommend" v-show="if_show_recommend">
            <!-- <Row v-for="(row, index_row) in photos" :key="index_row" type="flex" justify="space-between" align="middle" class="code-row-bg" :gutter="16">
              <Col v-for="(col, index_col) in row" :key="(index_row - 1) * 3 + index_col - 1" class="item-img" span="8"><img :src=col.src :alt="col.alt"></Col>
            </Row> -->
            <Row v-for='item in photos' :key='item' class="code-row-bg">
              <Col span='23' class="item-img">
                <img v-bind:src='item' class='imgcard-image' />
                <br><br>
              </Col>
            </Row>
          </div>
        </TabPane>
        <TabPane label="关 注" icon="eye">
          <Row>
            <Col>
              <Card dis-hover class="following-font">
                <ul>
                  <li v-for='item in following' :key='item'>
                    {{ item }}
                  </li>
                </ul>
              </Card>
            </Col>
          </Row>
        </TabPane>
      </Tabs>
      <div class = "log-out" @click='logout'>
        <Button type="primary" shape="circle" icon="log-out">退出登录</Button>
      </div>
    </div>

    <Modal v-model='isShown' ref='preview'>
      <img :src='photos[shownImg]' class='user-opimg'/>
    </Modal>
  </div>
</template>

<style>
  .imgcard-image {
    max-width: 100%;
    display: block;
    margin: 0 auto;
  }
  .log-out{
    padding: 10px 5px;
    margin-top: 50px;
    text-align: center
  }
  .user-opimg {
    width: 100%;
  }
  .message-font{
    font-family:Helvetica;
    font-size: 30px;
    text-align: center;
  }
  .following-font{
    font-family:Helvetica;
    font-size: 20px;
    text-align: center;
  }
  .Tel-font {
    font-size: 13px;
  }
  .show {
    font-size: 14px;
  }
  .tab-div {
    text-align: center;
    padding: 5px 5px;
    height: 40px;
    margin: 0 auto;
  }
  .ivu-tabs-nav {
    height: 40px;
  }
  .user-recommend {
    height: 240px;
  }
  .user-recommend img {
    width: 100%;
  }
  .ivu-tabs {
    margin: 0 atuo;
  }
  @media screen and (max-width: 700px) {
    .user-recommend {
      width: 95%;
      margin: 20px auto 0 auto;
    }
  }
  @media screen and (min-width: 700px) {
    .user-recommend {
      width: 60%;
      margin: 40px auto 0 auto;
    }
  }
  .demo-avatar{
    padding: 10px 5px;
    margin-top: 50px;
    text-align: center
  }
</style>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      photos: [],
      following: [],
      name: '',
      value: '',
      if_show_recommend: true,
      user: '',
      color: '',
      tel: '',
      Email: '',
      show: '',
      isLogin: true,
      isShown: false,
      shownImg: -1
    }
  },
  methods: {
    logout: function () {
      var that = this
      this.$store.commit('reset')
      this.$cookie.delete('username')
      this.$cookie.delete('password')
      this.$Message.success('退出成功，即将返回主界面')
      var c = setInterval(function () {
        that.$router.push('/')
        clearInterval(c)
      }, 1000)
    },
    operateImg: function (index) {
      this.shownImg = index
      this.isShown = true
    }
  },
  created: function () {
    this.user = this.$store.state.userId
    var that = this
    if (this.$store.state.userId === '') {
      this.isLogin = false
      // this.$Message.error('您尚未登录，3s后回到主页')
      var c = setInterval(function () {
        that.$router.push('/')
        clearInterval(c)
      }, 3000)
    } else {
      this.isLogin = true

      axios({
        url: '/service/getUserInformation.do',
        method: 'get',
        params: {
          userid: that.$store.state.userId
        }
      }).then(function (res) {
        that.name = res.data.username
      }).catch(function (err) {
        that.$Message.error('无法从服务器获取内容，请稍后重试')
        console.log(err)
      })

      axios({
        url: '/service/getUserPhotos.do',
        method: 'get',
        params: {
          userid: that.$store.state.userId
        }
      }).then(function (res) {
        var path = 'http://localhost:8080/Unitoto-web/'
        for (var i = 0; i < res.data.length; i++) {
          that.photos.push(path + res.data[i].photoaddress)
        }
      }).catch(function (err) {
        that.$Message.error('无法从服务器获取内容，请稍后重试')
        console.log(err)
      })

      axios({
        url: '/service/getFollowings.do',
        method: 'get',
        params: {
          userid: that.$store.state.userId
        }
      }).then(function (res) {
        for (var i = 0; i < res.data.length; i++) {
          that.following.push(res.data[i].username)
        }
      }).catch(function (err) {
        that.$Message.error('无法从服务器获取内容，请稍后重试')
        console.log(err)
      })
    }
  }
}
</script>
