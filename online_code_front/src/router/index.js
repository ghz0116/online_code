import Vue from 'vue'
import VueRouter from 'vue-router'

import homePage from '@/views/HomePage.vue'
import questionView from '@/views/QuestionView.vue'
import modelView from '@/views/ModelView.vue'
import codeView from '@/views/QuestionTry.vue'
import courseView from '@/views/CourseView.vue'
import courseTeach from '@/views/CourseTeach.vue'
import courseAdd from '@/views/CourseAdd.vue'
import courseManage from '@/views/CourseManage.vue'
import personalInfo from '@/views/PersonalInfo.vue'
import questionAdd from '@/views/QuestionEdit.vue'
import courseDetail from '@/views/CourseDetail.vue'
import welcomePage from '@/views/WelcomePage.vue'
import questionFavorite from '@/views/QuestionFavorite.vue'
import courseStudy from '@/views/CourseStudy.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: homePage,
    children: [
      {
        path: '/',
        component: welcomePage
      },
      {
        path: '/question',
        component: questionView
      },
      {
        path: '/question_add',
        component: questionAdd
      },
      {
        path: '/favorite',
        component: questionFavorite
      },
      {
        path: '/module',
        component: modelView
      },
      {
        path: '/code',
        component: codeView
      },
      {
        path: '/course',
        component: courseView
      }, {
        path: '/t_course',
        component: courseTeach
      }, {
        path: '/s_course',
        component: courseStudy
      }, {
        path: '/add_course',
        component: courseAdd
      }, {
        path: '/detail_course',
        component: courseDetail
      },
      {
        path: '/manage_course',
        component: courseManage
      }, {
        path: '/personal_info',
        component: personalInfo
      }
    ]
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
