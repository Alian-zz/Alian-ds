package com.alian.ums.controller;


import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.ums.entity.AdminRoleRelation;
import com.alian.ums.entity.bo.AdminRoleRelationBo;
import com.alian.ums.service.IAdminRoleRelationService;
import com.alian.utils.result.CommonsReturn;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/adminRoleRelation")
public class AdminRoleRelationController {

    @Resource
    private IAdminRoleRelationService adminRoleRelationService;

    @PostMapping("saveRoleByAdminId")
    @LoginAuthorization
    @ApiOperation("更新用户拥有的角色")
    public CommonsReturn saveRoleByAdminId(@RequestBody AdminRoleRelationBo adminRoleRelationBo){
        adminRoleRelationService.saveRoleByAdminId(adminRoleRelationBo);
        return CommonsReturn.success();
    }

}
