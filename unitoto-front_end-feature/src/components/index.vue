<template>
  <div>
    <Button type="primary" shape="circle" icon="plus-round" size='large' class='index-addButton' @click='addImg'></Button>
    <div class='index-img' v-for='item in imgArr'>
      <img-card v-bind:object="item"></img-card>
    </div>
    <infinite-loading @infinite="loadMore" ref='upFresh'>
      <p slot='no-more'>到底啦~</p>
      <p slot='no-results'>找不到信息，请登录后刷新</p>
    </infinite-loading>
  </div>
</template>

<style>
.index-img {
  width: 50%;
  margin: 10px auto;
}
.index-addButton {
  position: fixed;
  right: 40px;
  bottom: 40px;
  z-index: 1;
}
@media screen and (max-width: 700px){
  .index-img {
    width: 100%;
    margin: 10px auto;
  }
  .index-addButton {
    position: fixed;
    right: 20px;
    bottom: 20px;
    z-index: 1;
  }
}
</style>

<script>
import ImgCard from './inner-components/ImgCard'
import axios from 'axios'
import InfiniteLoading from 'vue-infinite-loading'

export default {
  components: {
    ImgCard,
    InfiniteLoading
  },
  data: function () {
    return {
      imgArr: [

      ],
      isComplete: true
    }
  },
  created: function () {
    var that = this
    axios({
      method: 'get',
      url: '/service/getImageDownflashing.do',
      params: {
        userId: this.$store.state.userId
      }
    })
      .then(function (res) {
        that.isLoading = true
        var data = res.data
        if (data.length > 0) {
          for (var i = 0; i < data.length; i++) {
            var tmp = {
              userId: data[i].userId,
              imgSrc: data[i].photoAddress,
              uploader: data[i].userName,
              likeAmount: data[i].photoLikeNum,
              comments: data[i].commentSize,
              commentAmount: data[i].commentContext,
              photoId: data[i].photoId,
              photoContext: data[i].photoContext
            }
            that.imgArr.push(tmp)
          }
          that.isComplete = false
          that.$nextTick(() => {
            console.log(1)
            that.$refs.upFresh.$emit('$InfiniteLoading:reset')
          })
        } else {
          that.isComplete = true
        }
        console.log(res)
      })
      .catch(function (err) {
        that.$Message.error('无法从服务器获取内容，请稍后重试')
        console.log(err)
        that.isComplete = true
      })
  },
  methods: {
    addImg: function () {
      this.$router.push('/add')
    },
    loadMore: function (state) {
      var that = this
      if (this.isComplete) {
        state.complete()
        return
      }
      var params = {
        userId: this.$store.state.userId
      }
      if (this.imgArr.length > 0) {
        params.photoId = this.imgArr[this.imgArr.length - 1].photoId
      } else {
        state.complete()
        return
      }
      axios({
        method: 'get',
        url: '/service/getImageUpflashing.do',
        params
      })
        .then(function (res) {
          var data = res.data
          if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
              var tmp = {
                userId: data[i].userId,
                imgSrc: data[i].photoAddress,
                uploader: data[i].userName,
                likeAmount: data[i].photoLikeNum,
                comments: data[i].commentSize,
                commentAmount: data[i].commentContext,
                photoId: data[i].photoId,
                photoContext: data[i].photoContext
              }
              that.imgArr.push(tmp)
            }
            state.loaded()
          } else {
            that.isComplete = true
            state.complete()
          }
        })
        .catch(function (err) {
          that.$Message.error('无法从服务器获取内容，请稍后重试')
          console.log(err)
          state.complete()
        })
    }
  }
}
</script>
