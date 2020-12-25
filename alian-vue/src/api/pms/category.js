import request from '../../utils/request';

/*------------------查询分类数据方法------------------------------*/
export const fetchCategoryData = query => {
    return request({
        url: '/api/pms/product-category/getProductCategory',
        method: 'get',
        params: query
    });
};
/*-----------------修改回显-----------------*/
export const fetchCategoryById = id => {
    return request({
        url: '/api/pms/product-category/getProductCategoryById',
        method: 'get',
        params: {
            id: id
        }
    });
};

/*查询1级分类下拉框的方法*/
export const fetchProductCateList = level => {
    return request({
        url: '/api/pms/product-category/getProductCateList',
        method: 'get',
        params: {
            level: level
        }
    });
};

export function fetchProductCateChildrenList (){
    return request({
        url: '/api/pms/product-category/getProductCateChildrenList',
        method: 'get',
    })
}

/*新增分类信息方法*/
export const fetchAddCategory = ProductCategoryForm => {
    return request({
        url: '/api/pms/product-category/addProductCategory',
        method: 'post',
        params: ProductCategoryForm
    });
};

/*修改分类信息方法*/
export const fetchUpdateCategory = ProductCategoryForm => {
    return request({
        url: '/api/pms/product-category/updateProductCategory',
        method: 'put',
        params: ProductCategoryForm
    });
};

//是否显示在导航栏（修改）
export function fetchNavStatusStatus(id,navStatus){
    return request({
        url: '/api/pms/product-category/updateProductCategory',
        method: 'put',
        params: {
            id: id,
            navStatus: navStatus
        }
    });
}

//是否显示（修改）
export function fetchShowStatusCategory(id,showStatus){
    return request({
        url: '/api/pms/product-category/updateProductCategory',
        method: 'put',
        params: {
            id: id,
            showStatus: showStatus
        }
    });
}




