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
    <page-title title="我听的课"></page-title>
    <div :key="item.courseId" class="courseDiv" @click="viewCourse(item.courseId)" v-for="item in courses">
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
  </div>
</template>
<script>
import group from '@/resource/img/group.png'
import { studyCourse, teachCourse } from '@/api/course'
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
    this.getStudyCourse()
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    jumpPage (courseId) {
      this.$router.push({
        path: '/manage_course',
        query: { courseId: courseId }
      })
    },
    getStudyCourse(){
      studyCourse().then(res => {
        this.courses = res.data
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
