package com.alian.pms.service.impl;

import com.alian.pms.entity.*;
import com.alian.pms.entity.bo.ProductBo;
import com.alian.pms.mapper.ProductMapper;
import com.alian.pms.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Resource
    private IMemberPriceService memberPriceService;

    @Resource
    private IProductLadderService productLadderService;

    @Resource
    private ISkuStockService skuStockService;

    @Resource
    private IProductAttributeValueService productAttributeValueService;

    @Resource
    private IProductFullReductionService productFullReductionService;

    @Override
    public Product getProductById(Long id) {
        ProductBo productBo = new ProductBo();
        BeanUtils.copyProperties(super.getById(id),productBo);
        Map<String,Object> map = new HashMap<>();
        map.put("product_id",id);
        List<MemberPrice> memberPrices = memberPriceService.listByMap(map);
        List<ProductLadder> productLadders = productLadderService.listByMap(map);
        List<SkuStock> skuStocks = skuStockService.listByMap(map);
        List<ProductAttributeValue> productAttributeValues = productAttributeValueService.listByMap(map);
        List<ProductFullReduction> productFullReductions = productFullReductionService.listByMap(map);
        productBo.setMemberPriceList(memberPrices);
        productBo.setProductLadderList(productLadders);
        productBo.setSkuStockList(skuStocks);
        productBo.setProductAttributeValueList(productAttributeValues);
        productBo.setProductFullReductionList(productFullReductions);
        return productBo;
    }



    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveOrUpdateProduct(ProductBo productBo) {
        Product product = productBo;
        boolean status = super.saveOrUpdate(product);
        if(status){
            Long productId = product.getId();
            saveMemberPrice(productBo.getMemberPriceList(),productId);
            saveProductLadder(productBo.getProductLadderList(),productId);
            saveSkuStock(productBo.getSkuStockList(),productId);
            saveFullReduction(productBo.getProductFullReductionList(),productId);
            saveAttribute(productBo.getProductAttributeValueList(),productId);
        }
    }





    /**
     * 对商品的属性信息进行维护
     * @param productAttributeValueList
     * @param productId
     */
    private void saveAttribute(List<ProductAttributeValue> productAttributeValueList, Long productId) {
        productAttributeValueList.forEach(productAttributeValue -> {
            productAttributeValue.setProductId(productId);
        });
        productAttributeValueService.remove(new QueryWrapper<ProductAttributeValue>().eq("product_id",productId));
        productAttributeValueService.saveBatch(productAttributeValueList);
    }

    /**
     * 对商品的满减价格进行维护
     * @param productFullReductionList
     * @param productId
     */
    private void saveFullReduction(List<ProductFullReduction> productFullReductionList, Long productId) {
        productFullReductionList.forEach(productFullReduction -> {
            productFullReduction.setProductId(productId);
        });
        productFullReductionService.remove(new QueryWrapper<ProductFullReduction>().eq("product_id",productId));
        productFullReductionService.saveBatch(productFullReductionList);
    }

    /**
     * 对商品的sku进行维护
     * @param skuStockList
     * @param productId
     */
    private void saveSkuStock(List<SkuStock> skuStockList, Long productId) {

        for(int i=0; i<skuStockList.size(); i++){
            SkuStock skuStock = skuStockList.get(i);
            skuStock.setProductId(productId);
            String dateStr = DateFormatUtils.format(new Date(),"yyyyMMdd");
            String productCode = String.format("%06d",productId);
            String skuStockCode = String.format("%03d",i+1);
            skuStock.setSkuCode(StringUtils.join(dateStr,productCode,skuStockCode));
        }
        skuStockService.remove(new QueryWrapper<SkuStock>().eq("product_id",productId));
        skuStockService.saveBatch(skuStockList);

    }

    /**
     *对阶梯价格维护
     * @param productLadderList
     * @param productId
     */
    private void saveProductLadder(List<ProductLadder> productLadderList, Long productId) {
        productLadderList.forEach(productLadder -> {
            productLadder.setProductId(productId);
        });
        productLadderService.remove(new QueryWrapper<ProductLadder>().eq("product_id",productId));
        productLadderService.saveBatch(productLadderList);
    }


    /**
     * 对商品的价格进行维护
     * @param memberPriceList
     * @param productId
     */
    private void saveMemberPrice(List<MemberPrice> memberPriceList, Long productId) {
        memberPriceList.forEach(memberPrice -> {
            memberPrice.setProductId(productId);
        });
        memberPriceService.remove(new QueryWrapper<MemberPrice>().eq("product_id",productId));
        memberPriceService.saveBatch(memberPriceList);
    }



}
