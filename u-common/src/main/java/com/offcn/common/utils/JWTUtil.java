package com.offcn.common.utils;

import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtil {
    private static final String SECRET_KEY = "u-member-offcn123"; //秘钥
    public static final long TOKEN_EXPIRE_TIME = 48 * 60 * 60 * 1000; //token过期时间
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 45 * 60 * 60 * 1000; //refreshToken过期时间
    private static final String ISSUER = "offcn"; //签发人

    //生成jwt令牌
    public static String generateToken(String username){
        Date now = new Date();
        return   Jwts.builder()
                .setIssuer(ISSUER) //签发人
                .setIssuedAt(now)//签发时间
                .setExpiration(new Date(now.getTime()+TOKEN_EXPIRE_TIME))//过期时间
                //载荷
                .claim("username",username)//用户名
                //加密算法
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)//签名算法、秘钥
                .compact();
    }

    //解析token
    private static Claims parseToken(String token){
        return    Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
    }

    //验证签名
    public static boolean verify(String token){
        //尝试调用解析令牌方法，如果没有异常，解析成功，返回true
        try {
            Claims claims = parseToken(token);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("签名验证失败");
            return false;
        }
    }

    //从token获取用户名
    public static String getUsername(String token){
        try {
            //调用解析令牌方法，获取载荷
            Claims claims = parseToken(token);
            //从载荷获取用户名
            String username = (String) claims.get("username");
            return username;
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("解析令牌失败");
            return "";
        }
    }
}