package cn.caigh.coding_platform.constants;

/**
 * 使用到的 Redis Key 枚举值
 */
public enum RedisKey {
  /**
   * 项目 ID 前缀
   */
  AppId("coding:platform"),
  /**
   * token key
   */
  Token("token"),
  /**
   * 存储用户信息的key
   */
  UserKey("cache:user"),
  /**
   * 图形验证码Key
   */
  Captcha("captcha");

  private final String key;

  RedisKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public static String getTokenKey(String username) {
    return RedisKey.AppId.getKey() + ":" + RedisKey.Token.getKey() + ":" + username;
  }

  public static String getUserKey(String uuid) {
    return RedisKey.AppId.getKey() + ":" + RedisKey.UserKey.getKey() + ":" + uuid;
  }

  public static String getCaptchaKey(String uuid) {
    return RedisKey.Captcha.getKey() + ":" + uuid;
  }
}
