package com.alian.ums.service;

import com.alian.ums.entity.Admin;
import com.alian.ums.entity.Role;
import com.alian.ums.entity.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-16
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 通过用户id获取用户拥有的权限
     * @param id
     * @return
     */
    List<UmsResource> queryResourceByAdmin(Long id,String username);

    /**
     *通过用户id获取用户拥有的角色
     * @param id
     * @return
     */
    List<Role> queryRoleByAdmin(Long id,String username);

    String login(String username, String password);

    Admin queryOne(String username);
}
