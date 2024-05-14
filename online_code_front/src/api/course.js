import request from '@/utils/request'

//快速登录
export function addCourse (course) {
  return request({
    url: '/course/add',
    method: 'post',
    data: course
  })
}

export function teachCourse () {
  return request({
    url: '/course/teach',
    method: 'post',
  })
}

export function studyCourse () {
  return request({
    url: '/course/study',
    method: 'post',
  })
}

export function list (search, page) {
  return request({
    url: '/course/list',
    method: 'post',
    params: {
      page: page,
      search: search
    }
  })
}

export function join (courseId) {
  return request({
    url: '/course/join',
    method: 'post',
    params: {
      courseId: courseId
    }
  })
}

export function termScore (courseId) {
  return request({
    url: '/course/score',
    method: 'post',
    params: {
      courseId: courseId
    },
    responseType: 'blob'
  })
}

export function fetchCourseDetail (courseId) {
  return request({
    url: '/course/fetchDetail',
    method: 'post',
    params: {
      courseId: courseId
    }
  })
}

export function topSix () {
  return request({
    url: '/course/top',
    method: 'post',

  })
}

export function getChart (courseId) {
  return request({
    url: '/course/chart',
    method: 'post',
    params: {
      courseId: courseId
    }
  })
}
