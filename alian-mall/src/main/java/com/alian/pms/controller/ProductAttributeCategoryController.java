package com.alian.pms.controller;


import com.alian.aop.LogFilter;
import com.alian.pms.entity.ProductAttributeCategory;
import com.alian.pms.entity.vo.ProductAttributeCategoryVo;
import com.alian.pms.service.IProductAttributeCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/pms/productAttributeCategory")
public class ProductAttributeCategoryController {

    @Autowired
    private IProductAttributeCategoryService productAttributeCategoryService;

    @GetMapping("/queryAttributeCategoryData")
    @LoginAuthorization
    @LogFilter("分页查询所有商品类型信息")
    public CommonsReturn queryAttributeCategory(Page<ProductAttributeCategory> page, ProductAttributeCategoryVo productAttributeCategoryVo){
        QueryWrapper<ProductAttributeCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",productAttributeCategoryVo.getName());
        IPage<ProductAttributeCategory> iPage = productAttributeCategoryService.page(page,queryWrapper);
        return CommonsReturn.success(iPage);
    }

    @GetMapping("/getAttrCategoryAll")
    @LoginAuthorization
    @LogFilter("根据id查出单个商品类型信息")
    public CommonsReturn getAttrCategoryAll(){
        List<ProductAttributeCategory> list = productAttributeCategoryService.list();
        return CommonsReturn.success(list);
    }

    @PostMapping("saveOrUpdateCategory")
    @LoginAuthorization
    @LogFilter("新增或者修改商品类型信息")
    public CommonsReturn saveOrUpdateCategory(ProductAttributeCategory productAttributeCategory){
        productAttributeCategoryService.saveOrUpdate(productAttributeCategory);
        return CommonsReturn.success();
    }



}
