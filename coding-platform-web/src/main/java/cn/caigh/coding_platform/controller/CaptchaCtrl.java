package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.dto.captcha.GraphCaptchaDto;
import cn.caigh.coding_platform.pojo.dto.captcha.TencentAppidVerifyDto;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVerifyVo;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVo;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.CaptchaService;

import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class CaptchaCtrl {
  @Value("${env}")
  private String env;

  @Value("${tencent.captchaAppid}")
  private Long captchaAppid;

  @Value("${tencent.appSecretKey}")
  private String appSecretKey;

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
  @GetMapping(value = "/tencent/encryptappid.json")
  public ResultVo<String> encryptTencentAppid() throws Exception {
    // 客户从控制台获取对应验证码账号下的 AppSecretKey，25位
    byte[] appSecretKeyBytes = appSecretKey.getBytes(StandardCharsets.UTF_8);
    // 密钥填充至 32 字节
    byte[] key = padKey(appSecretKeyBytes);
    // 业务参数
    long curTime = (System.currentTimeMillis() / 1000) - 10;  // 当前时间戳（秒）
    long expireTime = 86400L;    // 过期时间（秒），最大 86400
    // 构造明文：CaptchaAppid&时间戳&过期时间
    String plaintext = Long.parseLong(String.valueOf(captchaAppid)) + "&" + curTime + "&" + expireTime;
    // IV（16字节随机数，示例使用固定值，生产环境请使用安全随机数）
    byte[] iv = new byte[16];
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(iv);
    // 加密
    String ciphertext = encrypt(plaintext, key, iv);
    return ResultVo.success(ciphertext);
  }

  @GetMapping(value = "/tencent/verifyappid.json")
  public ResultVo<String> verifyTencentAppid(@RequestBody @Valid TencentAppidVerifyDto tencentAppidVerifyDto) {
    Credential cred = new Credential("SecretId", "SecretKey");
    CaptchaClient client = new CaptchaClient(cred, "");
    // 2. 创建请求对象
    DescribeCaptchaResultRequest req = new DescribeCaptchaResultRequest();
    req.setCaptchaType(9L);
    req.setTicket(tencentAppidVerifyDto.getTicket());
    req.setRandstr(tencentAppidVerifyDto.getRandstr());
    req.setCaptchaAppId(Long.parseLong(String.valueOf(captchaAppid)));
    req.setAppSecretKey(appSecretKey);
    // 3. 发起请求并处理响应
    try {
      DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);
      // 判断结果：CaptchaCode为1表示验证通过
      if (resp.getCaptchaCode() == 1) {
        // 验证通过，继续处理你的业务逻辑
        ResultVo.success(null);
      } else {
        // 验证失败，可以根据错误码进行相应处理
        return ResultVo.failed("验证失败，错误码：" + resp.getCaptchaCode());
      }
    } catch (TencentCloudSDKException e) {
      e.printStackTrace();
      return ResultVo.failed("腾讯调用失败验证失败");
    }
    return ResultVo.success(null);
  }

  /**
   * 生成图形验证码
   */
  @GetMapping(value = "/graph/captcha.json")
  public ResultVo<CaptchaVo> generateGraphCaptcha() {
    return captchaService.generateGraphCaptcha();
  }

  /**
   * 验证图形验证码
   */
  @PostMapping("/graph/captcha/verify.json")
  public ResultVo<CaptchaVerifyVo> verifyGraphCaptcha(@RequestBody @Valid GraphCaptchaDto graphCaptchaDto) {
    CaptchaVerifyVo captchaVerifyVo = captchaService.verifyGraphCaptcha(graphCaptchaDto.getCaptchaId(), graphCaptchaDto.getCaptchaCode());
    return ResultVo.success(captchaVerifyVo);
  }
}
