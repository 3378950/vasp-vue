import request from '@/utils/request'

// 查询整改动态列表
export function listActivity(query) {
  return request({
    url: '/platform/activity/list',
    method: 'get',
    params: query
  })
}

// 查询整改动态详细
export function getActivity(activityId) {
  return request({
    url: '/platform/activity/' + activityId,
    method: 'get'
  })
}

// 新增整改动态
export function addActivity(data) {
  return request({
    url: '/platform/activity',
    method: 'post',
    data: data
  })
}

// 修改整改动态
export function updateActivity(data) {
  return request({
    url: '/platform/activity',
    method: 'put',
    data: data
  })
}

// 删除整改动态
export function delActivity(activityId) {
  return request({
    url: '/platform/activity/' + activityId,
    method: 'delete'
  })
}
