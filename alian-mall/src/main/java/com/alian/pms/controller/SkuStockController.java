package com.alian.pms.controller;


import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.pms.entity.SkuStock;
import com.alian.pms.service.ISkuStockService;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * sku的库存 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/pms/skuStock")
public class SkuStockController {

    @Resource
    private ISkuStockService skuStockService;


    @GetMapping("getSkuStockByProductId")
    @LoginAuthorization
    public CommonsReturn getSkuStockByProductId(Long productId){
        List<SkuStock> skuStockList = skuStockService.list(new QueryWrapper<SkuStock>().eq("product_id", productId));
        return CommonsReturn.success(skuStockList);
    }


}
