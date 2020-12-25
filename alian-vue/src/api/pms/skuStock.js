import request from '@/utils/request'

export function fetchSkuStockList(productId) {
  return request({
    url:'/api/pms/skuStock/getSkuStockByProductId',
    method:'get',
    params: {
      productId: productId
    }
  })
}

export function update(pid,data) {
  return request({
    url:'/sku/update/'+pid,
    method:'post',
    data:data
  })
}
