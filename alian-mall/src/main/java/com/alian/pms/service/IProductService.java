package com.alian.pms.service;

import com.alian.pms.entity.Product;
import com.alian.pms.entity.bo.ProductBo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-12
 */
public interface IProductService extends IService<Product> {



    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    Product getProductById(Long id);

    /**
     * 对商品信息进行新增或修改操作
     * @param productBo
     */
    void saveOrUpdateProduct(ProductBo productBo);
}
