package com.alian.ums.entity.bo;

import com.alian.ums.entity.AdminRoleRelation;
import com.alian.ums.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class AdminRoleRelationBo extends Role {

    /**
     * 用户与角色关联数据
     */
    private List<AdminRoleRelation> adminRoleRelationList;


}
