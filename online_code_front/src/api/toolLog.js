import request from '@/utils/request'

export function getLogs () {
  return request({
    url: '/log/get',
    method: 'post'
  })
}
