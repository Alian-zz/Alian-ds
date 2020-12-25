/*
package com.alian.utils.login;


import com.alian.ums.entity.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.alian.utils.result.CommonsReturn;
import com.alian.utils.result.ReturnCode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author ZZL
 *//*

@Component
public class LoginJwtUtil {

    @Value("${user.jwtset.signature}")
    private String signature;
    @Value("${user.jwtset.exptime}")
    private Long exptime;
    @Value("${user.jwtset.alg}")
    private String alg;
    @Value("${user.jwtset.type}")
    private String type;

    public String createToken(Admin admin){

        //头部信息
        Map<String,Object> headerMap = new HashMap<>();
        headerMap.put("alg",alg);
        headerMap.put("type",type);

        Map<String,Object> payloadMap = new HashMap<>();
        payloadMap.put("id",admin.getId());
        payloadMap.put("username",admin.getUsername());

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
*/
