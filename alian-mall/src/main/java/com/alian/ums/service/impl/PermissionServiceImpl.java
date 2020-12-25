package com.alian.ums.service.impl;

import com.alian.ums.entity.Permission;
import com.alian.ums.mapper.PermissionMapper;
import com.alian.ums.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户权限表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
