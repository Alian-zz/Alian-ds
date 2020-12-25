package com.alian.ums.service;

import com.alian.ums.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
public interface IMenuService extends IService<Menu> {

    List<Map<String, Object>> getTreeList();

    List<Map<String, Object>> queryMenuList(String userName);
}
