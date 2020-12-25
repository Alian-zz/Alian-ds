package com.alian.ums.service.impl;

import com.alian.ums.entity.Role;
import com.alian.ums.mapper.RoleMapper;
import com.alian.ums.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
