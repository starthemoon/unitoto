<template>
  <div v-if="true">
    <Dropdown trigger="custom" :visible="visible" @on-click="confirmSelect" class="search-main-content">
      <Input v-model="value" @on-keyup.enter="confirmSearch" @on-blur="hide_dropdown_list" @on-focus="show_dropdown_list">
        <Button @click="confirmSearch" slot="append" icon="ios-search"></Button>
      </Input>
      <DropdownMenu slot="list" class="search-menu">
        <div class="search-title">
          <span>SearchUserByUserName</span>
        </div>
      </DropdownMenu>
    </Dropdown>
    <div class="recommend" v-show="if_show_recommend">
      <Row v-for="(row, index_row) in recommendRows" :key="index_row" type="flex" justify="space-between" align="middle" class="code-row-bg" :gutter="16">
        <Col v-for="(col, index_col) in row" :key="(index_row - 1) * 3 + index_col - 1" class="item-img" span="8"><img src="https://tse3.mm.bing.net/th?id=OIP.CMy2WiMRd-dYJzKGC77WBgHaFj&pid=Api">
        <span>{{col.userName}}</span>
        <Button class="follow" type="primary" shape="circle" @click="follow(col.userId)" ></Button>
        </Col>
      </Row>
    </div>
    <router-view></router-view>
  </div>
</template>

<style>
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
    margin-left: 67%;
  }
</style>

<script>
  import axios from 'axios'
  export default {
    data () {
      return {
        visible: false,
        users: [
          // {
          //   // send the value inputted by user to server to get the matched user list
          //   id: '1',
          //   name: 'test1',
          //   avatar: 'http://img3.3lian.com/2013/c2/14/d/11.jpg'
          // },
          // {
          //   id: '2',
          //   name: 'test2',
          //   avatar: 'http://pic.58pic.com/58pic/13/52/59/34q58PIC3pT_1024.jpg'
          // }
        ],
        recommendRows: [
          // [
          //   {
          //     src: 'http://img3.3lian.com/2013/c2/14/d/11.jpg',
          //     alt: ''
          //   },
          //   {
          //     src: 'http://img3.3lian.com/2013/c2/14/d/11.jpg',
          //     alt: ''
          //   },
          //   {
          //     src: 'http://img3.3lian.com/2013/c2/14/d/11.jpg',
          //     alt: ''
          //   }
          // ],
          // [
          //   {
          //     src: 'http://pic.58pic.com/58pic/14/20/58/95a58PICXQp_1024.jpg',
          //     alt: ''
          //   },
          //   {
          //     src: 'http://img3.3lian.com/2013/c2/14/d/11.jpg',
          //     alt: ''
          //   },
          //   {
          //     src: 'http://img4.imgtn.bdimg.com/it/u=832634338,2138864592&fm=27&gp=0.jpg',
          //     alt: ''
          //   }
          // ],
          // [
          //   {
          //     src: 'http://img5.imgtn.bdimg.com/it/u=806391916,1690025371&fm=11&gp=0.jpg',
          //     alt: ''
          //   },
          //   {
          //     src: 'http://img4.imgtn.bdimg.com/it/u=3201723314,3315056898&fm=27&gp=0.jpg',
          //     alt: ''
          //   },
          //   {
          //     src: 'http://pic.58pic.com/58pic/13/52/59/34q58PIC3pT_1024.jpg',
          //     alt: ''
          //   }
          // ]
        ],
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
          param: {
            userName: that.value
          }
        }).then(function (res) {
          var usersPerRow = []
          that.users = []
          that.recommendRows = []
          for (var i = 0; i < res.data.length; i++) {
            var user = res.data[i]
            that.users.push(user)
            usersPerRow.push(user)
            if (i !== 0 && i % 3 === 0) {
              that.recommendRows.push(usersPerRow)
              usersPerRow = []
            }
          }
          if (usersPerRow !== []) that.recommendRows.push(usersPerRow)
        }).catch(function (err) {
          console.log(err)
          console.log('搜索错误，请稍后重试')
        })
      },
      follow: function (targetid) {
        var that = this
        axios({
          url: '/service/addFollow.do',
          method: 'post',
          param: {
            userid: that.$store.state.userId,
            targetid: targetid
          }
        }).then(function (res) {
        }).catch(function (err) {
          console.log(err)
          console.log('关注错误，请稍后重试')
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
      show_dropdown_list: function () {
        this.visible = true
        this.if_show_recommend = false
      }
    },
    // created: function () {
    //   var that = this
    //   if (this.$store.state.userId === '') {
    //     this.isLogin = false
    //     this.$Message.error('您尚未登录，3s后回到主页')
    //     var c = setInterval(function () {
    //       that.$router.push('/')
    //       clearInterval(c)
    //     }, 3000)
    //   } else {
    //     this.isLogin = true
    //   }
    // },
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