package cn.caigh.coding_platform.pojo.dto.login;

import jakarta.validation.constraints.NotBlank;

public class PwdLoginDto {
  /**
   * 用户名：以手机号作为用户名登录
   */
  @NotBlank(message = "用户名不能为空")
  private String username;

  /**
   * 用户密码
   */
  @NotBlank(message = "密码不能为空")
  private String password;

  @NotBlank(message = "图形验证码不能为空")
  private String captchaCode;

  @NotBlank(message = "验证码id不能为空")
  private String captchaId;

  public PwdLoginDto(String username, String password, String captchaId, String captchaCode) {
    this.username = username;
    this.password = password;
    this.captchaId = captchaId;
    this.captchaCode = captchaCode;
  }

  @Override
  public String toString() {
    return "PwdLoginDto{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", captchaCode='" + captchaCode + '\'' +
        ", captchaId='" + captchaId + '\'' +
        '}';
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCaptchaId() {
    return captchaId;
  }

  public void setCaptchaId(String captchaId) {
    this.captchaId = captchaId;
  }

  public String getCaptchaCode() {
    return captchaCode;
  }

  public void setCaptchaCode(String captchaCode) {
    this.captchaCode = captchaCode;
  }
}
