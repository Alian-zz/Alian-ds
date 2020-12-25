import request from '@/utils/request'

export function listAllCate() {
  return request({
    url: '/api/ums/resourceCategory/getResourceCategorylist',
    method: 'get'
  })
}

export function fetchResourceCategoryById(id) {
  return request({
    url: '/api/ums/resourceCategory/getResourceCategoryById',
    method: 'get',
    params: {
      id: id
    }
  })
}

export function saveOrUpdateResourceCategory(data) {
  return request({
    url: '/api/ums/resourceCategory/saveOrUpdateResourceCategory',
    method: 'post',
    data: data
  })
}


export function deleteResourceCategory(id) {
  return request({
    url: '/api/ums/resourceCategory/deleteResourceCategory',
    method: 'delete',
    params: {
      id: id
    }
  })
}
