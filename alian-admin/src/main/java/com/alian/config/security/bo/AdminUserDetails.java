package com.alian.config.security.bo;

import com.alian.ums.entity.Admin;
import com.alian.ums.entity.Role;
import com.alian.ums.entity.UmsResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ZZL
 */
public class AdminUserDetails implements UserDetails {

    private Admin admin;

    private List<UmsResource> resourceList;

    private List<Role> roleList;

    public AdminUserDetails(Admin admin,List<UmsResource> resourceList,List<Role> roleList){
        this.admin = admin;
        this.resourceList = resourceList;
        this.roleList = roleList;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        resourceList.forEach(resource -> list.add(new SimpleGrantedAuthority(resource.getKeyword())));
        roleList.forEach(role -> list.add(new SimpleGrantedAuthority("ROLE_"+role.getKeyword())));
        return list;
    }

    /**
     * 获取用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    /**
     * 获取用户名
     * @return
     */
    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getStatus().equals(1);
    }
}

