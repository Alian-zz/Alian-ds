package com.alian.ums.controller;

import com.alian.ums.entity.ResourceCategory;
import com.alian.ums.entity.RoleResourceRelation;
import com.alian.ums.entity.UmsResource;
import com.alian.ums.entity.bo.RoleResourceRelationBo;
import com.alian.ums.entity.vo.ResourceVo;
import com.alian.ums.service.IResourceCategoryService;
import com.alian.ums.service.IResourceService;
import com.alian.ums.service.IRoleResourceRelationService;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 后台资源表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/resource")
public class ResourceController {

    @Resource
    private IResourceService resourceService;

    @Resource
    private IResourceCategoryService resourceCategoryService;

    @Resource
    private IRoleResourceRelationService roleResourceRelationService;


    @GetMapping("getResourceList")
    public CommonsReturn getResourceList(Page<UmsResource> page, ResourceVo resourceVo){
        IPage<UmsResource> iPage = resourceService.getResourceList(page,resourceVo);
        return CommonsReturn.success(iPage);
    }


    @GetMapping("getCategoryOptions")
    public CommonsReturn getCategoryOptions(){
        List<ResourceCategory> list = resourceCategoryService.list();
        return CommonsReturn.success(list);
    }

    @GetMapping("getResourceById")
    public CommonsReturn getResourceById(Long id){
        UmsResource umsResource = resourceService.getById(id);
        return CommonsReturn.success(umsResource);
    }


    @PostMapping("saveOrUpdateResource")
    public CommonsReturn saveOrUpdateResource(@RequestBody UmsResource umsResource){
        resourceService.saveOrUpdate(umsResource);
        return CommonsReturn.success();
    }

    @DeleteMapping("deleteResource")
    public CommonsReturn deleteResource(Long id){
        resourceService.removeById(id);
        return CommonsReturn.success();
    }

    @GetMapping("getAllResourceList")
    public CommonsReturn getAllResourceList(){
        List<UmsResource> list = resourceService.list();
        return CommonsReturn.success(list);
    }


    @GetMapping("getResourceByRole")
    public CommonsReturn getResourceByRole(Long roleId){
        QueryWrapper<RoleResourceRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<RoleResourceRelation> list = roleResourceRelationService.list(queryWrapper);
        return CommonsReturn.success(list);
    }

    @PostMapping("saveResourceByRole")
    public CommonsReturn saveResourceByRole(@RequestBody RoleResourceRelationBo roleResourceRelationBo){
        QueryWrapper<RoleResourceRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleResourceRelationBo.getRoleId());
        roleResourceRelationService.remove(queryWrapper);
        roleResourceRelationService.saveBatch(roleResourceRelationBo.getRoleResourceList());
        return CommonsReturn.success();
    }



}
