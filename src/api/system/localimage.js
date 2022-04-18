import request from '@/utils/request'

// 查询本地资源管理列表
export function listLocalimage(query) {
  return request({
    url: '/system/localimage/list',
    method: 'get',
    params: query
  })
}

// 查询本地资源管理详细
export function getLocalimage(imageId) {
  return request({
    url: '/system/localimage/' + imageId,
    method: 'get'
  })
}

// 新增本地资源管理
export function addLocalimage(data) {
  return request({
    url: '/system/localimage',
    method: 'post',
    data: data
  })
}

// 修改本地资源管理
export function updateLocalimage(data) {
  return request({
    url: '/system/localimage',
    method: 'put',
    data: data
  })
}
//查询下载得资源
export function getDownLoadLocalimageList(query) {
  return request({
    url: '/system/localimage/downList',
    method: 'get',
    params: query
  })
}
//查询上传的资源
export function getUploadLocalimageList(query) {
  return request({
    url: '/system/localimage/upList',
    method: 'get',
    params: query
  })
}
// 下载网络资源
export function downloadP2PLocalimage(data) {
  return request({
    url: '/system/localimage/download',
    method: 'put',
    data: data
  })
}
// 删除本地资源管理
export function delLocalimage(imageId) {
  return request({
    url: '/system/localimage/' + imageId,
    method: 'delete'
  })
}
