package com.alian.ums.entity.bo;

import com.alian.ums.entity.RoleMenuRelation;
import lombok.Data;

import java.util.List;

/**
 * @author ZZL
 */
@Data
public class RoleMenuRelationBo extends RoleMenuRelation{

    private List<RoleMenuRelation> menuByRoleList;

}
