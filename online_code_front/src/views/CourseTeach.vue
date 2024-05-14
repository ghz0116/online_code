<style>
.courseDiv {
  border-radius: 5px;
  padding-bottom: 5px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
  cursor: pointer;
  height: 220px;
  float: left;
  width: 18%;
  margin-right: 2%;
}

.courseDiv:hover {
  transform: scale(1.05); /* 设置图片放大的比例 */
  transition: transform 0.5s ease; /* 添加过渡效果，使放大平滑过渡 */

}

.center {
  margin-left: 10px;
  display: flex;
  justify-content: left; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 50px;
  font-size: 15px;
}
</style>
<template>
  <div>
    <page-title title="我教的课"></page-title>
    <div :key="item.courseId" class="courseDiv" @click="jumpPage(item.courseId)" v-for="item in courses">
      <el-image
        style="width:100%; height: 150px;border-radius: 5px"
        :src="baseURL+'/course/covers/'+item.courseCover"
        fit="cover"></el-image>
      <span style="margin-left: 10px"><b>{{ item.courseName }}</b></span>
      <div class="center">
        <el-image style="width: 25px;height: 25px;margin-left: 10px" :src="group"></el-image>
        :{{ item.studentCount }}
      </div>
    </div>
    <div class="courseDiv" style="text-align: center">
      <div style="background: #d3dce6;width:100%; height: 150px;border-radius: 5px" @click="createCourse">
        <el-icon class="el-icon-plus" style="font-size: 50px;margin-top: 50px"></el-icon>
      </div>
      <h1 style="text-align: center">新建课程</h1>
    </div>
  </div>
</template>
<script>
import group from '@/resource/img/group.png'
import { teachCourse } from '@/api/course'
import { baseURL } from '@/utils/request'
import PageTitle from '@/components/PageTitle.vue'

export default {
  components: { PageTitle },
  data () {
    return {
      baseURL: baseURL,
      group: group,
      courses: []
    }
  },
  created () {
    this.getTeachCourse()
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    getTeachCourse () {
      teachCourse().then(res => {
        this.courses = res.data
      })
    },
    jumpPage (courseId) {
      this.$router.push({
        path: '/manage_course',
        query: { courseId: courseId }
      })

    },
    createCourse () {
      this.$router.push({
        path: '/add_course',
      })
    }
  }
}
</script>
