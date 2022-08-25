package com.fan.system.config.security;

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
 * @author fgq
 * @date 2021/12/22
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     * @param userDetails 用户信息
     * @return token
     */
    public String genToken(UserDetails userDetails){
        Map<String,Object> claim = new HashMap<>(10);
        claim.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claim.put(CLAIM_KEY_CREATED,new Date());
        return genToken(claim);
    }

    /**
     * 根据token 获取用户名
     * @param token 令牌
     * @return 令牌
     */
    public String getUserNameByToken(String token){
        String username;
        try {
            Claims claims = getClaimByToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否有效
     * @param token 令牌
     * @param userDetails 用户信息
     * @return 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUserNameByToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否失效
     * @param token 令牌
     * @return 是否有效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateByToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     * @param token 令牌
     * @return 过期时间
     */
    private Date getExpiredDateByToken(String token) {
        Claims claims = getClaimByToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否可以被刷新
     * @param token 令牌
     * @return 是否刷新
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token 令牌
     * @return token
     */
    public String refreshToken(String token){
        Claims claims = getClaimByToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return genToken(claims);
    }
    /**
     * 从token中获取荷载 负载
     * @param token 令牌
     * @return 负载
     */
    private Claims getClaimByToken(String token) {
        Claims claims = null;
        try {
           claims = Jwts.parser().setSigningKey(secret)
                   .parseClaimsJws(token)
                   .getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据负载生成 JWT TOKEN
     * @param claim map负载
     * @return 令牌TOKEN
     */
    private String genToken(Map<String, Object> claim) {
        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(genExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 生成token失效时间
     * @return 时间
     */
    public Date genExpirationDate() {
        return new Date(System.currentTimeMillis()+ expiration *1000);
    }
}
