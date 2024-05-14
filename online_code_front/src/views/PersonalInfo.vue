<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}

.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>
<template>
  <div>
    <page-title title="个人信息"></page-title>
    <el-form :model="userInfo" label-width="100px" style="width: 40%;margin-left: 30%">
      <el-form-item label="用户名" prop="name">
        <el-input v-model="userInfo.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="头像">
        <el-upload
          class="avatar-uploader"
          :action="uploadUrl"
          :on-success="handleAvatarSuccess"
          :show-file-list="false">
          <img v-if="userInfo.headPortrait" :src="showImgUrl+userInfo.headPortrait" class="avatar">
          <img v-else src="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png" class="avatar">
        </el-upload>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="userInfo.realName" disabled></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <template>
          <el-radio v-model="userInfo.gender" :label="1">男</el-radio>
          <el-radio v-model="userInfo.gender" :label="0">女</el-radio>
        </template>
      </el-form-item>
      <el-form-item label="生日">
        <el-date-picker
          v-model="userInfo.birthday"
          type="date"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="userInfo.telephone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="userInfo.email"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" @click="save()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

import PageTitle from '@/components/PageTitle.vue'
import { personalInfo, updatePersonal } from '@/api/user'
import { baseURL } from '@/utils/request'

export default {
  components: { PageTitle },
  data () {
    return {
      showImgUrl: baseURL + '/user/fetch_img/',
      uploadUrl: baseURL + '/user/upload_img',
      userInfo: {
        username: '202221130310',
        headPortrait: '',
        email: '2199978888@qq.com',
        realName: '高和召',
        gender: '1',
        birthday: '',
        telephone: '18888888888'
      }
    }
  },
  mounted () {
    this.getPersonalInfo()
  },
  methods: {
    handleAvatarSuccess (res, file) {
      this.userInfo.headPortrait = res
    },
    save () {
      updatePersonal(this.userInfo).then(res => {
        this.$message({
          message: '保存成功',
          type: 'success'
        })
      })
    },
    getPersonalInfo () {
      personalInfo().then(res => {
        console.log(res)
        this.userInfo = res.data
      })
    },
  }
}
</script>
