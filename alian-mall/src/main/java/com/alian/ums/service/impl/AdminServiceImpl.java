package com.alian.ums.service.impl;

import com.alian.ums.entity.Admin;
import com.alian.ums.entity.Role;
import com.alian.ums.entity.UmsResource;
import com.alian.ums.mapper.AdminMapper;
import com.alian.ums.service.IAdminService;
import com.alian.utils.login.SecurityJwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SecurityJwtUtil securityJwtUtil;

    @Resource
    private RedisTemplate redisTemplate;

    private static final String PRE_TOKEN = "ACCESS_TOKEN:";

    private static final String LOGIN_ADMIN = "LOGIN:";


    @Override
    public List<UmsResource> queryResourceByAdmin(Long id,String username) {
        String adminKey = LOGIN_ADMIN+username+"Resource";
        List<UmsResource> list  = (List<UmsResource>) redisTemplate.opsForValue().get(adminKey);
        if(list != null){
            return list;
        }
        List<UmsResource> resourceList = adminMapper.queryResourceByAdmin(id);
        redisTemplate.opsForValue().set(adminKey,resourceList);
        redisTemplate.expire(adminKey,2,TimeUnit.HOURS);
        return resourceList;
    }

    @Override
    public List<Role> queryRoleByAdmin(Long id,String username) {
        String adminKey = LOGIN_ADMIN+username+"ROLE";
        List<Role> list = (List<Role>) redisTemplate.opsForValue().get(adminKey);
        if(list != null){
            return list;
        }

        List<Role> roleList = adminMapper.queryRoleByAdmin(id);
        redisTemplate.opsForValue().set(adminKey,roleList);
        redisTemplate.expire(adminKey,2,TimeUnit.HOURS);
        return roleList;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            clearLoginInfo(username);
            //调用登录认证方法
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("用户名或密码不正确");
            }
            //创建UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = securityJwtUtil.createToken(userDetails);
            Long currentTime = System.currentTimeMillis();
            String accessKey = PRE_TOKEN + userDetails.getUsername() + ":" + token;
            redisTemplate.opsForValue().set(accessKey, currentTime);
            redisTemplate.expire(accessKey, 30, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return token;
    }




    public void clearLoginInfo(String username){
        String tokeyKey = PRE_TOKEN+username+":*";
        Set<String> keys = redisTemplate.keys(tokeyKey);
        if(!keys.isEmpty()){
            redisTemplate.delete(keys);
        }

        String adminKey = LOGIN_ADMIN+username+":";
        Set<String> keys1= redisTemplate.keys(adminKey);
        if(!keys.isEmpty()){
            redisTemplate.delete(keys1);
        }

    }

    @Override
    public Admin queryOne(String username) {
        String adminKey = LOGIN_ADMIN+username+"ADMIN";
        Admin admin1 = (Admin) redisTemplate.opsForValue().get(adminKey);
        if(admin1 != null){
            return admin1;
        }
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Admin admin = super.getOne(queryWrapper);
        redisTemplate.opsForValue().set(adminKey,admin);
        redisTemplate.expire(adminKey,2,TimeUnit.HOURS);
        return admin;
    }


}
