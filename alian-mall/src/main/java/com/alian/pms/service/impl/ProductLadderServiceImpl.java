package com.alian.pms.service.impl;

import com.alian.pms.entity.ProductLadder;
import com.alian.pms.mapper.ProductLadderMapper;
import com.alian.pms.service.IProductLadderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-14
 */
@Service
public class ProductLadderServiceImpl extends ServiceImpl<ProductLadderMapper, ProductLadder> implements IProductLadderService {

}
