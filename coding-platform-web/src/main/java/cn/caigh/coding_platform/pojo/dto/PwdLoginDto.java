package cn.caigh.coding_platform.pojo.dto;

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

  public PwdLoginDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return "PwdLoginDto{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
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
}
