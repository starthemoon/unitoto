<template>
    <div class='group'>
        <div id='title'>{{ name }}</div>
            <div class = 'users'>成员({{ num }}):
                <div v-for='user in users' :key='user.id' class='userInfo'>
                    <router-link to='user' class='user'>{{ user.name }}</router-link>
                    <img :src='user.pic'>
                    <div class='discuss'>
                      这里是聊天对话框,主要问题是不知道交流的形式是什么
                      <br>
                      
                    </div>
                </div>
                
            </div>
        
    </div>
</template>

<script>
export default {
  name: 'group',
  data () {
    return {
      // id: this.$route.params.id,
      id: 1,
      name: '萌宠交流',
      text: '分享你萌宠的日常。',
      num: 4,
      isCollected: false,
      users: [
        {
          name: 'usr0',
          pic: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513285022402&di=b4f838a1145f24451c981c9610ea875c&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fac6eddc451da81cb79d739255866d01609243120.jpg'
        },
        {
          name: 'usr1',
          pic: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513284989635&di=5bb66a73c8f2bd2a7aab535c702135af&imgtype=0&src=http%3A%2F%2Fimg1.qq.com%2Fnews%2Fpics%2F13721%2F13721808.jpg'
        },
        {
          name: 'usr2',
          pic: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513285057641&di=e7a912ed4ebcb9b7d7bb519e1b31f4bb&imgtype=0&src=http%3A%2F%2Fwww.people.com.cn%2Fmediafile%2Fpic%2F20160604%2F21%2F9598398398692862113.jpg'
        },
        {
          name: 'usr3',
          pic: 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=489336886,4086202038&fm=27&gp=0.jpg'
        }
      ]
    }
  },
  method: {}
}
</script>

<style>
.group #title {
    font-size: 20px;
    width: 100%;
    text-align: center;
    font-style: bold;
    position: relative;
    color: 20cfea;
}
.group .users {
    background-color: white;
    font-size: 15px;
}
.group .user {
  position: relative;
}
.group .discuss {
  border-style: dashed;
  border-color: 20cfea;
}
.group img {
  height: 30px;
  width: 30px;
}
</style>