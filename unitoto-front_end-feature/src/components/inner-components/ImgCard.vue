<template>
  <div>
    <Card class="imgcard-card" dis-hover>
      <div class="imgcard-content">
        <div class="imgcard-userinfo">
          <Avatar class='imgcard-icon' icon="person" size="large" />
          <p class='imgcard-name'>{{uploader}}</p>
          <p class='context-front'>{{photoContext}}</p>
        </div>
        <img class='imgcard-image' v-bind:src='imgSrc' />
        <div class="imgcard-operate">
          <div class='imgcard-like'>
            <Button v-on:click="addLikeNum" type="ghost" shape="circle" icon="ios-heart-outline"></Button>
            <p class='imgcard-likenum'>{{likeAmount}}</p>
          </div>
          <!-- <div class='imgcard-comment'>
            <Icon type="ios-chatbubble-outline" size=30></Icon>
            <p class='imgcard-commentnum'>{{commentAmount}}</p>
          </div> -->
        </div>
      </div>
    </Card>
  </div>
</template>

<style>
.ivu-card-body {
  padding: 0 !important;
}
.imgcard-content {
  margin: 20px auto;
  width: 100%;
}
.context-front {
  font-family: Helvetica;
  font-size: 20px;
  margin-left: 50px;
}
.imgcard-imageroom {
  background-color: #EEEEEE;
  margin: 3px 5px;
}
.imgcard-image {
  max-width: 100%;
  display: block;
  margin: 0 auto;
}
.imgcard-name {
  vertical-align: middle;
  display: inline;
  font-size: 30px;
  padding: 0 5px;
}
.imgcard-userinfo {
  margin-bottom: 10px;
  margin-left: 10px;
  margin-right: 10px;
}
.imgcard-operate {
  margin: 0 10px;
}
.imgcard-likenum, .imgcard-commentnum {
  font-size: 15px;
  display: inline;
  vertical-align: text-bottom;
}
.imgcard-like, .imgcard-comment {
  display: inline;
}
.imgcard-comment {
  margin-left: 20px;
}
.imgcard-hotcomment * {
  display: inline;
}
.imgcard-hotcomment {
  margin-top: 3px;
}
</style>

<script>
import axios from 'axios'
export default {
  props: {
    object: {
      type: Object,
      default: null
    }
  },
  data: function () {
    var path = 'http://45.77.182.195:8080/Unitoto-web/'
    return {
      userId: this.$props.object.userId,
      imgSrc: path + this.$props.object.imgSrc,
      uploader: this.$props.object.uploader,
      likeAmount: this.$props.object.likeAmount,
      comments: this.$props.object.comments,
      commentAmount: this.$props.object.commentAmount,
      words: this.$props.object.photoContext,
      photoId: this.$props.object.photoId,
      photoContext: this.$props.object.photoContext
    }
  },
  methods: {
    userPage: function () {
      this.$router.push({name: 'User', query: {userid: this.uploader}})
    },
    addLikeNum: function () {
      var that = this
      axios({
        url: '/service/addPhotoLikeNum.do',
        method: 'get',
        params: {
          photoId: this.$props.object.photoId
        }
      }).then(function (res) {
        that.likeAmount = res.data
      }).catch(function (err) {
        that.$Message.error('无法从服务器获取内容，请稍后重试')
        console.log(err)
      })
    }
  }
}
</script>

