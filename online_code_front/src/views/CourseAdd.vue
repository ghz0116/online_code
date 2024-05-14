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

.avatar-uploader .el-upload__text {
  font-size: 14px;
  color: #606266;
}

.avatar-uploader .el-upload__tip {
  font-size: 12px;
  color: #909399;
  text-align: left;
}
</style>

<template>
  <div>
    <PageTitle title="创建课程"></PageTitle>
    <div style="width: 60%;margin-left: 20%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="课程名称">
          <el-input v-model="form.courseName"></el-input>
        </el-form-item>
        <el-form-item label="课程封面">
          <el-upload
            class="avatar-uploader"
            action="/your-upload-url"
            :show-file-list="false"
            :before-upload="beforeUpload"
            :on-change="handleChange"
          >
            <img v-if="form.courseCover" :src="form.courseCover" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="起止日期">
          <el-col :span="10">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.startDate"
                            style="width: 100%;"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2" :offset="2">至</el-col>
          <el-col :span="10">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.endDate"
                            style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="课程介绍">
          <el-input type="textarea" v-model="form.description" :rows="5"></el-input>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col :span="12">
              <el-button style="width: 80%" type="primary" @click="onSubmit">创建课程</el-button>
            </el-col>
            <el-col :span="12">
              <el-button style="width: 80%">重置表单</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { addCourse } from '@/api/course'
import PageTitle from '@/components/PageTitle.vue'

export default {
  components: { PageTitle },
  data () {
    return {
      form: {
        courseName: '',
        courseCover: '',
        startDate: '',
        endDate: '',
        description: '',
      },
    }
  },
  mounted () {
  },
  methods: {
    onSubmit () {
      const user = localStorage.getItem('user')
      this.form.teacherId = JSON.parse(user).id
      addCourse(this.form).then(res => {
        this.$message.success('创建课程成功')
        this.$router.push('/t_course')
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

  }
}
</script>
