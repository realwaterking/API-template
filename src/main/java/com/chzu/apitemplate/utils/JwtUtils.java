package com.chzu.apitemplate.utils;

import com.chzu.apitemplate.model.dto.user.UserLoginRequset;
import com.chzu.apitemplate.model.entity.User;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtUtils {

    private static final String SECRET_KEY = "RcMmZK1ZkwCgwE7p3fWHuGJIUSlQaP";
    private static final long EXPIRATION_TIME = 1000*3600*24; // 1 days

    public String generateToken(UserLoginRequset userLoginRequset) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("id", user.getId());
        claims.put("phone", userLoginRequset.getPhone());

       Date date = new Date(System.currentTimeMillis());
        System.out.println(date.getTime());
        System.out.println(date.toInstant());
        System.out.println(date.getDate());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public User parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        User user = new User();
        user.setId(Long.valueOf(String.valueOf(claims.get("id"))));
        user.setPhone(String.valueOf(claims.get("phone")));
        return user;
    }

    public boolean validateToken(String token, RedisTemplate redisTemplate) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            //token 过期
            return false;
        } catch (JwtException e) {
            //token 不合法
            return false;
        }
        String phone = (String) claims.get("phone");
        String tokenKey = "TOKEN_" + phone;
        String tokenFromRedis =  String.valueOf(redisTemplate.opsForValue().get(tokenKey));

        if (StringUtils.isEmpty(tokenFromRedis)) {
            //redis中没有对应的Token，拒绝请求
            return false;
        }
        return StringUtils.equals(tokenFromRedis, token);
    }
}
