package com.alian.ums.controller;


import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.ums.entity.Role;
import com.alian.ums.entity.vo.RoleVo;
import com.alian.ums.service.IRoleService;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping("getRoleList")
    @LoginAuthorization
    public CommonsReturn getRoleList(Page<Role> page, RoleVo roleVo){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(roleVo.getName())){
            queryWrapper.like("name",roleVo.getName());
        }
        IPage<Role> iPage = roleService.page(page, queryWrapper);
        return CommonsReturn.success(iPage);
    }

    @GetMapping("getRoleById")
    @LoginAuthorization
    public CommonsReturn getRoleById(Integer id){
        Role role = roleService.getById(id);
        return CommonsReturn.success(role);
    }

    @PostMapping("saveOrUpdateRole")
    @LoginAuthorization
    public CommonsReturn saveOrUpdateRole(@RequestBody Role role){
        roleService.saveOrUpdate(role);
        return CommonsReturn.success();
    }

    @PostMapping("updateStatus")
    @LoginAuthorization
    public CommonsReturn updateStatus(Role role){
        roleService.updateById(role);
        return CommonsReturn.success();
    }

    @DeleteMapping("deleteRoleById")
    @LoginAuthorization
    public CommonsReturn deleteRoleById(Long id){
        roleService.removeById(id);
        return CommonsReturn.success();
    }





}
