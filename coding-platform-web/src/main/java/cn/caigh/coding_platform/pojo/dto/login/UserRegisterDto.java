package cn.caigh.coding_platform.pojo.dto.login;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户注册 dto
 */
public class UserRegisterDto {
  @NotBlank(message = "请输入用户名")
  private String username;

  @NotBlank(message = "请输入密码")
  private String password;

  @NotBlank(message = "请输入确认密码")
  private String passwordConfirm;

  @NotBlank(message = "图形验证码不能为空")
  private String captchaCode;

  @NotBlank(message = "验证码id不能为空")
  private String captchaId;

  public UserRegisterDto(String username, String password, String captchaCode, String passwordConfirm, String captchaId) {
    this.username = username;
    this.password = password;
    this.captchaCode = captchaCode;
    this.passwordConfirm = passwordConfirm;
    this.captchaId = captchaId;
  }

  @Override
  public String toString() {
    return "UserRegisterDto{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", passwordConfirm='" + passwordConfirm + '\'' +
        ", captchaCode='" + captchaCode + '\'' +
        ", captchaId='" + captchaId + '\'' +
        '}';
  }

  public @NotBlank(message = "请输入用户名") String getUsername() {
    return username;
  }

  public void setUsername(@NotBlank(message = "请输入用户名") String username) {
    this.username = username;
  }

  public @NotBlank(message = "请输入密码") String getPassword() {
    return password;
  }

  public void setPassword(@NotBlank(message = "请输入密码") String password) {
    this.password = password;
  }

  public @NotBlank(message = "图形验证码不能为空") String getCaptchaCode() {
    return captchaCode;
  }

  public void setCaptchaCode(@NotBlank(message = "图形验证码不能为空") String captchaCode) {
    this.captchaCode = captchaCode;
  }

  public @NotBlank(message = "请输入确认密码") String getPasswordConfirm() {
    return passwordConfirm;
  }

  public void setPasswordConfirm(@NotBlank(message = "请输入确认密码") String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }

  public @NotBlank(message = "验证码id不能为空") String getCaptchaId() {
    return captchaId;
  }

  public void setCaptchaId(@NotBlank(message = "验证码id不能为空") String captchaId) {
    this.captchaId = captchaId;
  }
}
