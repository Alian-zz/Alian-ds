package com.alian.pms.service.impl;

import com.alian.pms.entity.ProductAttribute;
import com.alian.pms.mapper.ProductAttributeMapper;
import com.alian.pms.service.IProductAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-10
 */
@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements IProductAttributeService {

}
