package com.alian.ums.controller;


import com.alian.ums.entity.MemberLevel;
import com.alian.ums.service.IMemberLevelService;
import com.alian.utils.result.CommonsReturn;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 会员等级表 前端控制器
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/ums/memberLevel")
public class MemberLevelController {

    @Resource
    private IMemberLevelService memberLevelService;

    @GetMapping("queryMemberLevelList")
    public CommonsReturn queryMemberLevelList(Integer defaultStatus){
        QueryWrapper<MemberLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("default_status",defaultStatus);
        List<MemberLevel> list = memberLevelService.list(queryWrapper);
        return CommonsReturn.success(list);
    }

}
