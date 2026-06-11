import request from '@/utils/request'

// 查询字典数据列表
export function bill (params) {
  return request({
    url: '/cache/'+params,
    method: 'get'
  })
}
