package com.alian.config.security.filter;

import com.alian.utils.login.SecurityJwtUtil;
import com.alian.utils.result.CommonsReturn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author ZZL
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {



    @Resource
    private SecurityJwtUtil securityJwtUtil;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private RedisTemplate redisTemplate;

    private static final String PRE_TOKEN = "ACCESS_TOKEN:";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token值
        String token = request.getHeader("Authorization-token");
        if(StringUtils.isNotBlank(token)){
            CommonsReturn commonsReturn = securityJwtUtil.authToken(token);
            if(commonsReturn.getCode() == 200 && SecurityContextHolder.getContext().getAuthentication() == null){
                String userName = securityJwtUtil.getUserName(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                String accessKey = PRE_TOKEN+userDetails.getUsername()+":"+token;
                if(redisTemplate.hasKey(accessKey)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //让对象实现上下文传递共享
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    long currentTime = System.currentTimeMillis();
                    //续签
                    //将jwt值放入redis缓存中
                    redisTemplate.opsForValue().set(accessKey,currentTime);
                    //设置失效时间
                    redisTemplate.expire(accessKey,2, TimeUnit.HOURS);

                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
