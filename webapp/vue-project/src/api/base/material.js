import request from '@/utils/request'

// 查询字典数据列表
export function getMaterialByMaterialCode (params) {
  return request({
    url: '/cache/'+params,
    method: 'get'
  })
}

export function listMaterial () {
  return request({
    url: '/material/',
    method: 'get'
  })
}

export function addMaterialInfo(data){
  return request({
    url: '/material',
    method: 'post',
    data:data
  })
}


export function listMaterialMeta () {
  return request({
    url: '/material/meta',
    method: 'get'
  })
}


export function addMaterialMeta(data){
  return request({
    url:'/material/meta',
    method: 'post',
    data:data
  })
}