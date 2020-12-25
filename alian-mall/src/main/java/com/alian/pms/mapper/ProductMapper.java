package com.alian.pms.mapper;

import com.alian.pms.entity.Product;
import com.alian.pms.entity.vo.ProductVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-12
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询商品信息
     * @param page
     * @param productVo
     * @return
     */
   /* IPage<Product> queryPageProduct(@Param("page") Page<Product> page,@Param("productVo") ProductVo productVo);*/
}
