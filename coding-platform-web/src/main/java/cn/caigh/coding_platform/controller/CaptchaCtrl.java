package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.vo.login.CaptchaVerifyVo;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVo;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.CaptchaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class CaptchaCtrl {
  @Value("${server.port}")
  private String env;

  @Autowired
  private CaptchaService captchaService;

  /**
   * 循环填充密钥至 32 字节
   *
   * @param appSecretKey 原始密钥
   * @return 32 字节密钥
   */
  public static byte[] padKey(byte[] appSecretKey) {
    if (appSecretKey.length >= 32) {
      return appSecretKey;
    }
    byte[] key = new byte[32];
    int srcPos = 0;
    for (int i = 0; i < 32; i++) {
      key[i] = appSecretKey[srcPos];
      srcPos++;
      if (srcPos >= appSecretKey.length) {
        srcPos = 0;
      }
    }
    return key;
  }

  /**
   * AES CBC 加密
   *
   * @param plaintext 明文
   * @param key       密钥（32字节）
   * @param iv        初始化向量（16字节）
   * @return Base64(IV + 密文)
   */
  public static String encrypt(String plaintext, byte[] key, byte[] iv) throws Exception {
    // 创建 AES 密钥规格
    SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
    // 创建 IV 参数
    IvParameterSpec ivSpec = new IvParameterSpec(iv);

    // 创建 Cipher 实例，使用 AES/CBC/PKCS5Padding（等同于 PKCS7Padding）
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
    // 加密
    byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
    // 拼接 IV + 密文
    byte[] ivAndCiphertext = new byte[iv.length + encrypted.length];
    System.arraycopy(iv, 0, ivAndCiphertext, 0, iv.length);
    System.arraycopy(encrypted, 0, ivAndCiphertext, iv.length, encrypted.length);
    // Base64 编码
    return Base64.getEncoder().encodeToString(ivAndCiphertext);
  }


  /**
   * 获取加密后的 CaptchaAppid
   */
  @GetMapping(value = "/encryptappid.json")
  public ResultVo<String> encryptAppid() throws Exception {
    // 客户从控制台获取对应验证码账号下的 AppSecretKey，25位
    String appSecretKeyStr = "DUhOXWubHcgMIk4UvApAFjHb8";
    byte[] appSecretKey = appSecretKeyStr.getBytes(StandardCharsets.UTF_8);
    // 密钥填充至 32 字节
    byte[] key = padKey(appSecretKey);
    // 业务参数
    String captchaAppid = "194520119";
    long curTime = (System.currentTimeMillis() / 1000) - 10;  // 当前时间戳（秒）
    long expireTime = 86400L;    // 过期时间（秒），最大 86400
    System.out.println("curTime: " + curTime);
    // 构造明文：CaptchaAppid&时间戳&过期时间
    String plaintext = captchaAppid + "&" + curTime + "&" + expireTime;
    // IV（16字节随机数，示例使用固定值，生产环境请使用安全随机数）
    String ivStr = "0123456789012345";
    byte[] iv = ivStr.getBytes(StandardCharsets.UTF_8);
    // 加密
    String ciphertext = encrypt(plaintext, key, iv);
    System.out.println("Plaintext: " + plaintext);
    System.out.println("Ciphertext (Base64): " + ciphertext);
    return ResultVo.success(ciphertext);
  }

  @GetMapping(value = "/verifyAppid.json")
  public ResultVo<String> verifyAppid() {
    return ResultVo.success(env);
  }

  /**
   * 生成验证码
   */
  @GetMapping(value = "/captcha.json")
  public ResultVo<CaptchaVo> generateCaptcha() {
    return captchaService.generateCaptcha();
  }

  /**
   * 图形验证码验证
   */
  @PostMapping("/captcha/verify.json")
  public ResultVo<CaptchaVerifyVo> verifyCaptcha(String captchaId, String code) {
    CaptchaVerifyVo captchaVerifyVo = captchaService.verifyCaptcha(captchaId, code);
    return ResultVo.success(captchaVerifyVo);
  }
}
