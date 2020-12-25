package com.alian.pms.service.impl;

import com.alian.pms.entity.Brand;
import com.alian.pms.mapper.BrandMapper;
import com.alian.pms.service.IBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-09
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

}
