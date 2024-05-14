import request from '@/utils/request'

//快速登录
export function quickLoginCode () {
  return request({
    url: '/user/quickLoginCode',
    method: 'get'
  })
}

export function getUUidStatus (uuid) {
  return request({
    url: '/user/getUUidStatus',
    method: 'get',
    params: {
      uuid: uuid
    }
  })
}

export function sendVerificationCode (username, email) {
  return request({
    url: '/user/verificationCode',
    method: 'get',
    params: {
      username: username,
      email: email
    }
  })
}

export function checkCode (email, username, code) {
  return request({
    url: '/user/checkCode',
    method: 'get',
    params: {
      email: email,
      username: username,
      code: code
    }
  })
}

export function resetPassword (username, password, code) {
  return request({
    url: '/user/resetPassword',
    method: 'post',
    headers: { 'content-type': 'application/x-www-form-urlencoded' },
    data: {
      username: username,
      password: password,
      code: code
    }
  })
}

//用户登录
export function login (form) {
  return request({
    url: '/user/login',
    method: 'post',
    data: form
  })
}

//获取用户信息
export function checkUsername (username) {
  return request({
    url: '/user/checkAccount',
    method: 'get',
    params: {
      username: username
    }
  })
}

export function addUser (form) {
  return request({
    url: '/user/addUser',
    method: 'post',
    data: form
  })
}

export function personalInfo () {
  return request({
    url: '/user/personalInfo',
    method: 'post'
  })
}

export function updatePersonal (form) {
  return request({
    url: '/user/updatePersonal',
    method: 'post',
    data: form
  })
}

export function getCourseUsers (courseId) {
  return request({
    url: '/user/course_users',
    method: 'post',
    params: {
      courseId: courseId
    }
  })
}



