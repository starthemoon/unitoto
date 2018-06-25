<template>
    <div id = 'list' class='comm'> 

        <div id='myGroups'>
          <div id='my-title'>您所在的社区</div>
          <div class='items' v-for='item in items' :key='item.name' :name='item.name'>
            <router-link to='group' class='title'>{{ item.name }}</router-link>
            <span class='flag'>收藏</span>                   
            <span class='memberNum'>{{ item.num }}</span>
            <p class='describ'>
              <span class = 'content'> {{ item.text }} </span>
            </p>
            
          </div>
        </div>        
        
        <div id='recommand-groups'>
          <div id='reco-title'>为您推荐的社区</div>
          <div class = 'items' v-for='item in recoItems' :key='item.name' :name='item.name'>
            <router-link to='group' class='title'>{{ item.name }}</router-link>
            <span class='reco-flag'></span>              
            <span class='reco-num'>{{ item.num }}</span>
            <p class='describ'>
              <span class = 'content'> {{ item.text }} </span>
            </p>
          </div>
        </div>
    </div>
</template>
    
<script>
export default {
  data () {
    return {
      items: [
        {
          name: '萌宠交流',
          text: '分享你萌宠的日常。',
          num: 4
        },
        {
          name: '风景人情',
          text: '难忘的足迹。',
          num: 31
        },
        {
          name: '唯美人物',
          text: '我真的只是在欣赏人体艺术而已。',
          num: 123
        }
      ],
      recoItems: [
        {
          name: '软件学院',
          text: '这是一个人才济济的学院。',
          num: 1234
        },
        {
          name: '管理学院',
          text: '商界黄埔。',
          num: 32
        },
        {
          name: '中山大学',
          text: '国力中山大学。',
          num: 21
        }
      ]
    }
  },
  method: {}
}
</script>

<style>
.comm #list {
  z-index: l;
  width: 100%;
}
.comm #my-title {
  font-size: 17px;
  color: 20cfea;
  padding-left: 35%;
}
.comm .items {
  width: 100%;
  flex: 4;
  background-color: white;
  padding-top: 15px;
  padding-left: 5px;
  font-size: 15px;
  overflow: hidden;
}
.comm .flag {
  background-color: #e5e5e5;
  padding: 2px 5px;
  font-size: 80%;
  color: #9c9c9c;
  border-radius: 5px;
  margin-right: 3px;
}
.comm .memberNum {
  padding-left: 60%;
  font-size: 12px;
}
.comm .content {
  position: relative;
  font-size: 80%;
  color: gray;
  padding-top: 10px;
  padding-right: 0px;
}
.comm .title:hover {
  background-color: #f5f5f5;
}
.comm #reco-title {
  margin-top: 2%;
  font-size: 17px;
  color: 20cfea;
  padding-left: 34%;
}
.comm .reco-num {
  padding-left: 71%;
  font-size: 12px;
}
</style>