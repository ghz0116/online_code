import request from '@/utils/request'

//快速登录
export function list (pagination) {
  return request({
    url: '/question/list',
    method: 'post',
    data: pagination
  })
}

export function courseQuestions (courseId) {
  return request({
    url: '/question/c_questions',
    method: 'post',
    params: {
      courseId: courseId
    }
  })
}

export function addQuestion (question) {
  return request({
    url: '/question/add',
    method: 'post',
    data: question
  })
}

export function deleteQuestion (questionId) {
  return request({
    url: '/question/delete',
    method: 'post',
    params: {
      questionId: questionId
    }
  })
}

export function fetchQuestion (id) {
  return request({
    url: '/question/fetch',
    method: 'post',
    params: {
      questionId: id
    }
  })
}

export function uploadImage (image) {
  return request({
    url: '/question/upload_img',
    method: 'post',
    data: image
  })
}

export function fetchCodeType (questionId) {
  return request({
    url: '/question/fetch_code_type',
    method: 'post',
    params: {
      questionId: questionId
    }
  })
}

export function fetchTestCase (questionId) {
  return request({
    url: '/question/fetch_test_case',
    method: 'post',
    params: {
      questionId: questionId
    }
  })
}

export function fetchPreCode (questionId, language) {
  return request({
    url: '/question/fetch_pre_code',
    method: 'post',
    params: {
      questionId: questionId,
      language: language
    }
  })
}

export function predictTags (questionName) {
  return request({
    url: '/question/predict_tags',
    method: 'post',
    params: {
      questionName: questionName
    }
  })
}
