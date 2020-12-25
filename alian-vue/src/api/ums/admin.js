import request from '../../utils/request';

//分页查询品牌信息
export const fetchAdminList = query => {
    return request({
        url: 'api/ums/admin/getAdminList',
        method: 'get',
        params: query
    });
};

export const fetchAdminById = id => {
    return request({
        url: 'api/ums/admin/getAdminById',
        method: 'get',
        params: {
            id: id
        }
    });
};

export function fetchUpdateAdmin(admin) {
    return request({
        url: 'api/ums/admin/updateAdmin',
        method: 'put',
        data: admin
    })
}


export function fetchCreateAdmin(admin) {
    return request({
        url: 'api/ums/admin/createAdmin',
        method: 'post',
        data: admin
    })
}

export function fetchUpdateStatus(id,status) {
    return request({
        url: 'api/ums/admin/updateStatus',
        method: 'put',
        params: {
            id: id,
            status: status
        }
    })
}

export function fetchDeleteAdminById(id) {
    return request({
        url: 'api/ums/admin/deleteAdminById',
        method: 'delete',
        params: {
            id: id,
        }
    })
}

export function fetchRoleByAdmin(adminId) {
    return request({
        url: 'api/ums/admin/getRoleByAdminId',
        method: 'get',
        params: {
            adminId: adminId
        }
    })
}

export function fetchAllRole() {
    return request({
        url: 'api/ums/admin/getAllRole',
        method: 'get',
    })
}

export function createRoleById(adminId,allList) {
    return request({
        url: 'api/ums/adminRoleRelation/saveRoleByAdminId',
        method: 'post',
        data: {
            adminId: adminId,
            adminRoleRelationList: allList
        }
    })
}

/**
 * 验证用户名是否唯一
 * @param username
 * @returns {AxiosPromise}
 */
export function usernameValidate(username) {
    return request({
        url: 'api/ums/admin/queryUserNameOnly',
        method: 'get',
        params: {
            username: username
        }
    })
}
