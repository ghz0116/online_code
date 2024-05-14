<style>
.center {
  justify-content: left; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 50px;
}
</style>
<template>
  <div>
    <el-row>
      <el-col :span="4">
        <page-title title="课程详情"></page-title>
      </el-col>
      <el-col :span="16" style="background: rgba(221,234,248,0.55);padding: 20px;border-radius: 20px">
        <el-container>
          <el-aside width="450">
            <img v-if="course.courseCover!==undefined" :src="baseURL()+'/course/covers/'+course.courseCover"
                 alt="课程封面"
                 style="width: 450px;height: 300px;border-radius: 20px">
          </el-aside>
          <el-main>
            <div style="margin-left: 50px">
              <h2 style="margin-top: -20px;">{{ course.courseName }}</h2>
              <p><b>开课时间：</b>{{ formatDate(course.startDate) }}-{{ formatDate(course.endDate) }}</p>
              <div class="center">
                <p><b style="margin-left: -10px">授课老师：{{ course.teacher.realName }}</b></p>
                <div style="margin-left: 10px">
                  <el-avatar
                    v-if="course.teacher.headPortrait!==undefined&course.teacher.headPortrait!==null"
                    size="large"
                    :src="showImgUrl+course.teacher.headPortrait"></el-avatar>
                  <el-avatar
                    v-else
                    size="large"
                    icon="el-icon-user-solid"></el-avatar>
                </div>
                <p style="margin-left: 100px"><b>参加人数
                  <el-icon class="el-icon-user"></el-icon>
                  ：</b>{{ course.studentCount }}
                </p>
              </div>

              <p style="margin-top: 10px">
                <b>课程介绍：</b>{{ course.description }}
              </p>
              <el-button plain v-if="course.joined" type="danger" style="width: 100%;margin-top:10px">退出课堂
              </el-button>
              <el-button v-else type="primary" style="width: 100%;margin-top:10px" @click="joinCourse">
                加入课堂
              </el-button>
            </div>
          </el-main>
        </el-container>
      </el-col>
    </el-row>
    <el-row style="margin-top: 20px">
      <el-col :offset="4" :span="16" style="background: rgba(64,158,255,0.09);padding: 20px;border-radius: 20px">
        <el-tabs v-model="activeName">
          <el-tab-pane label="评论区" name="first">
            <div style="width: 100%;float: left;text-align:left;height:80%;overflow-y: auto">
              <template>
                <recursive-component :key="item.evaluateId + Math.random()" v-for="(item) in evaluates" :userId="userId"
                                     :data="item"
                                     :repeat="repeat"/>
              </template>
            </div>
            <span v-if="evaluate.parentId!==0" style="width: 100%;text-align: left;float: left">回复@{{
                repeatEvaluate
              }}<el-button
                type="text" @click="evaluate.parentId=0" size="mini" icon="el-icon-circle-close"></el-button></span>
            <span v-else style="width: 100%;text-align: left;float: left;margin-bottom: 4px">
          <el-button type="text" size="mini"></el-button>
        </span>
            <el-input v-model="evaluate.content" style="width: 80%" placeholder="请输入内容"></el-input>
            <el-button icon="el-icon-s-promotion" type="primary" style="width: 19%" @click="sendEvaluate()"></el-button>
          </el-tab-pane>
          <el-tab-pane label="题库" name="second">
            <el-table
              :data="questionList"
              stripe
              style="width: 100%">
              <el-table-column
                prop="questionId"
                label="题目编号"
              >
              </el-table-column>
              <el-table-column
                prop="questionName"
                label="题目名称"
              >
              </el-table-column>
              <el-table-column
                prop="solutionNum"
                label="题解">
              </el-table-column>
              <el-table-column
                prop="passRate"
                :formatter="formatPassRate"
                label="通过率">
              </el-table-column>
              <el-table-column
                prop="degree"
                label="难度">
                <template v-slot:default="scope">
                  <el-rate
                    v-model="scope.row.degree"
                    disabled
                    text-color="#ff9900">
                  </el-rate>
                </template>
              </el-table-column>
              <el-table-column
                label="操作">
                <template v-slot:default="scope">
                  <el-button type="primary" icon="el-icon-edit" @click="solveQuestion(scope.row.questionId)"
                             circle></el-button>
                  <el-button v-if="scope.row.favorite" type="warning" icon="el-icon-star-off" circle
                             @click="changeFavorite(scope.row)"></el-button>
                  <el-button v-else type="info" icon="el-icon-star-off" circle
                             @click="changeFavorite(scope.row)"></el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>

      </el-col>
    </el-row>
  </div>
</template>
<script>
import group from '@/resource/img/group.png'
import PageTitle from '@/components/PageTitle.vue'
import { fetchCourseDetail, join } from '@/api/course'
import { baseURL } from '@/utils/request'
import RecursiveComponent from '@/components/RecursiveComponent.vue'
import { sendEvaluate, getEvaluate } from '@/api/courseEvaluate'
import { courseQuestions } from '@/api/question'

export default {
  components: {
    RecursiveComponent,
    PageTitle
  },
  data () {
    return {
      activeName: 'first',
      showImgUrl: baseURL + '/user/fetch_img/',
      group: group,
      course: {
        teacher: {
          realName: ''
        }
      },
      evaluates: [],
      evaluate: {
        parentId: 0,
        courseId: 0,
        userId: 0,
        content: null
      },
      repeatEvaluate: null,
      userId: 0,
      questionList: []
    }
  },
  created () {
    const currentUser = JSON.parse(localStorage.getItem('user'))
    if (currentUser != null) {
      this.userId = currentUser.id
    }
    this.getCourseDetails()
    this.getEvaluates()
    this.getQuestions()
  },
  methods: {
    solveQuestion (questionId) {
      this.$router.push({
        path: '/code',
        query: { questionId: questionId }
      })
    },
    getQuestions () {
      const courseId = this.$route.query.courseId
      courseQuestions(courseId).then(res => {
        this.questionList = res.data
      })
    },
    baseURL () {
      return baseURL
    },
    getCourseDetails () {
      const courseId = this.$route.query.courseId
      fetchCourseDetail(courseId).then(res => {
        this.course = res.data
      })
    },
    formatDate (date) {
      return new Date(date).toLocaleDateString()
    },
    joinCourse () {
      const currentUser = JSON.parse(localStorage.getItem('user'))
      if (!currentUser) {
        this.$message({
          message: '请先登录',
          type: 'warning'
        })
        return
      }
      if (currentUser.identity === 2) {
        this.$message({
          message: '教师无法加入课堂',
          type: 'warning'
        })
        return
      }
      join(this.course.courseId).then(res => {
        this.$message({
          message: '课堂加入成功',
          type: 'success'
        })
        this.course.joined = true
      })
    },
    //获取当前小说的所有评论
    getEvaluates () {
      const courseId = this.$route.query.courseId
      getEvaluate(courseId).then(res => {
        this.evaluates = res.data
      })
    },
    //发表评论
    sendEvaluate () {
      const currentUser = JSON.parse(localStorage.getItem('user'))
      if (!currentUser) {
        this.$message({
          message: '请先登录',
          type: 'warning'
        })
        return
      }
      this.evaluate.courseId = this.$route.query.courseId
      this.evaluate.userId = currentUser.id
      sendEvaluate(this.evaluate).then(res => {
        this.getEvaluates()
        this.evaluate.parentId = 0
        this.evaluate.content = null
      })
    },
    //回复评论
    repeat (realName, id) {
      this.repeatEvaluate = realName
      this.evaluate.parentId = id
    }
  }
}
</script>
