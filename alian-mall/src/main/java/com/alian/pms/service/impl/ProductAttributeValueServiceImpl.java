package com.alian.pms.service.impl;

import com.alian.pms.entity.ProductAttributeValue;
import com.alian.pms.mapper.ProductAttributeValueMapper;
import com.alian.pms.service.IProductAttributeValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储产品参数信息的表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-16
 */
@Service
public class ProductAttributeValueServiceImpl extends ServiceImpl<ProductAttributeValueMapper, ProductAttributeValue> implements IProductAttributeValueService {

}
