package com.alian.ums.service.impl;

import com.alian.ums.entity.UmsResource;
import com.alian.ums.entity.vo.ResourceVo;
import com.alian.ums.mapper.ResourceMapper;
import com.alian.ums.service.IResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, UmsResource> implements IResourceService {

    @Override
    public IPage<UmsResource> getResourceList(Page<UmsResource> page, ResourceVo resourceVo) {
        QueryWrapper<UmsResource> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(resourceVo.getName())){
            queryWrapper.like("name",resourceVo.getName());
        }
        if(StringUtils.isNotBlank(resourceVo.getUrl())){
            queryWrapper.like("url",resourceVo.getUrl());
        }
        if(resourceVo.getCategoryId() != null){
            queryWrapper.eq("category_id",resourceVo.getCategoryId());
        }
        IPage<UmsResource> iPage = super.page(page, queryWrapper);
        return iPage;

    }
}
