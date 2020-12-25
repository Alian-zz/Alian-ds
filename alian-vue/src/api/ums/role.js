import request from '../../utils/request';

/**
 * 查询角色列表
 * @param query
 * @returns {AxiosPromise}
 */
export function fetchRoleList(query) {
    return request({
        url: 'api/ums/role/getRoleList',
        method: 'get',
        params: query
    })
}

/**
 * 根据id查询角色信息
 * @param id
 * @returns {AxiosPromise}
 */
export function fetchRoleById(id) {
    return request({
        url: 'api/ums/role/getRoleById',
        method: 'get',
        params: {
            id: id,
        }
    })
}

/**
 * 新增或修改角色
 * @param roleForm
 * @returns {AxiosPromise}
 */
export function  fetchSaveOrUpdateRole(roleForm) {

    return request({
        url: 'api/ums/role/saveOrUpdateRole',
        method: 'post',
        data: roleForm
    })
}

/**
 * 根据id删除角色
 * @param id
 * @returns {AxiosPromise}
 */
export function  fetchDeleteRole(id) {
    return request({
        url: 'api/ums/role/deleteRoleById',
        method: 'delete',
        params: {
            id: id
        }
    })
}

/**
 * 修改启用按钮状态
 * @param id
 * @param status
 * @returns {AxiosPromise}
 */
export function updateStatus(id,status) {
    return request({
        url: 'api/ums/role/updateStatus',
        method: 'put',
        params: {
            id: id,
            status: status
        }
    })
}

/**
 * 查询菜单树
 * @returns {AxiosPromise}
 */
export function fetchTreeList() {
    return request({
        url: 'api/ums/menu/getTreeList',
        method: 'get',
    })
}

/**
 * 查询用户已拥有菜单
 * @returns {AxiosPromise}
 */
export function fetchMenuByRole(roleId) {
    return request({
        url: 'api/ums/roleMenuRelation/getMenuByRole',
        method: 'get',
        params: {
            roleId:roleId
        }
    })
}


/**
 * 获取所有的资源列表
 * @returns {AxiosPromise}
 */
export function fetchAllResourceList(){
    return request({
        url: '/api/ums/resource/getAllResourceList',
        method: 'get'
    })
}