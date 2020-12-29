package com.alian.pms.controller;


import com.alian.config.LogFilter;
import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.pms.entity.Brand;
import com.alian.pms.service.IBrandService;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/pms/pmsBrand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    /**
     * 分页查询品牌信息
     * @param page
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('pms:brand')")
    @LogFilter("查询所有品牌信息")
    public CommonsReturn queryBrand(Page<Brand> page){
        IPage<Brand> iPage = brandService.page(page);
        return CommonsReturn.success(iPage);
    }

    /**
     * 查询所有品牌信息
     * @return
     */
    @GetMapping("queryBrandList")
    @PreAuthorize("hasAnyAuthority('pms:brand')")
    public CommonsReturn queryBrandList(){
        List<Brand> list = brandService.list();
        return CommonsReturn.success(list);
    }

    /**
     * 新增品牌信息
     * @param brand
     * @return
     */
    @PostMapping("/addBrand")
    @LogFilter("新增品牌信息")
    @PreAuthorize("hasAnyAuthority('pms:brand')")
    public CommonsReturn addBrand(Brand brand){
        brandService.save(brand);
        return CommonsReturn.success();
    }


    /**
     * 修改品牌信息
     * @param brand
     * @return
     */
    @PutMapping("/updateBrand")
    @LoginAuthorization
    @LogFilter("修改品牌信息")
    public CommonsReturn updateFactoryStatus(Brand brand){
        brandService.updateById(brand);
        return CommonsReturn.success();
    }


}
