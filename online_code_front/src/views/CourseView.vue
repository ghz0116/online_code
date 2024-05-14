<style>
.courseDiv {
  border-radius: 5px;
  padding-bottom: 5px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
  cursor: pointer;
  margin-bottom: 20px;
}

.courseDiv:hover {
  transform: scale(1.05); /* 设置图片放大的比例 */
  transition: transform 0.5s ease; /* 添加过渡效果，使放大平滑过渡 */
}

.center {
  display: flex;
  justify-content: left; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 50px;
  font-size: 15px;
  margin-left: 20px;
}
</style>
<template>
  <el-container>
    <el-form :inline="true" class="demo-form-inline" style="margin-left: 20px">
      <el-form-item>
        <el-input v-model="searchContent" placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="list" style="width: 70px" type="primary"></el-button>
      </el-form-item>
    </el-form>
    <el-main>
      <div class="courseDiv" v-for="item in page.records" :key="item.courseName" @click="viewCourse(item.courseId)">
        <el-image
          style="width:100%; height: 150px;border-radius: 5px"
          :src="baseURL()+'/course/covers/'+item.courseCover"
          fit="cover"></el-image>
        <span style="margin-left: 10px"><b>{{ item.courseName }}</b></span>
        <el-row>
          <el-col :span="8">
            <div class="center">
              <el-avatar
                style="width: 30px;height: 30px"
                size="small"
                :src="showImgUrl+item.teacher.headPortrait"></el-avatar>
              <span style="margin-left: 5px">{{ item.teacher.realName }}</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="center">
              <el-image style="width: 25px;height: 25px;margin-left: 10px" :src="group"></el-image>
              :{{ item.studentCount }}
            </div>
          </el-col>
          <el-col :span="6">
            <div class="center">
              <el-button v-if="item.joined" size="mini" type="info" disabled>已加入
              </el-button>
              <el-button v-else size="mini" type="primary" @click.stop="joinCourse(item)">
                加入
              </el-button>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-main>
    <el-footer>
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="page.size"
        :total="page.total"
        :current-page="page.current"
        @current-change="changePage"
      >
      </el-pagination>
    </el-footer>

  </el-container>
</template>
<script>
import group from '@/resource/img/group.png'
import { list, join } from '@/api/course'
import { baseURL } from '@/utils/request'

export default {
  data () {
    return {
      showImgUrl: baseURL + '/user/fetch_img/',
      group: group,
      searchContent: '',
      page: {
        current: 1,
        size: 15,
        total: 0,
        records: [
          {
            studentCount: 0,
            teacher:{
              realName:''
            }
          }
        ]
      }
    }
  },
  mounted () {
    this.list()
  },
  methods: {
    baseURL () {
      return baseURL
    },
    changePage(val){
      this.page.current = val
      this.list()
    },
    list () {
      list(this.searchContent, this.page.current).then(res => {
        this.page = res.data
      })
    },
    joinCourse (course) {
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
      join(course.courseId).then(res => {
        if (res.status === 200) {
          this.$message({
            message: '课堂加入成功',
            type: 'success'
          })
          course.joined = true
        }
      })
    },
    viewCourse (id) {
      this.$router.push({
          path: '/detail_course',
          query: { courseId: id }
        }
      )
    }
  }
}
</script>
