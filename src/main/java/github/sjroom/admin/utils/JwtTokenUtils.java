package github.sjroom.admin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by echisan on 2018/6/23
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Token";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String SUBJECT = "congge";

    public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;

    public static final String APPSECRET_KEY = "congge_secret";

    private static final String ROLE_CLAIMS = "rol";

    /**
     * 生成token
     *
     * @param username
     * @param role
     * @return
     */
    public static String createToken(String username, String role) {

        Map<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setClaims(map)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();

        return token;
    }

    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 获取用户角色
     *
     * @param token
     * @return
     */
    public static String getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("rol").toString();
    }

    /**
     * 是否过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(APPSECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static void main(String[] args) {

        Claims claims = checkJWT("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTQxOTYzNjMsImlhdCI6MTU5MzU5MTU2Mywicm9sIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJtYW5zb24ifQ.6943y1-wF4PVRmkG_ViLTIj-HQLPg1KkckAFgpM2Aw0");
        System.out.println("claims:" + claims);

        claims = checkJWT("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTQyMDEyMzAsImlhdCI6MTU5MzU5NjQzMCwicm9sIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJtYW5zb24ifQ.lKsOYDLBB-yt-IvGac0nWoyYwdjxmSGp6S8oEIPJEas");
        System.out.println("claims:" + claims);

    }

}
