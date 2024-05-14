import request from '@/utils/request'

export function submitAnswer (answer) {
  return request({
    url: '/answer/submit',
    method: 'post',
    data: answer
  })
}

export function askAI (answer) {
  return request({
    url: '/answer/askAI',
    method: 'post',
    data: answer
  })
}

export function getRecords (questionId) {
  return request({
    url: '/answer/records',
    method: 'post',
    params: {
      questionId: questionId
    }
  })
}

export function getAnswerCode (answerId) {
  return request({
    url: '/answer/answer_code',
    method: 'post',
    params: {
      answerId: answerId
    }
  })
}
