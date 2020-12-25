package com.alian.pms.service.impl;

import com.alian.pms.entity.ProductCategory;
import com.alian.pms.mapper.ProductCategoryMapper;
import com.alian.pms.service.IProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-10
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {

    @Override
    public List<Map<String,Object>> getProductCateChildrenList() {
        List<ProductCategory> productCategoryList = super.list();
        return getProductCateChildren(productCategoryList, 0L);
    }

    private List<Map<String, Object>> getProductCateChildren(List<ProductCategory> productCategoryList,Long pid) {
        List<Map<String, Object>> list = new ArrayList<>();
        productCategoryList.forEach(productCategory -> {
            Map<String,Object> map = null;
            if(pid.equals(productCategory.getParentId())){
                map = new HashMap<>();
                map.put("value",productCategory.getId());
                map.put("label",productCategory.getName());
                if(productCategory.getLevel() == 0){
                    map.put("children",getProductCateChildren(productCategoryList,productCategory.getId()));
                }
            }
            if(map != null){
                list.add(map);
            }

        });
        return list;

    }


}
