<template>
  <div>
    <el-amap class='map-map' :zoom='14' :center='center'>
      <el-amap-marker v-for="i in len(photoArr)" :position="photoArr[i].position" @click='show(i)'></el-amap-marker>
    </el-amap>
    <Modal v-model="isShow">
      <img :src='photoArr[selected].url' class='map-opimg'/>
      <div slot="footer">
        <Button size="large" long @click="removeImg">进入查看更多</Button>
      </div>
    </Modal>
  </div>
</template>

<style>
.map-map {
  height: 200px;
}
.add-opimg {
  width: 100%;
}
</style>

<script>
import axios from 'axios'
export default {
  data: function () {
    return {
      center: [113.3849, 23.0608],
      photoArr: [],
      selected: -1,
      isShow: false
    }
  },
  created: function () {
    var that = this
    if (this.$store.state.userId === '') {
      this.$Message.error('您尚未登录，3s后回到主页')
      var c = setInterval(function () {
        that.$router.push('/')
        clearInterval(c)
      }, 3000)
    } else {
      axios({
        method: 'get',
        url: '/service/getPhotoBySite.do'
      }).then(function (res) {
        var data = res.data
        for (var i = 0; i < data.length; i++) {
          var latitude = data[i].photoLatitude
          var longitude = data[i].photoLongitude
          var url = data[i].photoadress
          that.windows.push(window)
          var tmp = {
            position: [longitude, latitude],
            url
          }
          that.photoArr.push(tmp)
        }
      }).catch(function (err) {
        console.log(err)
        that.$Message.error('获取数据信息失败')
      })
    }
  },
  method: {
    show: function (num) {
      this.selected = num
      this.isShow = true
    }
  }
}
</script>