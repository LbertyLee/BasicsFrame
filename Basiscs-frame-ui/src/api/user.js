import request from '@/utils/request'

export function getUserList(data) {
  return request({
    url: '/backend/sysUser/list',
    method: 'post',
    data
  })
}

