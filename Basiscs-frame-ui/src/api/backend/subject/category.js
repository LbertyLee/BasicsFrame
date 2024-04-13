import request from '@/utils/request'


export function getCategoryList(data) {
    return request({
      url: '/backend/subject/category//query/tree',
      method: 'get',
      data
    })
  }
  