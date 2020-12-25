package com.alian.pms.entity.bo;

import com.alian.pms.entity.*;
import lombok.Data;

import java.util.List;


@Data
public class ProductBo extends Product {

    /**
     * 会员价格集合
     */
    private List<MemberPrice> memberPriceList;
    /**
     * 阶梯价格维护
     */
    private  List<ProductLadder> productLadderList;
    /**
     * 商品满减价格
     */
    private List<ProductFullReduction> productFullReductionList;
    /**
     *SKU维护
     */
    private List<SkuStock> skuStockList;
    /**
     *商品属性和参数列表
     */
    private List<ProductAttributeValue> productAttributeValueList;

}
