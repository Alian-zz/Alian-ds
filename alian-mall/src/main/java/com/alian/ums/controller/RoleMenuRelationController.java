package com.alian.ums.controller;


import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.ums.entity.RoleMenuRelation;
import com.alian.ums.entity.bo.RoleMenuRelationBo;
import com.alian.ums.service.IRoleMenuRelationService;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台角色菜单关系表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/roleMenuRelation")
public class RoleMenuRelationController {

    @Resource
    private IRoleMenuRelationService roleMenuRelationService;

    @GetMapping("getMenuByRole")
    @LoginAuthorization
    public CommonsReturn getMenuByRole(Long roleId){
        QueryWrapper<RoleMenuRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<RoleMenuRelation> list = roleMenuRelationService.list(queryWrapper);
        return CommonsReturn.success(list);
    }

    @PostMapping("saveMenuByRole")
    @LoginAuthorization
    public CommonsReturn saveMenuByRole(@RequestBody RoleMenuRelationBo roleMenuRelationBo){
        roleMenuRelationService.remove(new QueryWrapper<RoleMenuRelation>().eq("role_id",roleMenuRelationBo.getRoleId()));
        roleMenuRelationService.saveBatch(roleMenuRelationBo.getMenuByRoleList());
        return CommonsReturn.success();
    }


}
