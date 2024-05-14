<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}


</style>

<template>
  <div>
    <template>
      <el-carousel :interval="4000" type="card" height="280px">
        <el-carousel-item v-for="item in topCourses" :key="item.courseId"
                          style="text-align: center;">
          <div>
            <el-row>
              <el-col :span="16" :offset="4" style="background-color: rgb(246,246,246);border-radius: 30px;">
                <div @click="viewCourse(item.courseId)">
                  <el-image
                    style="width:100%;height: 200px;object-fit: cover;border-top-left-radius: 30px;border-top-right-radius: 30px"
                    :src="baseURL()+'/course/covers/'+item.courseCover"
                    fit="cover"></el-image>
                  <el-row>
                    <el-col :span="12" :offset="6" style="font-size: 25px">
                      <div>{{ item.courseName }}</div>
                    </el-col>
                    <el-col :span="5" :offset="1">
                      <div class="center">
                        <el-avatar
                          style="width: 30px;height: 30px"
                          size="small"
                          :src="showImgUrl+item.teacher.headPortrait"></el-avatar>
                        <span style="margin-left: 5px">{{ item.teacher.realName }}</span>
                      </div>
                    </el-col>
                  </el-row>
                </div>
              </el-col>

            </el-row>
          </div>
        </el-carousel-item>
      </el-carousel>
    </template>
    <template>
      <el-row :gutter="20" style="margin-top: 20px">
        <el-col :span="3">
          <el-select
            v-model="questionQuery.relativeTags"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请选择问题标签">
            <el-option
              v-for="item in relativeTags"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <template>
            <el-select v-model="questionQuery.troubleLevel" placeholder="难度">
              <el-option
                v-for="item in troubleLevel"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-col>
        <el-col :span="4">
          <el-input v-model="questionQuery.searchContent" :clearable="true" prefix-icon="el-icon-search"
                    placeholder="请输入搜索内容" required></el-input>
        </el-col>
        <el-col :span="2">
          <el-button @click="fetchQuestionList">搜索</el-button>
        </el-col>
      </el-row>
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
            <el-button v-else type="info" icon="el-icon-star-off" circle @click="changeFavorite(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.currentPage"
      :page-sizes="[10, 20, 30, 40,50]"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total">
    </el-pagination>
  </div>
</template>
<script>
import imageSrc1 from '@/resource/img/1.webp' // 使用正确的相对路径
import imageSrc2 from '@/resource/img/2.webp' // 使用正确的相对路径
import imageSrc3 from '@/resource/img/3.webp' // 使用正确的相对路径
import imageSrc4 from '@/resource/img/4.webp' // 使用正确的相对路径
import imageSrc5 from '@/resource/img/5.webp' // 使用正确的相对路径
import imageSrc6 from '@/resource/img/6.webp' // 使用正确的相对路径

import { list } from '@/api/question'
import { changeFavorite } from '@/api/questionFavorite'
import { topSix } from '@/api/course'
import { baseURL } from '@/utils/request'

export default {
  data () {
    return {
      showImgUrl: baseURL + '/user/fetch_img/',
      imgSrc: [imageSrc1,
        imageSrc2,
        imageSrc3,
        imageSrc4,
        imageSrc5,
        imageSrc6,
      ],
      troubleLevel: [
        {
          label: '简单',
          value: '1'
        },
        {
          label: '一般',
          value: '2'
        },
        {
          label: '困难',
          value: '3'
        },
      ],
      relativeTags: [
        'java', 'c++', '数据结构'
      ],
      questionList: [
        { favorite: true }
      ],
      questionQuery: {
        searchContent: '',
        troubleLevel: '',
        relativeTags: []
      },
      pagination: {
        currentPage: 1,
        pageSize: 10,
        total: 100,
      },
      topCourses: {}
    }
  },
  mounted () {
    this.fetchQuestionList()
    this.getTopCourses()
  },
  methods: {
    viewCourse (id) {
      this.$router.push({
          path: '/detail_course',
          query: { courseId: id }
        }
      )
    },
    baseURL () {
      return baseURL
    },
    getTopCourses () {
      topSix().then(res => {
        this.topCourses = res.data
      })
    },
    changeFavorite (question) {
      const currentUser = JSON.parse(localStorage.getItem('user'))
      if (!currentUser) {
        this.$message({
          message: '请先登录',
          type: 'warning'
        })
        return
      }
      changeFavorite(question.questionId).then(res => {
        question.favorite = !question.favorite
      })
    },
    formatPassRate (row, column, cellValue) {
      // 将值乘以 100 并添加百分号
      return (cellValue * 100).toFixed(2) + '%'
    },
    fetchQuestionList () {
      const pageQuery = {
        pagination: this.pagination,
        questionQuery: this.questionQuery
      }
      list(pageQuery).then(res => {
        const data = res.data
        this.questionList = data.records
        this.pagination.total = data.total
      })
    },
    handleSizeChange (val) {
      this.pagination.pageSize = val
      this.fetchQuestionList()
    },
    handleCurrentChange (val) {
      this.pagination.currentPage = val
      this.fetchQuestionList()
    },
    solveQuestion (questionId) {
      this.$router.push({
        path: '/code',
        query: { questionId: questionId }
      })
    }
  }
}
</script>
