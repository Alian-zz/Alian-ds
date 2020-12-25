import request from '../../utils/request';

//分页查询品牌信息
export const fetchBrandData = query => {
    return request({
        url: 'api/pms/pmsBrand',
        method: 'get',
        params: query
    });
};

//查询所有品牌信息
export function fetchBrandList () {
    return request({
        url: 'api/pms/pmsBrand/queryBrandList',
        method: 'get',
    });
};


//是否为品牌制造商（修改）
export function fetchFactoryStatus(id,handleNavStatusChange){
    return request({
        url: 'api/pms/pmsBrand/updateBrand',
        method: 'put',
        params: {
            id: id,
            factoryStatus: handleNavStatusChange
        }
    });
}


//是否展示（修改）
export function fetchShowStatus(id,showStatus){
    return request({
        url: 'api/pms/pmsBrand/updateBrand',
        method: 'put',
        params: {
            id: id,
            showStatus: showStatus
        }
    });
}


//修改品品牌信息
export function fetchUpdateBrand(form){
    return request({
        url: 'api/pms/pmsBrand/updateBrand',
        method: 'put',
        params: form
    });
}



//新增品牌信息（新增）
export function fetchAddBrand(brandForm){
    return request({
        url: 'api/pms/pmsBrand/addBrand',
        method: 'post',
        params: brandForm
    });
}
