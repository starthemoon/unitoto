<template>
  <div v-if="true">
    <Dropdown trigger="custom" :visible="visible" @on-click="confirmSelect" class="search-main-content">
      <Input v-model="value" @on-keyup.enter="confirmSearch" @on-blur="hide_dropdown_list" @on-focus="show_dropdown_list">
        <Button @click="confirmSearch" slot="append" icon="ios-search"></Button>
      </Input>
      <DropdownMenu slot="list" class="search-menu">
        <div class="search-title">
          <span>SearchByUsername</span>
        </div>
      </DropdownMenu>
    </Dropdown>
    <div class="recommend" v-show="if_show_recommend">
<<<<<<< HEAD
      <Row v-for="(row, index_row) in recommendRows" :key="index_row" type="flex" justify="space-between" align="middle" class="code-row-bg" :gutter="16">
        <Col v-for="(col, index_col) in row" :key="(index_row - 1) * 3 + index_col - 1" class="item-img" justify="space-between" span="8">
          <img src="https://tse3.mm.bing.net/th?id=OIP.CMy2WiMRd-dYJzKGC77WBgHaFj&pid=Api">
          <span>{{col.userName}}</span>
          <Button @click="follow(col.userId)" type="primary" shape="circle" class="follow" icon="person"></Button>
        </Col>
=======
      <!-- <Row v-for="(row, index_row) in recommendRows" :key="index_row" type="flex" justify="space-between" align="middle" class="code-row-bg" :gutter="16">
        <Col v-for="(col, index_col) in row" :key="(index_row - 1) * 3 + index_col - 1" class="item-img" span="8"><img :src="col.src" :alt="col.alt"></Col>
      </Row> -->
      <Row v-for='i in Math.ceil(photos.length / 3)' :key='i' class="code-row-bg">
              <Col span='8' v-for='j in 3' :key='j' class="item-img">
                <img v-if='(i - 1) * 3 + j - 1 < photos.length' :src='photos[(i - 1) * 3 + j - 1]' class='add-showImg' @click='operateImg((i - 1) * 3 + j - 1)' />
              </Col>
>>>>>>> e7ab454c7e26246784f379e30d6393c9d1640dc1
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
    position: relative;
    min-width: 40px;
    min-height: 40px;
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
<<<<<<< HEAD
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
=======
        photos: [],
>>>>>>> e7ab454c7e26246784f379e30d6393c9d1640dc1
        value: '',
        if_show_recommend: true,
        user: '',
        color: '',
        tel: '',
        Email: '',
        show: '',
        isLogin: true,
        isShown: false,
        shownImg: -1,
        // photos: ["http://img3.3lian.com/2013/c2/14/d/11.jpg","http://img3.3lian.com/2013/c2/14/d/11.jpg",
        // "http://img3.3lian.com/2013/c2/14/d/11.jpg","http://pic.58pic.com/58pic/14/20/58/95a58PICXQp_1024.jpg",
        // "http://img3.3lian.com/2013/c2/14/d/11.jpg"],
        // recommendRows: [
        //   [
        //     {
        //       src: 'http://img3.3lian.com/2013/c2/14/d/11.jpg',
        //       alt: ''
        //     },
        //     {
        //       src: 'http://img3.3lian.com/2013/c2/14/d/11.jpg',
        //       alt: ''
        //     },
        //     {
        //       src: 'http://img3.3lian.com/2013/c2/14/d/11.jpg',
        //       alt: ''
        //     },
        //   ],
        //   [
        //     {
        //       src: 'http://pic.58pic.com/58pic/14/20/58/95a58PICXQp_1024.jpg',
        //       alt: ''
        //     },
        //     {
        //       src: 'http://img3.3lian.com/2013/c2/14/d/11.jpg',
        //       alt: ''
        //     },
        //     {
        //       src: 'http://img4.imgtn.bdimg.com/it/u=832634338,2138864592&fm=27&gp=0.jpg',
        //       alt: ''
        //     },
        //   ],
        //   [
        //     {
        //       src: 'http://img5.imgtn.bdimg.com/it/u=806391916,1690025371&fm=11&gp=0.jpg',
        //       alt: ''
        //     },
        //     {
        //       src: 'http://img4.imgtn.bdimg.com/it/u=3201723314,3315056898&fm=27&gp=0.jpg',
        //       alt: ''
        //     },
        //     {
        //       src: 'http://pic.58pic.com/58pic/13/52/59/34q58PIC3pT_1024.jpg',
        //       alt: ''
        //     },
        //   ]
        // ],
      }
    },
    methods: {
      confirmSearch: function (event) {
        var that = this
        // send the value to get the result from server
        axios({
          url: '/service/getUserByUserName.do',
          method: 'get',
          params: {
            userName: that.value
          }
        }).then(function (res) {
          that.users = []
          that.recommendRows = []
          var usersPerRow = []
          for (var i = 0; i < res.data.length; i++) {
            var user = res.data[i]
            that.users.push(user)
            usersPerRow.push(user)
            if (i % 3 === 0) {
              that.recommendRows.push(usersPerRow)
              usersPerRow = []
            }
          }
          that.recommendRows.push(usersPerRow)
          that.if_show_recommend = true
        }).catch(function (err) {
          console.log(err)
          that.$Message.error('搜索失败，请稍后重试')
        })
      },
      confirmSelect: function (name) {
        this.value = name
        this.visible = false
      },
      follow: function (target) {
        var that = this
        console.log(that.$store.state.userId)
        console.log(target)
        axios({
          url: '/service/addFollow.do',
          method: 'post',
          param: {
            userid: that.$store.state.userId,
            targetid: target
          }
        }).then(function (res) {
          console.log(res)
        }).catch(function (err) {
          console.log(err)
          console.log('关注失败，请稍后重试')
        })
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
    created: function () {
    this.user = this.$store.state.userId
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
      axios({
        url: '/service/getUserPhotos.do',
        method: 'get',
        params: {
          userid: that.$store.state.userId
        }
      }).then(function (res) {
        var path = 'http://45.77.182.195:8080/Unitoto-web/'
        for (var i = 0; i < res.data.length; i++) {
          that.photos.push(path+res.data[i].photoaddress)
          console.log('!!!')
          console.log(that.photos)
          console.log('!!!')
          console.log(res)
        }
      }).catch(function (err) {
        that.$Message.error('无法从服务器获取内容，请稍后重试')
        console.log(err)
      })
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
  .user-opimg {
    width: 100%;
  }
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