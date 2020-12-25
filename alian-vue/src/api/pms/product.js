import request from '@/utils/request'
export function fetchList(params) {
    return request({
        url:'/api/pms/product/queryProductListData',
        method:'get',
        params:params
    })
}


/*export function fetchSimpleList(params) {
    return request({
        url:'/product/simpleList',
        method:'get',
        params:params
    })
}


export function updateDeleteStatus(params) {
    return request({
        url:'/product/update/deleteStatus',
        method:'post',
        params:params
    })
}*/
/**
 * 新品开关功能维护
 * @param id
 * @param newStatus
 * @returns {AxiosPromise}
 */
export function updateNewStatus(id,newStatus) {
    return request({
        url:'/api/pms/product/updateProductStatus',
        method:'put',
        params: {
            id: id,
            newStatus: newStatus
        }
    })
}

/**
 * 推荐开关功能维护
 * @param id
 * @param recommendStatus
 * @returns {AxiosPromise}
 */
export function updateRecommendStatus(id,recommendStatus) {
    return request({
        url:'/api/pms/product/updateProductStatus',
        method:'put',
        params: {
            id: id,
            recommendStatus: recommendStatus
        }
    })
}

/**
 * 上下架开关功能维护
 * @param id
 * @param publishStatus
 * @returns {AxiosPromise}
 */
export function updatePublishStatus(id,publishStatus) {
    return request({
        url:'/api/pms/product/updateProductStatus',
        method:'put',
        params: {
            id: id,
            publishStatus: publishStatus
        }
    })
}

export function saveProductData(data) {
    return request({
        url:'/api/pms/product/saveOrUpdateProductData',
        method:'post',
        data:data
    })
}

export function updateProductData(data) {
    return request({
        url:'/api/pms/product/saveOrUpdateProductData',
        method:'post',
        data:data
    })
}

export function getProduct(id) {
    return request({
        url:'/api/pms/product/getProductById',
        method:'get',
        params: {
            id:id
        }
    })
}
