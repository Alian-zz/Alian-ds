package com.alian.ums.controller;


import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.ums.entity.Admin;
import com.alian.ums.entity.AdminRoleRelation;
import com.alian.ums.entity.Role;
import com.alian.ums.entity.vo.AdminVo;
import com.alian.ums.service.IAdminRoleRelationService;
import com.alian.ums.service.IAdminService;
import com.alian.ums.service.IRoleService;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/ums/admin")
@Api(tags = "AdminController")
public class AdminController {

    @Resource
    private IAdminService adminService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IAdminRoleRelationService adminRoleRelationService;

    @GetMapping("getAdminList")
    @LoginAuthorization
    @ApiOperation("获取用户信息列表")
    public CommonsReturn getAdminList(Page<Admin> page, AdminVo adminVo){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(adminVo.getUsername())){
            queryWrapper.like("username", adminVo.getUsername());
        }
        IPage<Admin> iPage = adminService.page(page, queryWrapper);
        return CommonsReturn.success(iPage);
    }

    @GetMapping("queryUserNameOnly")
    @ApiOperation("查询名字是否唯一")
    public CommonsReturn queryUserNameOnly(String username){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Admin admin = adminService.getOne(queryWrapper);
        return CommonsReturn.success(admin);
    }

    @GetMapping("getAdminById")
    @LoginAuthorization
    @ApiOperation("根据id获取用户信息用来进行修改回显")
    public CommonsReturn getAdminById(Long id){
        Admin admin = adminService.getById(id);
        return CommonsReturn.success(admin);
    }

    @PostMapping("createAdmin")
    @LoginAuthorization
    @ApiOperation("新增用户信息")
    public CommonsReturn createAdmin(@RequestBody Admin admin){
        adminService.save(admin);
        return CommonsReturn.success();
    }

    @PutMapping("updateAdmin")
    @LoginAuthorization
    @ApiOperation("修改用户信息")
    public CommonsReturn updateAdmin(@RequestBody Admin admin){
        adminService.updateById(admin);
        return CommonsReturn.success();
    }

    @PutMapping("updateStatus")
    @LoginAuthorization
    @ApiOperation("修改用户启用状态")
    public CommonsReturn updateStatus(Admin admin){
        adminService.updateById(admin);
        return CommonsReturn.success();
    }

    @DeleteMapping("deleteAdminById")
    @LoginAuthorization
    @ApiOperation("删除用户信息")
    public CommonsReturn deleteAdminById(Integer id){
        adminService.removeById(id);
        return CommonsReturn.success();
    }

    @GetMapping("getAllRole")
    @LoginAuthorization
    @ApiOperation("获取所有角色信息")
    public CommonsReturn getAllRole(){
        List<Role> list = roleService.list();
        return CommonsReturn.success(list);
    }

    @GetMapping("getRoleByAdminId")
    @LoginAuthorization
    @ApiOperation("根据用户id获取该用户拥有的角色")
    public CommonsReturn getRoleByAdminId(@RequestParam Long adminId){
        QueryWrapper<AdminRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id",adminId);
        List<AdminRoleRelation> list = adminRoleRelationService.list(queryWrapper);
        return CommonsReturn.success(list);
    }



}
