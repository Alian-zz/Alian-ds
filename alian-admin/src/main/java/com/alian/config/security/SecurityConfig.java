package com.alian.config.security;

import com.alian.config.security.bo.AdminUserDetails;
import com.alian.config.security.filter.JwtAuthenticationTokenFilter;
import com.alian.config.security.filter.RestAuthenticationEntryPoint;
import com.alian.config.security.filter.RestfulAccessDeniedHandler;
import com.alian.ums.entity.Admin;
import com.alian.ums.entity.Role;
import com.alian.ums.entity.UmsResource;
import com.alian.ums.service.IAdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author ZZL
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private IAdminService adminService;

    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-resources/**"
                ).permitAll()
                .antMatchers("/login/**", "/uploadPhoto").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
        //禁用缓存
        http.headers().cacheControl();
        /*添加一个Filter到XXXFilter之前，放在这里就是把JwtAuthenticationTokenFilter放在
        UsernamePasswordAuthenticationFilter之前，因为filter的执行也是有顺序的，
        我们必须要把我们的filter放在UsernamePasswordAuthenticationFilter之前才会起到自动认证的效果。*/
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     * 用户登录认证和权限授权
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }


    /**
     * 密码校验和密码加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 验证身份
     *
     * @return
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminService.queryOne(username);
            if (admin != null) {
                //获取用户拥有的权限
                List<UmsResource> resourceList = adminService.queryResourceByAdmin(admin.getId(), username);
                //获取用户拥有的角色
                List<Role> roleList = adminService.queryRoleByAdmin(admin.getId(), username);
                return new AdminUserDetails(admin, resourceList, roleList);
            }
            //如果用户对象为空，抛出用户名不存在异常
            throw new UsernameNotFoundException("用户名不存在");

        };
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
