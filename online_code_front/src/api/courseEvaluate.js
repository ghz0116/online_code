import request from '@/utils/request'

export function sendEvaluate (evaluate) {
  return request({
    url: '/evaluate/addEvaluate',
    method: 'post',
    data: evaluate
  })
}

export function getEvaluate (courseId) {
  return request({
    url: '/evaluate/getCourseEvaluate',
    method: 'post',
    params: {
      courseId: courseId
    }
  })
}


export function deleteEvaluate (evaluateId) {
  return request({
    url: '/evaluate/delete',
    method: 'post',
    params: {
      evaluateId: evaluateId
    }
  })
}
