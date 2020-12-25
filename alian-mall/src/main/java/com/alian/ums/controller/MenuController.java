package com.alian.ums.controller;


import com.alian.config.logAuthorization.LoginAuthorization;
import com.alian.ums.entity.Menu;
import com.alian.ums.entity.vo.MenuVo;
import com.alian.ums.service.IMenuService;
import com.alian.utils.login.SecurityJwtUtil;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/menu")
public class MenuController {


    @Resource
    private IMenuService menuService;

    @Resource
    private SecurityJwtUtil securityJwtUtil;

    @GetMapping("getMenuList")
    @LoginAuthorization
    public CommonsReturn getMenuList(Page<Menu> page, MenuVo menuVo){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if(menuVo.getParentId() != null){
            queryWrapper.eq("parent_id",menuVo.getParentId());
        }else {
            queryWrapper.eq("level",menuVo.getLevel());

        }
        queryWrapper.eq("hidden",0);
        IPage<Menu> iPage = menuService.page(page, queryWrapper);
        return CommonsReturn.success(iPage);
    }

    @GetMapping("getMenu")
    @LoginAuthorization
    public CommonsReturn getMenu(Integer level){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("hidden",0);
        queryWrapper.eq("level",level);
        List<Menu> list = menuService.list(queryWrapper);
        return CommonsReturn.success(list);
    }

    @GetMapping("getMenuById")
    @LoginAuthorization
    public CommonsReturn getMenuById(Long id){
        Menu menu = menuService.getById(id);
        return CommonsReturn.success(menu);
    }


    @PostMapping("saveOrUpdateMenu")
    @LoginAuthorization
    public CommonsReturn saveOrUpdateMenu(@RequestBody Menu menu){
        if(menu.getParentId() == 0){
            menu.setLevel(0);
        }else {
            menu.setLevel(1);
        }
        menuService.saveOrUpdate(menu);
        return CommonsReturn.success();
    }

    @PutMapping("updateHiddenById")
    @LoginAuthorization
    public CommonsReturn updateHiddenById(Menu menu){
        menuService.updateById(menu);
        return CommonsReturn.success();
    }

    @DeleteMapping("deleteMenuById")
    @LoginAuthorization
    public CommonsReturn deleteMenuById(Long id){
        menuService.removeById(id);
        return CommonsReturn.success();
    }

    /**
     * 获取菜单树
     * @return
     */
    @GetMapping("getTreeList")
    @LoginAuthorization
    public CommonsReturn getTreeList(){
        List<Map<String,Object>> list = menuService.getTreeList();
        return CommonsReturn.success(list);
    }

    @GetMapping("queryMenuList")
    public CommonsReturn queryMenuList(HttpServletRequest request){
        String token = request.getHeader("Authorization-token");
        String userName = securityJwtUtil.getUserName(token);
        List<Map<String,Object>> menuList = menuService.queryMenuList(userName);
        return CommonsReturn.success(menuList);
    }




}
