<style scoped>
.avatar-uploader {
  display: inline-block;
  width: 200px;
  height: 150px;
  text-align: center;
  line-height: 120px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader:hover {
  border-color: #409EFF;
}

.avatar-uploader img {
  width: 200px;
  height: 150px;
  display: block;
}

.avatar-uploader .el-icon-plus {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 150px;
  line-height: 150px;
  background-color: rgba(255, 255, 255, 0.8);
}
</style>
<template>
  <div>
    <page-title title="课程管理"></page-title>

    <el-row :gutter="20">
      <el-col :span="16">
        <p>基本信息</p>
        <el-form r ef="form" :model="courseForm" label-width="80px">
          <el-form-item label="课程名称">
            <el-input v-model="courseForm.courseName"></el-input>
          </el-form-item>
          <el-form-item label="课程封面">
            <el-upload
              class="avatar-uploader"
              action="/your-upload-url"
              :show-file-list="false"
              :before-upload="beforeUpload"
              :on-change="handleChange"
            >
              <img v-if="courseForm.courseCover" :src="baseURL+'/course/covers/'+courseForm.courseCover"
                   class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="起止日期">
            <el-col :span="10">
              <el-date-picker type="date" placeholder="选择日期" v-model="courseForm.startDate"
                              style="width: 100%;"></el-date-picker>
            </el-col>
            <el-col class="line" :span="2" :offset="2">至</el-col>
            <el-col :span="10">
              <el-date-picker type="date" placeholder="选择日期" v-model="courseForm.endDate"
                              style="width: 100%;"></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="课程介绍">
            <el-input type="textarea" v-model="courseForm.description" :rows="5"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%" type="primary" @click="onSubmit">保存</el-button>
          </el-form-item>
        </el-form>
        <div>
          <p>评论区</p>
          <div style="width: 100%;float: left;text-align:left;height:80%;overflow-y: auto">
            <template>
              <recursive-component :key="item.evaluateId + Math.random()" v-for="(item) in evaluates" :userId="userId"
                                   :data="item"
                                   :repeat="repeat"/>
            </template>
          </div>
          <span v-if="evaluate.parentId!==0" style="width: 100%;text-align: left;float: left">回复@{{ repeatEvaluate }}<el-button
            type="text" @click="evaluate.parentId=0" size="mini" icon="el-icon-circle-close"></el-button></span>
          <span v-else style="width: 100%;text-align: left;float: left;margin-bottom: 4px">
          <el-button type="text" size="mini"></el-button>
        </span>
          <el-input v-model="evaluate.content" style="width: 80%" placeholder="请输入内容"></el-input>
          <el-button icon="el-icon-s-promotion" type="primary" style="width: 19%" @click="sendEvaluate()"></el-button>
        </div>
      </el-col>
      <el-col :span="8">
        <p>题目完成情况</p>
        <div id="main" style="width: 100%;height: 300px"></div>
        <p>题目列表</p>
        <template>
          <el-table
            :data="questions"
            style="width: 100%">
            <el-table-column
              prop="questionId"
            width="85"
              label="ID"
            >
            </el-table-column>
            <el-table-column
              prop="questionName"
              label="题目名称"
              width="200">
            </el-table-column>
            <el-table-column
              prop="solutionNum"
              label="答案数量"
            width="100"
            >
            </el-table-column>
            <el-table-column
              label="操作"
              width="150">
              <template slot-scope="scope">
                <el-button icon="el-icon-edit" @click="editQuestion(scope.row)" type="text" size="small">编辑
                </el-button>
                <el-button icon="el-icon-delete" @click="deleteQuestion(scope.row)" type="text" size="small">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <el-button @click="addQuestion()" style="width: 100%" type="text" icon="el-icon-plus">添加问题</el-button>
        <div style="max-width: 100%;height:200px;overflow-x: auto;float: left">
          <p>课堂成员<el-button icon="el-icon-document" style="margin-left: 350px" @click="exportScore()">导出成绩</el-button></p>
          <div :key="student.id" v-for="student in courseStudents"
               style="width: 100px;height: 100px;border-radius:20px;text-align:center;float: left">
            <el-avatar style="height: 80px;width: 80px;margin-top: 10px"
                       :src="showImgUrl+student.headPortrait"></el-avatar>
            <br>{{ student.realName }}
          </div>
          <br>
        </div>
      </el-col>

    </el-row>
    <el-row :gutter="20">
      <el-col :span="8" :offset="16">

      </el-col>
    </el-row>
    <el-row>

    </el-row>
  </div>

</template>
<script>

import PageTitle from '@/components/PageTitle.vue'
import { courseQuestions, deleteQuestion } from '@/api/question'
import { addCourse, fetchCourseDetail, getChart, termScore } from '@/api/course'
import { baseURL } from '@/utils/request'
import { getCourseUsers } from '@/api/user'
import * as echarts from 'echarts';
import RecursiveComponent from '@/components/RecursiveComponent.vue'
import { getEvaluate, sendEvaluate } from '@/api/courseEvaluate'
export default {
  components: { RecursiveComponent, PageTitle },
  data () {
    return {
      showImgUrl: baseURL + '/user/fetch_img/',
      baseURL: baseURL,
      questions: [],
      courseForm: {
        courseName: '',
        courseCover: '',
        startDate: '',
        endDate: '',
        description: ''
      },
      courseStudents: [],
      evaluates: [],
      evaluate: {
        parentId: 0,
        courseId: 0,
        userId: 0,
        content: null
      },
      repeatEvaluate: null,
      userId: 0,
    }
  },
  mounted () {
    const currentUser = JSON.parse(localStorage.getItem('user'))
    if (currentUser != null) {
      this.userId = currentUser.id
    }
    this.getCourseInfo()
    this.getQuestions()
    this.getCourseUsers()
    this.initQuestionComplete()
    this.getEvaluates()
  },
  methods: {
    exportScore(){
      const courseId = this.$route.query.courseId
      termScore(courseId).then((response) => {
        // 处理响应数据
        const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'scores.xlsx'); // 设置下载文件的名称
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      })
        .catch((error) => {
          // 处理错误
          console.error('Error fetching scores:', error);
        });
    },
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
    },
    getEvaluates () {
      const courseId = this.$route.query.courseId
      getEvaluate(courseId).then(res => {
        this.evaluates = res.data
      })
    },
    deleteQuestion (question) {
      deleteQuestion(question.questionId).then(res => {
        this.questions = this.questions.filter(item => item.questionId !== question.questionId)
      })
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/jpeg/webp'
      const isLT2M = file.size / 1024 / 1024 < 2
      if (!isLT2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLT2M
    },
    handleChange (file, fileList) {
      const reader = new FileReader()
      reader.onload = (e) => {
        this.form.courseCover = e.target.result
      }
      reader.readAsDataURL(file.raw)
    },
    onSubmit () {
      const user = localStorage.getItem('user')
      this.form.teacherId = JSON.parse(user).id
      addCourse(this.form).then(res => {
        this.$message.success('创建课程成功')
        this.$router.push('/t_course')
      })
    },
    getQuestions () {
      const courseId = this.$route.query.courseId
      courseQuestions(courseId).then(res => {
        this.questions = res.data
      })
    },
    getCourseUsers () {
      const courseId = this.$route.query.courseId
      getCourseUsers(courseId).then(res => {
        this.courseStudents = res.data
      })
    },
    getCourseInfo () {
      const courseId = this.$route.query.courseId
      fetchCourseDetail(courseId).then(res => {
        this.courseForm = res.data
      })
    },
    addQuestion () {
      this.$router.push({
        path: '/question_add',
        query: {
          courseId: this.$route.query.courseId,
          type: 'add'
        }
      })
    },
    editQuestion (question) {
      this.$router.push({
        path: '/question_add',
        query: {
          questionId: question.questionId,
          type: 'edit'
        }
      })
    },
    initQuestionComplete(){
      const courseId = this.$route.query.courseId
      getChart(courseId).then(res => {
        console.log(res)
        const data=res.data
        var chartDom = document.getElementById('main');
        var myChart = echarts.init(chartDom);
        var option;
        option = {
          xAxis: {
            type: 'category',
            data: data['ids'],
            name: '问题id'
          },
          yAxis: {
            type: 'value',
            name: '通过人数'

          },
          series: [
            {
              data: data['counts'],
              type: 'bar'
            }
          ]
        };
        myChart.setOption(option);
      })
    }
  }
}
</script>
