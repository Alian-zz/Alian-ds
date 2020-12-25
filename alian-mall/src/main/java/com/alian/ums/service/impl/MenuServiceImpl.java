package com.alian.ums.service.impl;

import com.alian.ums.entity.Admin;
import com.alian.ums.entity.Menu;
import com.alian.ums.mapper.MenuMapper;
import com.alian.ums.service.IAdminService;
import com.alian.ums.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private IAdminService adminService;

    @Override
    public List<Map<String, Object>> getTreeList() {
        List<Menu> menuList = super.list();
        return queryTreeList(menuList,0L);
    }

    private List<Map<String, Object>> queryTreeList(List<Menu> menuList, Long pid) {
        List<Map<String, Object>> list = new ArrayList<>();
        menuList.forEach(menu -> {
            Map<String, Object> map = null;
            if(pid.equals(menu.getParentId())){
                map = new HashMap<>();
                map.put("id",menu.getId());
                map.put("title",menu.getTitle());
                map.put("children",queryTreeList(menuList,menu.getId()));
            }
            if(map != null){
                list.add(map);
            }

        });
        return list;
    }

    /**
     * 动态菜单
     * @param username
     * @return
     */
    @Override
    public List<Map<String, Object>> queryMenuList(String username) {
        Admin admin = adminService.queryOne(username);
        List<Menu> menuList = menuMapper.queryMenuList(admin.getId());
        return queryMenuDataList(menuList,0L);
    }

    private List<Map<String, Object>> queryMenuDataList(List<Menu> menuList, Long pid) {
        List<Map<String, Object>> list = new ArrayList<>();
        menuList.forEach(menu -> {
            Map<String, Object> map = null;
            if(pid.equals(menu.getParentId())){
                map = new HashMap<>();
                map.put("icon",menu.getIcon());
                map.put("title",menu.getTitle());
                map.put("index",menu.getName());
                if (menu.getLevel()==0) {
                    map.put("subs",queryMenuDataList(menuList,menu.getId()));
                }

            }
            if(map != null){
                list.add(map);
            }

        });
        return list;

    }


}
