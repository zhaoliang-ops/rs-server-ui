import request from '@/utils/request'

// 查询数据资源管理列表
export function listImage(query) {
  return request({
    url: '/system/image/list',
    method: 'get',
    params: query
  })
}

// 查询数据资源管理详细
export function getImage(imageId) {
  return request({
    url: '/system/image/' + imageId,
    method: 'get'
  })
}
// 查询所有任务
export function getAllTaskList(query) {
  return request({
    url: '/system/image/allTaskList',
    method: 'get',
    params: query
  })
}
// 新增数据资源管理
export function addImage(data) {
  return request({
    url: '/system/image',
    method: 'post',
    data: data
  })
}

// 修改数据资源管理
export function updateImage(data) {
  return request({
    url: '/system/image',
    method: 'put',
    data: data
  })
}

// 删除数据资源管理
export function delImage(imageId) {
  return request({
    url: '/system/image/' + imageId,
    method: 'delete'
  })
}
