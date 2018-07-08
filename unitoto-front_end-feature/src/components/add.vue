<template>
  <div class='add-add'>
    <Input type='textarea' :autosize='{minRows: 5, maxRows: 5}' placeholder='写下你想到的...' v-model='message'></Input>
    <Upload multiple accept='image/*' class='add-uploader' action='/upload' ref='upload' :before-upload='beforeAddImg' :show-upload-list=false>
      <div><Icon type="camera" size="50"></Icon></div>
    </Upload>
    <div class='add-preview'>
      <p v-if='urlList.length == 0'>点击相机添加图片</p>
      <!-- TODO: 优化预览界面 -->
      <Row v-for='i in Math.ceil(urlList.length / 3)' :key='i'>
        <Col span='8' v-for='j in 3' :key='j'>
          <img v-if='(i - 1) * 3 + j - 1 < urlList.length' :src='urlList[(i - 1) * 3 + j - 1]' class='add-showImg' @click='operateImg((i - 1) * 3 + j - 1)' />
        </Col>
      </Row>
    </div>
    <div class='add-button'>
      <Button type="primary" long @click='upload'>上传</Button>
      <Button type="warning" long @click='cancelUpload'>取消</Button>
    </div>

    <Modal v-model='isShown' ref='preview'>
      <img :src='urlList[shownImg]' class='add-opimg'/>
      <div slot="footer">
        <Button type="error" size="large" long @click="removeImg">删除</Button>
      </div>
    </Modal>
  </div>
</template>

<style>
.add-add {
  width: 40%;
  margin: 70px auto;
}
.add-uploader > div {
  border: dashed;
  width: 60px;
  height: 60px;
  text-align: center;
  vertical-align: middle;
}
.add-uploader {
  margin-top: 20px;
}
.add-showImg {
  height: 60px;
  margin: 0 auto;
  display: block;
}
.add-preview {
  overflow: scroll;
  height: 230px;
  margin-top: 20px;
  background-color: #FFFFFF;
  padding: 5px;
}
.add-opimg {
  width: 100%;
}
.add-button * {
  margin-top: 10px;
}
.add-button {
  width: 80%;
  margin: 0 auto;
}
@media screen and (max-width: 700px) {
  .add-add {
    width: 98%;
    margin: 70px auto;
  }
}
</style>

<script>
import axios from 'axios'

export default {
  data: function () {
    return {
      imgFileList: [],
      urlList: [],
      shownImg: -1,
      isShown: false,
      message: ''
    }
  },
  methods: {
    beforeAddImg: function (file) {
      this.imgFileList.push(file)
      var reader = new FileReader()
      reader.readAsDataURL(file)
      var that = this
      reader.onloadend = function (e) {
        var index = that.imgFileList.indexOf(file)
        that.urlList.splice(index, 0, reader.result)
      }
      return false
    },
    removeImg: function () {
      this.$refs['preview'].close()
      this.imgFileList.splice(this.shownImg, 1)
      this.urlList.splice(this.shownImg, 1)
    },
    upload: function () {
      if (this.imgFileList.length === 0) {
        this.$Message.error('您尚未选择图片')
        return
      }
      var that = this
      var data = new FormData()
      data.append('photoContext', this.message)
      data.append('userId', this.$store.state.userId)
      for (var i = 0; i < this.imgFileList.length; i++) {
        if (this.imgFileList.length === 1) {
          data.append('file', this.imgFileList[i])
        } else {
          data.append('files', this.imgFileList[i])
        }
      }
      var url = this.imgFileList.length === 1 ? '/service/uploadSingleImg.do' : '/service//uploadPluralImg.do'
      axios({
        method: 'post',
        data,
        url,
        headers: {
          'Content-Type': 'multipart/form-data',
          'Accept-Language': 'en'
        }
      }).then(function (res) {
        that.$Message.success('上传成功，即将回到主页')
        var c = setInterval(function () {
          that.$router.push('/')
          clearInterval(c)
        }, 2000)
      }).catch(function (err) {
        console.log(err)
        that.$Message.error('上传失败，请稍后重试')
      })
    },
    cancelUpload: function () {
      this.imgFileList = []
      this.isShown = false
      this.urlList = []
      this.shownImg = -1
      this.$router.go(-1)
    },
    operateImg: function (index) {
      this.shownImg = index
      this.isShown = true
    }
  }
}
</script>
