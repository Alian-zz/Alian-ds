package com.alian.pms.controller;


import com.alian.aop.LogFilter;
import com.alian.pms.entity.ProductAttribute;
import com.alian.pms.entity.vo.ProductAttributeVo;
import com.alian.pms.service.IProductAttributeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alian.config.logAuthorization.LoginAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.alian.utils.result.CommonsReturn;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/pms/productAttribute")
public class ProductAttributeController {

    @Autowired
    private IProductAttributeService productAttributeService;

    @GetMapping("queryAttributeData")
    @LogFilter("查看单个商品类型的所有属性信息")
    public CommonsReturn queryAttributeData(Page<ProductAttribute> page, ProductAttributeVo productAttributeVo){
        QueryWrapper<ProductAttribute> queryWrapper = new QueryWrapper<>();
        //根据名字进行条件查询
        queryWrapper.like("name",productAttributeVo.getName());
        //查看是类型是属性还是参数
        queryWrapper.eq("type",productAttributeVo.getType());
        queryWrapper.eq("product_attribute_category_id",productAttributeVo.getCid());
        Page<ProductAttribute> iPage = productAttributeService.page(page,queryWrapper);
        return CommonsReturn.success(iPage);
    }

    @GetMapping("getArrtCategoryById")
    @LogFilter("查看单个商品类型单个属性信息")
    public CommonsReturn getArrtCategoryById(Integer id){
        ProductAttribute productAttribute = productAttributeService.getById(id);
        return CommonsReturn.success(productAttribute);
    }

    @PostMapping("saveOrUpdateCategory")
    @LogFilter("新增或修改单个商品类型单个属性信息")
    public CommonsReturn saveOrUpdateCategory(ProductAttribute productAttribute){
        productAttributeService.saveOrUpdate(productAttribute);
        return CommonsReturn.success();
    }

    @GetMapping("getProductAttrList")
    @LogFilter("查看商品属性信息")
    public CommonsReturn getProductAttrList(Long cid,Integer type){
        QueryWrapper<ProductAttribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_attribute_category_id",cid);
        //查看是类型是属性还是参数
        queryWrapper.eq("type",type);
        List<ProductAttribute> list = productAttributeService.list(queryWrapper);
        return CommonsReturn.success(list);
    }

}
