package github.sjroom.admin.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by echisan on 2018/6/23
 */
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";

    /**
     * 5天(毫秒)
     */
    private static final long EXPIRATION_TIME = 432000000;
    /**
     * JWT密码
     */
    private static final String SECRET = "secret";
    /**
     * token
     */
    public static final String HEADER_TOKEN = "token";

    /**
     * 签发JWT
     */
    public static String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, userName);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 获取token是否过期
     */
    public static Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 根据token获取username
     */
    public static String getUsernameFromToken(String token) {
        String username = getClaimsFromToken(token).getSubject();
        return username;
    }

    /**
     * 获取token的过期时间
     */
    public static Date getExpirationDateFromToken(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration;
    }

    /**
     * 解析JWT
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public static void main(String[] args) {
//
//        Claims claims = checkJWT("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTQxOTYzNjMsImlhdCI6MTU5MzU5MTU2Mywicm9sIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJtYW5zb24ifQ.6943y1-wF4PVRmkG_ViLTIj-HQLPg1KkckAFgpM2Aw0");
//        System.out.println("claims:" + claims);
//
//        claims = checkJWT("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTQyMDEyMzAsImlhdCI6MTU5MzU5NjQzMCwicm9sIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJtYW5zb24ifQ.lKsOYDLBB-yt-IvGac0nWoyYwdjxmSGp6S8oEIPJEas");
//        System.out.println("claims:" + claims);

    }

}
