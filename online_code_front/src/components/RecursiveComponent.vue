<style>
.evaluateRow {
  width: 98%;
  padding: 1%;
  border-radius: 10px;
  float: left;
}

.evaluateRow:hover {
  background-color: rgba(255, 255, 255, 0.75); /* 鼠标悬停时内部元素的背景色 */
}
</style>
<template>
  <div v-if="data">
    <div class="evaluateRow">
      <el-row>
        <el-col :span="1" :offset="data.parentId === 0?0:1">
          <el-avatar :src="baseURL()+'/user/fetch_img/'+data.sendUser.headPortrait"></el-avatar>
        </el-col>
        <el-col :span="5">
          <!-- 显示评论作者的昵称、身份 -->
          <span style="color: #606266">{{ data.sendUser.realName }}</span>
          <el-tag type="warning" v-if="data.isTeacher === 1" size="mini"> 讲师</el-tag>
          <el-tag type="success" v-if="data.sendUser.id === userId" size="mini"> 自己</el-tag>
          <span style="color: rgb(64, 158, 255)" v-if="data.parentId !== 0">回复</span>
          {{ parentName }}
          <br>
          <!-- 显示评论创建时间 -->
          <span style="font-size: 12px">{{ data.createTime }}</span>
        </el-col>
        <el-col :span="2" :offset="data.parentId === 0?16:15">
          <!-- 回复按钮 -->
          <el-button type="text" @click="repeatFunc(data.sendUser.realName, data.evaluateId)">回复</el-button>
          <el-button v-if="data.sendUser.id === userId" type="text" style="color: rgba(232,102,102,0.94)" @click="deleteItem(data)">删除
          </el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="data.parentId === 0?22:21" :offset="data.parentId === 0?1:2">
          <!-- 评论内容 -->
          {{ data.content }}
        </el-col>
      </el-row>
    </div>
    <!-- 递归渲染子评论 -->
    <div style="width: 100%">
      <recursive-component v-for="child in data.children" :data="child" :key="child.evaluateId" :userId="userId"
                           :parent-name="data.sendUser.realName" :repeat="repeatFunc"/>
    </div>
  </div>
</template>

<script>
import { baseURL } from '@/utils/request'
import { deleteEvaluate } from '@/api/courseEvaluate'

export default {
  name: 'RecursiveComponent',
  props: {
    // 评论数据
    data: {
      sendUser: '',
      isTeacher: '',
      createTime: '',
      evaluateId: ''
    },
    // 父评论作者昵称
    parentName: {
      type: String,
      default: ''
    },
    // 回复方法
    repeat: {
      type: Function,
      required: true
    },
    userId: {
      type: Number,
      required: true
    }
  },
  methods: {
    baseURL () {
      return baseURL
    },
    // 回复方法的实现
    repeatFunc (realName, evaluateId) {
      this.repeat(realName, evaluateId) // 调用父组件传递过来的回复方法
    },
    deleteItem (data) {
      deleteEvaluate(data.evaluateId).then(res=>{
        this.$message({
          message: '删除成功',
          type: 'success'
        })
        window.location.reload();
      })
    }
  }
}
</script>

