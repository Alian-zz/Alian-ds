package com.alian.ums.service;

import com.alian.ums.entity.AdminRoleRelation;
import com.alian.ums.entity.bo.AdminRoleRelationBo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户和角色关系表 服务类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
public interface IAdminRoleRelationService extends IService<AdminRoleRelation> {

    void saveRoleByAdminId(AdminRoleRelationBo adminRoleRelationBo);
}
