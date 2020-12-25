package com.alian.utils.login;


import com.alian.utils.result.CommonsReturn;
import com.alian.utils.result.ReturnCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZL
 */
@Component
public class SecurityJwtUtil {

    @Value("${user.jwtset.signature}")
    private String signature;
    @Value("${user.jwtset.exptime}")
    private Long exptime;
    @Value("${user.jwtset.alg}")
    private String alg;
    @Value("${user.jwtset.type}")
    private String type;

    public String createToken(UserDetails userDetails){

        //头部信息
        Map<String,Object> headerMap = new HashMap<>();
        headerMap.put("alg",alg);
        headerMap.put("type",type);

        Map<String,Object> payloadMap = new HashMap<>();
        payloadMap.put("username",userDetails.getUsername());

        long startTime = System.currentTimeMillis();
        Long expTime = startTime+exptime;

        String token = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(payloadMap)
                .setExpiration(new Date(expTime))
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return token;

    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserName(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signature)
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("username");
        }catch (Exception e){
            return null;
        }
    }


    public CommonsReturn authToken(String token){

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signature)
                    .parseClaimsJws(token)
                    .getBody();
            return CommonsReturn.success(claims);
        }catch (Exception e){
            return CommonsReturn.error(ReturnCode.login_disable);
        }



    }






}
