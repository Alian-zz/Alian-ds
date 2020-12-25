package com.alian.ums.mapper;

import com.alian.ums.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> queryMenuList(Long id);
}
