package cn.caigh.coding_platform.pojo.vo.login;

/**
 * 图形验证码的返回数据
 */
public class CaptchaVo {
  private String captcha;
  private String captchaId;

  public CaptchaVo() {
  }

  public CaptchaVo(String captcha, String captchaId) {
    this.captcha = captcha;
    this.captchaId = captchaId;
  }

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  public String getCaptchaId() {
    return captchaId;
  }

  public void setCaptchaId(String captchaId) {
    this.captchaId = captchaId;
  }
}
