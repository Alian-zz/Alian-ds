package com.alian.ums.controller;


import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.ums.entity.ResourceCategory;
import com.alian.ums.service.IResourceCategoryService;
import com.alian.utils.result.CommonsReturn;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/resourceCategory")
public class ResourceCategoryController {

    @Resource
    private IResourceCategoryService resourceCategoryService;

    @GetMapping("getResourceCategorylist")
    @LoginAuthorization
    public CommonsReturn getResourceCategorylist(){
        List<ResourceCategory> list = resourceCategoryService.list();
        return CommonsReturn.success(list);
    }

    @GetMapping("getResourceCategoryById")
    @LoginAuthorization
    public CommonsReturn getResourceCategoryById(Long id){
        ResourceCategory resourceCategory = resourceCategoryService.getById(id);
        return CommonsReturn.success(resourceCategory);
    }

    @PostMapping("saveOrUpdateResourceCategory")
    @LoginAuthorization
    public CommonsReturn saveOrUpdateResourceCategory(@RequestBody ResourceCategory resourceCategory){
        resourceCategoryService.saveOrUpdate(resourceCategory);
        return CommonsReturn.success();
    }

    @DeleteMapping("deleteResourceCategory")
    @LoginAuthorization
    public CommonsReturn deleteResourceCategory(Long id){
        resourceCategoryService.removeById(id);
        return CommonsReturn.success();
    }



}
