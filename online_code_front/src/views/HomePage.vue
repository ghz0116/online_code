<style>
.center {
  margin-left: -50px;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 50px
}

.el-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

body, html {
  margin: 0;
  padding: 0;
  height: 100%;
  display: grid;
  grid-template-rows: 1fr;
}

iframe {
  border: none;
}
</style>
<template>
  <el-container>
    <el-header>
      <el-drawer
        title="消息中心"
        :visible.sync="drawer"
      >
        <template>
          <el-table
            :data="tableData"
            style="width: 100%">
            <el-table-column
              prop="date"
              label="日期"
              width="180">
            </el-table-column>
            <el-table-column
              prop="name"
              label="消息内容"
              width="180">
            </el-table-column>
            <el-table-column
              label="操作">
              <template v-slot>
                <el-button type="text">去看看</el-button>
                <el-button type="text">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-drawer>
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        active-text-color="#409EFF">
        <el-row style="text-align: center">
          <el-col :offset="1" :span="1">
            <img :src="require('@/resource/img/logo.png')" style="width: 80%" alt="svgLogo">
          </el-col>
          <el-col :offset="1" :span="1">
            <el-menu-item index="1" @click="jumpPage('/')">首页</el-menu-item>
          </el-col>
          <el-col :span="1">
            <el-menu-item index="2" @click="jumpPage('/question')">题库</el-menu-item>
          </el-col>
          <el-col :span="1">
            <el-menu-item index="4" @click="jumpPage('/course')">课程</el-menu-item>
          </el-col>

          <el-col :span="3" :offset="14">
            <el-submenu v-if="loginUser!=null" index="5">
              <template v-slot:title>
                <div class="center">
                  <el-avatar v-if="loginUser.headPortrait==null" size="large"
                             src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"></el-avatar>
                  <el-avatar v-else size="large"
                             :src="showImgUrl+loginUser.headPortrait" style=""></el-avatar>
                  <span style="margin-left: 10px">{{ loginUser.realName }}</span>
                </div>
              </template>
              <el-menu-item index="5-1" @click="jumpPage('/personal_info')">
                <el-icon class="el-icon-user"></el-icon>
                个人信息
              </el-menu-item>

              <el-menu-item v-if="loginUser.identity===1" index="5-5" @click="jumpPage('/s_course')">
                <el-icon class="el-icon-notebook-2"></el-icon>
                我听的课
              </el-menu-item>
              <el-menu-item v-if="loginUser.identity===2" index="5-5" @click="jumpPage('/t_course')">
                <el-icon class="el-icon-notebook-2"></el-icon>
                我教的课
              </el-menu-item>
              <el-menu-item index="5-2" @click="jumpPage('/favorite')">
                <el-icon class="el-icon-star-off"></el-icon>
                收藏夹
              </el-menu-item>
              <el-menu-item index="5-3" @click="logout">
                <el-icon class="el-icon-key"></el-icon>
                退出登录
              </el-menu-item>
            </el-submenu>
            <el-menu-item v-else>
              <template v-slot:title>
                <div class="center">
                  <el-avatar size="large"
                             src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"></el-avatar>
                  <el-link style="margin-left: 10px" @click="loginDialogVisible=true">登录</el-link>
                  <el-dialog v-model="loginDialogVisible" :visible="loginDialogVisible" width="30%" center
                             @close="loginDialogVisible = false">
                    <!-- 登录弹框标题 -->
                    <template v-slot:title>
                      <div class="custom-title">用户登录</div>
                    </template>

                    <!-- 使用 Flex 布局 -->
                    <div class="login-container">
                      <!-- 左侧二维码 -->
                      <!--                  <div class="qr-code">-->
                      <!--                    <img :src="quickLogin" alt="快速登录二维码" width="230" height="230">-->
                      <!--                    <div class="scan-text">请使用微信小程序扫一扫进行登录</div>-->
                      <!--                  </div>-->


                      <!-- 右侧登录表单 -->
                      <div class="login-form-container">
                        <!-- 登录表单和登录逻辑 -->
                        <el-form ref="loginForm" :model="loginForm" :rules="rules" class="login-form"
                                 @keyup.enter.native="login">
                          <!-- 输入用户名和密码 -->
                          <el-form-item prop="username">
                            <el-input v-model="loginForm.username" :clearable="true" prefix-icon="el-icon-user-solid"
                                      placeholder="请输入用户名" required></el-input>
                          </el-form-item>

                          <el-form-item prop="password" class="login-options">
                            <el-input type="password" v-model="loginForm.password" :clearable="true"
                                      prefix-icon="el-icon-lock"
                                      placeholder="请输入密码" required></el-input>
                          </el-form-item>

                          <!-- 记住密码 忘记密码 -->
                          <el-form-item class="login-options">
                            <el-row type="flex" justify="left">
                              <el-col class="left-option">
                                身份：
                                <el-radio v-model="loginForm.identity" label="1">学生</el-radio>
                                <el-radio v-model="loginForm.identity" label="2">教师</el-radio>
                              </el-col>
                              <el-col class="right-option" style="text-align: right">
                                <el-link target="_blank" @click="reset">忘记密码</el-link>
                              </el-col>
                            </el-row>
                          </el-form-item>

                          <!-- 登录按钮 -->
                          <el-form-item>
                            <el-button type="primary" @click="login" style="width: 100%">登录</el-button>
                          </el-form-item>
                        </el-form>
                      </div>
                    </div>
                  </el-dialog>
                </div>
              </template>
            </el-menu-item>

          </el-col>
          <el-col :span="1">
            <el-menu-item index="6" @click="drawer=true">
              <el-icon class=" el-icon-bell">
              </el-icon>
            </el-menu-item>
          </el-col>
        </el-row>
      </el-menu>
    </el-header>
    <el-main style="width: 100%;overflow-x: hidden">
      <router-view/>
    </el-main>
    <el-footer style="text-align: center;color: #909399;margin-top: 15px">
      <div>联系方式：2199978888@qq.com</div>
      <div>@作者：GaoHeZhao</div>
    </el-footer>
  </el-container>


</template>
<script>
import { login } from '@/api/user'
import { baseURL } from '@/utils/request'

export default {
  data () {
    return {
      showImgUrl: baseURL + '/user/fetch_img/',
      activeIndex: '1',
      loginDialogVisible: false,
      loginForm: {
        username: '',
        password: '',
        identity: '1'
      },
      rules: {
        username: [{
          required: true,
          message: '请输入账号！',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请输入密码！',
          trigger: 'blur'
        }],
      },
      quickLogin: '',
      loginUser: {
        realName: ''
      },
      loginIn: false,
      drawer: false,
      tableData: [{
        date: '2016-05-02 05:50:21',
        name: '计算机网络课程发布了新的问题！',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04 05:50:21',
        name: '计算机网络课程发布了新的问题！',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01 05:50:21',
        name: '计算机网络课程发布了新的问题！',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03 05:50:21',
        name: '计算机网络课程发布了新的问题！',
        address: '上海市普陀区金沙江路 1516 弄'
      }]
    }
  },
  mounted () {
    this.loginUser = JSON.parse(localStorage.getItem('user'))
  },
  methods: {
    logout () {
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      this.loginUser = null
      this.loginIn = false
      this.$message.success('退出登录成功！')
      window.location.href = '/'
    },
    jumpPage (src) {
      this.$router.push(src)
    },
    login () {
      login(this.loginForm).then(res => {
        if (res.status === 200) {
          const user = res.data.user
          this.loginUser = JSON.parse(user)
          localStorage.setItem('user', user)
          localStorage.setItem('token', res.data.token)
          this.loginIn = true
          this.$message.success('登录成功！')
        }
      }).catch(e => {
        const res = e.response
        this.$message.warning(res.data)
      })
    },
    reset () {

    },

  }
}
</script>
