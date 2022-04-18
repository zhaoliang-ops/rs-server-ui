import request from '@/utils/request'

// 查询调度算法管理列表
export function listAlgorithm(query) {
  return request({
    url: '/system/algorithm/list',
    method: 'get',
    params: query
  })
}

// 查询调度算法管理详细
export function getAlgorithm(id) {
  return request({
    url: '/system/algorithm/' + id,
    method: 'get'
  })
}

// 新增调度算法管理
export function addAlgorithm(data) {
  return request({
    url: '/system/algorithm',
    method: 'post',
    data: data
  })
}

// 修改调度算法管理
export function updateAlgorithm(data) {
  return request({
    url: '/system/algorithm',
    method: 'put',
    data: data
  })
}

// 删除调度算法管理
export function delAlgorithm(id) {
  return request({
    url: '/system/algorithm/' + id,
    method: 'delete'
  })
}
