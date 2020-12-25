/*
package com.alian.config.logAuthorization;



import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.alian.utils.login.LoginJwtUtil;
import com.alian.utils.result.CommonsReturn;
import com.alian.utils.result.ReturnCode;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

*/
/**
 * @author ZZL
 *//*

@Aspect
@Component
@Order(4)
public class LoginAuthorizationAop {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginJwtUtil loginJwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String PRE_TOKEN = "ACCESS_TOKEN:";

    //@Around(value = "execution(* com.alian.*.controller..*.*(..)) && @annotation(loginAuthorization)")
    public Object loginAround(ProceedingJoinPoint joinPoint, LoginAuthorization loginAuthorization) throws Throwable {

        String token = request.getHeader("Authorization-token");
        if(StringUtils.isBlank(token)){
            return CommonsReturn.error(ReturnCode.login_disable);
        }

        CommonsReturn commonsReturn = loginJwtUtil.authToken(token);
        if(commonsReturn.getCode() != 200){
            return commonsReturn;
        }

        //获取用户信息
        Claims claims = (Claims) commonsReturn.getData();
        String userId = String.valueOf(claims.get("id"));
        String accessKey = PRE_TOKEN+userId+":"+token;
        if(!redisTemplate.hasKey(accessKey)){
            return CommonsReturn.error(ReturnCode.login_disable);
        }

        Long currentTime = System.currentTimeMillis();
        //续签
        redisTemplate.opsForValue().set(accessKey,currentTime);
        redisTemplate.expire(accessKey,2, TimeUnit.HOURS);

        Object obj;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }
        return obj;
    }



}
*/
