package cn.caigh.coding_platform.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
  private static final String SECRET_KEY = "i-am-kyler-tsai-hahahahahahahaha";

  /**
   * 生成token
   *
   * @param uuid uuid
   * @return token
   */
  public static String generateToken(String username, String uuid) {
    return JWT.create()
        .withIssuer(username)
        .withClaim("uuid", uuid)
        .sign(Algorithm.HMAC256(SECRET_KEY));
  }

  /**
   * 验证token
   */
  public static void verifyToken(String token) {
    JWT.require(Algorithm.HMAC256(SECRET_KEY)).build()
        .verify(token);
  }

  /**
   * 解析token
   */
  public static DecodedJWT parseToken(String token) {
    return JWT.decode(token);
  }
}
