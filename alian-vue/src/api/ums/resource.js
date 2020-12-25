import request from '@/utils/request'

/**
 * 分页查询资源列表
 * @param params
 * @returns {AxiosPromise}
 */
export function fetchResourceList(params) {
  return request({
    url: '/api/ums/resource/getResourceList',
    method: 'get',
    params: params
  })
}

/**
 * 对资源列表进行新增或者修改
 * @param data
 * @returns {AxiosPromise}
 */
export function saveOrUpdateResource(data) {
  return request({
    url: '/api/ums/resource/saveOrUpdateResource',
    method: 'post',
    data: data
  })
}

export function getResourceById(id) {
  return request({
    url: '/api/ums/resource/getResourceById',
    method: 'get',
    params: {
      id: id
    }
  })
}


export function deleteResource(id) {
  return request({
    url: '/api/ums/resource/deleteResource',
    method: 'delete',
    params:{
      id: id
    }
  })
}


export function fetchCategoryOptions() {
  return request({
    url: '/api/ums/resource/getCategoryOptions',
    method: 'get'
  })
}

/**
 * 根据角色id查询角色拥有资源
 * @returns {AxiosPromise}
 */
export function listResourceByRole(roleId) {
  return request({
    url: '/api/ums/resource/getResourceByRole',
    method: 'get',
    params: {
      roleId: roleId
    }
  })
}

/**
 *
 * @param roleId
 * @returns {AxiosPromise}
 */
export function saveResourceByRole(roleId,roleResourceList) {
  return request({
    url: '/api/ums/resource/saveResourceByRole',
    method: 'post',
    data: {
      roleId: roleId,
      roleResourceList: roleResourceList
    }
  })
}
