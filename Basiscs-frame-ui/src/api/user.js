import request from '@/utils/request'

export function getUserList(data) {
  return request({
    url: '/sysUser/list',
    method: 'get',
    data
  })
}

