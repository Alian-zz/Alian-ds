package com.alian.login.controller;

import com.alian.ums.service.IAdminService;
import com.alian.utils.result.CommonsReturn;
import com.alian.utils.result.ReturnCode;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zhangzhilian
 */
@RestController
@RequestMapping("/login")
@Api(tags = "LoginController")
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @PostMapping
    public CommonsReturn goLogin(String username, String password){

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return CommonsReturn.error(ReturnCode.userName_password_null);
        }

        String token = adminService.login(username, password);
        if(token == null){
            return CommonsReturn.error(ReturnCode.login_error);
        }

        return CommonsReturn.success(token);
    }



}

