package com.alian.ums.service;

import com.alian.ums.entity.UmsResource;
import com.alian.ums.entity.vo.ResourceVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
public interface IResourceService extends IService<UmsResource> {

    IPage<UmsResource> getResourceList(Page<UmsResource> page, ResourceVo resourceVo);
}
