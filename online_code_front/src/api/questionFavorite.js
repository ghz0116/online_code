import request from '@/utils/request'

export function changeFavorite (questionId) {
  return request({
    url: '/question_favorite/change',
    method: 'post',
    params: {
      questionId: questionId
    }
  })
}
