import request from '@/utils/request'

export function fetchMenuList(params) {
  return request({
    url: '/api/ums/menu/getMenuList',
    method: 'get',
    params: params
  })
}

export function deleteMenuById(id) {
  return request({
    url: '/api/ums/menu/deleteMenuById',
    method: 'delete',
    params: {
      id: id
    }
  })
}

export function saveOrUpdateMenu(data) {
  return request({
    url: '/api/ums/menu/saveOrUpdateMenu',
    method: 'post',
    data: data
  })
}


export function fetchMenu(level) {
  return request({
    url: 'api/ums/menu/getMenu',
    method: 'get',
    params: {
      level: level,
    },
  })
}

export function updateHiddenById(id, hidden) {
  return request({
    url: '/api/ums/menu/updateHiddenById',
    method: 'put',
    params: {
      id: id,
      hidden: hidden
    }
  })
}

export function getMenuById(id) {
  return request({
    url: '/api/ums/menu/getMenuById',
    method: 'get',
    params: {id: id}
  })
}

export function saveMenuByRole(roleId,menuByRoleList) {
  return request({
    url: '/api/ums/roleMenuRelation/saveMenuByRole',
    method: 'post',
    data: {
      roleId: roleId,
      menuByRoleList: menuByRoleList
    }
  })
}

/**
 * 动态菜单栏
 * @returns {AxiosPromise}
 */
export function queryMenuList() {
  return request({
    url: '/api/ums/menu/queryMenuList',
    method: 'get',
  })
}


