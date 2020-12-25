package com.alian.pms.entity.vo;

import com.alian.pms.entity.ProductCategory;
import lombok.Data;

@Data
public class ProductCategoryVo extends ProductCategory {

    private Integer level;

    private Integer pid;

}
