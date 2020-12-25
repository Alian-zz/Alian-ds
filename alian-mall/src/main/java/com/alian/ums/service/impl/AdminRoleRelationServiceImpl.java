package com.alian.ums.service.impl;

import com.alian.ums.entity.AdminRoleRelation;
import com.alian.ums.entity.bo.AdminRoleRelationBo;
import com.alian.ums.mapper.AdminRoleRelationMapper;
import com.alian.ums.service.IAdminRoleRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@Service
public class AdminRoleRelationServiceImpl extends ServiceImpl<AdminRoleRelationMapper, AdminRoleRelation> implements IAdminRoleRelationService {

    @Override
    public void saveRoleByAdminId(AdminRoleRelationBo adminRoleRelationBo) {
        super.remove(new QueryWrapper<AdminRoleRelation>().eq("admin_id",adminRoleRelationBo.getId()));
        super.saveBatch(adminRoleRelationBo.getAdminRoleRelationList());
    }


}
