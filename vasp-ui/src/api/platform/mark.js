import request from '@/utils/request'

// 查询检测列表
export function listMark(query) {
  return request({
    url: '/platform/mark/list',
    method: 'get',
    params: query
  })
}

// 查询检测详细
export function getMark(markId) {
  return request({
    url: '/platform/mark/' + markId,
    method: 'get'
  })
}

// 新增检测
export function addMark(data) {
  return request({
    url: '/platform/mark',
    method: 'post',
    data: data
  })
}

// 修改检测
export function updateMark(data) {
  return request({
    url: '/platform/mark',
    method: 'put',
    data: data
  })
}

// 删除检测
export function delMark(markId) {
  return request({
    url: '/platform/mark/' + markId,
    method: 'delete'
  })
}
