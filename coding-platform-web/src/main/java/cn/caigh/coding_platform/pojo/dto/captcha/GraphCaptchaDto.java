package cn.caigh.coding_platform.pojo.dto.captcha;

import jakarta.validation.constraints.NotBlank;

public class GraphCaptchaDto {
  @NotBlank(message = "图形验证码不能为空")
  private String captchaCode;

  @NotBlank(message = "验证码id不能为空")
  private String captchaId;

  public GraphCaptchaDto() {
  }

  @Override
  public String toString() {
    return "GraphCaptchaDto{" +
        "captchaCode='" + captchaCode + '\'' +
        ", captchaId='" + captchaId + '\'' +
        '}';
  }

  public String getCaptchaCode() {
    return captchaCode;
  }

  public void setCaptchaCode(String captchaCode) {
    this.captchaCode = captchaCode;
  }

  public String getCaptchaId() {
    return captchaId;
  }

  public void setCaptchaId(String captchaId) {
    this.captchaId = captchaId;
  }
}
