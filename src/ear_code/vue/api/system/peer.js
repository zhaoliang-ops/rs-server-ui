import request from '@/utils/request'

// 查询对等成员列表
export function listPeer(query) {
  return request({
    url: '/system/peer/list',
    method: 'get',
    params: query
  })
}

// 查询对等成员详细
export function getPeer(id) {
  return request({
    url: '/system/peer/' + id,
    method: 'get'
  })
}

// 新增对等成员
export function addPeer(data) {
  return request({
    url: '/system/peer',
    method: 'post',
    data: data
  })
}

// 修改对等成员
export function updatePeer(data) {
  return request({
    url: '/system/peer',
    method: 'put',
    data: data
  })
}

// 删除对等成员
export function delPeer(id) {
  return request({
    url: '/system/peer/' + id,
    method: 'delete'
  })
}
