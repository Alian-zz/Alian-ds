package com.alian.ums.mapper;

import com.alian.ums.entity.Admin;
import com.alian.ums.entity.Role;
import com.alian.ums.entity.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-16
 */
public interface AdminMapper extends BaseMapper<Admin> {

    List<UmsResource> queryResourceByAdmin(Long id);

    List<Role> queryRoleByAdmin(Long id);
}
