package com.alian.pms.controller;


import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.pms.entity.Product;
import com.alian.pms.entity.bo.ProductBo;
import com.alian.pms.entity.vo.ProductVo;
import com.alian.pms.service.IBrandService;
import com.alian.pms.service.IProductCategoryService;
import com.alian.pms.service.IProductService;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-12
 */
@RestController
@RequestMapping("/pms/product")
public class ProductController {

    @Resource
    private IProductService productService;

    /**
     * 查询商品信息
     * @param page
     * @param productVo
     * @return
     */
    @GetMapping("queryProductListData")
    @LoginAuthorization
    public CommonsReturn queryProductListData(Page<Product> page, ProductVo productVo){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("keywords",productVo.getKeywords());
        queryWrapper.like("product_sn",productVo.getProductSn());
        if(productVo.getProductCategoryId() != null){
            queryWrapper.eq("product_category_id",productVo.getProductCategoryId());
        }
        if(productVo.getBrandId() != null){
            queryWrapper.eq("brand_id",productVo.getBrandId());
        }
        if(productVo.getPublishStatus() != null){
            queryWrapper.eq("publish_status",productVo.getPublishStatus());
        }
        if(productVo.getVerifyStatus() != null){
            queryWrapper.eq("verify_status",productVo.getVerifyStatus());
        }
        IPage<Product> iPage = productService.page(page, queryWrapper);
        return CommonsReturn.success(iPage);
    }

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @GetMapping("getProductById")
    @LoginAuthorization
    public CommonsReturn getProductById(Long id){
        Product product = productService.getProductById(id);
        return CommonsReturn.success(product);
    }


    /**
     * 对商品的新增或者修改进行维护
     * @param productBo
     * @return
     */
    @PostMapping("saveOrUpdateProductData")
    public CommonsReturn saveOrUpdateProduct(@RequestBody ProductBo productBo){
        productService.saveOrUpdateProduct(productBo);
        return CommonsReturn.success();
    }

    /**
     * 对商品的上下架、新品、推荐状态进行修改
     * @param product
     * @return
     */
    @PutMapping("updateProductStatus")
    public CommonsReturn updateProductStatus(Product product){
        if(product.getId() != null){
            productService.updateById(product);
        }
        return CommonsReturn.success();
    }






}
