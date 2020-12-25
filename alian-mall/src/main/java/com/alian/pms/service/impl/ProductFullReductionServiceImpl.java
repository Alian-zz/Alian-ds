package com.alian.pms.service.impl;

import com.alian.pms.entity.ProductFullReduction;
import com.alian.pms.mapper.ProductFullReductionMapper;
import com.alian.pms.service.IProductFullReductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品满减表(只针对同商品) 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-14
 */
@Service
public class ProductFullReductionServiceImpl extends ServiceImpl<ProductFullReductionMapper, ProductFullReduction> implements IProductFullReductionService {

}
