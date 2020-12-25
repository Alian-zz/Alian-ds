import request from '../../utils/request';

/**
 * 查询所有商品属性信息（分页）
 * @param query
 * @returns {AxiosPromise}
 */
export const fetchAttrCategoryData = query => {
    return request({
        url: '/api/pms/productAttributeCategory/queryAttributeCategoryData',
        method: 'get',
        params: query
    });
};

/**
 * 根据id查询出商品的属性信息
 * @param id
 * @returns {AxiosPromise}
 */
export const getArrtCategoryById = id => {
  return request({
    url: '/api/pms/productAttributeCategory/getAttributeCategoryById',
    method: 'get',
    params: {
        id: id
    }
  });
};


/**
 * 对商品的属性信息进行新增或者修改
 * @param params
 * @returns {AxiosPromise}
 */
export const saveOrUpdateCategory = params => {
  return request({
    url: '/api/pms/productAttributeCategory/saveOrUpdateCategory',
    method: 'post',
    params: params
  });
};

/**
 * 查询所有商品属性信息（不带分页）
 * @returns {AxiosPromise}
 */
export function fetchAttrCategoryAll(){
    return request({
        url: '/api/pms/productAttributeCategory/getAttrCategoryAll',
        method: 'get',
    })
}
