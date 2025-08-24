package com.zeeyeh.vortexiaserver.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenProvider {

    // 过期时间（秒）
    @Value("${nyt.auth.token.expiration}")
    private long EXPIRE_TIME;

    // 密钥
    @Value("${nyt.auth.token.secret}")
    private String SECRET;

    /**
     * 创建JWT Token
     * @param payload 载荷（Claims）
     * @return JWT Token
     */
    public String createToken(Map<String, Object> payload) {
        JWTCreator.Builder builder = JWT.create();
        // 添加载荷信息
        payload.forEach((key, value) -> {
            if (value instanceof String) {
                builder.withClaim(key, (String) value);
            } else if (value instanceof Integer) {
                builder.withClaim(key, (Integer) value);
            } else if (value instanceof Long) {
                builder.withClaim(key, (Long) value);
            } else if (value instanceof Boolean) {
                builder.withClaim(key, (Boolean) value);
            } else {
                builder.withClaim(key, value.toString());
            }
        });

        // 设置过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, (int) EXPIRE_TIME);
        builder.withExpiresAt(instance.getTime());

        // 签名并生成token
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证token是否有效（包括过期检查）
     * @param token JWT Token
     * @return 验证结果 true:有效 false:无效
     */
    public boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查token是否过期
     * @param token JWT Token
     * @return true:已过期 false:未过期
     */
    public boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return decodedJWT.getExpiresAt().before(new Date());
        } catch (TokenExpiredException e) {
            return true; // 明确表示token已过期
        } catch (Exception e) {
            return true; // 其他异常也认为token无效
        }
    }

    /**
     * 根据旧token生成新token
     * @param oldToken 旧的JWT Token
     * @return 新的JWT Token
     */
    public String refreshToken(String oldToken) {
        try {
            // 解析旧token获取载荷信息
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(oldToken);

            // 提取载荷信息
            Map<String, Claim> claims = decodedJWT.getClaims();

            // 创建新的载荷map
            Map<String, Object> payload = new HashMap<>();
            claims.forEach((key, claim) -> {
                // 将Claim转换为合适的对象类型
                if (claim.asString() != null) {
                    payload.put(key, claim.asString());
                } else if (claim.asBoolean() != null) {
                    payload.put(key, claim.asBoolean());
                } else if (claim.asInt() != null) {
                    payload.put(key, claim.asInt());
                } else if (claim.asLong() != null) {
                    payload.put(key, claim.asLong());
                } else if (claim.asDouble() != null) {
                    payload.put(key, claim.asDouble());
                } else if (claim.asDate() != null) {
                    payload.put(key, claim.asDate());
                } else {
                    payload.put(key, claim.asString());
                }
            });

            // 生成新token
            return createToken(payload);
        } catch (Exception e) {
            throw new RuntimeException("刷新token失败", e);
        }
    }


    /**
     * 解析token获取载荷信息
     * @param token JWT Token
     * @return DecodedJWT对象
     */
    public DecodedJWT decodeToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * 从token中获取指定claim的值
     * @param token JWT Token
     * @param claimName claim名称
     * @return claim值
     */
    public String getClaim(String token, String claimName) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim(claimName).asString();
    }

    /**
     * 获取token过期时间
     * @param token JWT Token
     * @return 过期时间
     */
    public Date getExpirationDate(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getExpiresAt();
    }

    /**
     * 验证token并返回解码后的信息
     * @param token JWT Token
     * @return DecodedJWT 解码后的token信息
     * @throws Exception 验证失败时抛出异常
     */
    public DecodedJWT verifyAndGetToken(String token) throws Exception {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        } catch (TokenExpiredException e) {
            throw new Exception("Token已过期", e);
        } catch (Exception e) {
            throw new Exception("Token验证失败", e);
        }
    }
}
