package com.alian.ums.entity.bo;

import com.alian.ums.entity.RoleResourceRelation;
import lombok.Data;

import java.util.List;

@Data
public class RoleResourceRelationBo extends RoleResourceRelation {

    private List<RoleResourceRelation> roleResourceList;
}
