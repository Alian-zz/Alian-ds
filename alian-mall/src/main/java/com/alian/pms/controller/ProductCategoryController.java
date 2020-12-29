package com.alian.pms.controller;


import com.alian.config.LogFilter;
import com.alian.pms.entity.ProductCategory;
import com.alian.pms.entity.vo.ProductCategoryVo;
import com.alian.pms.service.IProductCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alian.config.logAuthorization.LoginAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alian.utils.result.CommonsReturn;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/pms/product-category")
public class ProductCategoryController {

    @Autowired
    private IProductCategoryService productCategoryService;

    /**
     * 查询一级商品的所有分类信息
     * @param page
     * @param categoryVo
     * @return
     */
    @GetMapping("/getProductCategory")
    @LoginAuthorization
    @LogFilter("查询一级商品的所有分类信息")
    public CommonsReturn queryProductCategory(Page<ProductCategory> page, ProductCategoryVo categoryVo){
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        if(categoryVo.getPid() != null){
            queryWrapper.eq("parent_id",categoryVo.getPid());
        }else {
            queryWrapper.eq("level",categoryVo.getLevel());
        }
        IPage<ProductCategory> iPage = productCategoryService.page(page,queryWrapper);
        return CommonsReturn.success(iPage);
    }

    /**
     * 查询一级分类下拉框方法
     * @param categoryVo
     * @return
     */
    @GetMapping("/getProductCateList")
    @LoginAuthorization
    @LogFilter("查询一级分类下拉框")
    public CommonsReturn getProductCateList(ProductCategoryVo categoryVo){
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",categoryVo.getLevel());
        List<ProductCategory> list = productCategoryService.list(queryWrapper);
        return CommonsReturn.success(list);
    }

    /**
     * 查询分类所有下拉框方法
     * @param
     * @return
     */
    @GetMapping("/getProductCateChildrenList")
    @LoginAuthorization
    @LogFilter("查询分类所有下拉框")
    public CommonsReturn getProductCateChildrenList(){
        List<Map<String,Object>> list = productCategoryService.getProductCateChildrenList();
        return CommonsReturn.success(list);
    }

    /**
     * 根据id查询单个分类信息
     * @param id
     * @return
     */
    @GetMapping("/getProductCategoryById")
    @LoginAuthorization
    @LogFilter("查询单个分类信息")
    public CommonsReturn getProductCategoryById(Integer id){
        ProductCategory productCategory = productCategoryService.getById(id);
        return CommonsReturn.success(productCategory);
    }

    /**
     * 新增分类信息的方法
     * @param productCategory
     * @return
     */
    @PostMapping("addProductCategory")
    @LoginAuthorization
    @LogFilter("新增分类信息数据")
    public CommonsReturn addProductCategory(ProductCategory productCategory){
        if(productCategory.getParentId() == 0){
            productCategory.setLevel(0);
        }else {
            productCategory.setLevel(1);
        }
        productCategoryService.save(productCategory);
        return CommonsReturn.success();
    }

    /*------------------------修改分类信息的方法------------------*/
    @PutMapping("updateProductCategory")
    @LoginAuthorization
    @LogFilter("修改分类信息数据")
    public CommonsReturn updateProductCategory(ProductCategory productCategory){
        productCategoryService.updateById(productCategory);
        return CommonsReturn.success();
    }


}
