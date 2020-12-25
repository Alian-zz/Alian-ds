package com.alian.pms.service;

import com.alian.pms.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-10
 */
public interface IProductCategoryService extends IService<ProductCategory> {

    /**
     * 获取商品所有分类信息
     * @return
     */
    List<Map<String,Object>> getProductCateChildrenList();
}
