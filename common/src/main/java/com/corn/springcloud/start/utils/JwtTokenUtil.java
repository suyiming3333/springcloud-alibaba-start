package com.corn.springcloud.start.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {
    // Token请求头
    public static final String TOKEN_HEADER = "Authorization";
    // Token前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    // 签名主题
    public static final String SUBJECT = "piconjo";
    // 过期时间
    public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
    // 应用密钥
    public static final String APPSECRET_KEY = "aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrsssttt";
    // 角色权限声明
    private static final String ROLE_CLAIMS = "role";

    /**
     * 生成Token
     */
    public static String createToken(String username,String role) {
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);

        byte[] keyBytes = APPSECRET_KEY.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setClaims(map)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    /**
     * 校验Token
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从Token中获取username
     */
    public static String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 从Token中获取用户角色
     */
    public static String getUserRole(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
        return claims.get("role").toString();
    }

    /**
     * 校验Token是否过期
     */
    public static boolean isExpiration(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiW3VzZXI6bWFuYWdlXSIsInVzZXJuYW1lIjoie1wiZnVsbG5hbWVcIjpcIuiLj-S6pumTrVwiLFwiaWRcIjoxLFwicGFzc3dvcmRcIjpcIiQyYSQxMCR3RkExNGxHS2pQdWZmQVN0OE8vY1BPeTF2Rm01YWdYemRaR0xNbi9uZW5waFlRUkhhVnlkdVwiLFwidXNlcm5hbWVcIjpcInN1eWltaW5nXCJ9IiwiaWF0IjoxNjEyMzYyNTk3LCJleHAiOjE2MTI5NjczOTd9.enLBeNRIvLNVLycMOox0CrQuRY7BcgYhMeHMxyNhQM0";
        Claims claims = checkJWT(token);
        System.out.println(1);
    }
}
