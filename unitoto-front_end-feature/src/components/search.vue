<template>
  <div v-if="true">
    <Dropdown trigger="custom" :visible="visible" @on-click="confirmSelect" class="search-main-content">
      <Input v-model="value" @on-keyup.enter="confirmSearch" @on-blur="hide_dropdown_list" @on-focus="show_dropdown_list">
        <Button @click="confirmSearch" slot="append" icon="ios-search"></Button>
      </Input>
      <DropdownMenu slot="list" class="search-menu">
        <DropdownItem v-for="user in users" :name="user.name" :key="user.name">
          <Avatar :src="user.avatar"></Avatar>
          <span>{{user.name}}</span>
        </DropdownItem>
      </DropdownMenu>
    </Dropdown>
    <div class="recommend" v-show="if_show_recommend">
      <Row v-for="(row, index_row) in recommendRows" :key="index_row" type="flex" justify="space-between" align="middle" class="code-row-bg" :gutter="16">
        <Col v-for="(col, index_col) in row" :key="(index_row - 1) * 3 + index_col - 1" class="item-img" span="8"><img :src="col.src" :alt="col.alt"></Col>
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
</style>

<script>
  export default {
    data () {
      return {
        visible: false,
        users: [

        ],
        recommendRows: [

        ],
        value: '',
        if_show_recommend: true,
        isLogin: true
      }
    },
    methods: {
      confirmSearch: function (event) {
        // send the value to get the result from server
        alert('yes')
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
