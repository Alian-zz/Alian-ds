import request from '../../utils/request';

export const fetchAttributeCategoryListData = query => {
    return request({
        url: '/api/pms/productAttribute/queryAttributeData',
        method: 'get',
        params: query
    });
};
export const getArrtCategoryById = id => {
  return request({
    url: '/api/pms/productAttribute/getArrtCategoryById',
    method: 'get',
    params:{
        id: id
    }
  });
};

export const saveOrUpdateCategory = params => {
  return request({
    url: '/api/pms/productAttribute/saveOrUpdateCategory',
    method: 'post',
    params: params
  });
};

export function fetchProductAttrList(cid,type){
    return request({
        url: '/api/pms/productAttribute/getProductAttrList',
        method: 'get',
        params: {
            cid: cid,
            type: type
        }
    })
}
